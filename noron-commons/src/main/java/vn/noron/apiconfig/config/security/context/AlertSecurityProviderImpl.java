package vn.noron.apiconfig.config.security.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.noron.core.sercurity.AlertSecurityProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.math.NumberUtils.createLong;

@Service
public class AlertSecurityProviderImpl implements AlertSecurityProvider {
    @Override
    public List<String> getRoles() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return new ArrayList<>();
        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAdmin() {
        Set<String> asList = Stream.of("ADMIN", "SUPPER_ADMIN").collect(toSet());
        return getRoles().stream().anyMatch(asList::contains);
    }

    @Override
    public Long loggedUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return null;
        return createLong(authentication.getPrincipal().toString());
    }

    @Override
    public boolean showLogInfo() {
        return false;
    }
}
