package es.jccm.edu.evaluacion.application.domain.programacionAula.entidades;

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
@Table(name="EVA_INSTEVA", schema = "DELPHOS")
public class EvaInstrumentoEvaluacion  extends BaseAudited implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_INSTEVA")
	private Long id;
	
	@NotBlank
	@Size(max=100, message = "No puede superar los 100 caracteres")
	@Column(name="DS_INSTRUMENTO")
	private String descripcion;
	
	@Size(max = 10, message = "No puede superar los 10 caracteres")
	@Column(name="TX_ABREV")
	private String abreviatura;
}