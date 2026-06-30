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
@Table(name="FCT_ANEXOS_HISTORIAL")
public class GastoAnexoHistorial implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_ANEXOS_HISTORIAL")
	private Long id;
	
	@Column(name="FH_REGISTRO")
	private Date fechaRegistro;

	@Column(name="TX_OBSERVACIONES")
	private String observaciones;
	
	@Column(name="TX_ANEHIS_FICHERO")
	private String nombreFichero;
	
	@Column(name="X_USUARIO1_FIRMA")
	private Long idUsuario1_firma;
	
	@Column(name="FH_REGISTRO1_FIRMA")
	private Date fhRegistro1;
	
	@Column(name="X_USUARIO2_FIRMA")
	private Long idUsuario2_firma;
	
	@Column(name="FH_REGISTRO2_FIRMA")
	private Date fhRegistro2;
	
	// ---------- Relationships -----------	
	
	@Column(name="ID_ANEHIS_RODAL")
	private String idAneHisRodal;
	
	@Column(name="X_USUARIO")
	private Long idUsuario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_GASTOS_ANEXOS")
	private GastoAnexo gastoAnexo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_GASTOS_FLUJO")
	private GastoFlujo gastoFlujo;

}