package es.jccm.edu.proyectosfct.application.domain.programasPFE.entities;

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
@Table(name = "FCT_ANEAUTPROG")
public class AnexosAutFPE extends BaseAudited implements Serializable {
	
	 private static final long serialVersionUID = 1L;
	 
	 @Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_ANEAUTPROG")
	 @SequenceGenerator(name = "SQ_FCT_ANEAUTPROG", sequenceName = "SQ_FCT_ANEAUTPROG", allocationSize = 1)
	 @Column(name = "ID_ANEAUTPROG")
	 private Long id;
	    
	 @Column(name = "ID_PROGPERFOR")
	 private Long idProgramaFPE;
	    
	 @Column(name = "CD_TIPOAUT")
	 private Long cdTipo;
	    
	 @Column(name = "ID_ANEXO_RODAL")
	 private Long idAneRodal;
	    
	 @Column(name = "TX_ANEXO_FICHERO")
	 private String txAneFichero;
	    
	    
}
