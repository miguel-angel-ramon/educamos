package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.actividadesModulos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
@Schema(name = "ActividadesModulosDto", description = "Información sobre la actividad")
public class ActividadesModulosDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ID_ACTIVIDAD_MODULO")
	private Long idActividadModulo;

	@Column(name = "DS_ACTIVIDAD")
	private String dsActividad;
	
	@Column(name = "ID_MODULO_CURSO")
	private Long idModuloCurso;	

	@Column(name = "TX_NOMBRE")
	private String txNombre;
	
	@Column(name = "F_CREACION")
	private Date fCreacion;

	@Column(name = "C_USUCREACION")
	private Long cUsuCreacion;

	@Column(name = "NU_ORDEN")
	private Integer nuOrden;

	@Column(name = "C_USUACTUALIZA")
	private Long cUsuActualiza;

	@Column(name = "F_ACTUALIZA")
	private Date fActualiza;
}