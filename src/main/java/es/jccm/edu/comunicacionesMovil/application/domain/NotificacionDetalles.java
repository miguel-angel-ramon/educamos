package es.jccm.edu.comunicacionesMovil.application.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "NOTIF_FIREBASE")
public class NotificacionDetalles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTIF_FIREBASE_SEQ")
    @SequenceGenerator(name = "NOTIF_FIREBASE_SEQ", sequenceName = "NOTIF_FIREBASE_SEQ", allocationSize = 1)
    @Column(name = "ID_NOTIFICACION")
    private Long idNotifFirebase;

    @Column(name = "ID_USUARIO")
    private Long idUser;

    @Column(name = "T_NOTIFICACION", nullable = false)
    private String tituloNotificacion;

    @Column(name = "DS_NOTIFICACION", nullable = false)
    private String descripcionNotificacion;

    // Si el archivo es muy grande cambiar el String a CLOB
    @Column(name = "LB_IMG_NOTIFICACION", nullable = false)
    private String fotoNotificacion;

    @Column(name = "F_CREACION", nullable = false)
    private Date fechaCreacion;

    @Column(name = "F_ACTUALIZA", nullable = false)
    private Date fechaActualizacion;

    @Column(name = "C_USUCREACION", nullable = false)
    private Long usuarioCreacion;

    @Column(name = "C_USUACTUALIZA", nullable = false)
    private Long usuarioActualizacion;

}
