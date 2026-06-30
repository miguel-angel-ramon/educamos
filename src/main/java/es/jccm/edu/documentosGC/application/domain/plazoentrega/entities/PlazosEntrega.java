package es.jccm.edu.documentosGC.application.domain.plazoentrega.entities;

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

import es.jccm.edu.documentosGC.application.domain.tipodoc.entities.TipoDocumentosGC;
import lombok.Data;

@Data
@Entity
@Table(name="DGC_PLAZOS_ENTREGA")
public class PlazosEntrega {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_PLAZO")
	private Long id;
	
	@Column(name="C_ANNO")
	private Integer cAnno;
	
	@Column(name="FH_INICIO")		
	private Date fechaInicio;
	
	@Column(name="FH_FIN")		
	private Date fechaFin;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_DOCUMENTO")
	private TipoDocumentosGC tipo;
	
}
