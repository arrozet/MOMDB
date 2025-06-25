package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

/**
 * Controlador base que proporciona funcionalidades comunes de autenticación y autorización
 * para el resto de controladores de la aplicación.
 * Centraliza la lógica de comprobación de sesión y roles.
 * 
 * @author arrozet (Rubén Oliva)
 */
public class BaseController {
    /**
     * Obtiene el objeto UserDTO de la sesión HTTP.
     *
     * @param session La sesión HTTP actual.
     * @return El UserDTO si existe, o null en caso contrario.
     */
    protected UserDTO getSessionUser(HttpSession session) {
        return (UserDTO) session.getAttribute("user");
    }

    /**
     * Comprueba si hay un usuario autenticado en la sesión HTTP.
     *
     * @param session La sesión HTTP actual.
     * @return {@code true} si hay un usuario autenticado, {@code false} en caso contrario.
     */
    protected boolean isAuthenticated(HttpSession session) {
        return getSessionUser(session) != null;
    }

    /**
     * Comprueba si el usuario en sesión cumple con un rol específico.
     * Si la autorización falla, añade un mensaje de error al modelo.
     * Si la autorización es exitosa, añade el objeto UserDTO al modelo.
     *
     * @param session La sesión HTTP actual.
     * @param model El modelo para la vista.
     * @param requiredRole El nombre del rol requerido. Si es null, solo comprueba si el usuario está autenticado.
     * @return {@code true} si el usuario está autorizado, {@code false} en caso contrario.
     */
    protected boolean checkAuth(HttpSession session, Model model, String requiredRole) {
        UserDTO user = getSessionUser(session);
        boolean authorized = false;

        if (isAuthenticated(session)) {
            // Para saber si puede estar en esta página, el rol debe ser el requerido
            authorized = requiredRole.equals(user.getRolename());
        }

        if (!authorized) {
            // Si no está autorizado, añade un mensaje de error
            model.addAttribute("error", "You are not authorized to access this page.");
            return false;
        }

        // Si está autorizado, añade el objeto UserDTO al modelo
        model.addAttribute("user", user);
        return true;
    }
}
