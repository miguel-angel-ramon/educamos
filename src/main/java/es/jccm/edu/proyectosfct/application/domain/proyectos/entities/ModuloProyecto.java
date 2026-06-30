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
public class ModuloProyecto implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="CURSO")
	private String curso;
	
	@Column(name="CODIGO")
	private String codigo;
	
	@Column(name="IDOFERTAMATRIG")
	private String idOfertamatrig;
	
	@Column(name="IDMATERIAOMG")
	private String idMateriaomg;
	
	@Column(name="MODULO")
	private String modulo;	
	
	@Column(name="HORASTOTALES")
	private Integer horasTotales;
	
	@Column(name="HORASSEMANALES")
	private Integer horasSemanales;
	
	@Column(name="HORASCENTRO")
	private Integer horasCentro;
	
	@Column(name="HORASEMPRESA")
	private Integer horasEmpresa;

	@Column(name="ACTIVIDADES")
	private Integer actividades;

	@Column(name="PUEDEELIMINAR")
	private Integer puedeEliminar;

}
