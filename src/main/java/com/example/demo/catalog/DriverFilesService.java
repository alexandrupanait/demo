package com.example.demo.catalog;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;

/**
 * Lists driver/firmware files for a product from the real, live FTP server
 * (ftp.ral.ro) - mirrors the legacy Product#get_files/#drivers_path, which
 * did the same live anonymous FTP listing and silently returned nothing on
 * any failure. This is a real external dependency outside our control, so
 * every failure mode (unreachable host, missing directory, timeout) just
 * degrades to an empty list rather than breaking the product page.
 */
@Service
public class DriverFilesService {

    private static final String FTP_HOST = "ftp.ral.ro";
    private static final int TIMEOUT_MILLIS = 5000;

    public List<String> listDriverFiles(String codProducator, String cod) {
        if (codProducator == null || codProducator.isBlank() || cod == null || cod.isBlank()) {
            return List.of();
        }
        String path = driversPath(codProducator, cod);
        FTPClient ftp = new FTPClient();
        try {
            ftp.setConnectTimeout(TIMEOUT_MILLIS);
            ftp.setDefaultTimeout(TIMEOUT_MILLIS);
            ftp.connect(FTP_HOST);
            ftp.setSoTimeout(TIMEOUT_MILLIS);
            if (!ftp.login("anonymous", "") || !ftp.changeWorkingDirectory(path)) {
                return List.of();
            }
            ftp.enterLocalPassiveMode();
            String[] names = ftp.listNames();
            return names == null ? List.of() : Arrays.asList(names);
        } catch (IOException e) {
            return List.of();
        } finally {
            disconnectQuietly(ftp);
        }
    }

    public String driversPath(String codProducator, String cod) {
        return "FTP/" + codProducator.split(" ")[0] + "/" + cod;
    }

    public String downloadUrl(String codProducator, String cod, String filename) {
        return "ftp://" + FTP_HOST + "/" + driversPath(codProducator, cod) + "/" + filename;
    }

    private void disconnectQuietly(FTPClient ftp) {
        try {
            if (ftp.isConnected()) {
                ftp.logout();
                ftp.disconnect();
            }
        } catch (IOException ignored) {
        }
    }
}
