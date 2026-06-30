package es.jccm.edu.documentosGC.application.domain.datosterritoriales;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Embeddable
public class MunicipioPKDoc implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotBlank
	@Column(name="C_PROVINCIA", insertable=false, updatable=false)
	private Long provincia;

	@NotBlank
	@Column(name="C_MUNICIPIO")
	private Long municipio;

}
