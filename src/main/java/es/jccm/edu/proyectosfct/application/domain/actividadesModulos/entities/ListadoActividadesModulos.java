package es.jccm.edu.proyectosfct.application.domain.actividadesModulos.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Entity
public class ListadoActividadesModulos implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="nombre")
	private String nombre;

	@Column(name="lsResula")
	private String lsResula;
	
	@Column(name="dsDescripcion")
	private String dsDescripcion;

	@Column(name ="txAbrev")
	private String txAbrev;

	@Column(name = "nuOrden")
	private Integer nuOrden;

	@Column(name = "actividadRegistrada")
	private Integer actividadRegistrada;

}