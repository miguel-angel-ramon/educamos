package es.jccm.edu.documentosGC.application.domain.firmantesdigital.entities;

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
import es.jccm.edu.documentosGC.application.domain.adjuntosdocumento.entities.AdjuntosDocumento;
import es.jccm.edu.documentosGC.application.domain.frompfc.DgcEmpleado;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name="DGC_ADJUNTO_FIRMANTES")
public class AdjuntosFirmantes extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_FIRMANTE")
	private Long id;

	@Column(name="NU_ORDEN")
	private Integer orden;	

	

	@Column(name="CD_TIPOFIRMA")
	private String tipoFirma;	
	
	@Column(name="LG_FIRMADO")
	private Integer isFirmado;	
	
	@Column(name="FH_FIRMA")
	private Date fechaFirma;	
	
	@Column(name="DS_DESCRIPCION")
	private String descripcion;	
	
	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "ID_ADJUNTO")
	private AdjuntosDocumento adjunto;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_EMPLEADO")
	private DgcEmpleado empleado;	
	
}
