package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dao.MovieRepository;
import es.uma.taw.momdb.entity.Movie;
import es.uma.taw.momdb.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/analyst")
public class AnalystController extends BaseController {

    @Autowired private MovieRepository movieRepository;


    @GetMapping("/")
    public String doInit(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().getName().equals("analista")) {
            model.addAttribute("error", "You are not authorized to access this page.");
            return "redirect:/";
        } else {
            // Aquí puedes añadir lógica específica para el analista
            List<Movie> movies = this.movieRepository.findAll();
            model.addAttribute("movies", movies);
             model.addAttribute("user", user);
            return "analyst";
        }
    }
    @PostMapping("/filtrar")
    public String doFiltrar(HttpSession session, Model model, @RequestParam("filter") String filter) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().getName().equals("analista")) {
            model.addAttribute("error", "You are not authorized to access this page.");
        }
        List<Movie> filteredMovies = this.movieRepository.filterByTitle(filter);
        model.addAttribute("movies", filteredMovies);
        model.addAttribute("user", user);
        return "analyst";
    }
    //TODO: Filtrar por nombre todas las películas, entrar a una parte de analisis por película
}
