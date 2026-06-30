package es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name="TLCENTROS", schema = "DELPHOS")
public class EvaCentro extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_CENTRO")
	private Long id;

	@NotNull
	@Column(name="C_CODIGO")
	private Long codigo;

	@Column(name="L_PROREGCEN" , columnDefinition = "VARCHAR2(1) DEFAULT 'S'")
	private String lProRegCen;

	@Column(name="L_MODADSCRIP")
	private String lModoAdscripcion;
	
	@NotBlank
	@Column(name="L_EXTRANJERO" , columnDefinition = "VARCHAR2(1) DEFAULT 'S'")
	private String lExtranjero;
	
	@NotBlank
	@Column(name="L_DELEGACION" , columnDefinition = "VARCHAR2(1) DEFAULT 'S'")
	private String lDelegacion;
	
	@Column(name="L_ADSCRIPACT")
	private String lAdscripcionActiva;
	
	@Column(name="F_PUB_BAJA")
	private Date fechaPublicaCese;
	
	@Column(name="F_PUB_ALTA")
	private Date fechaPublicaAlta;
	
	@Column(name="F_FUNCIONA")
	private Date fechaInicioFuncionamiento;
	
	@Column(name="F_FIN_FUNC")
	private Date fechaFinFuncionamiento;
	
	@Column(name="F_CREACIONCENT")
	private Date fechaCreacion;
	
	@Column(name="F_BAJA")
	private Date fechaBaja;
	
	// ---------- Relationships -----------
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "X_TIPO")
	private EvaTipoCentro tipo;
	
	@OneToMany(mappedBy = "ofertaMatriculaGenerico", fetch = FetchType.LAZY)
	private List<EvaOfertaMatriculaCentro> ofertaMatriculaCentros;

}