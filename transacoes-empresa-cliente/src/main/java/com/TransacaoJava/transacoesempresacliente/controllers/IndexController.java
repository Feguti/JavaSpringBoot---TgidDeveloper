package com.TransacaoJava.transacoesempresacliente.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index"; // Retorna o nome da página (index.html, por exemplo)
    }
    

}

