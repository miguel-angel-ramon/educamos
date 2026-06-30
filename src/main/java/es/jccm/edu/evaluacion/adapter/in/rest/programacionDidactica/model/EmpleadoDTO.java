package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(name = "EmpleadoDTO", description = "DTO empleado")
public class EmpleadoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id del empleado")
    private Long id;
    
    @Schema(description = "Municipio")
    private Long municipio;
    
    @Schema(description = "Provincia")
    private Long provincia;
    
    @Schema(description = "Tipo de vía")
    private Long tipoVia;
    
    @Schema(description = "Tipo de identificacion")
    private String tipoIdentificacion;

    @Schema(description = "Identificacion")
    private String identificacion;

    @Schema(description = "Primer apellido")
    private String apellido1;

    @Schema(description = "Segundo apellido")
    private String apellido2;

    @Schema(description = "Nombre")
    private String nombre;

    @Schema(description = "Sexo")
    private String sexo;

    @DateTimeFormat(pattern="dd-MM-yyyy")
    @Schema(description = "Fecha de nacimiento")
    private Date nacimiento;

    @Schema(description = "Localidad")
    private Long localidad;

    @Schema(description = "Letra nif")
    private String letraNif;

    @Schema(description = "")
    private String nrp;

    @Schema(description = "Calle")
    private String calle;

    @Schema(description = "Numero de calle")
    private String numeroCalle;

    @Schema(description = "Escalera")
    private String escalera;

    @Schema(description = "Piso")
    private String piso;

    @Schema(description = "Letra")
    private String letra;

    @Schema(description = "Código postal")
    private String codigoPostal;

    @Schema(description = "Prefijo telefónico")
    private String prefijoTelefonico;

    @Schema(description = "Teléfono")
    private String telefono;

    @Schema(description = "Id del centro")
    private Long idCentro;

    @Schema(description = "Id del puesto")
    private Long idPuestos;

    @Schema(description = "")
    private String proSir;

    @Schema(description = "")
    private String proOriSir;

    @Schema(description = "")
    private Long idEmpSir;

    @Schema(description = "")
    private Long idTipoViaHab;

    @Schema(description = "")
    private String calleHab;

    @Schema(description = "")
    private String numCalHab;

    @Schema(description = "")
    private String escaleraHab;

    @Schema(description = "")
    private String pisoHab;

    @Schema(description = "")
    private String letraHab;

    @Schema(description = "")
    private String codPosHab;

    @Schema(description = "")
    private String telefonoHab;

    @Schema(description = "")
    private Long idLocalidadHab;

    @Schema(description = "")
    private Long municipioHab;

    @Schema(description = "")
    private Long provinciaHab;

    @Schema(description = "Correo electronico")
    private String correoElectronico;

    @Schema(description = "Activo")
    private String activo;

    @Schema(description = "Foto")
    private String foto;

    @Schema(description = "Ficticio")
    private String ficticio;

    @Schema(description = "Correo LDAP")
    private String correoLdap;

    @Schema(description = "Correo externo")
    private String correoExterno;

    @Schema(description = "Id persona")
    private Long idPersona;
}
