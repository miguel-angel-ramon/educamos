package es.jccm.edu.proyectosfct.application.domain.gastos.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_TIPOS_GASTOS")
public class TipoGasto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_TIPOS_GASTOS")
	private Long id;
	
	@Column(name="DS_ABREV")
	private String abrev;

	@Column(name="DS_NOMBRE")
	private String nombre;
	
	@Column(name="FH_INICIO")
	private Date fechaInicio;
	
	@Column(name="FH_FIN")
	private Date fechaFin;
	
	@Column(name="TX_AVISO")
	private String aviso;

}