package es.jccm.edu.proyectosfct.application.domain.extraordinario.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ListadoAutorizacionExtraordinario  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private Long id;
	
	@Column(name="NOMBREALUMNO")
	private String nombreAlumno;
	
	@Column(name="FINDE")
    private String finde;
	
	@Column(name="VACACIONES")
    private String vacaciones;
	
	@Column(name="HORARIOEXT")
    private String horarioext;
	
	@Column(name="FINIEXT")
    private Date finiext;
	
	@Column(name="FFINEXT")
    private Date ffinext;
	
	@Column(name="HORARIOFUE")
    private String horariofue;
	
	@Column(name="ESTADO")
    private String estado;
	
	@Column(name="FINIFUE")
    private Date finifue;
	
	@Column(name="FFINFUE")
    private Date ffinfue;
	
	@Column(name="IDMATRICULA")
    private Long idMatricula;
	
	@Column(name="calenFur")
	private String calenFur;
	
	@Column(name="EDITARESTADO")
    private Integer editarestado;
	
	@Column(name="CURSO")
	private String curso;
	
	@Column(name="TUTOR")
	private String tutor;	
	
	@Column(name="calenExt")
	private String calenExt;
	
	@Column(name="empresa")
	private String empresa;
	
	@Column(name = "puedeBorrar")
	private String puedeBorrar;	
	
	@Column(name = "fultgen")
	private String fultgen;

	@Column(name="NUPETICION")
	private Integer nuPeticion;

}