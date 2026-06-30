package es.jccm.edu.documentosGC.application.domain.tipodoc.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="DGC_TIPOS_DOCUMENTOS")
public class TipoDocumentosGC implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_TIPO_DOCUMENTO")
	private Long id;
	
	@Column(name="NU_ORDEN", nullable = false)
	private Integer orden;
	
	@Column(name="DS_ABREV", nullable = false)		
	private String abrev;
	
	@Column(name="DS_DESCRIPCION", nullable = false)
	private String descripcion;
	
	@Column(name="LG_ANUAL", nullable = false)
	private Integer anual;
	
	@Column(name="C_ANNO_DESDE")
	private Integer annodesde;
	
	@Column(name="C_ANNO_HASTA")
	private Integer annohasta;
	
	@Column(name="LG_OBLIGATORIO",nullable = false)
	private Integer lgObligatorio;
	
	@Column(name="ID_TIPO_PADRE")
	private Long idTipoDocumentoPadre;
	
	//@ManyToOne(fetch=FetchType.LAZY)
	//@JoinColumn(name = "ID_TIPO_PADRE", referencedColumnName =  "ID_TIPO_DOCUMENTO")
	//private TipoDocumentosGC idTipoDocumentoPadre;
	
	//@OneToMany(mappedBy = "idTipoDocumentoPadre")
	//private List<TipoDocumentosGC> listTipoDocumentoPadre;
	
	
}
