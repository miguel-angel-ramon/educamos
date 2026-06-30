package es.jccm.edu.proyectosfct.application.domain.modalidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name="TLMODALIDADES")
public class Modalidad implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_MODALIDAD")
	private Long id;

	@NotBlank
	@Column(name="S_MODALIDAD")
	private String descripcionCorta;

	@NotBlank
	@Column(name="D_MODALIDAD")
	private String descripcionLarga;
	
	
	
}
