package es.jccm.edu.documentosGC.application.domain.datosterritoriales;


import java.io.Serializable;
import java.util.Date;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name="TLPROVINCIAS")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class ProvinciaDoc extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="C_PROVINCIA")
	private Long id;
	
	@NotBlank
	@Column(name="D_PROVINCIA")
	private String descripcionLarga;

	@NotBlank
	@Column(name="L_OFRECERDEFECTO")
	private String ofrecerDefecto;

	@Column(name="D_ULTNOMSIR")
	private String nombreSirhus;

	@NotBlank
	@Column(name="L_MANCHEGA")
	private String esManchega;

	@Column(name="F_FINVIG")
	private Date fFifechaVigencianvig;

	// ----------- Relationships ------------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="C_PAIS")
	private CodigoPaisDoc codigoPais;
	
	
}

