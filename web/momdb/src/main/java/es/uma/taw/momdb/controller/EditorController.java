package es.uma.taw.momdb.controller;


import es.uma.taw.momdb.dto.GenreDTO;
import es.uma.taw.momdb.dto.MovieDTO;

import es.uma.taw.momdb.dto.UserDTO;
import es.uma.taw.momdb.service.GeneroService;
import es.uma.taw.momdb.service.MovieService;
import es.uma.taw.momdb.ui.Filtro;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * @author - Artur797 (Artur Vargas)
 * @co-authors -
 */

@Controller
@RequestMapping("/editor")
public class EditorController extends BaseController{

    @Autowired
    private MovieService movieService;

    @Autowired
    private GeneroService generoService;

    @GetMapping("/")
    public String doInit(HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        } else {
            return this.listarPeliculasConFiltro(null, model);
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
        return "editor";
    }

    @PostMapping("/filtrar")
    public String doFiltrar (@ModelAttribute("filtro") Filtro filtro, Model model) {
        return this.listarPeliculasConFiltro(filtro, model);
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

        List<GenreDTO> generos = this.generoService.listarGeneros();

        model.addAttribute("generos", generos);
        model.addAttribute("movie", movie);
        return "movie_editor";
    }

    private boolean checkAuth(HttpSession session, Model model) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null || !user.getRolename().equals("editor")) {
            model.addAttribute("error", "No estás autorizado para acceder a esta página.");
            return false;
        }
        model.addAttribute("user", user);
        return true;
    }

    @PostMapping("/saveMovie")
    public String doGuardar (@ModelAttribute("movie") MovieDTO movie) {

        this.movieService.saveMovie(movie);

        return "redirect:/editor/";
    }

    @GetMapping("/delete")
    public String doBorrar (@RequestParam("id") Integer id, HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        } else {
            this.movieService.borrarPelicula(id);
            return "redirect:/editor/";
        }
    }

}
