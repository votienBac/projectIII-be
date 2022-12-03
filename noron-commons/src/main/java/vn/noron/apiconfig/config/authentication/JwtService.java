package vn.noron.apiconfig.config.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import vn.noron.apiconfig.model.SimpleSecurityUser;
import vn.noron.core.json.JsonObject;
import vn.noron.data.tables.pojos.Role;
import vn.noron.data.tables.pojos.User;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static vn.noron.core.json.JsonObject.mapFrom;
import static vn.noron.utils.CollectionUtils.extractField;

@Service
public class JwtService {
    @Value("${spring.security.jwt.secret-key}")
    private String secretKey;
    @Value("${spring.security.jwt.expired-in}")
    private Long expiresIn;

    public AbstractAuthenticationToken extractAuthentication(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            String userString = (String) body.get("user");
            SimpleSecurityUser user = new JsonObject(userString).mapTo(SimpleSecurityUser.class);
            return new UsernamePasswordAuthenticationToken(user.getId(), user, getAuthorities(user));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<SimpleGrantedAuthority> getAuthorities(SimpleSecurityUser user) {
        return Optional.ofNullable(user.getRoles())
                .orElse(Collections.emptyList())
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(toList());
    }

    public String generateJwt(User user, List<Role> roles) {
        final SimpleSecurityUser securityUser = new SimpleSecurityUser()
                .setRoles(extractField(roles, Role::getName))
                .setPassword(user.getPassword())
                .setId(user.getId().toString());
        return generateJwt(securityUser);
    }

    public String generateJwt(SimpleSecurityUser securityUser) {
        Map<String, Object> claims = new HashMap<>();
        putAuthentication(claims, securityUser);

        return Jwts.builder()
                .setSubject(securityUser.getId())
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiresIn))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private void putAuthentication(Map<String, Object> claims, SimpleSecurityUser securityUser) {

        SimpleSecurityUser user = new SimpleSecurityUser();
        user.setRoles(securityUser.getRoles());
        user.setId(securityUser.getId());
        claims.put("user", mapFrom(user).encode());
    }
}
