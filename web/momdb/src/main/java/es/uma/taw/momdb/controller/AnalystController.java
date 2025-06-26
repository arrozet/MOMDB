package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dto.*;
import es.uma.taw.momdb.service.GeneroService;
import es.uma.taw.momdb.service.MovieService;
import es.uma.taw.momdb.service.ReviewService;
import es.uma.taw.momdb.ui.Filtro;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controlador para la gestión de las funcionalidades del analista.
 * Permite visualizar películas, compararlas y ver analíticas de géneros.
 *
 * @author edugbau (Eduardo González), arrozet (Rubén Oliva - refactorización para auth, Javadocs)
 */

@Controller
@RequestMapping("/analyst")
public class AnalystController extends BaseController {
    private final int PAGE_SIZE = 48;

    @Autowired
    private MovieService movieService;

    @Autowired
    private GeneroService generoService;

    @Autowired
    protected ReviewService reviewService;

    /**
     * Inicializa la página principal del analista.
     * Muestra una lista paginada de películas, aplicando filtros si existen en la sesión.
     *
     * @param page El número de página a mostrar.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return La vista "analyst/analyst" o una redirección si no hay autorización.
     */
    @GetMapping("/")
    public String doInit(@RequestParam(name = "page", defaultValue = "1") int page, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        Filtro filtro = (Filtro) session.getAttribute("filtroAnalyst");
        if (filtro != null && filtro.isEmpty()) {
            filtro = null;
            session.removeAttribute("filtroAnalyst");
        }
        return this.listarPeliculasConFiltro(filtro, page, model, session);
    }

    /**
     * Aplica un filtro a la lista de películas.
     * Guarda el filtro en la sesión y redirige a la vista principal para mostrar los resultados.
     *
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @param filter El filtro a aplicar.
     * @return La vista "analyst/analyst" con las películas filtradas.
     */
    @PostMapping("/filtrar")
    public String doFiltrar(HttpSession session, Model model, @ModelAttribute("filtro") Filtro filter) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        session.setAttribute("filtroAnalyst", filter);
        return this.listarPeliculasConFiltro(filter, 1, model, session);
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
        addFilteredMoviesToModel(filtro, page, model);
        return "analyst/analyst";
    }

    /**
     * Añade al modelo los datos necesarios para la vista de películas filtradas.
     * Incluye la lista de películas paginada, los géneros para el filtro,
     * el filtro actual y la información de paginación.
     *
     * @param filtro El filtro a aplicar a la búsqueda de películas. Si es nulo, se crea uno nuevo.
     * @param page El número de página actual.
     * @param model El modelo al que se añadirán los atributos para la vista.
     */
    private void addFilteredMoviesToModel(Filtro filtro, int page, Model model) {
        if (filtro == null) {
            filtro = new Filtro();
        }

        Page<MovieDTO> moviePage = this.movieService.findPaginatedWithFilters(filtro, page - 1, PAGE_SIZE);

        List<GenreDTO> generos = this.generoService.listarGeneros();
        model.addAttribute("generos", generos);
        model.addAttribute("movies", moviePage.getContent());
        model.addAttribute("filtro", filtro);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", moviePage.getTotalPages());
    }

    /**
     * Muestra los detalles de una película específica.
     *
     * @param id El ID de la película a mostrar.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return La vista "analyst/movie_details" o una redirección si no hay autorización.
     */
    @GetMapping("/movie/{id}")
    public String doShowMovie(@PathVariable("id") Integer id, HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        MovieDTO movie = this.movieService.findPeliculaById(id);
        if (movie == null) {
            return "redirect:/analyst/";
        }

        List<ReviewDTO> reviews = this.reviewService.getReviewsByMovieId(id);
        BigDecimal averageReviewRating = this.reviewService.getAverageReviewRating(id);

        model.addAttribute("averageReviewRating", averageReviewRating);
        model.addAttribute("reviews", reviews);
        model.addAttribute("generos", movie.getGeneros());
        model.addAttribute("movie", movie);
        return "analyst/movie_details";
    }

    /**
     * Muestra una comparativa de métricas de una película con la media total y la media por género.
     *
     * @param id El ID de la película a comparar.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return La vista "analyst/movie_comparison" o una redirección si no hay autorización.
     */
    @GetMapping("/movie/{id}/comparison")
    public String doShowMovieComparison(@PathVariable("id") Integer id, HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        MovieDTO movie = this.movieService.findPeliculaById(id);
        model.addAttribute("movie", movie);

        List<MovieComparisonDTO> comparisons = new ArrayList<>();

        // Popularity
        MovieComparisonDTO popularityComparison = new MovieComparisonDTO();
        popularityComparison.setMetricName("Popularity");
        popularityComparison.setMovieValue(movie.getPopularity());
        popularityComparison.setOverallAverage(movieService.getAveragePopularity());
        if (movie.getGeneros() != null && !movie.getGeneros().isEmpty()) {
            popularityComparison.setGenreAverage(movieService.getAveragePopularityByGenre(movie.getGeneros().get(0).getId()));
        }
        comparisons.add(popularityComparison);

        // Revenue
        MovieComparisonDTO revenueComparison = new MovieComparisonDTO();
        revenueComparison.setMetricName("Revenue");
        revenueComparison.setMovieValue(movie.getIngresos());
        revenueComparison.setOverallAverage(movieService.getAverageRevenue());
        if (movie.getGeneros() != null && !movie.getGeneros().isEmpty()) {
            revenueComparison.setGenreAverage(movieService.getAverageRevenueByGenre(movie.getGeneros().get(0).getId()));
        }
        comparisons.add(revenueComparison);

        // Vote Average
        MovieComparisonDTO voteAverageComparison = new MovieComparisonDTO();
        voteAverageComparison.setMetricName("Vote Average");
        voteAverageComparison.setMovieValue(movie.getMediaVotos());
        voteAverageComparison.setOverallAverage(movieService.getAverageVoteAverage());
        if (movie.getGeneros() != null && !movie.getGeneros().isEmpty()) {
            voteAverageComparison.setGenreAverage(movieService.getAverageVoteAverageByGenre(movie.getGeneros().get(0).getId()));
        }
        comparisons.add(voteAverageComparison);

        // Vote Count
        MovieComparisonDTO voteCountComparison = new MovieComparisonDTO();
        voteCountComparison.setMetricName("Vote Count");
        voteCountComparison.setMovieValue(movie.getVotos());
        voteCountComparison.setOverallAverage(movieService.getAverageVoteCount());
        if (movie.getGeneros() != null && !movie.getGeneros().isEmpty()) {
            voteCountComparison.setGenreAverage(movieService.getAverageVoteCountByGenre(movie.getGeneros().get(0).getId()));
        }
        comparisons.add(voteCountComparison);

        // Runtime
        MovieComparisonDTO runtimeComparison = new MovieComparisonDTO();
        runtimeComparison.setMetricName("Runtime");
        runtimeComparison.setMovieValue(movie.getDuracion());
        runtimeComparison.setOverallAverage(movieService.getAverageRuntime());
        if (movie.getGeneros() != null && !movie.getGeneros().isEmpty()) {
            runtimeComparison.setGenreAverage(movieService.getAverageRuntimeByGenre(movie.getGeneros().get(0).getId()));
        }
        comparisons.add(runtimeComparison);

        model.addAttribute("comparisons", comparisons);

        return "analyst/movie_comparison";
    }

    /**
     * Muestra la página para comparar dos películas.
     * Carga las películas seleccionadas y su reparto en común si se proporcionan IDs.
     *
     * @param page El número de página para la lista de películas.
     * @param movieId1 El ID de la primera película a comparar (opcional).
     * @param movieId2 El ID de la segunda película a comparar (opcional).
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return La vista "analyst/compare" o una redirección si no hay autorización.
     */
    @GetMapping("/compare")
    public String showComparePage(@RequestParam(name = "page", defaultValue = "1") int page,
                                  @RequestParam(name = "movieId1", required = false) Integer movieId1,
                                  @RequestParam(name = "movieId2", required = false) Integer movieId2,
                                  HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        Filtro filtro = (Filtro) session.getAttribute("filtroCompare");
        addFilteredMoviesToModel(filtro, page, model);

        if (movieId1 != null) {
            model.addAttribute("movie1", movieService.findPeliculaById(movieId1));
        }

        if (movieId2 != null) {
            model.addAttribute("movie2", movieService.findPeliculaById(movieId2));
        }

        if (movieId1 != null && movieId2 != null) {
            Map<String, List<PersonDTO>> commonCrew = movieService.getCommonCrew(movieId1, movieId2);
            model.addAttribute("sharedCast", commonCrew.get("sharedCast"));
            model.addAttribute("sharedCrew", commonCrew.get("sharedCrew"));
        }

        return "analyst/compare";
    }

    /**
     * Realiza la comparación entre dos películas.
     * Muestra los detalles de ambas películas y el reparto que tienen en común.
     *
     * @param movieId1 El ID de la primera película.
     * @param movieId2 El ID de la segunda película.
     * @param page El número de página actual de la lista de películas.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return La vista "analyst/compare" con los resultados de la comparación.
     */
    @PostMapping("/compare")
    public String doCompare(@RequestParam("movieId1") Integer movieId1,
                            @RequestParam("movieId2") Integer movieId2,
                            @RequestParam(name = "page", defaultValue = "1") int page,
                            HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        MovieDTO movie1 = movieService.findPeliculaById(movieId1);
        MovieDTO movie2 = movieService.findPeliculaById(movieId2);

        model.addAttribute("movie1", movie1);
        model.addAttribute("movie2", movie2);

        Map<String, List<PersonDTO>> commonCrew = movieService.getCommonCrew(movieId1, movieId2);

        model.addAttribute("sharedCast", commonCrew.get("sharedCast"));
        model.addAttribute("sharedCrew", commonCrew.get("sharedCrew"));

        Filtro filtro = (Filtro) session.getAttribute("filtroCompare");
        addFilteredMoviesToModel(filtro, page, model);

        return "analyst/compare";
    }

    /**
     * Aplica un filtro a la lista de películas en la página de comparación.
     * Guarda el filtro en sesión y redirige a la página de comparación manteniendo las películas seleccionadas.
     *
     * @param filtro El filtro a aplicar.
     * @param movieId1 El ID de la primera película seleccionada (opcional).
     * @param movieId2 El ID de la segunda película seleccionada (opcional).
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return Una redirección a la página de comparación.
     */
    @PostMapping("/compare/filtrar")
    public String doFiltrarCompare(@ModelAttribute("filtro") Filtro filtro,
                                   @RequestParam(name = "movieId1", required = false) String movieId1,
                                   @RequestParam(name = "movieId2", required = false) String movieId2,
                                   HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        session.setAttribute("filtroCompare", filtro);

        StringBuilder redirectUrl = new StringBuilder("redirect:/analyst/compare");
        boolean hasParams = false;
        if (movieId1 != null && !movieId1.isEmpty()) {
            redirectUrl.append("?movieId1=").append(movieId1);
            hasParams = true;
        }
        if (movieId2 != null && !movieId2.isEmpty()) {
            redirectUrl.append(hasParams ? "&" : "?").append("movieId2=").append(movieId2);
        }

        return redirectUrl.toString();
    }

    /**
     * Muestra la herramienta de analíticas por género.
     *
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return La vista "analyst/genre_analytics" o una redirección si no hay autorización.
     */
    @GetMapping("/genre-analytics")
    public String showTool2(HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        List<GenreAnalyticsDTO> analytics = generoService.getGenreAnalytics();
        model.addAttribute("genreAnalytics", analytics);

        return "analyst/genre_analytics";
    }

    @GetMapping("/aggregated-statistics")
    public String showAggregatedStatistics(HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        model.addAttribute("statistics", movieService.getAggregatedStatistics());

        return "analyst/aggregated_statistics";
    }

    /**
     * Comprueba si el usuario en sesión tiene el rol de analista.
     * Utiliza el método centralizado de BaseController para realizar la verificación.
     * Si la autorización es exitosa, añade automáticamente el usuario al modelo.
     *
     * @param session La sesión HTTP actual.
     * @param model El modelo para la vista.
     * @return {@code true} si el usuario es analista, {@code false} en caso contrario.
     */
    private boolean checkAuth(HttpSession session, Model model) {
        return super.checkAuth(session, model, "analista");
    }

    //TODO: entrar a una parte de analisis por película
}
