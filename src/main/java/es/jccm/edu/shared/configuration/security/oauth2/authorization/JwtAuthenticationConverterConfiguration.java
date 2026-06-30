package es.jccm.edu.shared.configuration.security.oauth2.authorization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import es.jccm.edu.shared.configuration.security.oauth2.configuration.JwtConfiguration;
import lombok.extern.slf4j.Slf4j;

@ConditionalOnMissingBean(UserDetailsService.class)
@Configuration
@Slf4j
public class JwtAuthenticationConverterConfiguration {
    
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter2(JwtConfiguration jwtConfiguration) {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setPrincipalClaimName(jwtConfiguration.getPrincipalClaimName());
        converter.setJwtGrantedAuthoritiesConverter(
                jwt -> {
                    Collection<GrantedAuthority> authorities = new ArrayList<>();
                    for (String roleMapping : jwtConfiguration.getRoleMappings()) {
                        log.debug("claim: {}", roleMapping);
                        List<?> roles = getRoles(jwt, roleMapping);
                        log.debug("roles: {}", roles);
                        authorities.addAll(
                                roles
                                        .stream()
                                        .map(role -> new SimpleGrantedAuthority(role.toString()))
                                        .collect(Collectors.toList()));
                        log.debug("authorities: {}", authorities);
                    }
                    return authorities;
                });
        return converter;
    }

    private List<?> getRoles(Jwt jwt, String roleMapping) {
        List<?> roles = new ArrayList<>();
        String[] claims = roleMapping.split("\\.");
        Map<?, ?> root = jwt.getClaim(claims[0]);
        var node = root;
        if (root != null) {
            for (int i = 1; i < claims.length - 1; i++) {
                node = (Map<?, ?>) root.get(claims[i]);
            }
            if (node != null) {
                var lastClaim = claims[claims.length - 1];
                roles = (List<?>) node.get(lastClaim);
            }
        }
        return roles;
    }
}
