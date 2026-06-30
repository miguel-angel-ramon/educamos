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
import lombok.Data;

@Data
@Entity
@Table(name="FCT_GASTOS_FLUJO")
public class GastoFlujo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_GASTOS_FLUJO")
	private Long id;
	
	@Column(name="LG_REQADJUNTO")
	private Boolean requiereAdjunto;

	@Column(name="LG_BORRADO")
	private Boolean borrado;
	
	@Column(name="FH_BORRADO")
	private Date fechaBorrado;
	
	@Column(name="C_USUBORRADO")
	private Long usuarioBorrado;
	
	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPOS_GASTOS")
	private TipoGasto tipoGasto;
	
	@Column(name="X_PERFIL")
	private Long idPerfil;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_ORI")
	private EstadoGasto estadoOrigen;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_DES")
	private EstadoGasto estadoDestino;

}