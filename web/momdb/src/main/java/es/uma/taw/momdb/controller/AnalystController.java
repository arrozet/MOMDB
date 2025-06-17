package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dao.MovieRepository;
import es.uma.taw.momdb.dto.UserDTO;
import es.uma.taw.momdb.entity.Movie;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*
 * @author - edugbau (Eduardo González)
 * @co-authors -
 */

@Controller
@RequestMapping("/analyst")
public class AnalystController extends BaseController {

    @Autowired private MovieRepository movieRepository;

    @GetMapping("/")
    public String doInit(HttpSession session, Model model) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null || !user.getRolename().equals("analista")) {
            model.addAttribute("error", "You are not authorized to access this page.");
            return "redirect:/";
        } else {
            List<Movie> movies = this.movieRepository.findAll(); // Cargar todas

            model.addAttribute("movies", movies);
            model.addAttribute("user", user);
            return "analyst/analyst";
        }
    }

    @PostMapping("/filtrar")
    public String doFiltrar(HttpSession session, Model model, @RequestParam("filter") String filter) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null || !user.getRolename().equals("analista")) {
            model.addAttribute("error", "You are not authorized to access this page.");
        }

        List<Movie> movies;
        if (filter == null || filter.trim().isEmpty()) {
            movies = this.movieRepository.findAll(); // Cargar todas
        } else {
            movies = this.movieRepository.filterByTitle(filter);
        }

        model.addAttribute("movies", movies);
        model.addAttribute("user", user);
        model.addAttribute("currentFilter", filter);
        return "analyst/analyst";
    }

    //TODO: entrar a una parte de analisis por película
}
