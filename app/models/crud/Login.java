package models.crud;

import models.entity.Admin;
import models.crud.AdminService;

/**
 * DESCRIPTION
 *
 * @author harakazuhiro
 * @since 2013/06/15 10:02
 */
public class Login {
    public String username;
    public String password;

    public String validate() throws java.security.NoSuchAlgorithmException {
        if (AdminService.authenticate(username, password) == null) {
            return "Invalid user or password";
        }
        return null;
    }
}
