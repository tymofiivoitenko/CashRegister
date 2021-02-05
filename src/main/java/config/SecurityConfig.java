package config;

import java.util.*;

public class SecurityConfig {

    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_EMPLOYEE = "EMPLOYEE";
    public static final String ROLE_COMMODITY_EXPERT = "COMMODITY_EXPERT";

    // String: Role
    // List<String>: urlPatterns.
    private static final Map<String, List<String>> mapConfig = new HashMap<>();

    static {
        // Configure For "EMPLOYEE" Role.
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/userInfo");
        urlPatterns.add("/employeeTask");

        mapConfig.put(ROLE_EMPLOYEE, urlPatterns);

        // Configure For "MANAGER" Role.
        urlPatterns= new ArrayList<>();
        urlPatterns.add("/userInfo");
        urlPatterns.add("/managerTask");

        mapConfig.put(ROLE_MANAGER, urlPatterns);

        // Configure For "Commodity Expert" Role.
        urlPatterns= new ArrayList<>();
        urlPatterns.add("/products");
        urlPatterns.add("/createProducts");

        mapConfig.put(ROLE_COMMODITY_EXPERT, urlPatterns);

    }

    public static Set<String> getAllAppRoles() {
        return mapConfig.keySet();
    }

    public static List<String> getUrlPatternsForRole(String role) {
        return mapConfig.get(role);
    }

}