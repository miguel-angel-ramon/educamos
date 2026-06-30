package es.jccm.edu.gestionidentidades.application.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


	@Data
	@Entity
	@Table(name = "TLINTERFACE_RESUL" , schema="DELPHOS")
	public class ResultadoInterfaz implements Serializable{

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ORDEN")
	    private Long id;

	    @Column(name = "FECHA")
	    private Date fecha;

	    @Column(name = "TIPO")
	    private String tipo;

	    @Column(name = "TABLA")
	    private String tabla;

	    @Column(name = "MENSAJE")
	    private String mensaje;

}

