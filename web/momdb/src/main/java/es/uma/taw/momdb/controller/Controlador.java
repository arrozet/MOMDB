package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dao.MovieRepository;
import es.uma.taw.momdb.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Controlador {

    @Autowired
    protected MovieRepository movieRepository;

    @GetMapping("/")
    public String doProbar (Model model) {
        Movie m = this.movieRepository.findById(2).orElse(null);
        model.addAttribute("primeraPeli", m);

        return "HelloWorld";
    }
}
