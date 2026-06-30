package es.jccm.edu.proyectosfct.application.domain.alumnado.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name = "FCT_CORRECCIONES_NUSS")
public class CorreccionesAlumnado  extends BaseAudited implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_CORRECCIONES_NUSS")
    @SequenceGenerator(name = "SQ_FCT_CORRECCIONES_NUSS", sequenceName = "SQ_FCT_CORRECCIONES_NUSS", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
    @Column(name = "ID_CORRECCION_NUSS")
    private Long id;
	
	@Column(name = "CD_NUSS_OLD")
	private String nussOld;
	
	@Column(name = "CD_NUSS_NEW")
	private String nussNew;	
	
	@Column(name = "F_REGISTRO")
    private Date fregistro;
	
	// ---------- Relationships -----------
	
	 @Column(name = "X_MATRICULA")
	 private Long xMatricula;
	

}
