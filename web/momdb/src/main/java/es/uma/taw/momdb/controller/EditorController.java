package es.uma.taw.momdb.controller;


import es.uma.taw.momdb.dto.*;

import es.uma.taw.momdb.service.*;
import es.uma.taw.momdb.ui.Filtro;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * @author - Artur797 (Artur Vargas)
 * @co-authors - arrozet (Rubén Oliva - refactorización para auth)
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

    @Autowired
    private PersonService personService;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private ReviewService reviewService;

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

        MovieDTO movie = this.movieService.findPeliculaById(id);
        
        List<ReviewDTO> reviews = this.reviewService.getReviewsByMovieId(id);
        model.addAttribute("movie", movie);
        model.addAttribute("reviews", reviews);
        return "editor/movie_reviews";
    }

    @GetMapping("/movie/review/delete")
    public String deleteReview(@RequestParam("movieId") Integer movieId, 
                             @RequestParam("userId") Integer userId,
                             HttpSession session, 
                             Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        this.reviewService.deleteReview(movieId, userId);
        return "redirect:/editor/movie/reviews?id=" + movieId;
    }

    /**
     * Comprueba si el usuario en sesión tiene el rol de editor.
     * Utiliza el método centralizado de BaseController para realizar la verificación.
     * Si la autorización es exitosa, añade automáticamente el usuario al modelo.
     *
     * @param session La sesión HTTP actual.
     * @param model El modelo para la vista.
     * @return {@code true} si el usuario es editor, {@code false} en caso contrario.
     */
    private boolean checkAuth(HttpSession session, Model model) {
        return super.checkAuth(session, model, "editor");
    }

    @PostMapping("/saveMovie")
    public String doGuardar (@ModelAttribute("movie") MovieDTO movie) {
        if (movie.getId() == -1) {
            // Es una nueva película
            this.movieService.crearPelicula(movie);
        } else {
            // Es una edición
            this.movieService.saveMovie(movie);
        }

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

    @GetMapping("/movie/character/edit")
    public String editCharacter(@RequestParam("id") Integer id, @RequestParam("characterId") Integer characterId, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        CrewDTO crew = this.crewService.findCrewById(id);
        List<PersonDTO> people = this.personService.findAll();
        CharacterDTO character = this.characterService.findById(characterId);

        model.addAttribute("movie", this.movieService.findPeliculaById(crew.getPeliculaId()));
        model.addAttribute("people", people);

        model.addAttribute("crew", crew);
        model.addAttribute("character", character);

        return "/editor/edit_character";
    }

    @PostMapping("/movie/character/save")
    public String saveCharacter(@ModelAttribute("crew") CrewDTO crew, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        this.crewService.saveCrew(crew);
        return "redirect:/editor/movie/characters?id=" + crew.getPeliculaId();
    }

    
    @GetMapping("/movie/character/delete")
    public String deleteCharacter(@RequestParam("characterId") int characterId, @RequestParam("movieId") int movieId, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        this.crewService.deleteCharacter(characterId);
        return "redirect:/editor/movie/characters?id=" + movieId;
    }

    @GetMapping("/movie/character/new")
    public String addCharacter(@RequestParam("movieId") int movieId, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        List<PersonDTO> people = this.personService.findAll();

        model.addAttribute("movie", this.movieService.findPeliculaById(movieId));
        model.addAttribute("people", people);
        model.addAttribute("crew", new CrewDTO());

        return "editor/add_character";
    }

    @PostMapping("/movie/character/add")
    public String doAddCharacter(@ModelAttribute("crew") CrewDTO crew, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        this.crewService.addNewCharacter(crew);
        return "redirect:/editor/movie/characters?id=" + crew.getPeliculaId();
    }

    @GetMapping("/newMovie")
    public String newMovie(Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        List<GenreDTO> generos = this.generoService.listarGeneros();
        model.addAttribute("generos", generos);
        model.addAttribute("movie", new MovieDTO());

        return "editor/movie_editor";
    }

}
