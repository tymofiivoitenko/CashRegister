package utils;

import config.SecurityConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public class SecurityUtils {

    // Check whether this 'request' is required to login or not.
    public static boolean isSecurityPage(HttpServletRequest request) {
        String urlPattern = UrlPatternUtils.getUrlPattern(request);

        Set<String> roles = SecurityConfig.getAllAppRoles();

        for (String role : roles) {
            List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(role);
            if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }

    // Check if this 'request' has a 'valid role'?
    public static boolean hasPermission(HttpServletRequest request, String role) {
        String urlPattern = UrlPatternUtils.getUrlPattern(request);

        // Check if given role exists in security config
        Set<String> allRoles = SecurityConfig.getAllAppRoles();
        if (!allRoles.contains(role)) {
            throw new IllegalStateException();
        }

        // Fetch all urlPatterns, which can be accessed by this role
        List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(role);

        // Check if user can access this url
        if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
            return true;
        }

        return false;
    }
}