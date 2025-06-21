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

import java.util.List;

/*
 * @author - projectGeorge (Jorge Repullo)
 * @co-authors - arrozet (Rubén Oliva - refactorización para auth)
 */

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

    private static final int PAGE_SIZE = 12;

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

    @PostMapping("/filtrar")
    public String doFiltrar(HttpSession session, @ModelAttribute("filtro") Filtro filter, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        session.setAttribute("filtro", filter);
        return this.listarPeliculasConFiltro(filter, 1, model, session);
    }

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
        model.addAttribute("reviews", reviews);
        model.addAttribute("generos", movie.getGeneros());
        model.addAttribute("movie", movie);
        return "user/movie_details";
    }

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

    @PostMapping("/favorites/add")
    public String anyadirAFavoritos(@RequestParam("movieId") Integer movieId, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "error";
        }

        this.favoriteService.addToFavorites(user.getUserId(), movieId);
        return "redirect:/user/";
    }

    @PostMapping("/favorites/remove")
    public String eliminarDeFavoritos(@RequestParam("movieId") Integer movieId, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "error";
        }

        this.favoriteService.removeFromFavorites(user.getUserId(), movieId);
        return "redirect:/user/favorites";
    }

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

    @PostMapping("/review/save")
    public String guardarReview(@ModelAttribute("reviewDTO") ReviewDTO reviewDTO,
                                @RequestParam(value = "from", required = false) String from,
                                HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");
        this.reviewService.saveOrUpdateReview(user.getUserId(), reviewDTO.getMovieId(), reviewDTO);
        this.reviewService.updateMovieRating(reviewDTO.getMovieId());

        if ("myreviews".equals(from)) {
            return "redirect:/user/userReviews";
        } else {
            return "redirect:/user/movie?id=" + reviewDTO.getMovieId();
        }
    }

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

    @PostMapping("/watchlist/add")
    public String anyadirAWatchlist(@RequestParam("movieId") Integer movieId, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "error";
        }

        this.watchlistService.addToWatchlist(user.getUserId(), movieId);
        return "redirect:/user/";
    }

    @PostMapping("/watchlist/remove")
    public String eliminarDeWatchlist(@RequestParam("movieId") Integer movieId, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "error";
        }

        this.watchlistService.removeFromWatchlist(user.getUserId(), movieId);
        return "redirect:/user/watchlist";
    }

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