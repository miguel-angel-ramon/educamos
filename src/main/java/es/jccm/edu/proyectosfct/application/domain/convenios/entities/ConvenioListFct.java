package es.jccm.edu.proyectosfct.application.domain.convenios.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ConvenioListFct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String numeroConvenio;
	
	private String centro;	
	
	private String fechaFirma;	
	
	private Long idEmpresa;	
	
	private String nombreEmpresa;	
	
	private String numIde;
	
	private String fechaFinVigencia;	
	
	private String fechaInicio;
	
	private String fechaProrroga;
	
	private String fechaFirmaProrroga;
	
	private Long idSedeEmp;
	
	private String listFamilias;
	
	private Integer numeroProgramas;
	
    private String idConfirRodal;
	
    private String txConfirFichero;
    
    private Integer lConvante;
    
    private String estado;
    
    private String listTutores;
    
    private String listAlumnado;

	private Integer lgLofp;

}
