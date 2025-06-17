package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dto.UserDTO;
import jakarta.servlet.http.HttpSession;

/*
 * @author - arrozet (Rubén Oliva)
 * @co-authors -
 */

public class BaseController {
    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    protected boolean isAdmin(HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        return isAuthenticated(session) && user.getRolename().equals("admin");
    }
}
