package es.jccm.edu.alumnos.application.domain.historicoDomiciliosAlumno;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="TLFAMILIASALU")
public class FamiliaAlumno implements Serializable {
	private static final long serialVersionUID=1L;
	
	@Id
	@Column (name="X_FAMILIA")
	Long id;


	@OneToOne 
	@JoinColumn(name="X_TUTOR1")
	TutorHistDom tutor1;

	@OneToOne 
	@JoinColumn(name="X_TUTOR2")
	TutorHistDom tutor2;


}
