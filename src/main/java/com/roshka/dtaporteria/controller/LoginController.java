package com.roshka.dtaporteria.controller;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.roshka.dtaporteria.correo.ServiceMail;

import com.roshka.dtaporteria.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private FirebaseAuth auth;
    @Autowired
    private UserService service;

    @Autowired
    private ServiceMail mail;

    @GetMapping("/forgotPass")
    public String olvideContra() {return "olvideContraseña";}

    @PostMapping("/sendEmail")
    public String PassResetEmail(@RequestParam("email") String email){
        String link = null;
        try {
            link = auth.generatePasswordResetLink(email);
        } catch (FirebaseAuthException e) {
            return "redirect:/forgotPass?err";
        }
        Map<String, Object> parametros =new HashMap<>();
        parametros.put("linkRecuperacion", link);

        mail.sendEmailWithHTML(email, "Cambio de contraseña" , parametros);

        return "redirect:/forgotPass?success";
    }


    @GetMapping("/error")
    public String base() {
        return "holaMundo";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

}
