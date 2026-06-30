package es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
@Table(name="TLMATERIASGEN", schema = "DELPHOS")
public class EvaMateriaGenerica extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_MATERIAG")
	private Long id;

	@NotBlank
	@Size(max=80, message = "No puede superar los 80 caracteres")
	@Column(name="D_MATERIAG")
	private String descripcion;

	@NotBlank
	@Size(max=30, message = "No puede superar los 30 caracteres")
	@Column(name="S_MATERIAG")
	private String descripcionCorta;

	@NotBlank
	@Size(max=5, message = "No puede superar los 5 caracteres")
	@Column(name="T_ABREV")
	private String abreviatura;
	
	@NotBlank
	@Column(name="L_AUTORIZACION" , columnDefinition = "VARCHAR2(1) DEFAULT 'S'")
	private String lAutorizacion;
	
	@NotBlank
	@Column(name="L_IMPARTE" , columnDefinition = "VARCHAR2(1) DEFAULT 'S'")
	private String lImparte;
	
	@Size(max=4, message = "No puede superar los 4 caracteres")
	@Column(name="C_CODIGOSENU")
	private String codigoEnu;
	
	@Size(max=60, message = "No puede superar los 60 caracteres")
	@Column(name="D_ULTDESSEN")
	private String descripcionSenu;
	
	@Column(name="L_MATSELPAU")
	private String lMatSelPau;
	
	@Column(name="L_MATOBLPAU")
	private String lMatoBlPau;
	
	// ---------- Relationships -----------
}