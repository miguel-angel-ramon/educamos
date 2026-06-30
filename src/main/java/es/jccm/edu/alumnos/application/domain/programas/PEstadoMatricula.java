package es.jccm.edu.alumnos.application.domain.programas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name="TLESTGENMATR")
public class PEstadoMatricula implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column (name="X_ESTGENMATR")
	private Long id;
	
	@Column(name="C_RESULTADO")
	private int resultado;
	
	
	@Column(name="D_ESTGENMATR")
	private String descResultado;
	
	
	

}
