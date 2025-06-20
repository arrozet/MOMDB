package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dao.UserRepository;
import es.uma.taw.momdb.dto.GenreDTO;
import es.uma.taw.momdb.dto.MovieDTO;
import es.uma.taw.momdb.dto.ReviewDTO;
import es.uma.taw.momdb.dto.UserDTO;
import es.uma.taw.momdb.entity.User;
import es.uma.taw.momdb.service.FavoriteService;
import es.uma.taw.momdb.service.GeneroService;
import es.uma.taw.momdb.service.MovieService;
import es.uma.taw.momdb.service.ReviewService;
import es.uma.taw.momdb.ui.Filtro;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * @author - amcgiluma (Juan Manuel Valenzuela)
 */
@Controller
@RequestMapping("/recommender")
public class RecommenderController extends BaseController{

    @Autowired
    protected MovieService movieService;

    @Autowired
    protected GeneroService generoService;

    @Autowired
    protected FavoriteService favoriteService;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ReviewService reviewService;

    @GetMapping("/")
    public String doInit(HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        } else {
            return this.listarPeliculasConFiltro(null, model, session);
        }
    }

    @PostMapping("/filtrar")
    public String doFiltrar(HttpSession session, @ModelAttribute("filtro") Filtro filter, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        } else {
            return this.listarPeliculasConFiltro(filter, model, session);
        }
    }

    protected String listarPeliculasConFiltro(Filtro filtro, Model model, HttpSession session) {
        List<MovieDTO> movies;

        if (filtro == null) {
            filtro = new Filtro();
            movies = movieService.listarPeliculas();
        } else if (filtro.getTexto() != null && !filtro.getTexto().isBlank()) {
            // Si hay texto, filtra solo por texto
            movies = movieService.listarPeliculas(filtro.getTexto());
        } else {
            // Si no hay texto, filtra por los selects
            movies = movieService.listarPeliculasBySelectFilters(filtro);
        }

        // Verificar estado de favoritos para cada película
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user != null) {
            for (MovieDTO movie : movies) {
                boolean isFavorite = favoriteService.isFavorite(user.getUserId(), movie.getId());
                movie.setFavorite(isFavorite);
            }
        }

        List<GenreDTO> generos = this.generoService.listarGeneros();
        model.addAttribute("generos", generos);
        model.addAttribute("movies", movies);
        model.addAttribute("filtro", filtro);
        return "recommender/recommender";
    }

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

        List<MovieDTO> recommendedMovies = this.movieService.findRecommendedMovies(movie.getId(), 4);
        model.addAttribute("recommendedMovies", recommendedMovies);

        model.addAttribute("generos", movie.getGeneros());
        model.addAttribute("movie", movie);
        return "recommender/movie_details";
    }

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

    @PostMapping("/favorites/add")
    public String addToFavorites(@RequestParam("movieId") Integer movieId, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "error";
        }

        favoriteService.addToFavorites(user.getUserId(), movieId);
        return "redirect:/recommender/";
    }

    @PostMapping("/favorites/remove")
    public String removeFromFavorites(@RequestParam("movieId") Integer movieId, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "error";
        }

        favoriteService.removeFromFavorites(user.getUserId(), movieId);
        return "redirect:/recommender/favorites";
    }

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

    @PostMapping("/editProfile")
    public String editarPerfil(@ModelAttribute("userDTO") UserDTO userDTO, HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        User user = this.userRepository.findById(userDTO.getUserId()).orElse(null);
        if (user == null) {
            model.addAttribute("error", "Usuario no encontrado");
            return "recommender/profile";
        }
        // Actualizar los campos editables
        user.setUsername(userDTO.getUsername());
        user.setProfilePicLink(userDTO.getProfilePic());
        this.userRepository.save(user);
        // Actualizar el usuario en la sesión
        UserDTO updatedUserDTO = user.toDTO();
        session.setAttribute("user", updatedUserDTO);
        return "redirect:/recommender/profile";
    }

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

    @PostMapping("/review/save")
    public String saveReview(@ModelAttribute("reviewDTO") ReviewDTO reviewDTO,
                             HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");
        reviewService.saveOrUpdateReview(user.getUserId(), reviewDTO.getMovieId(), reviewDTO);
        reviewService.updateMovieRating(reviewDTO.getMovieId());

        return "redirect:/recommender/movie?id=" + reviewDTO.getMovieId();
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
        return "redirect:/recommender/movie?id=" + movieId;
    }
    @GetMapping("/recommend/add")
    public String addRecommendation(@RequestParam("id") Integer originalMovieId, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        MovieDTO originalMovie = this.movieService.findPeliculaById(originalMovieId);
        if (originalMovie == null) {
            return "redirect:/recommender/";
        }

        List<MovieDTO> allMovies = movieService.listarPeliculas();
        List<GenreDTO> generos = this.generoService.listarGeneros();

        model.addAttribute("originalMovie", originalMovie);
        model.addAttribute("movies", allMovies);
        model.addAttribute("generos", generos);
        model.addAttribute("filtro", new Filtro());

        return "recommender/add_recommendation";
    }

    @PostMapping("/recommend/add/filtrar")
    public String doFiltrarForRecommendation(@RequestParam("originalMovieId") Integer originalMovieId,
                                             @ModelAttribute("filtro") Filtro filter,
                                             Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        MovieDTO originalMovie = this.movieService.findPeliculaById(originalMovieId);
        if (originalMovie == null) {
            return "redirect:/recommender/";
        }

        List<MovieDTO> filteredMovies;
        if (filter.getTexto() != null && !filter.getTexto().isBlank()) {
            filteredMovies = movieService.listarPeliculas(filter.getTexto());
        } else {
            filteredMovies = movieService.listarPeliculasBySelectFilters(filter);
        }

        List<GenreDTO> generos = this.generoService.listarGeneros();

        model.addAttribute("originalMovie", originalMovie);
        model.addAttribute("movies", filteredMovies);
        model.addAttribute("generos", generos);
        model.addAttribute("filtro", filter);

        return "recommender/add_recommendation";
    }

    @PostMapping("/recommend/save")
    public String saveRecommendation(@RequestParam("originalMovieId") Integer originalMovieId,
                                     @RequestParam("recommendedMovieId") Integer recommendedMovieId,
                                     HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        // Lógica de guardado no implementada, solo redirige.
        return "redirect:/recommender/movie?id=" + originalMovieId;
    }

    @GetMapping("/recommend/view")
    public String viewRecommendations(@RequestParam("id") Integer originalMovieId, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        MovieDTO originalMovie = this.movieService.findPeliculaById(originalMovieId);
        if (originalMovie == null) {
            return "redirect:/recommender/";
        }

        // Obtenemos las películas recomendadas por género.
        // El método findRecommendedMovies ya excluye la película original.
        // Usamos un límite alto para que aparezcan "todas".
        List<MovieDTO> recommendedMovies = this.movieService.findRecommendedMovies(originalMovieId, 100);

        model.addAttribute("originalMovie", originalMovie);
        model.addAttribute("recommendedMovies", recommendedMovies);

        return "recommender/view_recommendations";
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
        return super.checkAuth(session, model, "recomendador");
    }
}
