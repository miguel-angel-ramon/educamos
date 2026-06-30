package es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_ESTADOS_AUTORIZACIONES")
public class EstadoAutorizacion extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estadoautorizacion_seq")
    @SequenceGenerator(name = "estadoautorizacion_seq", sequenceName = "SQ_FCT_ESTADOSAUTORIZACIONES", allocationSize = 1)
	@Column(name="ID_ESTADO_AUTORIZACION")
	private Long id;
	
	@Column(name="DS_ABREV")
	private String abreviatura;
	
	@Column(name="DS_NOMBRE")
	private String nombre;
	
	@Column(name="FH_INICIO")
	private Date fechaInicio;
	
	@Column(name="FH_FIN")
	private Date fechaFin;
	
	@Column(name="TX_AVISO")
	private String aviso;

}