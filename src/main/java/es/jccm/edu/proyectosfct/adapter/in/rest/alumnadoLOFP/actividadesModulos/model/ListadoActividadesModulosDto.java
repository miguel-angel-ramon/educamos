package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.actividadesModulos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@Schema(name = "ListadoActividadesModulosDto", description = "Listado de actividades plan con información adicional")
public class ListadoActividadesModulosDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="id")
	private Long id;
	
	@Column(name="lsResula")
	private String lsResula;	

	@Column(name="nombre")
	private String nombre;
	
	@Column(name="dsDescripcion")
	private String dsDescripcion;
	
	@Column (name ="nuOrden")
	private Integer nuOrden;
	
	@Column(name ="actividadRegistrada")
	private Integer actividadRegistrada;
	
	@Column(name ="txAbrev")
	private String txAbrev;



}