package es.jccm.edu.documentosGC.application.domain.documentosGC.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class AdjuntosListDetalle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idAdjunto;

	private Long idTipoDoc;		
	
	private Long idTipoAdjunto;	
	
	private Integer orden;	
	
	private Integer principal;		

	private String nombre;	
	
	private String descripcion;	
	
	private Integer firmable;	
	
	private Integer tamano;	
	
	private Integer annodesde;	
	
	private Integer annohasta;
	
	private Long idHistorial;
	
	private Integer lgFirmado;
	
	private String IdDocHisRodal;	
	
	private String TxDocHisFichero;	
	
	private String fechaPantalla;	
}



