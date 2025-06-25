package es.uma.taw.momdb.controller;


import es.uma.taw.momdb.dto.*;

import es.uma.taw.momdb.service.*;
import es.uma.taw.momdb.ui.Filtro;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para las funcionalidades del rol Editor.
 * Permite la gestión de películas, incluyendo su creación, edición y borrado,
 * así como la gestión de su reparto, equipo técnico y reseñas.
 *
 * @author Artur797 (Artur Vargas), arrozet (Rubén Oliva - refactorización para auth, Javadocs)
 */
@Controller
@RequestMapping("/editor")
public class EditorController extends BaseController{

    private static final int PAGE_SIZE = 48;

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

    @Autowired
    private CrewRoleService crewRoleService;

    /**
     * Inicializa la página principal del editor.
     * Muestra una lista paginada de películas.
     *
     * @param page El número de página a mostrar.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return La vista "editor/editor" o una redirección si no hay autorización.
     */
    @GetMapping("/")
    public String doInit(@RequestParam(name = "page", defaultValue = "1") int page, HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        } else {
            return this.listarPeliculasConFiltro(null, page, model, session);
        }
    }

    /**
     * Prepara el modelo con la lista de películas filtradas y paginadas.
     *
     * @param filtro El filtro a aplicar.
     * @param page El número de página a mostrar.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return El nombre de la vista a renderizar.
     */
    protected String listarPeliculasConFiltro(Filtro filtro, int page, Model model, HttpSession session) {
        if (filtro == null) {
            filtro = new Filtro();
        }

        Page<MovieDTO> moviePage = this.movieService.findPaginatedWithFilters(filtro, page - 1, PAGE_SIZE);

        model.addAttribute("movies", moviePage.getContent());
        model.addAttribute("filtro", filtro);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", moviePage.getTotalPages());
        return "editor/editor";
    }

    /**
     * Aplica un filtro a la lista de películas.
     *
     * @param session La sesión HTTP.
     * @param filtro El filtro a aplicar.
     * @param model El modelo para la vista.
     * @return La vista "editor/editor" con las películas filtradas.
     */
    @PostMapping("/filtrar")
    public String doFiltrar(HttpSession session, @ModelAttribute("filtro") Filtro filtro, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        return this.listarPeliculasConFiltro(filtro, 1, model, session);
    }


    /**
     * Maneja la carga de datos de una película para diferentes secciones de edición.
     *
     * @param id El ID de la película.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @param viewName El nombre de la vista a la que se redirigirá.
     * @return El nombre de la vista o una redirección si la película no se encuentra.
     */
    private String handleMovieSection(Integer id, Model model, HttpSession session, String viewName) {

        MovieDTO movie = this.movieService.findPeliculaById(id);
        if (movie == null) {
            return "redirect:/editor/";
        }

        model.addAttribute("movie", movie);
        return viewName;
    }

    /**
     * Muestra el formulario para ver o editar los detalles de una película.
     *
     * @param id El ID de la película.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return La vista "editor/movie_editor".
     */
    @GetMapping("/movie")
    public String verPelicula(@RequestParam("id") Integer id, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        List<GenreDTO> generos = this.generoService.listarGeneros();
        model.addAttribute("generos", generos);
        return handleMovieSection(id, model, session, "editor/movie_editor");
    }

    /**
     * Muestra la lista de personajes de una película.
     *
     * @param id El ID de la película.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return La vista "editor/movie_characters".
     */
    @GetMapping("/movie/characters")
    public String movieCharacters(@RequestParam("id") Integer id, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        return handleMovieSection(id, model, session, "editor/movie_characters");
    }

    /**
     * Muestra la lista del equipo técnico de una película.
     *
     * @param id El ID de la película.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return La vista "editor/movie_crew".
     */
    @GetMapping("/movie/crew")
    public String movieCrew(@RequestParam("id") Integer id, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        return handleMovieSection(id, model, session, "editor/movie_crew");
    }

    /**
     * Muestra las reseñas de una película.
     *
     * @param id El ID de la película.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return La vista "editor/movie_reviews".
     */
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

    /**
     * Elimina una reseña de una película.
     *
     * @param movieId El ID de la película.
     * @param userId El ID del usuario que escribió la reseña.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return Redirige a la lista de reseñas de la película.
     */
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

    /**
     * Guarda o actualiza una película.
     * Si el ID es -1, crea una nueva película; de lo contrario, la actualiza.
     *
     * @param movie DTO de la película con los datos a guardar.
     * @return Redirige a la página principal del editor.
     */
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

    /**
     * Elimina una película.
     *
     * @param id El ID de la película a eliminar.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return Redirige a la página principal del editor.
     */
    @GetMapping("/delete")
    public String doBorrar (@RequestParam("id") Integer id, HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        } else {
            this.movieService.borrarPelicula(id);
            return "redirect:/editor/";
        }
    }

    /**
     * Muestra una lista paginada de personas (actores y equipo técnico).
     *
     * @param filtro Objeto para filtrar por texto.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @param page Número de página.
     * @param size Tamaño de la página.
     * @return La vista "editor/people".
     */
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

    /**
     * Muestra el formulario para editar un personaje de una película.
     *
     * @param id El ID del registro en la tabla crew.
     * @param characterId El ID del personaje.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return La vista "editor/edit_character".
     */
    @GetMapping("/movie/character/edit")
    public String editCharacter(@RequestParam("id") Integer id, @RequestParam("characterId") Integer characterId, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        CrewDTO crew = this.crewService.findCrewById(id);
        List<PersonDTO> people = this.personService.findAll();
        CharacterDTO character = this.characterService.findById(characterId);
        crew.setPersonajeName(character.getCharacterName());

        model.addAttribute("movie", this.movieService.findPeliculaById(crew.getPeliculaId()));
        model.addAttribute("people", people);

        model.addAttribute("crew", crew);
        model.addAttribute("character", character);

        return "/editor/edit_character";
    }

    /**
     * Guarda los cambios de un personaje.
     *
     * @param crew DTO con los datos del personaje y su actor.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return Redirige a la lista de personajes de la película.
     */
    @PostMapping("/movie/character/save")
    public String saveCharacter(@ModelAttribute("crew") CrewDTO crew, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        this.crewService.saveCrew(crew);
        return "redirect:/editor/movie/characters?id=" + crew.getPeliculaId();
    }

    
    /**
     * Elimina un personaje de una película.
     *
     * @param characterId El ID del personaje a eliminar.
     * @param crewId El ID del registro en la tabla crew.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return Redirige a la lista de personajes de la película.
     */
    @GetMapping("/movie/character/delete")
    public String deleteCharacter(@RequestParam("characterId") int characterId, @RequestParam("crewId") int crewId, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        CrewDTO crew = crewService.findCrewById(crewId);

        this.crewService.deleteCrewCharacter(crew, characterId);
        return "redirect:/editor/movie/characters?id=" + crew.getPeliculaId();
    }

    /**
     * Muestra el formulario para añadir un nuevo personaje a una película.
     *
     * @param movieId El ID de la película.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return La vista "editor/add_character".
     */
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

    /**
     * Procesa la adición de un nuevo personaje a una película.
     *
     * @param crew DTO con los datos del nuevo personaje.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return Redirige a la lista de personajes de la película.
     */
    @PostMapping("/movie/character/add")
    public String doAddCharacter(@ModelAttribute("crew") CrewDTO crew, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        this.crewService.addNewCharacter(crew);
        return "redirect:/editor/movie/characters?id=" + crew.getPeliculaId();
    }

    /**
     * Muestra el formulario para crear una nueva película.
     *
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return La vista "editor/movie_editor".
     */
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

    /**
     * Elimina una persona de la base de datos.
     *
     * @param id El ID de la persona a eliminar.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return Redirige a la lista de personas.
     */
    @GetMapping("/person/delete")
    public String deletePerson(@RequestParam("id") Integer id, HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        personService.deleteById(id);
        return "redirect:/editor/people";
    }

    /**
     * Muestra el formulario para añadir una nueva persona.
     *
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return La vista "editor/edit_person".
     */
    @GetMapping("/person/add")
    public String addPersonForm(Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        model.addAttribute("person", new PersonDTO());
        return "editor/edit_person";
    }

    /**
     * Muestra el formulario para editar una persona existente.
     *
     * @param id El ID de la persona a editar.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return La vista "editor/edit_person".
     */
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

    /**
     * Guarda los datos de una persona (nueva o existente).
     *
     * @param person DTO con los datos de la persona.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return Redirige a la lista de personas.
     */
    @PostMapping("/person/save")
    public String savePerson(@ModelAttribute("person") PersonDTO person, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        personService.save(person);
        return "redirect:/editor/people";
    }

    /**
     * Elimina un miembro del equipo técnico de una película.
     *
     * @param crewId El ID del registro en la tabla crew.
     * @param movieId El ID de la película.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return Redirige a la lista del equipo técnico de la película.
     */
    @GetMapping("/movie/crew/delete")
    public String deleteCrew(@RequestParam("crewId") int crewId, @RequestParam("movieId") int movieId, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        this.crewService.deleteCrew(crewId);
        return "redirect:/editor/movie/crew?id=" + movieId;
    }

    /**
     * Muestra el formulario para editar un miembro del equipo técnico.
     *
     * @param crewId El ID del registro en la tabla crew.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return La vista "editor/edit_crew".
     */
    @GetMapping("/movie/crew/edit")
    public String editCrew(@RequestParam("crewId") Integer crewId, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        CrewDTO crew = this.crewService.findCrewById(crewId);
        List<PersonDTO> people = this.personService.findAll();
        List<CrewRoleDTO> roles = this.crewRoleService.findAllRolesExceptActor();
        model.addAttribute("crew", crew);
        model.addAttribute("people", people);
        model.addAttribute("roles", roles);
        model.addAttribute("movie", movieService.findPeliculaById(crew.getPeliculaId()));
        model.addAttribute("error", null);
        return "editor/edit_crew";
    }

    /**
     * Guarda los cambios de un miembro del equipo técnico.
     *
     * @param crew DTO con los datos a guardar.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return Redirige a la lista del equipo técnico de la película o recarga el formulario si hay un error.
     */
    @PostMapping("/movie/crew/save")
    public String saveCrew(@ModelAttribute("crew") CrewDTO crew, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        boolean ok = this.crewService.saveCrewEdit(crew);
        if (!ok) {
            model.addAttribute("error", "This person is already assigned to that role in this movie.");
            List<PersonDTO> people = this.personService.findAll();
            List<CrewRoleDTO> roles = this.crewRoleService.findAllRolesExceptActor();
            model.addAttribute("people", people);
            model.addAttribute("roles", roles);
            model.addAttribute("movie", movieService.findPeliculaById(crew.getPeliculaId()));
            return "editor/edit_crew";
        }
        return "redirect:/editor/movie/crew?id=" + crew.getPeliculaId();
    }

    /**
     * Muestra el formulario para añadir un nuevo miembro del equipo técnico a una película.
     *
     * @param movieId El ID de la película.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return La vista "editor/edit_crew".
     */
    @GetMapping("/movie/crew/new")
    public String addCrew(@RequestParam("movieId") int movieId, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        CrewDTO crew = new CrewDTO();
        crew.setPeliculaId(movieId);
        List<PersonDTO> people = this.personService.findAll();
        List<CrewRoleDTO> roles = this.crewRoleService.findAllRolesExceptActor();
        model.addAttribute("crew", crew);
        model.addAttribute("people", people);
        model.addAttribute("roles", roles);
        model.addAttribute("movie", movieService.findPeliculaById(movieId));
        model.addAttribute("error", null);
        return "editor/edit_crew";
    }

}
