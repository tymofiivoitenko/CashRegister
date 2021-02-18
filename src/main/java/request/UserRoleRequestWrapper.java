package request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;

public class UserRoleRequestWrapper extends HttpServletRequestWrapper {

    private String user;
    private String realRole;
    private HttpServletRequest realRequest;

    public UserRoleRequestWrapper(String user, String role, HttpServletRequest request) {
        super(request);
        this.user = user;
        this.realRole = role;
        this.realRequest = request;
    }

    @Override
    public boolean isUserInRole(String roleToCheck) {
        if (realRole == null) {
            return this.realRequest.isUserInRole(roleToCheck);
        }
        return realRole.equals(roleToCheck);
    }

    @Override
    public Principal getUserPrincipal() {
        if (this.user == null) {
            return realRequest.getUserPrincipal();
        }

        // Return our user
        return () -> user;
    }
}