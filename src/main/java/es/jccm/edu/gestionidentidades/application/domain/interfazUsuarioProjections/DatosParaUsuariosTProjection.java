package es.jccm.edu.gestionidentidades.application.domain.interfazUsuarioProjections;

import java.util.Date;

public interface DatosParaUsuariosTProjection {
	
	 Long getOid();
	 Long getOid_persona();
	 String getT_login();
	 String getT_correo_e();
	 String getT_clave();
	 String getL_activo();
	 String getT_identificacion();
	 Long getOid_tipo_documentacion();
	 String getT_nombre();
	 String getT_apellido1();
	 String getT_apellido2();
	 Date getF_nacimiento();
	 String getEs_docente();
	 String getEs_alumno();
	 String getL_pendiente();
	 Long getUid_azure();
	 String getCorreo_aula();
	 String getCentro();
	 Long getUid_ldap();
	 String getMail_ldap();
	 Long getLg_equidirectivo();
	 Long getListacargos();
	 Long getTipo_personal();
	 Long getLg_tutor_unidad();
	 Long getCurso_tutor_unidad();
	 Long getDepartamento();
	 Long getUnidad_organizativa();
	 Long getPtotraemp();
	 Long getLg_comisioncoordpeda();

}
