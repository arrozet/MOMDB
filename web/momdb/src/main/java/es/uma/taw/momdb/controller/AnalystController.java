package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dao.MovieRepository;
import es.uma.taw.momdb.dto.MovieDTO;
import es.uma.taw.momdb.dto.UserDTO;
import es.uma.taw.momdb.entity.Movie;
import es.uma.taw.momdb.service.MovieService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
