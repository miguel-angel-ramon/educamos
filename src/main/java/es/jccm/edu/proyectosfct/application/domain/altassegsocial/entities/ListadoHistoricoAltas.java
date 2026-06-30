package es.jccm.edu.proyectosfct.application.domain.altassegsocial.entities;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class ListadoHistoricoAltas extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombreCompleto")
    private String nombreCompleto;

    @Column(name = "fechaInicio")
    private Date fechaInicio;

    @Column(name = "fechaFin")
    private Date fechaFin;

    @Column(name = "erasmusCb")
    private Integer erasmusCb;

    @Column(name = "dsWarnings")
    private String dsWarnings;

    @Column(name = "erasmusSb")
    private Integer erasmusSb;

    @Column(name = "anulado")
    private Integer anulado;

    @Column(name = "fechaEnvio")
    private String fechaEnvio;
    
    @Column(name = "accion")
    private String accion;
    
    @Column(name = "nuPeticion")
    private String nuPeticion;

}
