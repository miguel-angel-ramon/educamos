package es.jccm.edu.alumnos.application.domain.alumnosHorario;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class AlumnoDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long idAlumno;

    private String nombre;

    private String apellido1;

    private String apellido2;
    
    private byte[] foto;

    private String idescolar;
    
    private String numide;
    
    private String direccion;

    private Date fechaNacimiento;

    private String correo;
    
    private String telefono;
    
    private String tlefnourg;
    
    private String idExpediente;
    
	@OneToMany(mappedBy = "idGrupoActividad")
	private List<GrupoActividadAlumno> gruposActividad;
	
	@OneToMany(mappedBy = "idMateria")
	private List<MateriaAlumno> materias;
	
}
