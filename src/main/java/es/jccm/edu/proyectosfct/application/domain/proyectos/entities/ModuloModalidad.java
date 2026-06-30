package es.jccm.edu.proyectosfct.application.domain.proyectos.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ModuloModalidad implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="IDMATERIAOMG")
	private Long idMateriaomg;
	
	@Column(name="CODIGO")
	private String codigo;
	
	@Column(name="MODULO")
	private String modulo;
	
	@Column(name="ANUALES")
	private Integer horasAnuales;
	
	@Column(name="SEMANALES")
	private Integer horasSemanales;

}
