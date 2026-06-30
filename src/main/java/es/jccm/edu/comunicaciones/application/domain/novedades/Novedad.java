package es.jccm.edu.comunicaciones.application.domain.novedades;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "TLMENSAJESSAL")
public class Novedad extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "X_MENSAJESAL")
    private Long idNovedad;

    @NotBlank
    @Column(name = "S_TITULO")
    private String titulo;

    @NotBlank
    @Column(name = "D_DESCRIPCION")
    private String descripcion;

    @NotBlank
    @Column(name = "F_GENERACION")
    private Date fechaGeneracion;

}
