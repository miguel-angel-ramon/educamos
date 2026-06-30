package es.jccm.edu.proyectosfct.application.domain.alumnado.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class FechaSemana implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;

	@Column(name="primerDia")
	private Date primerDia;
	// ---------- Relationships -----------	
	
	@Column(name="semana")
	private String semana;
	
	@Column(name="actual")
	private String actual;

}