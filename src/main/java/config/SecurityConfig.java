package config;

import java.util.*;

public class SecurityConfig {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_CASHIER = "CASHIER";
    public static final String ROLE_SENIOR_CASHIER = "SENIOR_CASHIER";
    public static final String ROLE_COMMODITY_EXPERT = "COMMODITY_EXPERT";

    // String: Role
    // List<String>: urlPatterns.
    private static final Map<String, List<String>> mapConfig = new HashMap<>();

    static {
        // Configure urlPatterns for "ADMIN" Role.
        List<String> urlPatterns = new ArrayList<>();

        urlPatterns.add("/admin");

        mapConfig.put(ROLE_ADMIN, urlPatterns);

        // Configure urlPatterns for "Commodity Expert" Role.
        urlPatterns = new ArrayList<>();
        urlPatterns.add("/products");

        mapConfig.put(ROLE_COMMODITY_EXPERT, urlPatterns);

        // Configure urlPatterns for "CASHIER" Role.
        urlPatterns = new ArrayList<>();
        urlPatterns.add("/catalog");
        urlPatterns.add("/all-receipts");
        urlPatterns.add("/receipt");

        mapConfig.put(ROLE_CASHIER, urlPatterns);

        // Configure urlPatterns for "SENIOR CASHIER" Role.
        urlPatterns = new ArrayList<>();
        urlPatterns.add("/catalog");
        urlPatterns.add("/all-receipts");
        urlPatterns.add("/receipt");
        urlPatterns.add("/reports");
    }

    public static Set<String> getAllAppRoles() {
        return mapConfig.keySet();
    }

    public static List<String> getUrlPatternsForRole(String role) {
        return mapConfig.get(role);
    }

}