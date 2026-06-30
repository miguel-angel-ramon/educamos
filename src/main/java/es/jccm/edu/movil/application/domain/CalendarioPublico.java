package es.jccm.edu.movil.application.domain;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "TLCALENDARIO_V")
public class CalendarioPublico extends BaseAudited  {



	@Id
	@Column(name = "X_IDENTIFICADOR")
	private Long id;

	@Column(name = "C_ANNO")
	private Long anyo;

	@Column(name = "F_FIESTA")
	private Date fechaFiesta;

	@Column(name = "D_FIESTA")
	private String tipoFiesta;

	@Column(name = "AMBITO")
	private String ambito;

	@Column(name = "L_AFEDOC")
	private String L_AFEDOC;

	@Column(name = "L_AFENODOC")
	private String L_AFENODOC;


}
