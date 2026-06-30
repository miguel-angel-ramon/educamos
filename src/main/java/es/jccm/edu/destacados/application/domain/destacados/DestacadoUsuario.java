package es.jccm.edu.destacados.application.domain.destacados;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(schema = "DELPHOS_MODACC", name = "ESC_DESTACADOS_USUARIOS")
public class DestacadoUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_DESTACADO_USUARIO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DESTACADO")
    private Destacado destacado;

    @Column(name = "OID_USUARIO")
    private Long oidUsuario;

    @Column(name = "NU_ORDEN")
    private Long numeroOrden;

    @Column(name = "LG_ACTIVO")
    private Boolean activo;

}
