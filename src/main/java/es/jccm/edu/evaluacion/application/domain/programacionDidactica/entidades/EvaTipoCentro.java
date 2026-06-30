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
@Table(name="TLTIPOCEN", schema = "DELPHOS")
public class EvaTipoCentro extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_TIPO")
	private Long id;

	@NotBlank
	@Size(max=70, message = "No puede superar los 70 caracteres")
	@Column(name="D_TIPO")
	private String descripcion;
	
	@Size(max=30, message = "No puede superar los 30 caracteres")
	@Column(name="S_TIPO")
	private String descripcionCorta;
	
}