package es.jccm.edu.proyectosfct.application.domain.proyectos.entities;

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
@Table(name="FCT_TIPOS_PROYECTOS")
public class TipoProyecto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_TIPO_PROYECTO")
	private Long id;
	
	@NotBlank
	@Column(name="DS_ABREV")
	private String ds_abrev;
	
	@NotBlank
	@Column(name="DS_NOMBRE")
	private String ds_nombre;
	
	
	

}
