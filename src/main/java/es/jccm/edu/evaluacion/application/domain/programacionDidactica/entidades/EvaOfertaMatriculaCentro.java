package es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades;

import java.io.Serializable;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "TLOFEMATRCEN", schema = "DELPHOS")
public class EvaOfertaMatriculaCentro extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_OFERTAMATRIC")
	private Long id;

	@Column(name="L_VIGENCIA" , columnDefinition = "VARCHAR2(1) DEFAULT 'S'")
	private String lVigencia;
	
	@Column(name="L_BAREMACION" , columnDefinition = "VARCHAR2(1) DEFAULT 'N'")
	private String lBaremacion;
	
	@Column(name="L_PROREGCEN")
	private String lProRegCen;
	
	// ---------- Relationships -----------

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CENTRO")
	private EvaCentro centro;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_OFERTAMATRIG")
	private EvaOfertaMatriculaGenerico ofertaMatriculaGenerico;

}