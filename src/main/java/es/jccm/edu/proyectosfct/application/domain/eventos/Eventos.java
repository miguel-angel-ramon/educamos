package es.jccm.edu.proyectosfct.application.domain.eventos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "AU_OUTBOX", schema = "DELPHOS_MODACC")  // Aquí especificas el esquema
public class Eventos implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id   
    @Column(name = "UUID")
    private String id;

    @Column(name = "MESSAGE_ORDER")
    private Integer order;
    
    @Column(name = "MESSAGE_TIME")
    private Date time;
    
    @Column(name = "TOPIC")
    private String topic;
    
    @Column(name = "MESSAGE")
    private String message;	

}



