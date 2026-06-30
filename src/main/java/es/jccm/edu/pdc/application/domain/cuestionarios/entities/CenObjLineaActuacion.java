package es.jccm.edu.pdc.application.domain.cuestionarios.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import es.jccm.edu.shared.application.domain.baseAudited.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(schema= "DELPHOS_SEGEDU" ,name="TLPDCCENOBJLINACT")
public class CenObjLineaActuacion extends BaseAudited {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELPHOS_SEGEDU.TLS_LAOCXPDCCENOBJLINACT")
	@SequenceGenerator(name = "DELPHOS_SEGEDU.TLS_LAOCXPDCCENOBJLINACT", sequenceName = "DELPHOS_SEGEDU.TLS_LAOCXPDCCENOBJLINACT", allocationSize = 1)
	@Column(name="X_PDCCENOBJLINACT")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "X_PDCENOBJCEN")
	private CenObjCen idCenOjCen;
	
	@Column(name="X_LINEAACT")
	private Long idLineaAct;
	
	
	@Column(name="F_INICIO")
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaInicioEjecucion;
	
	@Column(name="F_FIN")
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaFinEjecucion;
	
	@Column(name="D_RECURSOS")
	private String recursos;
	
	
	@Column(name="D_INDLOGRO")
	private String indLogro;
	
	@Column(name="D_INSTRUMENTOS")
	private String instrumentos;
	
	@Column(name="T_LINEAACT")
	private String tituloLineaActuacion;
	
	@Column(name="D_LINEAACT")
	private String descripcionLineaActuacion;
	
	@Column(name="L_ACTIVO")
	private String activo;
	
	@Column(name="T_RESPONSABLE")
	private String responsable;
	
	@Column(name="EST_LINEAACT")
	private String estadoLineaActuacion;
	
}
