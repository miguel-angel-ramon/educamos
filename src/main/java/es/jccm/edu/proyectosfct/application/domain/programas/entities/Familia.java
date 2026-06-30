package es.jccm.edu.proyectosfct.application.domain.programas.entities;

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
@Table(name="TLFAMILIAS")
public class Familia implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_FAMILIA")
	private Long id;

	@NotBlank
	@Column(name="D_FAMILIA")
	private String descripcionLarga;

	@NotBlank
	@Column(name="S_FAMILIA")
	private String descripcionCorta;
	
}
