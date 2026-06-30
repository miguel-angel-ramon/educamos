package es.jccm.edu.buzon.application.domain.solicitudCorreoAlumno;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

public interface UsuariotProjection {
	
	//Esta clase tiene los nombres de los atributos en notación "fea" porque se usan posteriormente para generar un CSV y tratar los datos 
	//en un proceso automatizado. ¡No cambiar los nombres!

    @Schema(description = "Identificador único del usuario")
    Long getOID();

    @Schema(description = "Identificador único de la persona asociada al usuario")
    Long getOID_PERSONA();

    @Schema(description = "Nombre de usuario (login)")
    String getT_LOGIN();

    @Schema(description = "Correo electrónico del usuario")
    String getT_CORREO_E();

    @Schema(description = "Clave del usuario")
    String getT_CLAVE();

    @Schema(description = "Indicador de activo del usuario")
    String getL_ACTIVO();

    @Schema(description = "Identificación del usuario")
    String getT_IDENTIFICACION();

    @Schema(description = "Identificador único del tipo de documentación")
    Long getOID_TIPO_DOCUMENTACION();

    @Schema(description = "Nombre del usuario")
    String getT_NOMBRE();

    @Schema(description = "Primer apellido del usuario")
    String getT_APELLIDO1();

    @Schema(description = "Segundo apellido del usuario")
    String getT_APELLIDO2();

    @Schema(description = "Fecha de nacimiento del usuario")
    Date getF_NACIMIENTO();

    @Schema(description = "Indicador de docente del usuario")
    String getES_DOCENTE();

    @Schema(description = "Indicador de alumno del usuario")
    String getES_ALUMNO();

    @Schema(description = "Indicador de pendiente del usuario")
    String getL_PENDIENTE();

    @Schema(description = "UID Azure del usuario")
    String getUID_AZURE();

    @Schema(description = "Correo aula del usuario")
    String getCORREO_AULA();

    @Schema(description = "Centro del usuario")
    String getCENTRO();

    @Schema(description = "UID LDAP del usuario")
    String getUID_LDAP();

    @Schema(description = "Correo LDAP del usuario")
    String getMAIL_LDAP();

    @Schema(description = "Indicador de equidirectivo del usuario")
    Integer getLG_EQUIDIRECTIVO();

    @Schema(description = "Lista de cargos del usuario")
    String getLISTACARGOS();

    @Schema(description = "Tipo personal del usuario")
    Integer getTIPO_PERSONAL();

    @Schema(description = "Indicador de tutor de unidad del usuario")
    Integer getLG_TUTOR_UNIDAD();

    @Schema(description = "Curso de tutor de unidad del usuario")
    String getCURSO_TUTOR_UNIDAD();

    @Schema(description = "Departamento del usuario")
    String getDEPARTAMENTO();

    @Schema(description = "Unidad organizativa del usuario")
    String getUNIDAD_ORGANIZATIVA();

    @Schema(description = "Ptotraemp del usuario")
    String getPTOTRAEMP();

    @Schema(description = "Indicador de comisión coordinadora pedagógica del usuario")
    Integer getLG_COMISIONCOORDPEDA();

    @Schema(description = "Indicador de usuario nocturno")
    Integer getLG_NOCTURNO();

    @Schema(description = "Creador del usuario")
    Long getC_USUCREACION();

    @Schema(description = "Fecha de creación del usuario")
    Date getF_CREACION();

    @Schema(description = "Usuario que actualiza el usuario")
    Long getC_USUACTUALIZA();

    @Schema(description = "Fecha de actualización del usuario")
    Date getF_ACTUALIZA();

    @Schema(description = "Indicador de habilitación en OWA del usuario")
    Integer getLG_HABILITAOWA();

    @Schema(description = "Indicador de acceso al buzón del usuario")
    Integer getLG_ACCESOBUZON();

    @Schema(description = "Nextval del usuario")
    Long getNEXTVAL();

    @Schema(description = "Campo '1' del usuario")
    Long getCRT_PT();

    @Schema(description = "Indicador de acceso al buzón del alumnado del usuario")
    Integer getLG_ACCESOBUZONALUMNADO();

    @Schema(description = "Oid anterior del usuario")
    Long getOID_ANT();

    @Schema(description = "Cod.Permiso del usuario")
    String getCD_PERMISO_CORREO_ALUMNADO();
}
