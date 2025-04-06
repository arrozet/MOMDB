package es.uma.taw.momdb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Controlador {
    @GetMapping("/")
    public String doProbar (Model model) {
        return "HelloWorld";
    }
}
