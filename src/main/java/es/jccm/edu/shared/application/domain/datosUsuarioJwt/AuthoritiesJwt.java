package es.jccm.edu.shared.application.domain.datosUsuarioJwt;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class AuthoritiesJwt implements Serializable {

	List<String> authorities;

}
