package es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "TLEMPLEADOS", schema = "DELPHOS")
public class EvaEmpleado extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "X_EMPLEADO")
    private Long id;

    @Column(name = "C_MUNICIPIO") //TODO: Relationship
    private Long municipio;

    @Column(name = "C_PROVINCIA") //TODO: Relationship
    private Long provincia;

    @Column(name = "X_TIPOVIA") //TODO: Relationship
    private Long tipoVia;

    @NotBlank
    @Column(name = "L_TIPIDE", columnDefinition = "VARCHAR2(1) DEFAULT 'D'")
    private String tipoIdentificacion;

    @NotBlank
    @Column(name = "C_NUMIDE")
    private String identificacion;

    @NotBlank
    @Column(name = "APELLIDO1")
    private String apellido1;

    @Column(name = "APELLIDO2")
    private String apellido2;

    @NotBlank
    @Column(name = "NOMBRE")
    private String nombre;

    @NotBlank
    @Column(name = "L_SEXO", columnDefinition = "VARCHAR2(1) DEFAULT 'V'")
    private String sexo;

    @NotNull
    @Column(name = "F_NACIMIENTO")
    private Date nacimiento;

    @Column(name = "X_LOCALIDAD")
    private Long localidad;

    @Column(name = "C_LETNIF")
    private String letraNif;

    @Column(name = "NRP")
    private String nrp;

    @Column(name = "CALLE")
    private String calle;

    @Column(name = "NUMCAL")
    private String numeroCalle;

    @Column(name = "ESCALERA")
    private String escalera;

    @Column(name = "PISO")
    private String piso;

    @Column(name = "LETRA")
    private String letra;

    @Column(name = "CODPOS")
    private String codigoPostal;

    @Column(name = "PRETEL")
    private String prefijoTelefonico;

    @Column(name = "TELEFONO")
    private String telefono;

    @Column(name = "X_CENTRAS") //TODO: Relationship
    private Long idCentro;

    @Column(name = "X_PUESTOS") //TODO: Relationship
    private Long idPuestos;

    @NotBlank
    @Column(name = "L_PROSIR", columnDefinition = "VARCHAR2(1) DEFAULT 'V'")
    private String proSir;

    @Column(name = "L_PROORISIR")
    private String proOriSir;

    @Column(name = "X_EMPSIR")
    private Long idEmpSir;

    @Column(name = "X_TIPOVIA_HAB") //TODO: Relationship
    private Long idTipoViaHab;

    @Column(name = "CALLE_HAB")
    private String calleHab;

    @Column(name = "NUMCAL_HAB")
    private String numCalHab;

    @Column(name = "ESCALERA_HAB")
    private String escaleraHab;

    @Column(name = "PISO_HAB")
    private String pisoHab;

    @Column(name = "LETRA_HAB")
    private String letraHab;

    @Column(name = "CODPOS_HAB")
    private String codPosHab;

    @Column(name = "TELEFONO_HAB")
    private String telefonoHab;

    @Column(name = "X_LOCALIDAD_HAB") //TODO: Relationship
    private Long idLocalidadHab;

    @Column(name = "C_MUNICIPIO_HAB") //TODO: Relationship
    private Long municipioHab;

    @Column(name = "C_PROVINCIA_HAB") //TODO: Relationship
    private Long provinciaHab;

    @Column(name = "T_CORREO_E")
    private String correoElectronico;

    @Column(name = "L_ACTIVO")
    private String activo;

    @Column(name = "T_FOTO")
    private String foto;

    @NotBlank
    @Column(name = "L_FICTICIO", columnDefinition = "VARCHAR2(1) DEFAULT 'N'")
    private String ficticio;

    @Column(name = "T_CORREO_LDAP")
    private String correoLdap;

    @Column(name = "T_CORREO_EXTERNO")
    private String correoExterno;

    @Column(name = "X_PERSONA")
    private Long idPersona;
}