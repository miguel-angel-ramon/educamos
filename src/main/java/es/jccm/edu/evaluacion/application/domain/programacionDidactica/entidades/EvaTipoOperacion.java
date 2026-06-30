package es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
@Table(name="EVA_OPECALCRIEVA", schema = "DELPHOS")
public class EvaTipoOperacion extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "opecalcrieva_seq")
    @SequenceGenerator(name = "opecalcrieva_seq", sequenceName = "SQ_EVA_OPECALCRIEVA", allocationSize = 1)
	@Column(name="ID_OPECALCRIEVA")
	private Long id;

	@NotBlank
	@Size(max=100, message = "No puede superar los 100 caracteres")
	@Column(name="TX_OPECALCRIEVA")
	private String descripcion;
	
}