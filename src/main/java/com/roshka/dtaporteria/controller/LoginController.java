package com.roshka.dtaporteria.controller;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.roshka.dtaporteria.correo.ServiceMail;

import com.roshka.dtaporteria.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
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


    @PostMapping("/sendEmail")
    public String PassResetEmail(@RequestParam("email") String email){
        String link = null;
        try {
            link = auth.generatePasswordResetLink(email);
        } catch (FirebaseAuthException e) {
            return "redirect:/login?err";
        }
        Map<String, Object> parametros =new HashMap<>();
        parametros.put("linkRecuperacion", link);

        mail.sendEmailWithHTML(email, "Cambio de contraseña" , parametros);

        return "redirect:/login?success";
    }


    @GetMapping("/error")
    public String base() {
        return "holaMundo";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {

        String credentials = "";

        try {
            FileInputStream googleJs = new FileInputStream("./firebaseJsApp");
            credentials = getFileContent(googleJs, "UTF-8");
        }
        catch(FileNotFoundException e) {
            credentials = "console.log('firebaseJsApp no encontrado (se encuentra en firebase console)')";
        } catch (IOException e) {
            credentials = "console.log('Problemas al abrir el archivo')";
        }

        model.addAttribute("credentials", credentials);
        return "login";
    }

    public static String getFileContent(FileInputStream fis, String encoding) throws IOException {
        try(BufferedReader br = new BufferedReader( new InputStreamReader(fis, encoding) )){
            StringBuilder sb = new StringBuilder();
            String line;
            while(( line = br.readLine()) != null ) {
                sb.append( line );
                sb.append( '\n' );
            }
            return sb.toString();
        }
    }
}
