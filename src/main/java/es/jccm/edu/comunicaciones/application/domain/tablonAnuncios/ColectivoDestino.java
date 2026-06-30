package es.jccm.edu.comunicaciones.application.domain.tablonAnuncios;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table (name="TLCOLDESNOTTAB", schema="DELPHOS_SEGEDU")
public class ColectivoDestino implements Serializable {
	
	private static final long serialVersionUID=1L;
	@Id
	@Column(name="X_COLDESNOTTAB")
	private Integer id;
	
	@Column(name="X_NOTSECTABANUCEN")
	
	private Long idNoticia;
	
	@Column(name="X_COLECTIVO")
	private Integer idColectivo;
	
	@Column(name="X_CENTRO")
	private Long idCentro;
	
	@Column(name="X_OFERTAMATRIG")
	private Integer idCurso;
	
	@Column(name="X_UNIDAD")
	private Integer idUnidad;
	
	@Column(name="X_GRUACTPROALU")
	private Integer idGrupoActividad;
	
	@Column(name="C_USUCREACION")
	private Integer usuarioCreacion;
	
	@Column(name="X_USUARIO")
	private Integer usuario;
	
	@Column(name="C_CODIGO_PERFIL")
	private String perfil;
	
	
	
	
	
	

}
