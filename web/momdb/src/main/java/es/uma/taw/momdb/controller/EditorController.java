package es.uma.taw.momdb.controller;


import es.uma.taw.momdb.dto.CrewDTO;
import es.uma.taw.momdb.dto.GenreDTO;
import es.uma.taw.momdb.dto.MovieDTO;

import es.uma.taw.momdb.dto.UserDTO;
import es.uma.taw.momdb.service.CrewService;
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

    @Autowired
    private CrewService crewService;

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
        return "editor/editor";
    }

    @PostMapping("/filtrar")
    public String doFiltrar (@ModelAttribute("filtro") Filtro filtro, Model model) {
        return this.listarPeliculasConFiltro(filtro, model);
    }


    private String handleMovieSection(Integer id, Model model, HttpSession session, String viewName) {

        MovieDTO movie = this.movieService.findPeliculaById(id);
        if (movie == null) {
            return "redirect:/editor/";
        }

        model.addAttribute("movie", movie);
        return viewName;
    }

    @GetMapping("/movie")
    public String verPelicula(@RequestParam("id") Integer id, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        List<GenreDTO> generos = this.generoService.listarGeneros();
        model.addAttribute("generos", generos);
        return handleMovieSection(id, model, session, "editor/movie_editor");
    }

    @GetMapping("/movie/characters")
    public String movieCharacters(@RequestParam("id") Integer id, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        return handleMovieSection(id, model, session, "editor/movie_characters");
    }

    @GetMapping("/movie/crew")
    public String movieCrew(@RequestParam("id") Integer id, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        return handleMovieSection(id, model, session, "editor/movie_crew");
    }

    @GetMapping("/movie/reviews")
    public String movieReviews(@RequestParam("id") Integer id, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        return handleMovieSection(id, model, session, "editor/movie_reviews");
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

    @GetMapping("/actors")
    public String listActors (HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        } else {
            return this.listarActoresConFiltro(null, model);
        }
    }

    protected String listarActoresConFiltro(Filtro filtro, Model model) {
        List<CrewDTO> actores;

        if (filtro == null) {
            filtro = new Filtro();
            actores = crewService.listarActores();
        } else {
            actores = crewService.listarActores(filtro.getTexto());
        }
        model.addAttribute("actores", actores);
        model.addAttribute("filtro", filtro);
        return "editor/actors";
    }

}
