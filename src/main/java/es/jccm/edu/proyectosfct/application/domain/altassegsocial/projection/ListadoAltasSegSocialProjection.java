package es.jccm.edu.proyectosfct.application.domain.altassegsocial.projection;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(name = "ListadoAltasSegSociProg", description = "Listado de altas de la Seguridad Social de Programas")
public interface ListadoAltasSegSocialProjection {

    @Schema(description = "Nombre alumno")
    Long getId();
    
    @Schema(description = "Nombre convenio alumno")
    Long getIdAluCon();

    @Schema(description = "Centro")
    String getCentro();
    
    @Schema(description = "Tutor")
    String getTutor();
    
    @Schema(description = "Dni del alumno")
    String getDni();
    
    @Schema(description = "Número de la Seguridad Social del alumno")
    String getNuss();

    @Schema(description = "Número de la Seguridad Social del alumno actualizado si ha sido corregido en FCT_CORRECCIONES_NUSS")
    String getNussActualizado();

    @Schema(description = "Nombre alumno")
    String getNombreAlumno();

    @Schema(description = "Nombre empresa")
    String getNombreEmpresa();

    @Schema(description = "Tipo empresa")
    String getTipoEmpresa();

    @Schema(description = "Curso")
    String getCurso();

    @Schema(description = "Unidad")
    String getUnidad();

    @Schema(description = "Fecha de inicio")
    Date getInicio();

    @Schema(description = "fecha prevista de fin")
    Date getFin();

    @Schema(description = "warnings que indican los errores que puede haber en los datos enviados")
    String getDsWarnings();

    @Schema(description = "Alumnos de erasmus con boca")
    Integer getAluConBeca();

    @Schema(description = "Alumnos de erasmus sin beca")
    Integer getAluSinBeca();

    @Schema(description = "Boolean para indicar la valicadión por el tutor")
    String getValTut();

    @Schema(description = "Boolean para indicar la validación por el centro")
    String getValCen();

    @Schema(description = "Boolean para indicar la validación por el delegado")
    String getValDel();
    
    @Schema(description = "Cif empresa")
    String getCif();
    
    @Schema(description = "Codigo centro")
    String getCcodigo();
    
    @Schema(description = "Enviado")
    String getEnviado();
    
    @Schema(description = "Puede rechazar")
    Integer getPuederechazar();
    
    @Schema(description = "Promociona")
    String getPromociona();

    @Schema(description = "fechaEnvioss")
    String getFechaEnvioss();
    
    @Schema(description = "Situacion")
    String getSituacion();  
    
    @Schema(description = "Anulado")
    Integer getAnulado();
    
    @Schema(description = "Registro correcto con el registro de empresa gestora")
    Integer getEsCorrecto();
    
    @Schema(description = "Identificador del registro gestora")
    Long getIdGestora();
    
    @Schema(description = "Fecha de incio de la ultima modificación o anulacion del registro")
    String getInicioHist();
    
    @Schema(description = "Fecha de incio de la ultima modificación o anulacion del registro")
    String getFinHist();
    
    @Schema(description = "errores que puede haber en los datos enviados")
    String getDsErrors();
    
    @Schema(description = "idInterno")
    Long getIdInterno();
    
    @Schema(description = "Nombre alumno envio")
    String getNombreAlumnoEnvio();
    
    @Schema(description = "Se puede solicitar un nuevo perido")
    String getNuevoperiodo();
    
    @Schema(description = "Mensaje presentado en la pantalla cuando la fecha de fin de alta sea mayor que la de fin prácticas")
    String getTextofin();
    
    @Schema(description = "Fecha de nacimiento del alumno")
    String getFhNacimiento();

    @Schema(description = "Flag que indica si el alumno se puede excluir ")
    Integer getEsExcluible();

    @Schema(description = "Sexo del alumno")
    String getTextoSexo();

    @Schema(description = "Comprueba si número de la seguridad social es provisional")
    Integer getNussProvisional();
    

}
