package com.roshka.dtaporteria.contoller;

import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class Main {

    @GetMapping
    public String helloWorld(){
        return "<h1>Hello World!</h1?>";
    }

}
