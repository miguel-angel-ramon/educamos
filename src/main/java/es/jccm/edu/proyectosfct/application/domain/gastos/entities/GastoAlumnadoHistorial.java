package es.jccm.edu.proyectosfct.application.domain.gastos.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_GASTOS_ALUMNADO_HISTORIAL")
public class GastoAlumnadoHistorial extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_GASTOS_ALUMNADO_HISTORIAL")
	private Long id;
	
	@Column(name="FH_REGISTRO")
	private Date fechaRegistro;

	@Column(name="TX_OBSERVACIONES")
	private String observaciones;
	
	// ---------- Relationships -----------	
	
	@Column(name="X_USUARIO")
	private Long idUsuario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_GASTOS_ALUMNADO")
	private GastoAlumnado gastoAlumno;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_GASTOS_FLUJO")
	private GastoFlujo gastoFlujo;

}