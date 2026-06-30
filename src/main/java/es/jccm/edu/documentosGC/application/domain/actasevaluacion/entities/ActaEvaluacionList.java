package es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ActaEvaluacionList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long idConvocatoria;
	
	private String convocatoria;
	
	private String tipo;
	
	private Long idCurso;
	
	private String curso;
	
	private Long idOmc;
	
	private Long idTipoexp;
	
	private String abreviatura;	
	
	private Long idUnidad;
	
	private Integer nPeriodo;
	
	private String unidad;
	
	private Date sesion;
	
	private String estado;
	
	private Long idConvUnidad;
	
	private Long idEstado;

	private String idRodal;	
	
	private String fichero;		
	
	private String provincia;
	
	private String municipio;
	
	private String centro;
	
	private String acta;	
	
	private Long idDocumento;	
	
	private Integer permiteadjuntos;
	
	private Integer permitegenerar;
	
	private Date ffinconvomc;
	
	private Date ffinconvcen;
	
	private Long idConvOmc;
	
	private Long idmateriac;
	
	private String materia;
	
	private Long idAdjunto;
	
	private Integer permitefirmar;
	
	private Integer totalAdjuntos;
	
	private String aviso2;
	
	private String aviso3;
	
}
