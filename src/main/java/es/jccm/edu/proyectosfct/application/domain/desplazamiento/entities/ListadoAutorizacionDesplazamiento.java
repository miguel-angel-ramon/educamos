package es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Entity
public class ListadoAutorizacionDesplazamiento  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private Long id;
	
	@Column(name="NOMBREALUMNO")
	private String nombreAlumno;
	
	@Column(name="FINICIO")
    private Date fInicio;
	
	@Column(name="FFIN")
    private Date fFin;
	
	@Column(name="DIAS")
    private String dias;
	
	@Column(name="MATRICULA")
    private String matricula;
	
	@Column(name="ITINERARIO")
    private String itinerario;
	
	@Column(name="KMDIA")
    private String kmdia;
	
	@Column(name="TOTALKM")
    private String totalkm;
	
	@Column(name="fultgen")
	private String fultgen;
	
	@Column(name="ESTADO")
    private String estado;
	
	@Column(name="IDMATRICULA")
    private Long idMatricula;
	
	@Column(name="EDITAR")
    private Integer editarestado;

	@Column(name = "puedeBorrar")
	private String puedeBorrar;
}