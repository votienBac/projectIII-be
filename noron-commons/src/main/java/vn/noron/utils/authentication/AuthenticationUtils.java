package vn.noron.utils.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.math.NumberUtils.createLong;

public class AuthenticationUtils {
    public static List<String> getRoles(Authentication authentication ) {
        if (authentication == null) return new ArrayList<>();
        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public static boolean isAdmin(Authentication authentication) {
        Set<String> asList = Stream.of("ADMIN").collect(toSet());
        List<String> roleName = getRoles(authentication);
        return getRoles(authentication).contains("ROLE_ADMIN");
    }

    public static Long loggedUserId(Authentication authentication) {
        if (authentication == null) return null;
        return createLong(authentication.getPrincipal().toString());
    }

    public static boolean showLogInfo() {
        return false;
    }
}
