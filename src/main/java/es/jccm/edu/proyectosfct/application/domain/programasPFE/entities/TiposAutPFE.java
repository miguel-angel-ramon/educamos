package es.jccm.edu.proyectosfct.application.domain.programasPFE.entities;

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

@Data
@Entity
@Table(name = "FCT_TIPAUTPROG")
public class TiposAutPFE extends BaseAudited implements Serializable  {
	
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_TIPAUTPROG")
    @SequenceGenerator(name = "SQ_FCT_TIPAUTPROG", sequenceName = "SQ_FCT_TIPAUTPROG", allocationSize = 1)
    @Column(name = "ID_TIPAUTPROG")
    private Long id;
    
    @Column(name = "CD_TIPOAUT")
    private String cdTipoAut;    
    
    @Column(name = "DS_TIPOAUT")
    private String dsTipoAut;
    
	@Column(name="NU_ORDEN")
	private Integer nuOrden;


}
