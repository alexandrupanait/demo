package com.example.demo.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Simplified registration: one company + one admin contact person (the old
 * form also supports "individual" registrants and a file upload for the
 * incorporation certificate - dropped for this pass, see the Phase 3/5 plan
 * for that scope call). Writes real rows to clienti/personal_clienti -
 * confirmed with the user, since the new account has to work with the
 * existing login flow that reads from those same tables.
 */
@Controller
@RequestMapping("/ralonline/register")
public class RegisterController {

    private final ClientAccountRepository clientAccountRepository;
    private final ClientUserRepository clientUserRepository;

    public RegisterController(ClientAccountRepository clientAccountRepository, ClientUserRepository clientUserRepository) {
        this.clientAccountRepository = clientAccountRepository;
        this.clientUserRepository = clientUserRepository;
    }

    @GetMapping
    public String form() {
        return "ralonline/register";
    }

    @PostMapping
    public String register(@RequestParam String firma, @RequestParam String codFiscal,
            @RequestParam(required = false) String sediuLocalitate, @RequestParam(required = false) String sediuStrada,
            @RequestParam String tel, @RequestParam String nume, @RequestParam String prenume,
            @RequestParam String email, @RequestParam String parola, @RequestParam String parolaConfirmare,
            @RequestParam(required = false) String agreeTac, Model model) {

        List<String> erori = validate(firma, codFiscal, nume, prenume, email, parola, parolaConfirmare, agreeTac);
        if (!erori.isEmpty()) {
            model.addAttribute("erori", erori);
            return "ralonline/register";
        }

        ClientAccount cont = new ClientAccount();
        cont.setFirma(firma.trim());
        cont.setCodFiscal(codFiscal.trim());
        cont.setTel(tel);
        cont.setEmail(email);
        cont.setSediuLocalitate(sediuLocalitate);
        cont.setSediuStrada(sediuStrada);
        cont.setPType("Organization");
        cont.setStatus("new");
        cont.setSursa("site RAL java");
        cont.setActivitate("client nevalidat");
        cont.setProcent(BigDecimal.ZERO);
        cont = clientAccountRepository.save(cont);

        ClientUser user = new ClientUser();
        user.setIdClient(cont.getId());
        user.setUtilizator(email);
        user.setParola(parola);
        user.setNume(nume.trim());
        user.setPrenume(prenume.trim());
        user.setTelefon(tel);
        user.setEmail(email);
        user.setActiv(true);
        user.setAdmin(true);
        clientUserRepository.save(user);

        return "ralonline/register-success";
    }

    private List<String> validate(String firma, String codFiscal, String nume, String prenume, String email,
            String parola, String parolaConfirmare, String agreeTac) {
        List<String> erori = new ArrayList<>();

        if (firma == null || firma.trim().length() < 3) {
            erori.add("Numele firmei trebuie sa aiba cel putin 3 caractere.");
        } else if (clientAccountRepository.existsByFirmaIgnoreCase(firma.trim())) {
            erori.add("Exista deja un cont cu acest nume de firma.");
        }

        if (codFiscal == null || !codFiscal.trim().matches("\\d{2,20}")) {
            erori.add("Codul fiscal (CUI) trebuie sa contina doar cifre.");
        } else if (clientAccountRepository.existsByCodFiscal(codFiscal.trim())) {
            erori.add("Exista deja un cont cu acest cod fiscal.");
        }

        if (nume == null || nume.trim().length() < 3) {
            erori.add("Numele persoanei de contact trebuie sa aiba cel putin 3 caractere.");
        }
        if (prenume == null || prenume.trim().length() < 3) {
            erori.add("Prenumele persoanei de contact trebuie sa aiba cel putin 3 caractere.");
        }

        if (email == null || !email.matches("^[^@\\s]+@[-a-zA-Z0-9]+(\\.[-a-zA-Z0-9]+)+$")) {
            erori.add("Adresa de email nu este valida.");
        } else if (clientUserRepository.existsByEmailIgnoreCase(email)) {
            erori.add("Exista deja un cont cu aceasta adresa de email.");
        }

        if (parola == null || parola.length() < 6) {
            erori.add("Parola trebuie sa aiba cel putin 6 caractere.");
        } else if (!Objects.equals(parola, parolaConfirmare)) {
            erori.add("Parolele nu coincid.");
        }

        if (!"on".equals(agreeTac) && !"true".equals(agreeTac)) {
            erori.add("Trebuie sa acceptati termenii si conditiile.");
        }

        return erori;
    }
}
