package es.jccm.edu.shared.configuration.security.oauth2.authorization;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.shared.configuration.security.oauth2.configuration.JwtConfiguration;
import lombok.Builder;
import lombok.Data;

@Configuration
@ConditionalOnBean(UserDetailsService.class)
public class UserDetailsServiceAuthenticationConverterConfiguration {

    @Bean
    JwtAuthenticationConverter userDetailsServiceAuthenticationConverter(UserDetailsService userDetailsService,
            JwtConfiguration jwtConfiguration) {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setPrincipalClaimName(jwtConfiguration.getPrincipalClaimName());
        converter.setJwtGrantedAuthoritiesConverter(
                jwt -> {
                    String uid = jwt.getClaimAsString(jwtConfiguration.getPrincipalClaimName());
                    try {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(uid);
                        return userDetails.getAuthorities().stream()
                                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                                .collect(Collectors.toList());
                    } catch (Exception e) {
                        return Collections.emptyList();
                    }
                    
                });
        return converter;
    }

    @Builder
    @Data
    static class Usuario {
        String id;
        
        List<String> permisos;
    }
    
    @RestController
    @RequestMapping("${spring.data.rest.base-path:/api}/usuarios")
    class UserDetailsServiceRestController {

        @GetMapping("/me/permisos")
        public ResponseEntity<EntityModel<Usuario>> getAutorities(Authentication authentication) {
            List<String> authorities = authentication
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
           
            // @formatter:off
            Usuario usuario = Usuario.builder()
                                        .id(authentication.getName())
                                        .permisos(authorities)
                                        .build();
            // @formatter:on      

            return ResponseEntity.ok(EntityModel.of(usuario));
        }

    }

}
