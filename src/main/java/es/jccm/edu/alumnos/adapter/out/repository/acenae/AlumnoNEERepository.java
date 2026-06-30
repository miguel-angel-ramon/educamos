package es.jccm.edu.alumnos.adapter.out.repository.acenae;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.jccm.edu.alumnos.application.domain.acneae.AlumnoNEE;
import es.jccm.edu.alumnos.application.domain.acneae.QAlumnoNEE;
import es.jccm.edu.alumnos.application.domain.acneae.projection.AlumnoNEEProjection;
import es.jccm.edu.alumnos.application.domain.acneae.projection.DatosAlumnoNEEProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface AlumnoNEERepository extends AbstractRepository <AlumnoNEE,Long,QAlumnoNEE> {

	@Query(value="select distinct(alu.x_alumno) id, decode ((select count(*) from tlmatinfnee mne where mne.x_matricula=ma.x_matricula),0,'N','S') necesidadEducativa, "
			+ " ALU.T_NOMBRE nombre ,ALU.T_APELLIDO1 apellido1 ,ALU.T_APELLIDO2 apellido2, "
			+ " EGM.D_ESTGENMATR estadoMatricula, ALU.F_NACIMIENTO fechaNacimiento, ma.x_matricula idmatricula, "
			+ " case when ma.x_niveadap is not null then (select ofg.d_ofertamatrig from  tlofematrgen ofg where ofg.x_ofertamatrig=ma.x_niveadap) else '' end nivelAdaptación "
			+ " from  tlmatalu MA, tlalumnos ALU, TLESTGENMATR EGM, TLUNIDADESCEN UNI "
			+ " where ma.x_centro=:idCentro "
			+ " and ma.x_ofertamatrig=:idOfertaMatriculacion "
			+ " and ma.c_anno=:annio "
			+ " and ma.x_unidad=:idUnidad "
			+ " and ALU.x_alumno=ma.x_alumno "
			+ " AND MA.X_ESTGENMATR = EGM.X_ESTGENMATR(+) "
			+ " and (ma.x_estgenmatr is null or ma.x_estgenmatr not in (22,41))"
			+ " AND MA.X_UNIDAD = UNI.X_UNIDAD "
			+ " ORDER BY apellido1,apellido2,nombre  ", nativeQuery=true)	
	List <AlumnoNEEProjection> findAlumnosNEE(@Param("idCentro") Long idCentro,  @Param("annio")int annio,@Param("idOfertaMatriculacion") Long ofertaMatriculacion, @Param("idUnidad") Long unidad);
	

	@Query(value="SELECT ALU.X_ALUMNO id, X_FAMILIA idFamilia, C_NUMESCOLAR  numeroEscolar,   "
			+ "   T_APELLIDO1 AS apellido1, T_APELLIDO2 AS apellido2, T_NOMBRE AS nombre,  "
			+ "   C_NUMIDE nIdentificacion, C_TIPIDE tipoIdentificacion, F_NACIMIENTO  fechaNacimiento, L_EMANCIPADO emancipado,NVL(L_ESPANOL,'N') espanol,   "
			+ "   C_PROVINCIA_RESIDE AS codProvinciaResidencia,   "
			+ "   C_MUNICIPIO_RESIDE AS codMunicipioResidencia,   "
			+ "   X_LOCALIDAD_RESIDE AS codLocalidadResidencia,  "
			+ "   C_POSTAL , T_TELEFONO,T_CORREO_E, MUN.D_MUNICIPIO AS descMunicipio , L_SEXO,   "
			+ "   PROV.D_PROVINCIA, LOC.D_LOCALIDAD,   "
			+ "  TV.D_TIPOVIA AS tipoVia,   "
			+ "   ALU.T_CALLE AS nombreVia,   "
			+ "   ALU.T_NUMCALLE AS numeroCalle,  "
			+ "   ALU.T_ESCALERA AS escalera,   "
			+ "   ALU.T_PISO AS piso,   "
			+ "   ALU.T_LETRA AS letra   "
			+ "   FROM TLALUMNOS ALU, TLMUNICIPIOS MUN, TLPROVINCIAS PROV, TLLOCALIDADES LOC, TLMATALU MA , TLTIPOVIAS TV   "
			+ "   WHERE ALU.C_MUNICIPIO_RESIDE=MUN.C_MUNICIPIO(+) AND ALU.C_PROVINCIA_RESIDE=MUN.C_PROVINCIA(+) AND ALU.X_ALUMNO = MA.X_ALUMNO AND MA.X_MATRICULA=:idMatricula   "
			+ "   AND ALU.C_PROVINCIA_RESIDE = PROV.C_PROVINCIA(+) AND ALU.C_PROVINCIA_RESIDE = LOC.C_PROVINCIA(+)   "
			+ "   AND ALU.C_MUNICIPIO_RESIDE = LOC.C_MUNICIPIO(+) AND ALU.X_LOCALIDAD_RESIDE = LOC.X_LOCALIDAD(+)  "
			+ "   AND ALU.X_TIPOVIA=TV.X_TIPOVIA ",nativeQuery=true)
	DatosAlumnoNEEProjection findDatoAlumno(@Param("idMatricula") Long idMatricula);

}
