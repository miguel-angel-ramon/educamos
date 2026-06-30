package es.jccm.edu.gestionidentidades.application.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TIPOS_ACCESO_APLICACION", schema = "DELPHOS_MODACC")
public class TipoAccesoAplicacion {
	
	
	 	@Id
	    @Column(name = "OID")
	    private Long oid;
	    
	    @ManyToOne
	    @JoinColumn(name = "OID_APLICACION", referencedColumnName = "OID")
	    private Aplicacion aplicacion;
	    
	    @ManyToOne
	    @JoinColumn(name = "OID_TIPO_ACCESO", referencedColumnName = "OID")
	    private TipoAcceso tipoAcceso;
	    
	    @Column(name = "T_URL_INTERFAZ")
	    private String urlInterfaz;
	    
	    @Column(name = "L_ACTIVA")
	    private String activa;
	    
	    @Column(name = "T_MOTIVO_DESACTIVACION")
	    private String motivoDesactivacion;
	
}
