package es.jccm.edu.proyectosfct.application.domain.descarga;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name="PRINT_FIELD")
public class PrintField implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_PRINTFIELD")
	private Long id;
	
	@NotBlank
	@Column(name="TX_FORM_FIELD")
	private String campoArchivo;
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TX_PROPERTY")
	private String propiedad;
	
	@NotBlank
	@Column(name="TX_PROPERTY2")
	private String priopiedad2;
	
	// ---------- Relationships -----------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PRINTFORM")
	private PrintForm printForm;

}
