package es.jccm.edu.comunicacionesMovil.application.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "NOTIF_MOVIL")
public class NotificacionMovil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notif_movil_seq")
    @SequenceGenerator(name = "notif_movil_seq", sequenceName = "SQ_NOTIF_MOV", allocationSize = 1)
    @Column(name = "ID_NOTIF_MOVIL")
    private Long idNotifMovil;

    @Column(name = "CD_TOKEN", nullable = false)
    private String token;

    @Column(name = "ID_USER")
    private Long idUser;



    @Column(name = "F_CREACION", nullable = false)
    private Date fechaCreacion;

    @Column(name = "F_ACTUALIZA", nullable = false)
    private Date fechaActualizacion;

    @Column(name = "C_USUCREACION", nullable = false)
    private Long usuarioCreacion;

    @Column(name = "C_USUACTUALIZA", nullable = false)
    private Long usuarioActualizacion;

}
