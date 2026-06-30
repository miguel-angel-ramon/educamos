package es.jccm.edu.alumnos.application.domain.notificacionesFalta;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "TLNOTFALASIALU")
public class NotificacionFalta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "X_NOTFALASIALU")
    private Long idNotificacionFalta;
    
    private String fechau;
    private String  fechdia;
    private String observacion;
    private String  motivo;
    private Long  idMotivo;

}
