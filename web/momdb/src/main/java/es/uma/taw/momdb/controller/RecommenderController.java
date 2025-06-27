package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dto.*;
import es.uma.taw.momdb.service.*;
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
 * Controlador para las funcionalidades del rol Recomendador.
 * Gestiona la visualización de películas, favoritos, watchlist, perfiles de usuario,
 * reseñas y recomendaciones de películas.
 *
 * @author amcgiluma (Juan Manuel Valenzuela - 69.4%), arrozet (Rubén Oliva, Javadocs - 30.0%), projectGeorge (Jorge Repullo - 0.6%)
 */
@Controller
@RequestMapping("/recommender")
public class RecommenderController extends BaseController{

    private static final int PAGE_SIZE = 48;

    @Autowired
    protected MovieService movieService;

    @Autowired
    protected GeneroService generoService;

    @Autowired
    protected FavoriteService favoriteService;


    @Autowired
    protected ReviewService reviewService;

    @Autowired
    protected WatchlistService watchlistService;

    @Autowired
    protected RecommendationService recommendationService;

    @Autowired
    protected UserService userService;

    /**
     * Inicializa la página principal del recomendador.
     * Muestra una lista de películas sin filtrar.
     *
     * @param page El número de página a mostrar.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return La vista "recommender/recommender" o una redirección si no hay autorización.
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
     * @return La vista "recommender/recommender" con las películas filtradas.
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
        return "recommender/recommender";
    }

    /**
     * Muestra los detalles de una película.
     *
     * @param id El ID de la película.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return La vista "recommender/movie_details" o una redirección si la película no existe.
     */
    @GetMapping("/movie")
    public String verPelicula(@RequestParam("id") Integer id, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        MovieDTO movie = this.movieService.findPeliculaById(id);
        if (movie == null) {
            return "redirect:/recommender/";
        }

        List<ReviewDTO> reviews = reviewService.getReviewsByMovieId(id);
        model.addAttribute("reviews", reviews);

        BigDecimal averageReviewRating = this.reviewService.getAverageReviewRating(id);
        model.addAttribute("averageReviewRating", averageReviewRating);

        List<MovieDTO> recommendedMovies = this.movieService.findRecommendedMovies(movie.getId(), 4);
        model.addAttribute("recommendedMovies", recommendedMovies);

        model.addAttribute("generos", movie.getGeneros());
        model.addAttribute("movie", movie);
        return "recommender/movie_details";
    }

    /**
     * Muestra la lista de películas favoritas del usuario.
     *
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return La vista "recommender/favorites".
     */
    @GetMapping("/favorites")
    public String verFavoritos(HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");
        List<MovieDTO> favoriteMovies = favoriteService.getUserFavorites(user.getUserId());

        model.addAttribute("movies", favoriteMovies);
        return "recommender/favorites";
    }

    /**
     * Añade una película a la lista de favoritos del usuario.
     *
     * @param movieId El ID de la película a añadir.
     * @param session La sesión HTTP.
     * @return Redirección a la página principal del recomendador.
     */
    @PostMapping("/favorites/add")
    public String addToFavorites(@RequestParam("movieId") Integer movieId, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "error";
        }

        favoriteService.addToFavorites(user.getUserId(), movieId);
        return "redirect:/recommender/";
    }

    /**
     * Elimina una película de la lista de favoritos del usuario.
     *
     * @param movieId El ID de la película a eliminar.
     * @param session La sesión HTTP.
     * @return Redirección a la lista de favoritos.
     */
    @PostMapping("/favorites/remove")
    public String removeFromFavorites(@RequestParam("movieId") Integer movieId, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "error";
        }

        favoriteService.removeFromFavorites(user.getUserId(), movieId);
        return "redirect:/recommender/favorites";
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
    public String checkFavorite(@RequestParam("movieId") Integer movieId, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "false";
        }

        boolean isFavorite = favoriteService.isFavorite(user.getUserId(), movieId);
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
    public String toggleFavorite(@RequestParam("movieId") Integer movieId,
                                 @RequestParam("action") String action,
                                 HttpSession session, Model model, HttpServletRequest request) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");

        if ("add".equals(action)) {
            favoriteService.addToFavorites(user.getUserId(), movieId);
        } else if ("remove".equals(action)) {
            favoriteService.removeFromFavorites(user.getUserId(), movieId);
        }

        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty() && !referer.contains("recommender/filtrar")) {
            return "redirect:" + referer;
        }
        return "redirect:/recommender/";
    }

    /**
     * Muestra el perfil del usuario.
     *
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return La vista "recommender/profile".
     */
    @GetMapping("/profile")
    public String verPerfil(HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "redirect:/recommender/";
        }

        model.addAttribute("userDTO", user);
        return "recommender/profile";
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
        return "redirect:/recommender/";
    }

    /**
     * Muestra el formulario para escribir o editar una reseña de una película.
     *
     * @param movieId El ID de la película.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return La vista "recommender/write_review".
     */
    @GetMapping("/review/write")
    public String writeReview(@RequestParam("id") Integer movieId, HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");
        ReviewDTO review = reviewService.findReviewByUserAndMovie(user.getUserId(), movieId);
        if (review == null) {
            review = new ReviewDTO();
            review.setMovieId(movieId);
        }
        model.addAttribute("reviewDTO", review);
        model.addAttribute("movieId", movieId);

        return "recommender/write_review";
    }

    /**
     * Guarda o actualiza una reseña.
     *
     * @param reviewDTO DTO con los datos de la reseña.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return Redirección a la página de detalles de la película.
     */
    @PostMapping("/review/save")
    public String saveReview(@ModelAttribute("reviewDTO") ReviewDTO reviewDTO,
                             HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");
        reviewService.saveOrUpdateReview(user.getUserId(), reviewDTO.getMovieId(), reviewDTO);

        return "redirect:/recommender/movie?id=" + reviewDTO.getMovieId();
    }

    /**
     * Elimina una reseña.
     *
     * @param movieId El ID de la película.
     * @param userId El ID del usuario.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return Redirección a la página de detalles de la película.
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
        return "redirect:/recommender/movie?id=" + movieId;
    }

    /**
     * Muestra todas las reseñas escritas por el usuario.
     *
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return La vista "recommender/recommender_reviews".
     */
    @GetMapping("/userReviews")
    public String verMisReviews(HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");

        List<ReviewDTO> reviews = this.reviewService.getReviewsByUserId(user.getUserId());
        model.addAttribute("reviews", reviews);
        return "recommender/recommender_reviews";
    }

    /**
     * Muestra la watchlist del usuario.
     *
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return La vista "recommender/watchlist".
     */
    @GetMapping("/watchlist")
    public String verWatchlist(HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");
        List<MovieDTO> watchlistMovies = watchlistService.getUserWatchlist(user.getUserId());
        model.addAttribute("movies", watchlistMovies);

        return "recommender/watchlist";
    }

    /**
     * Añade una película a la watchlist del usuario.
     *
     * @param movieId El ID de la película a añadir.
     * @param session La sesión HTTP.
     * @return Redirección a la página principal del recomendador.
     */
    @PostMapping("/watchlist/add")
    public String anyadirAWatchlist(@RequestParam("movieId") Integer movieId, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "error";
        }

        watchlistService.addToWatchlist(user.getUserId(), movieId);
        return "redirect:/recommender/";
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
        watchlistService.removeFromWatchlist(user.getUserId(), movieId);

        return "redirect:/recommender/watchlist";
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

        return "redirect:/recommender/";
    }

    /**
     * Muestra el formulario para añadir una recomendación a una película.
     *
     * @param originalMovieId El ID de la película a la que se le añade la recomendación.
     * @param page El número de página para la lista de películas.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return La vista "recommender/add_recommendation".
     */
    @GetMapping("/recommend/add")
    public String addRecommendation(@RequestParam("id") Integer originalMovieId,
                                    @RequestParam(name = "page", defaultValue = "1") int page,
                                    Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        MovieDTO originalMovie = this.movieService.findPeliculaById(originalMovieId);
        if (originalMovie == null) {
            return "redirect:/recommender/";
        }

        Filtro filtro = new Filtro();
        Page<MovieDTO> moviePage = movieService.findPaginatedWithFilters(filtro, page - 1, PAGE_SIZE);
        List<GenreDTO> generos = this.generoService.listarGeneros();

        model.addAttribute("originalMovie", originalMovie);
        model.addAttribute("movies", moviePage.getContent());
        model.addAttribute("generos", generos);
        model.addAttribute("filtro", filtro);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", moviePage.getTotalPages());

        return "recommender/add_recommendation";
    }

    /**
     * Filtra la lista de películas en el formulario de añadir recomendación.
     *
     * @param originalMovieId El ID de la película original.
     * @param filter El filtro a aplicar.
     * @param page El número de página.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return La vista "recommender/add_recommendation" con la lista de películas filtrada.
     */
    @PostMapping("/recommend/add/filtrar")
    public String doFiltrarForRecommendation(@RequestParam("originalMovieId") Integer originalMovieId,
                                             @ModelAttribute("filtro") Filtro filter,
                                             @RequestParam(name = "page", defaultValue = "1") int page,
                                             Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        MovieDTO originalMovie = this.movieService.findPeliculaById(originalMovieId);
        if (originalMovie == null) {
            return "redirect:/recommender/";
        }

        Page<MovieDTO> moviePage = movieService.findPaginatedWithFilters(filter, page - 1, PAGE_SIZE);
        List<GenreDTO> generos = this.generoService.listarGeneros();

        model.addAttribute("originalMovie", originalMovie);
        model.addAttribute("movies", moviePage.getContent());
        model.addAttribute("generos", generos);
        model.addAttribute("filtro", filter);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", moviePage.getTotalPages());

        return "recommender/add_recommendation";
    }

    /**
     * Guarda una nueva recomendación de una película por parte de un usuario.
     *
     * @param originalMovieId El ID de la película original.
     * @param recommendedMovieId El ID de la película recomendada.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return Redirección a la página de visualización de recomendaciones.
     */
    @PostMapping("/recommend/save")
    public String saveRecommendation(@RequestParam("originalMovieId") Integer originalMovieId,
                                     @RequestParam("recommendedMovieId") Integer recommendedMovieId,
                                     HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");
        recommendationService.saveRecommendation(user.getUserId(), originalMovieId, recommendedMovieId);

        return "redirect:/recommender/recommend/view?id=" + originalMovieId;
    }

    /**
     * Muestra las recomendaciones para una película.
     * Incluye recomendaciones basadas en género, recomendaciones de otros usuarios y las del usuario actual.
     *
     * @param originalMovieId El ID de la película.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return La vista "recommender/view_recommendations".
     */
    @GetMapping("/recommend/view")
    public String viewRecommendations(@RequestParam("id") Integer originalMovieId, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");

        MovieDTO originalMovie = this.movieService.findPeliculaById(originalMovieId);
        if (originalMovie == null) {
            return "redirect:/recommender/";
        }

        // Recomendaciones basadas en género
        List<MovieDTO> genreRecommendedMovies = this.movieService.findRecommendedMovies(originalMovieId, 10);

        // Recomendaciones de usuarios (agregadas y ordenadas)
        List<RecommendationDTO> userRecommendations = this.recommendationService.findAggregatedUserRecommendationsForMovie(originalMovieId);

        // Recomendaciones del usuario actual (para poder borrarlas)
        List<RecommendationDTO> currentUserRecommendations = this.recommendationService.findCurrentUserRecommendationsForMovie(originalMovieId, user.getUserId());

        model.addAttribute("originalMovie", originalMovie);
        model.addAttribute("genreRecommendedMovies", genreRecommendedMovies);
        model.addAttribute("userRecommendations", userRecommendations);
        model.addAttribute("currentUserRecommendations", currentUserRecommendations);

        return "recommender/view_recommendations";
    }

    /**
     * Elimina una recomendación hecha por el usuario.
     *
     * @param originalMovieId El ID de la película original.
     * @param recommendedMovieId El ID de la película recomendada.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return Redirección a la página de visualización de recomendaciones.
     */
    @PostMapping("/recommend/delete")
    public String deleteRecommendation(@RequestParam("originalMovieId") Integer originalMovieId,
                                       @RequestParam("recommendedMovieId") Integer recommendedMovieId,
                                       HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");
        recommendationService.deleteRecommendation(user.getUserId(), originalMovieId, recommendedMovieId);

        return "redirect:/recommender/recommend/view?id=" + originalMovieId;
    }


    /**
     * Comprueba si el usuario en sesión tiene el rol de recomendador.
     * Utiliza el método centralizado de BaseController para realizar la verificación.
     * Si la autorización es exitosa, añade automáticamente el usuario al modelo.
     *
     * @param session La sesión HTTP actual.
     * @param model El modelo para la vista.
     * @return {@code true} si el usuario tiene el rol "recomendador", {@code false} en caso contrario.
     */
    private boolean checkAuth(HttpSession session, Model model) {
        return super.checkAuth(session, model, "recomendador");
    }

}