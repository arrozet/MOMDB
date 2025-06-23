package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dto.MovieDTO;
import es.uma.taw.momdb.dto.MovieComparisonDTO;
import es.uma.taw.momdb.dto.UserDTO;
import es.uma.taw.momdb.dto.PersonDTO;
import es.uma.taw.momdb.service.GeneroService;
import es.uma.taw.momdb.service.MovieService;
import es.uma.taw.momdb.ui.Filtro;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * @author - edugbau (Eduardo González)
 * @co-authors - arrozet (Rubén Oliva - refactorización para auth)
 */

@Controller
@RequestMapping("/analyst")
public class AnalystController extends BaseController {
    private final int PAGE_SIZE = 48;

    @Autowired
    private MovieService movieService;

    @Autowired
    private GeneroService generoService;

    @GetMapping("/")
    public String doInit(@RequestParam(name = "page", defaultValue = "1") int page, Model model, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }



        // Spring Data JPA numera las páginas desde 0, por eso se resta 1.
        Page<MovieDTO> moviePage = movieService.findPaginated(page - 1, PAGE_SIZE);

        model.addAttribute("movies", moviePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", moviePage.getTotalPages());
        // No es necesario volver a añadir el usuario si ya está en la sesión,
        // pero es buena práctica pasarlo explícitamente al modelo.
        model.addAttribute("user", user);

        return "analyst/analyst";
    }

    @PostMapping("/filtrar")
    public String doFiltrar(HttpSession session, Model model, @RequestParam("filter") String filter) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        List<MovieDTO> movies;
        if (filter == null || filter.trim().isEmpty()) {
            movies = this.movieService.listarPeliculas(); // Cargar todas
        } else {
            movies = this.movieService.listarPeliculas(filter);
        }

        model.addAttribute("movies", movies);
        model.addAttribute("currentFilter", filter);
        return "analyst/analyst";
    }

    @GetMapping("/movie/{id}")
    public String doShowMovie(@PathVariable("id") Integer id, HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        MovieDTO movie = this.movieService.findPeliculaById(id);

        model.addAttribute("movie", movie);
        return "analyst/movie_details";
    }

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

    @GetMapping("/compare")
    public String showComparePage(@RequestParam(name = "page", defaultValue = "1") int page,
                                  @RequestParam(name = "movieId1", required = false) Integer movieId1,
                                  @RequestParam(name = "movieId2", required = false) Integer movieId2,
                                  HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        Filtro filtro = (Filtro) session.getAttribute("filtroCompare");
        if (filtro == null) {
            filtro = new Filtro();
        }

        Page<MovieDTO> moviePage = movieService.findPaginatedWithFilters(filtro, page - 1, PAGE_SIZE);

        model.addAttribute("movies", moviePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", moviePage.getTotalPages());
        model.addAttribute("filtro", filtro);
        model.addAttribute("generos", generoService.listarGeneros());

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
        if (filtro == null) {
            filtro = new Filtro();
        }
        Page<MovieDTO> moviePage = movieService.findPaginatedWithFilters(filtro, page - 1, PAGE_SIZE);

        model.addAttribute("movies", moviePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", moviePage.getTotalPages());
        model.addAttribute("filtro", filtro);
        model.addAttribute("generos", generoService.listarGeneros());

        return "analyst/compare";
    }

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
