package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dto.GenreDTO;
import es.uma.taw.momdb.dto.MovieDTO;
import es.uma.taw.momdb.dto.ReviewDTO;
import es.uma.taw.momdb.dto.UserDTO;
import es.uma.taw.momdb.service.FavoriteService;
import es.uma.taw.momdb.service.GeneroService;
import es.uma.taw.momdb.service.MovieService;
import es.uma.taw.momdb.service.ReviewService;
import es.uma.taw.momdb.service.UserService;
import es.uma.taw.momdb.service.WatchlistService;
import es.uma.taw.momdb.ui.Filtro;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controlador para las funcionalidades del rol de Usuario.
 * Permite a los usuarios navegar por las películas, gestionar sus favoritos y watchlist,
 * editar su perfil y escribir reseñas.
 *
 * @author projectGeorge (Jorge Repullo - 59.6%), arrozet (Rubén Oliva - refactorización para auth, Javadocs - 33.1%), amcgiluma (Juan Manuel Valenzuela - 7.3%)
 */

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

    private static final int PAGE_SIZE = 48;

    @Autowired
    protected MovieService movieService;

    @Autowired
    protected GeneroService generoService;

    @Autowired
    protected FavoriteService favoriteService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected ReviewService reviewService;

    @Autowired
    protected WatchlistService watchlistService;

    /**
     * Inicializa la página principal del usuario.
     * Muestra una lista paginada de películas, aplicando filtros si existen en la sesión.
     *
     * @param page El número de página a mostrar.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return La vista "user/user" o una redirección si no hay autorización.
     */
    @GetMapping("/")
    public String doInit(@RequestParam(name = "page", defaultValue = "1") int page, HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        Filtro filtro = (Filtro) session.getAttribute("filtro");
        if (filtro != null && filtro.isEmpty()) {
            filtro = null;
            session.removeAttribute("filtro");
        }
        return this.listarPeliculasConFiltro(filtro, page, model, session);
    }

    /**
     * Aplica un filtro a la lista de películas.
     *
     * @param session La sesión HTTP.
     * @param filter El filtro a aplicar.
     * @param model El modelo para la vista.
     * @return La vista "user/user" con las películas filtradas.
     */
    @PostMapping("/filtrar")
    public String doFiltrar(HttpSession session, @ModelAttribute("filtro") Filtro filter, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        session.setAttribute("filtro", filter);
        return this.listarPeliculasConFiltro(filter, 1, model, session);
    }

    /**
     * Prepara el modelo con la lista de películas filtradas y paginadas.
     *
     * @param filtro El filtro a aplicar.
     * @param page El número de página.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return El nombre de la vista a renderizar.
     */
    protected String listarPeliculasConFiltro(Filtro filtro, int page, Model model, HttpSession session) {
        if (filtro == null) {
            filtro = new Filtro();
        }

        Page<MovieDTO> moviePage = this.movieService.findPaginatedWithFilters(filtro, page - 1, PAGE_SIZE);

        // Verificar estado de favoritos/watchlist para cada película
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user != null) {
            for (MovieDTO movie : moviePage.getContent()) {
                boolean isFavorite = this.favoriteService.isFavorite(user.getUserId(), movie.getId());
                movie.setFavorite(isFavorite);
                boolean isInWatchlist = this.watchlistService.isInWatchlist(user.getUserId(), movie.getId());
                movie.setInWatchlist(isInWatchlist);
            }
        }

        List<GenreDTO> generos = this.generoService.listarGeneros();
        model.addAttribute("generos", generos);
        model.addAttribute("movies", moviePage.getContent());
        model.addAttribute("filtro", filtro);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", moviePage.getTotalPages());
        return "user/user";
    }

    /**
     * Muestra los detalles de una película.
     *
     * @param id El ID de la película.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return La vista "user/movie_details" o una redirección si la película no existe.
     */
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

        List<ReviewDTO> reviews = this.reviewService.getReviewsByMovieId(id);
        BigDecimal averageReviewRating = this.reviewService.getAverageReviewRating(id);

        model.addAttribute("averageReviewRating", averageReviewRating);
        model.addAttribute("reviews", reviews);
        model.addAttribute("generos", movie.getGeneros());
        model.addAttribute("movie", movie);
        return "user/movie_details";
    }

    /**
     * Muestra la lista de películas favoritas del usuario.
     *
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return La vista "user/favorites".
     */
    @GetMapping("/favorites")
    public String verFavoritos(HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");
        List<MovieDTO> favoriteMovies = this.favoriteService.getUserFavorites(user.getUserId());

        model.addAttribute("movies", favoriteMovies);
        return "user/favorites";
    }

    /**
     * Añade una película a la lista de favoritos del usuario.
     *
     * @param movieId El ID de la película a añadir.
     * @param session La sesión HTTP.
     * @return Redirección a la página principal del usuario.
     */
    @PostMapping("/favorites/add")
    public String anyadirAFavoritos(@RequestParam("movieId") Integer movieId, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "error";
        }

        this.favoriteService.addToFavorites(user.getUserId(), movieId);
        return "redirect:/user/";
    }

    /**
     * Elimina una película de la lista de favoritos del usuario.
     *
     * @param movieId El ID de la película a eliminar.
     * @param session La sesión HTTP.
     * @return Redirección a la lista de favoritos.
     */
    @PostMapping("/favorites/remove")
    public String eliminarDeFavoritos(@RequestParam("movieId") Integer movieId, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "error";
        }

        this.favoriteService.removeFromFavorites(user.getUserId(), movieId);
        return "redirect:/user/favorites";
    }

    /**
     * Comprueba si una película está en la lista de favoritos del usuario.
     *
     * @param movieId El ID de la película a comprobar.
     * @param session La sesión HTTP.
     * @return "true" si es favorita, "false" en caso contrario.
     */
    @GetMapping("/favorites/check")
    @ResponseBody
    public String checkearFavorito(@RequestParam("movieId") Integer movieId, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "false";
        }

        boolean isFavorite = this.favoriteService.isFavorite(user.getUserId(), movieId);
        return isFavorite ? "true" : "false";
    }

    /**
     * Añade o elimina una película de la lista de favoritos.
     *
     * @param movieId El ID de la película.
     * @param action La acción a realizar ("add" o "remove").
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @param request La petición HTTP.
     * @return Redirección a la página anterior o a la principal.
     */
    @PostMapping("/favorites/toggle")
    public String toggleFavorito(@RequestParam("movieId") Integer movieId,
                                 @RequestParam("action") String action,
                                 HttpSession session, Model model, HttpServletRequest request) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");

        if ("add".equals(action)) {
            this.favoriteService.addToFavorites(user.getUserId(), movieId);
        } else if ("remove".equals(action)) {
            this.favoriteService.removeFromFavorites(user.getUserId(), movieId);
        }

        // Redirigir de vuelta a la página anterior SOLO si no es /user/filtrar
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty() && !referer.contains("user/filtrar")) {
            return "redirect:" + referer;
        }
        return "redirect:/user/";
    }

    /**
     * Muestra el perfil del usuario.
     *
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return La vista "user/profile".
     */
    @GetMapping("/profile")
    public String verPerfil(HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/";
        }

        model.addAttribute("userDTO", user);
        return "user/profile";
    }

    /**
     * Procesa la edición del perfil del usuario.
     *
     * @param userDTO DTO con los datos del usuario a actualizar.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return Redirección al perfil del usuario.
     */
    @PostMapping("/editProfile")
    public String editarPerfil(@ModelAttribute("userDTO") UserDTO userDTO, HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO updatedUserDTO = this.userService.updateUserProfile(userDTO);
        if (updatedUserDTO == null) {
            model.addAttribute("error", "Usuario no encontrado");
            return "user/profile";
        }

        // Actualizar el usuario en la sesión
        session.setAttribute("user", updatedUserDTO);
        return "redirect:/user/";
    }

    /**
     * Muestra el formulario para escribir o editar una reseña de una película.
     *
     * @param movieId El ID de la película.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return La vista "user/write_review".
     */
    @GetMapping("/review/write")
    public String escribirReview(@RequestParam("id") Integer movieId, HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");
        ReviewDTO review = this.reviewService.findReviewByUserAndMovie(user.getUserId(), movieId);
        if (review == null) {
            review = new ReviewDTO();
            review.setMovieId(movieId);
        }
        model.addAttribute("reviewDTO", review);
        model.addAttribute("movieId", movieId);

        return "user/write_review";
    }

    /**
     * Guarda o actualiza una reseña.
     *
     * @param reviewDTO DTO con los datos de la reseña.
     * @param from Cadena opcional para redirigir a la página de "Mis reseñas".
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return Redirección a la página de detalles de la película o a "Mis reseñas".
     */
    @PostMapping("/review/save")
    public String guardarReview(@ModelAttribute("reviewDTO") ReviewDTO reviewDTO,
                                @RequestParam(value = "from", required = false) String from,
                                HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");
        this.reviewService.saveOrUpdateReview(user.getUserId(), reviewDTO.getMovieId(), reviewDTO);

        if ("myreviews".equals(from)) {
            return "redirect:/user/userReviews";
        } else {
            return "redirect:/user/movie?id=" + reviewDTO.getMovieId();
        }
    }

    /**
     * Elimina una reseña.
     *
     * @param movieId El ID de la película.
     * @param userId El ID del usuario.
     * @param from Cadena opcional para redirigir a la página de "Mis reseñas".
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return Redirección a la página de detalles de la película o a "Mis reseñas".
     */
    @GetMapping("/movie/review/delete")
    public String eliminarReview(@RequestParam("movieId") Integer movieId,
                                 @RequestParam("userId") Integer userId,
                                 @RequestParam(value = "from", required = false) String from,
                                 HttpSession session,
                                 Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        this.reviewService.deleteReview(movieId, userId);
        if ("myreviews".equals(from)) {
            return "redirect:/user/userReviews";
        } else {
            return "redirect:/user/movie?id=" + movieId;
        }
    }

    /**
     * Muestra todas las reseñas escritas por el usuario.
     *
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return La vista "user/user_reviews".
     */
    @GetMapping("/userReviews")
    public String verMisReviews(HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");

        List<ReviewDTO> reviews = this.reviewService.getReviewsByUserId(user.getUserId());
        model.addAttribute("reviews", reviews);
        return "user/user_reviews";
    }

    /**
     * Muestra la watchlist del usuario.
     *
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return La vista "user/watchlist".
     */
    @GetMapping("/watchlist")
    public String verWatchlist(HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");

        List<MovieDTO> watchlistMovies = this.watchlistService.getUserWatchlist(user.getUserId());
        model.addAttribute("movies", watchlistMovies);
        return "user/watchlist";
    }

    /**
     * Añade una película a la watchlist del usuario.
     *
     * @param movieId El ID de la película a añadir.
     * @param session La sesión HTTP.
     * @return Redirección a la página principal del usuario.
     */
    @PostMapping("/watchlist/add")
    public String anyadirAWatchlist(@RequestParam("movieId") Integer movieId, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "error";
        }

        this.watchlistService.addToWatchlist(user.getUserId(), movieId);
        return "redirect:/user/";
    }

    /**
     * Elimina una película de la watchlist del usuario.
     *
     * @param movieId El ID de la película a eliminar.
     * @param session La sesión HTTP.
     * @return Redirección a la watchlist.
     */
    @PostMapping("/watchlist/remove")
    public String eliminarDeWatchlist(@RequestParam("movieId") Integer movieId, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "error";
        }

        this.watchlistService.removeFromWatchlist(user.getUserId(), movieId);
        return "redirect:/user/watchlist";
    }

    /**
     * Añade o elimina una película de la watchlist.
     *
     * @param movieId El ID de la película.
     * @param action La acción a realizar ("add" o "remove").
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @param request La petición HTTP.
     * @return Redirección a la página anterior o a la principal.
     */
    @PostMapping("/watchlist/toggle")
    public String toggleWatchlist(@RequestParam("movieId") Integer movieId,
                                  @RequestParam("action") String action,
                                  HttpSession session, Model model, HttpServletRequest request) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");

        if ("add".equals(action)) {
            watchlistService.addToWatchlist(user.getUserId(), movieId);
        } else if ("remove".equals(action)) {
            watchlistService.removeFromWatchlist(user.getUserId(), movieId);
        }

        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty() && !referer.contains("user/filtrar")) {
            return "redirect:" + referer;
        }

        return "redirect:/user/";
    }

    /**
     * Muestra la página para que un usuario pueda mejorar su cuenta a Recomendador.
     *
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return La vista "user/upgrade_pro" o una redirección si el usuario no es de tipo "usuario".
     */
    @GetMapping("/upgrade")
    public String upgradePage(HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (!"usuario".equals(user.getRolename())) {
            return "redirect:/user/";
        }
        return "user/upgrade_pro";
    }

    /**
     * Procesa la petición para mejorar la cuenta de un usuario a Recomendador.
     *
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return Redirección al área de recomendador.
     */
    @PostMapping("/perform-upgrade")
    public String performUpgrade(HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        UserDTO user = (UserDTO) session.getAttribute("user");
        UserDTO updatedUser = userService.upgradeUserToRecommender(user.getUserId());
        session.setAttribute("user", updatedUser);
        return "redirect:/recommender/";
    }
    
    /**
     * Comprueba si el usuario en sesión tiene el rol de usuario.
     * Utiliza el método centralizado de BaseController para realizar la verificación.
     * Si la autorización es exitosa, añade automáticamente el usuario al modelo.
     *
     * @param session La sesión HTTP actual.
     * @param model El modelo para la vista.
     * @return {@code true} si el usuario tiene el rol "usuario", {@code false} en caso contrario.
     */
    private boolean checkAuth(HttpSession session, Model model) {
        return super.checkAuth(session, model, "usuario");
    }
}