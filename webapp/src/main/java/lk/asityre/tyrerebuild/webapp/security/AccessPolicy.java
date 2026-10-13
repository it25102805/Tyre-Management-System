package lk.asityre.tyrerebuild.webapp.security;

import lk.asityre.tyrerebuild.webapp.model.Staff;
import lk.asityre.tyrerebuild.webapp.model.User;

import java.util.Map;
import java.util.Set;

public final class AccessPolicy {

    public static final String INVENTORY = "inventory";
    public static final String PRODUCTION = "production";
    public static final String ORDERS = "orders";
    public static final String COMPLAINTS = "complaints";
    public static final String ADMIN = "admin";

    /** Which pages each access role may open. */
    private static final Map<String, Set<String>> PAGES = Map.of(
            "ADMIN",              Set.of(INVENTORY, PRODUCTION, ORDERS, COMPLAINTS, ADMIN),
            "MANAGER",            Set.of(INVENTORY, PRODUCTION, ORDERS, COMPLAINTS),
            "CUSTOMER",           Set.of(ORDERS, COMPLAINTS),
            "PURCHASING_OFFICER", Set.of(INVENTORY),
            "WAREHOUSE_KEEPER",   Set.of(INVENTORY),
            "QUALITY_CHECKER",    Set.of(PRODUCTION),
            "EMPLOYEE",           Set.of(PRODUCTION, COMPLAINTS)
    );

    private AccessPolicy() { }

    /** Works out the access role from users.role and (for staff) the staff table. */
    public static String accessRole(User user, Staff staff) {
        String role = user.getRole() == null ? "" : user.getRole().toUpperCase();

        switch (role) {
            case "ADMIN":    return "ADMIN";
            case "MANAGER":  return "MANAGER";
            case "CUSTOMER": return "CUSTOMER";
            default:
                if (staff != null && staff.getRole() != null) {
                    switch (staff.getRole()) {
                        case "Purchasing Officer": return "PURCHASING_OFFICER";
                        case "Warehouse Keeper":   return "WAREHOUSE_KEEPER";
                        case "Quality Checker":    return "QUALITY_CHECKER";
                        case "Manager":            return "MANAGER";
                        default: break;
                    }
                }
                return "EMPLOYEE";
        }
    }

    public static Set<String> pagesFor(String accessRole) {
        return PAGES.getOrDefault(accessRole, Set.of());
    }

    /** Which protected page a URL belongs to, or null if it is not a restricted area. */
    public static String pageForPath(String path) {
        if (isUnder(path, "/sourcing"))   return INVENTORY;
        if (isUnder(path, "/production")) return PRODUCTION;
        if (isUnder(path, "/orders"))     return ORDERS;
        if (isUnder(path, "/complaints")) return COMPLAINTS;
        if (isUnder(path, "/admin"))      return ADMIN;
        return null;
    }

    private static boolean isUnder(String path, String prefix) {
        return path.equals(prefix) || path.startsWith(prefix + "/");
    }
}