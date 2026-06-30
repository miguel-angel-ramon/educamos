package es.jccm.edu.proyectosfct.application.domain.convenios.entities;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="EMP_EMPLEADOS")
public class DatosEmpresaTrabajador implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_EMPLEADO")
	private Long id;

	@Column(name="L_TIPIDE")
	private String tipoDocumento;

	@Column(name="C_NUMIDE")
	private String numeroDocumento;

	@Column(name="TX_NOMBRE")
	private String nombre;

	@Column(name="TX_APELLIDO1")
	private String apellido1;

	@Column(name="TX_APELLIDO2")
	private String apellido2;
	
}