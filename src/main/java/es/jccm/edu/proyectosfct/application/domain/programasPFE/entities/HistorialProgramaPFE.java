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
@Table(name = "FCT_PROGPERFOR_HISTORIAL")
public class HistorialProgramaPFE extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;
	 
	 @Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_PROGPERFOR_HISTORIAL")
	 @SequenceGenerator(name = "SQ_FCT_PROGPERFOR_HISTORIAL", sequenceName = "SQ_FCT_PROGPERFOR_HISTORIAL", allocationSize = 1)
	 @Column(name = "ID_PROGPERFOR_HISTORIAL")
	 private Long id;
	    
	 @Column(name = "ID_PROGPERFOR")
	 private Long idProgramaFPE;
	    
	 @Column(name = "ID_AUTORIZACION_FLUJO")
	 private Long idFlujo;
	    
	 @Column(name = "FH_REGISTRO")
	 private Date fRegistro;
	    
	 @Column(name = "X_USUARIO")
	 private Long idUsuario;
	 
	 @Column(name = "TX_OBSERVACIONES")
	 private String dObservaciones;
	 
	 @Column(name = "ID_ANEHIS_RODAL")
	 private String idAneRodal;
	    
	 @Column(name = "TX_ANEHIS_FICHERO")
	 private String txAneFichero;		 
	 
	 @Column(name = "ID_ANE_RODAL_INS")
	 private String idAneIns;
	    
	 @Column(name = "TX_ANE_FICHERO_INS")
	 private String txAneIns;	
	 
	 @Column(name = "FH_REGISTRO_ANEXO_INS")
	 private Date fRegistroIns;	
	 
	 
	 
	 
	 
	 
	 

}
