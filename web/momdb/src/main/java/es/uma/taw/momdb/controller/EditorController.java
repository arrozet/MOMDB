package es.uma.taw.momdb.controller;


import es.uma.taw.momdb.dto.*;
import es.uma.taw.momdb.entity.Crewrole;

import es.uma.taw.momdb.service.*;
import es.uma.taw.momdb.ui.Filtro;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping("/people")
    public String listPeople(@ModelAttribute("filtro") Filtro filtro, HttpSession session, Model model,
                            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                            @RequestParam(value = "size", required = false, defaultValue = "1000") int size) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        String texto = filtro != null ? filtro.getTexto() : null;
        Page<PersonDTO> peoplePage = personService.findPeoplePaged(texto, page, size);
        model.addAttribute("people", peoplePage.getContent());
        model.addAttribute("filtro", filtro);
        model.addAttribute("page", page);
        model.addAttribute("hasPrev", peoplePage.hasPrevious());
        model.addAttribute("hasNext", peoplePage.hasNext());
        return "editor/people";

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
    public String deleteCharacter(@RequestParam("characterId") int characterId, @RequestParam("crewId") int crewId, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        CrewDTO crew = crewService.findCrewById(crewId);

        this.crewService.deleteCrewCharacter(crew, characterId);
        return "redirect:/editor/movie/characters?id=" + crew.getPeliculaId();
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

    @GetMapping("/person/delete")
    public String deletePerson(@RequestParam("id") Integer id, HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        personService.deleteById(id);
        return "redirect:/editor/people";
    }

    @GetMapping("/person/add")
    public String addPersonForm(Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        model.addAttribute("person", new PersonDTO());
        return "editor/edit_person";
    }

    @GetMapping("/person/edit")
    public String editPerson(@RequestParam("id") Integer id, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        PersonDTO person = personService.findById(id);
        model.addAttribute("person", person);
        model.addAttribute("actorMovies", crewService.findMoviesWherePersonIsActor(id));
        model.addAttribute("crewMovies", crewService.findMoviesWherePersonIsCrewNoActor(id));
        return "editor/edit_person";
    }

    @PostMapping("/person/save")
    public String savePerson(@ModelAttribute("person") PersonDTO person, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        personService.save(person);
        return "redirect:/editor/people";
    }

    @GetMapping("/movie/crew/delete")
    public String deleteCrew(@RequestParam("crewId") int crewId, @RequestParam("movieId") int movieId, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        this.crewService.deleteCrew(crewId);
        return "redirect:/editor/movie/crew?id=" + movieId;
    }

    @GetMapping("/movie/crew/edit")
    public String editCrew(@RequestParam("crewId") Integer crewId, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        CrewDTO crew = this.crewService.findCrewById(crewId);
        List<PersonDTO> people = this.personService.findAll();
        List<Crewrole> roles = this.crewService.findAllRolesExceptActor();
        model.addAttribute("crew", crew);
        model.addAttribute("people", people);
        model.addAttribute("roles", roles);
        model.addAttribute("movie", movieService.findPeliculaById(crew.getPeliculaId()));
        model.addAttribute("error", null);
        return "editor/edit_crew";
    }

    @PostMapping("/movie/crew/save")
    public String saveCrew(@ModelAttribute("crew") CrewDTO crew, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        boolean ok = this.crewService.saveCrewEdit(crew);
        if (!ok) {
            model.addAttribute("error", "Ya existe un miembro del equipo con la misma persona y rol en esta película.");
            List<PersonDTO> people = this.personService.findAll();
            List<Crewrole> roles = this.crewService.findAllRolesExceptActor();
            model.addAttribute("people", people);
            model.addAttribute("roles", roles);
            model.addAttribute("movie", movieService.findPeliculaById(crew.getPeliculaId()));
            return "editor/edit_crew";
        }
        return "redirect:/editor/movie/crew?id=" + crew.getPeliculaId();
    }

    @GetMapping("/movie/crew/new")
    public String addCrew(@RequestParam("movieId") int movieId, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        CrewDTO crew = new CrewDTO();
        crew.setPeliculaId(movieId);
        List<PersonDTO> people = this.personService.findAll();
        List<Crewrole> roles = this.crewService.findAllRolesExceptActor();
        model.addAttribute("crew", crew);
        model.addAttribute("people", people);
        model.addAttribute("roles", roles);
        model.addAttribute("movie", movieService.findPeliculaById(movieId));
        model.addAttribute("error", null);
        return "editor/edit_crew";
    }

}
