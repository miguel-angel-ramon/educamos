package es.jccm.edu.shared.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación que comprueba habilita la autenticación de doble factor en un método
 * El primer argumento del método debe ser el jwt del usuario
 * El segundo argumento del método debe ser el código a validar
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Totp { 
	boolean necesitaAlgoQuePosees() default false;
}
