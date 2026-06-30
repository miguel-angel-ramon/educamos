package es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
@Table(name="FCT_CONVPROG_ANEXOS")
public class ConveniosProgramasAnexos extends BaseAudited implements Serializable {	
	
	private static final long serialVersionUID = 1L;	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "covpanexo_seq")
    @SequenceGenerator(name = "covpanexo_seq", sequenceName = "SQ_FCT_CONVPROGANEXOS", allocationSize = 1)
	@Column(name="ID_CONVPROG_ANEXO")
	private Long id;
	
	@Column(name="ID_CONV_PROG")
	private Long idConvProg;
	
	@Column(name="ID_ANEXO_RODAL")
	private String idAnexoRodal;
	
	@Column(name="TX_ANEXO_FICHERO")
	private String nombreFichero;

}
