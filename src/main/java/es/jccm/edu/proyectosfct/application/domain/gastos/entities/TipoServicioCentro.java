package es.jccm.edu.proyectosfct.application.domain.gastos.entities;

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
@Table(name = "FCT_TIPSERCEN", schema = "DELPHOS")
public class TipoServicioCentro extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_TIPSERCEN")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_TIPSERCEN")
	@SequenceGenerator(name = "SQ_FCT_TIPSERCEN", sequenceName = "SQ_FCT_TIPSERCEN", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
	private Long id;
	
	@NotBlank
	@Size(max=200, message = "No puede superar los 200 caracteres")
	@Column(name = "DS_TIPSERCEN")
	private String descripcion;
	
	@NotBlank
	@Column(name = "NU_ORDEN")
	private Integer orden;
	
	@NotBlank
	@Size(max=6, message = "No puede superar los 6 caracteres")
	@Column(name = "CD_TIPSERCEN")
	private String codigo;
	
	

}
