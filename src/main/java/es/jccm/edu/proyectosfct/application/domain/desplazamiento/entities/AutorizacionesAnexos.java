package es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities;

import java.io.Serializable;

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

import es.jccm.edu.proyectosfct.application.domain.gastos.entities.PeriodoGasto;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_AUTORIZACIONES_ANEXOS")
public class AutorizacionesAnexos extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "autanex_seq")
    @SequenceGenerator(name = "autanex_seq", sequenceName = "SQ_FCT_AUTORIZACIONESANEXOS", allocationSize = 1)
	@Column(name="ID_AUTORIZACION_ANEXO")
	private Long id;	
	
	
	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERIODO_GASTO")
	private PeriodoGasto periodoGasto;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_AUTORIZACION")
	private TipoAutorizacion tipo;
	
	@Column(name="C_ANNO")
	private Integer idAnno;
	
	@Column(name="X_CENTRO")
	private Long idCentro;
	
	@Column(name="ID_TUTORFCTDUAL")
	private Long idTutorFct;
	
	@Column(name="X_FAMILIA")
	private Long idFamilia;
	
	@Column(name="X_OFERTAMATRIG")
	private Long idCurso;
	
	@Column(name="X_UNIDAD")
	private Long idUnidad;
	
	@Column(name="TX_JUSTIFICACION")
	private String txtJustificacion;
	
	@Column(name="TX_CONTROL")
	private String txtControl;
	
	@Column(name="TX_COSTES")
	private String txtCostes;

	@Column(name="NU_PETICION")
	private Integer nuPeticion;
	
}
