package es.jccm.edu.gestionidentidades.application.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TIPOS_ACCESO")
public class TipoAcceso {

    @Id
    @Column(name = "OID")
    private Long id;
    
    @Column(name = "C_CODIGO")
    private String codigo;
    
    @Column(name = "T_NOMBRE")
    private String nombre;
    
    @Column(name = "L_POR_DEFECTO")
    private String porDefecto;
	

}
