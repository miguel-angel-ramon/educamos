package es.jccm.edu.shared.configuration.security.oauth2.authorization.acl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationConfigurationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.DenyAllPermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import es.jccm.edu.shared.configuration.security.oauth2.configuration.JwtConfiguration;





@Configuration
public class JwtPermissionEvaluatorConfiguration {

	@Autowired
	ApplicationContext context;
	
	@Autowired
	JwtConfiguration jwtConfiguration;

	@Bean
	@SuppressWarnings("unchecked")
	PermissionEvaluator permissionEvaluator() {
		return new DenyAllPermissionEvaluator() {
			@Override
			public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
					Object permission) {
				JwtAuthenticationToken auth = (JwtAuthenticationToken) authentication;
				var domainAccess = (Map<String, Map<String, List<String>>>) auth.getTokenAttributes().get(jwtConfiguration.getAclMappings());

				return hasDomainAccess(domainAccess, targetId, targetType, permission);
			}

			@Override
			public boolean hasPermission(Authentication authentication, Object target, Object permission) {
				for (SimpleEntry<Serializable, String> entry : this.getIdentifiers(target)) {
					if (!hasPermission(authentication, entry.getKey(), entry.getValue(), permission)) {
						return false;
					}
				}

				return true;
			}

			public boolean hasDomainAccess(Map<String, Map<String, List<String>>> domainAccess, Serializable targetId,
					String targetType, Object permission) {
				if (domainAccess != null) {
					Map<String, List<String>> permissions = domainAccess.get(targetType);
					if (permissions != null) {
						List<String> entities = permissions.get(permission);
						if (entities != null) {
							return entities.contains(targetId);
						}
					}
				}

				return false;
			}

			private List<SimpleEntry<Serializable, String>> getIdentifiers(Object target) {
				List<SimpleEntry<Serializable, String>> identifiers = new ArrayList<>();

				if (target.getClass().isAssignableFrom(ResponseEntity.class)) {
					target = ((ResponseEntity<?>) target).getBody();
				}

				if (target instanceof Iterable) {
					Iterable<?> iterable = (Iterable<?>) target;
					for (Object element : iterable) {
						var entry = this.getIdentifier(element);
						if (entry != null) {
							identifiers.add(entry);
						}
					}
				} else {
					var entry = this.getIdentifier(target);
					if (entry != null) {
						identifiers.add(entry);
					}
				}

				return identifiers;
			}

			private SimpleEntry<Serializable, String> getIdentifier(Object target) {
				try {
					Field[] fields = FieldUtils.getFieldsWithAnnotation(target.getClass(), Id.class);
					if (fields.length > 1) {
						throw new AnnotationConfigurationException(
								"Sólo puede haber una anotación @Identifier por clase");
					}
					fields[0].setAccessible(true);
					Serializable value = (Serializable) fields[0].get(target);

					return new SimpleEntry<>(value, target.getClass().getCanonicalName());
				} catch (IllegalAccessException e ) {
					return null;
				}
			}
		};
	}
}
