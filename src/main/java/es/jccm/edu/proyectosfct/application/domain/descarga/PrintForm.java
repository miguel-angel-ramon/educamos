package es.jccm.edu.proyectosfct.application.domain.descarga;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name="PRINT_FORM")
public class PrintForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_PRINTFORM")
	private Long id;
	
	@NotBlank
	@Column(name="TX_PRINTFORM_ALIAS")
	private String printFormAlias;

}
