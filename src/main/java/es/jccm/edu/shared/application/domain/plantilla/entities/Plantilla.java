package es.jccm.edu.shared.application.domain.plantilla.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@Entity
public class Plantilla implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	
	private String tituloFormulario;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotEmpty
	@NotNull
	private String campo1;
	
	private String campo2;
	
	@NotEmpty
	@NotNull
	@Size(max=8, message = "No puede superar los 8 carácteres")
	private String campo3;
	
	private String campo4;
	
	private String campo5;
	
	private String campo6;
	
	private String campo7;
	
	private String select;
	
	private String campoTextarea;
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date campoFecha;
	
	private String check1;
	
	private String check2;
	
	private String radioButton;

}
