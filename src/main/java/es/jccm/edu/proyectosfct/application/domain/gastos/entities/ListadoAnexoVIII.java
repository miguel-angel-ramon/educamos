package es.jccm.edu.proyectosfct.application.domain.gastos.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ListadoAnexoVIII implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="TOTALT")
	private String totalT;
	
	@Column(name="TOTALA")
	private String totalA;

	@Column(name="LOCALIDAD")
	private String localidad;
	
	@Column(name="DENOMINACION")
	private String denominacion;
	
}