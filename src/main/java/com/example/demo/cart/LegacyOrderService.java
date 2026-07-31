package com.example.demo.cart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.account.ClientAccount;

/**
 * Places a real order through the same table chain the legacy Ruby checkout
 * used: jurnalvanzari_web (invoice) -&gt; cursuri_facturi_web (per-invoice
 * exchange rates) -&gt; continutfacturi_web (invoice lines), then
 * comenzi_web -&gt; continut_comenzi_web (the order itself, a legacy-adapter
 * copy of the invoice). Mirrors Invoice#before_create_legacy_adapter /
 * #after_create / LegacyComandaWeb#init.
 *
 * Deliberately simplified vs. the original: no green-stamp eco-tax, no
 * weight-based transport lookup, flat transport fee - same simplification
 * already chosen for the Phase 4 pricing model. This writes real rows into
 * production tables that sync into Axapta (confirmed and accepted by the
 * user) - not test-isolated data.
 */
@Service
public class LegacyOrderService {

    private static final BigDecimal VAT_RATE = new BigDecimal("0.21");
    private static final BigDecimal COTA_TVA_PROCENT = new BigDecimal("21");
    private static final int IDVALUTA_RON = 1;
    private static final int IDVALUTA_USD = 2;
    private static final int IDVALUTA_EUR = 3;
    private static final String TRANSPORT_COD = "NEMOEXPRES";

    private final JurnalvanzariWebRepository jurnalvanzariWebRepository;
    private final CursFacturaWebRepository cursFacturaWebRepository;
    private final ContinutFacturaWebRepository continutFacturaWebRepository;
    private final ComandaWebRepository comandaWebRepository;
    private final ContinutComandaWebRepository continutComandaWebRepository;
    private final FinanciarRateRepository financiarRateRepository;

    public LegacyOrderService(JurnalvanzariWebRepository jurnalvanzariWebRepository,
            CursFacturaWebRepository cursFacturaWebRepository,
            ContinutFacturaWebRepository continutFacturaWebRepository,
            ComandaWebRepository comandaWebRepository,
            ContinutComandaWebRepository continutComandaWebRepository,
            FinanciarRateRepository financiarRateRepository) {
        this.jurnalvanzariWebRepository = jurnalvanzariWebRepository;
        this.cursFacturaWebRepository = cursFacturaWebRepository;
        this.continutFacturaWebRepository = continutFacturaWebRepository;
        this.comandaWebRepository = comandaWebRepository;
        this.continutComandaWebRepository = continutComandaWebRepository;
        this.financiarRateRepository = financiarRateRepository;
    }

    @Transactional
    public ComandaPlasata plaseazaComanda(ClientAccount cont, Integer idAutor, String numePersoana, String emailClient,
            String adresaLivrare, String modPlataUi, List<CartLine> linii, BigDecimal transportFaraTva) {

        BigDecimal cursUsd = rate("COPUSD");
        BigDecimal cursEur = rate("COPEUR");

        BigDecimal transportTva = transportFaraTva.multiply(VAT_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal transportCuTva = transportFaraTva.add(transportTva);

        BigDecimal baza = transportFaraTva;
        BigDecimal tva = transportTva;
        for (CartLine linie : linii) {
            baza = baza.add(linie.getSubtotal());
            tva = tva.add(linie.getSubtotal().multiply(VAT_RATE).setScale(2, RoundingMode.HALF_UP));
        }
        BigDecimal total = baza.add(tva);

        boolean ramburs = "ramburs".equals(modPlataUi);
        String modPlataRaw = ramburs ? "ramburs" : "la termen";
        String modPlataAx = ramburs ? "RAMBURS" : "OP";

        JurnalvanzariWeb factura = new JurnalvanzariWeb();
        factura.setData(OffsetDateTime.now());
        factura.setIdClient(cont.getId());
        factura.setCumparator(cont.getFirma());
        factura.setCodFiscal(cont.getCodFiscal());
        factura.setLocalitate(cont.getSediuLocalitate());
        factura.setModPlata(modPlataRaw);
        factura.setCurs(cursUsd);
        factura.setTva(tva);
        factura.setBaza(baza);
        factura.setTotal(total);
        factura.setTransport(TRANSPORT_COD);
        factura.setOnline(true);
        factura.setIdAutor(idAutor);
        factura.setRamburs(ramburs);
        factura.setZileTp(0);
        factura.setInfoLivrare(adresaLivrare);
        factura.setEmailConfirmare(emailClient);
        factura = jurnalvanzariWebRepository.save(factura);

        cursFacturaWebRepository.save(new CursFacturaWeb(factura.getNumar(), IDVALUTA_USD, cursUsd));
        cursFacturaWebRepository.save(new CursFacturaWeb(factura.getNumar(), IDVALUTA_EUR, cursEur));

        for (CartLine linie : linii) {
            ContinutFacturaWeb linieFactura = new ContinutFacturaWeb();
            linieFactura.setFactNr(factura.getNumar());
            linieFactura.setProdus(linie.getProdus().getNumeAfisat());
            linieFactura.setSvid(linie.getProdus().getId());
            linieFactura.setPu(linie.getPretUnitar());
            linieFactura.setPuFinal(linie.getPretUnitar());
            linieFactura.setPuLista(linie.getPretUnitar());
            linieFactura.setIdValutaPretLista(IDVALUTA_RON);
            linieFactura.setIdValuta(IDVALUTA_RON);
            linieFactura.setCursPu(BigDecimal.ONE);
            linieFactura.setValoare(linie.getSubtotal());
            linieFactura.setValoareTva(linie.getSubtotal().multiply(VAT_RATE).setScale(2, RoundingMode.HALF_UP));
            linieFactura.setGarantie(linie.getProdus().getGarantie());
            linieFactura.setCotaTva(COTA_TVA_PROCENT);
            linieFactura.setPonderePercent(BigDecimal.ZERO);
            continutFacturaWebRepository.save(linieFactura);
        }

        ComandaWeb comanda = new ComandaWeb();
        comanda.setIdClient(cont.getId());
        comanda.setFirma(cont.getFirma());
        comanda.setCf(cont.getCodFiscal());
        comanda.setLocalitatea(cont.getSediuLocalitate());
        comanda.setPersoana(numePersoana);
        comanda.setCostTransport(transportCuTva);
        comanda.setTransport(TRANSPORT_COD);
        comanda.setCurs(cursUsd);
        comanda.setPlata(modPlataAx);
        comanda.setStare("neprelucrata");
        comanda.setIdAutor(idAutor);
        comanda.setCursEur(cursEur);
        comanda.setInfoLivrare(adresaLivrare);
        comanda.setEmailConfirmare(emailClient);
        comanda.setData(LocalDateTime.now());
        comanda = comandaWebRepository.save(comanda);

        for (CartLine linie : linii) {
            ContinutComandaWeb linieComanda = new ContinutComandaWeb();
            linieComanda.setComId(comanda.getId());
            linieComanda.setSvid(linie.getProdus().getId());
            linieComanda.setProdus(linie.getProdus().getNumeAfisat());
            linieComanda.setCant(linie.getCantitate());
            linieComanda.setPu(linie.getPretUnitar());
            linieComanda.setVal(linie.getSubtotal());
            linieComanda.setTva(linie.getSubtotal().multiply(VAT_RATE).setScale(2, RoundingMode.HALF_UP));
            linieComanda.setIdValuta(IDVALUTA_RON);
            continutComandaWebRepository.save(linieComanda);
        }

        return new ComandaPlasata(comanda.getId(), total);
    }

    private BigDecimal rate(String nume) {
        return financiarRateRepository.findByNume(nume).map(FinanciarRate::getValoare).orElse(BigDecimal.ONE);
    }
}
