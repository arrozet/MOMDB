package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dao.CrewRoleRepository;
import es.uma.taw.momdb.dao.MovieRepository;
import es.uma.taw.momdb.entity.Movie;
import es.uma.taw.momdb.entity.User;
import es.uma.taw.momdb.ui.Filtro;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/editor")
public class EditorController extends BaseController{

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/")
    public String doInit(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "You are not authorized to access this page.");
            return "redirect:/";
        } else {
            // Lógica para el editor
            List<Movie> movies = this.movieRepository.findAll();
            model.addAttribute("movies", movies);
            model.addAttribute("filtro", new Filtro());
            return "editor";
        }
    }
}
