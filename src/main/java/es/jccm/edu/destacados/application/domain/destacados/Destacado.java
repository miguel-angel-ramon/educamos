package es.jccm.edu.destacados.application.domain.destacados;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(schema = "DELPHOS_MODACC", name = "ESC_DESTACADOS")
public class Destacado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DESTACADO")
    private Long id;

    @Column(name = "CD_DESTACADO")
    private String codigo;

    @Column(name = "ID_PERFIL")
    private Long idPerfil;

    @Column(name = "CD_PERFIL")
    private String codigoPerfil;

    @Column(name = "CD_APLICACION")
    private String codigoAplicacion;

    @Column(name = "TX_NOMBRE")
    private String nombre;

    @Column(name = "TX_URL")
    private String url;

    @Column(name = "NU_PAGINA")
    private Long numeroPagina;

    @Column(name = "NU_ORDEN")
    private Long numeroOrden;

    @Column(name = "LG_ORDEN_PREFERENTE")
    private Boolean ordenPreferente;

    @Column(name = "F_INICIO")
    private Date fechaInicio;

    @Column(name = "F_FIN")
    private Date fechaFin;

    @Column(name = "LG_ACTIVO")
    private Boolean activo;

}
