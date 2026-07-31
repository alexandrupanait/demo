package com.example.demo.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

/**
 * "Administrare cont" - editing the logged-in user's own details. Mirrors
 * legacy Ralonline::AccountsController#edit/#save, but only the
 * edit-your-own-account slice (not the admin-manages-other-users branch,
 * which needs an admin/sub-user concept this rebuild doesn't have).
 */
@Controller
@RequestMapping("/ralonline/accounts")
public class AccountController {

    private final ClientUserRepository clientUserRepository;

    public AccountController(ClientUserRepository clientUserRepository) {
        this.clientUserRepository = clientUserRepository;
    }

    @GetMapping
    public String edit(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        model.addAttribute("account", clientUserRepository.findById(userId).orElseThrow());
        return "ralonline/account-edit";
    }

    @PostMapping
    public String save(@RequestParam String nume, @RequestParam String prenume, @RequestParam String email,
            @RequestParam(required = false) String departament, @RequestParam(required = false) String functie,
            @RequestParam(required = false) String telefon, @RequestParam(required = false) String actSerie,
            @RequestParam(required = false) String actNr, @RequestParam(required = false) String actEliberat,
            @RequestParam(required = false) String parola, @RequestParam(required = false) String parolaConfirmare,
            @RequestParam(required = false) String activ,
            HttpSession session, Model model) {

        Integer userId = (Integer) session.getAttribute("userId");
        ClientUser account = clientUserRepository.findById(userId).orElseThrow();

        List<String> erori = validate(nume, prenume, email, parola, parolaConfirmare, userId);
        if (!erori.isEmpty()) {
            // Re-show what the user typed, not the stale saved values - this
            // instance isn't persisted unless validation passes below.
            account.setNume(nume);
            account.setPrenume(prenume);
            account.setEmail(email);
            account.setDepartament(departament);
            account.setFunctie(functie);
            account.setTelefon(telefon);
            account.setActSerie(actSerie);
            account.setActNr(actNr);
            account.setActEliberat(actEliberat);
            account.setActiv(activ != null);
            model.addAttribute("erori", erori);
            model.addAttribute("account", account);
            return "ralonline/account-edit";
        }

        account.setNume(nume);
        account.setPrenume(prenume);
        account.setEmail(email);
        // Mirrors the legacy save action: the login username always follows the email.
        account.setUtilizator(email);
        account.setDepartament(departament);
        account.setFunctie(functie);
        account.setTelefon(telefon);
        account.setActSerie(actSerie);
        account.setActNr(actNr);
        account.setActEliberat(actEliberat);
        if (parola != null && !parola.isBlank()) {
            account.setParola(parola);
        }
        account.setActiv(activ != null);
        // Mirrors the legacy save action: reviewing/saving the account once
        // clears the "please review your details" welcome message for good.
        account.setChecked(true);
        clientUserRepository.save(account);

        session.setAttribute("userName", account.getNumeComplet());

        model.addAttribute("succes", true);
        model.addAttribute("account", account);
        return "ralonline/account-edit";
    }

    private List<String> validate(String nume, String prenume, String email, String parola, String parolaConfirmare, Integer userId) {
        List<String> erori = new ArrayList<>();
        if (nume == null || nume.isBlank()) {
            erori.add("Numele este obligatoriu.");
        }
        if (prenume == null || prenume.isBlank()) {
            erori.add("Prenumele este obligatoriu.");
        }
        if (email == null || email.isBlank() || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            erori.add("Adresa de email nu este valida.");
        } else if (clientUserRepository.existsByEmailIgnoreCaseAndIdNot(email, userId)) {
            erori.add("Aceasta adresa de email este deja folosita de alt cont.");
        }
        if (parola != null && !parola.isBlank() && !parola.equals(parolaConfirmare)) {
            erori.add("Parolele nu coincid.");
        }
        return erori;
    }
}
