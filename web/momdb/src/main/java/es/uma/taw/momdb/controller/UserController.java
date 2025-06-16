package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dao.MovieRepository;
import es.uma.taw.momdb.dto.MovieDTO;
import es.uma.taw.momdb.dto.UserDTO;
import es.uma.taw.momdb.entity.Movie;
import es.uma.taw.momdb.service.MovieService;
import es.uma.taw.momdb.ui.Filtro;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * @author - projectGeorge (Jorge Repullo)
 * @co-authors -
 */

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    protected MovieService movieService;

    @GetMapping("/")
    public String doInit(HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        } else {
            return this.listarPeliculasConFiltro(null, model);
        }
    }

    @PostMapping("/filtrar")
    public String doFiltrar(HttpSession session, @ModelAttribute("filtro") Filtro filter, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        } else {
            return this.listarPeliculasConFiltro(filter, model);
        }
    }

    protected String listarPeliculasConFiltro(Filtro filtro, Model model) {
        List<MovieDTO> movies;

        if (filtro == null) {
            filtro = new Filtro();
            movies = movieService.listarPeliculas();
        } else {
            movies = movieService.listarPeliculas(filtro.getTexto());
        }
        model.addAttribute("movies", movies);
        model.addAttribute("filtro", filtro);
        return "user";
    }

    @GetMapping("/movie")
    public String verPelicula(@RequestParam("id") Integer id, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        // Buscar la película por ID
        MovieDTO movie = this.movieService.findPeliculaById(id);
        if (movie == null) {
            return "redirect:/user/";
        }

        model.addAttribute("generos", movie.getGeneros());
        model.addAttribute("movie", movie);
        return "movie_details";
    }

    private boolean checkAuth(HttpSession session, Model model) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "No estás autorizado para acceder a esta página.");
            return false;
        }
        model.addAttribute("user", user);
        return true;
    }
}
