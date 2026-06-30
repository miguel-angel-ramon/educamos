package es.jccm.edu.proyectosfct.application.domain.datosterritoriales;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@Entity
@Table(name="TLMUNICIPIOS")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Municipio implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private MunicipioPK id;
	
	@NotBlank
	@Column(name="D_MUNICIPIO")
	private String descripcionLarga;
	
	@NotBlank
	@Column(name="S_MUNICIPIO")
	private String descripcionCorta;
	
	@Column(name="F_FINVIG")
	private Date fechaVigencia;
	
	@Column(name="C_USUCREACION")
	private Long idUsuarioCreacion;
	
	@Column(name="F_CREACION")
	private Date fechaCreacion;
	
	@Column(name="C_USUACTUALIZA")
	private Long idUsuarioModificacion;
	
	@Column(name="F_ACTUALIZA")
	private Date fechaModificacion;
	
	@Column(name="D_ULTNOMSIR")
	private String nombreSirhus;
	
	// ---------- Relationships -----------	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="C_PROVINCIA", insertable = false, updatable = false)
	private Provincia provincia;

}
