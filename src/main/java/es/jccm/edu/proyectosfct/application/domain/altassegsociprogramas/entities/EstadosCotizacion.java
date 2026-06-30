package es.jccm.edu.proyectosfct.application.domain.altassegsociprogramas.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_ESTADOS_COTIZACION")
public class EstadosCotizacion extends BaseAudited implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_ESTADOSCOTIZACION")
    @SequenceGenerator(name = "SQ_FCT_ESTADOSCOTIZACION", sequenceName = "SQ_FCT_ESTADOSCOTIZACION", initialValue = 10, allocationSize = 1, schema = "DELPHOS")
    @Column(name = "ID_ESTADOS_COTIZACION")
    private Long id;
    
    @Column(name = "DS_ESTADO")
    private String dsEstado;
    
    @Column(name = "DS_ABREV")
    private String dsAbrev;    
    
    @Column(name = "CD_TIPO")
    private String cdTipo;
    
    @Column(name = "NU_DIAS")
    private Integer nudias;
    
    @Column(name = "NU_ORDEN")
    private Integer nuOrden;    
    
    @Column(name = "LG_ACTIVO")
    private Integer lgActivo;
    


}
