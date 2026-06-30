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
@Table(name = "FCT_MODAUTPROG")
public class ModulosProgPFE  extends BaseAudited implements Serializable {
	
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_MODAUTPROG")
    @SequenceGenerator(name = "SQ_FCT_MODAUTPROG", sequenceName = "SQ_FCT_MODAUTPROG", allocationSize = 1)
    @Column(name = "ID_MODAUTPROG")
    private Long id;
    
    @Column(name = "ID_PROGPERFOR")
    private Long idProgramaFPE;
    
    @Column(name = "X_MATERIAOMG")
    private Long idModulo;
	
	
}
