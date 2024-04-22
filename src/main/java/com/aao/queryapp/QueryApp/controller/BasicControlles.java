package com.aao.queryapp.QueryApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BasicControlles {
    
    @GetMapping("hello")
    public String helloString() {
        return new String("hello desconocido");
    }
    @GetMapping("bye")
    public String byeString() {
        return new String("bye desconocido");
    }
    @GetMapping("tie")
    public String tieString() {
        String saludo = "ta cual pinpan";
        return new String(saludo);
    }
    
}
