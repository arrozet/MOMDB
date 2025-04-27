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
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired private MovieRepository movieRepository;

    @GetMapping("/")
    public String doInit(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "You are not authorized to access this page.");
            return "redirect:/";
        } else {
            // Lógica para el usuario
            List<Movie> movies = this.movieRepository.findAll();
            model.addAttribute("movies", movies);
            model.addAttribute("user", user);
            return "user";
        }
    }

    @PostMapping("/filtrar")
    public String doFiltrar(HttpSession session, Model model, @RequestParam("filter") String filter) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "You are not authorized to access this page.");
        }
        List<Movie> filteredMovies = this.movieRepository.filterByTitle(filter);
        model.addAttribute("movies", filteredMovies);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/movie")
    public String verPelicula(@RequestParam("id") Integer id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "No estás autorizado para acceder a esta página.");
            return "redirect:/";
        }

        // Buscar la película por ID
        Movie movie = this.movieRepository.findById(id).orElse(null);
        if (movie == null) {
            return "redirect:/user/";
        }

        model.addAttribute("generos", movie.getGenres());
        model.addAttribute("movie", movie);
        return "movie_details";
    }
}
