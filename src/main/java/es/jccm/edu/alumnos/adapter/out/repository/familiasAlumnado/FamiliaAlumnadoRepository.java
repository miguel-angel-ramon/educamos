package es.jccm.edu.alumnos.adapter.out.repository.familiasAlumnado;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.jccm.edu.alumnos.application.domain.familiasAlumnado.FAlumno;
import es.jccm.edu.alumnos.application.domain.familiasAlumnado.QFAlumno;
import es.jccm.edu.alumnos.application.domain.familiasAlumnado.projection.FamiliaAlumnoProjection;
import es.jccm.edu.alumnos.application.domain.familiasAlumnado.projection.TutorFamiliaProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface FamiliaAlumnadoRepository extends AbstractRepository<FAlumno, Long, QFAlumno> {
	
	@Query(value="SELECT FAM.X_FAMILIA idFamilia "
			+ ", (SELECT C_NUMIDE FROM TLTUTORES WHERE X_TUTOR=FAM.X_TUTOR1) numideTutor1 "
			+ " ,(SELECT T_APELLIDO1||' '||T_APELLIDO2||', '||T_NOMBRE FROM TLTUTORES WHERE X_TUTOR=FAM.X_TUTOR1) nombreTutor1"
			+ ", FAM.X_TUTOR1 idTutor1"
			+ " ,(SELECT C_NUMIDE FROM TLTUTORES WHERE X_TUTOR=FAM.X_TUTOR2) numideTutor2 "
			+ " ,(SELECT T_APELLIDO1||' '||T_APELLIDO2||', '||T_NOMBRE FROM TLTUTORES WHERE X_TUTOR=FAM.X_TUTOR2) nombreTutor2 "
			+ ",  FAM.X_TUTOR2 idTutor2"
			+ ", TLF_DIRECCION(X_TIPOVIA, T_CALLE,T_NUMCALLE,T_ESCALERA,T_PISO,T_LETRA, 'C')  direccion"
			+ ", FAM.T_TELEFONO TELEFONO"
			+ ", FAM.T_TELEFONOURG tfnoUrgencias"
			+ ",(SELECT DECODE(  (SELECT COUNT(distinct(lpad(c_numide,9,'0'))) FROM TLTUTORES  WHERE X_FAMILIA=FAM.X_FAMILIA or x_tutor=fAM.x_tutor1 or x_tutor=fAM.x_tutor2), 2,'Tutor 1 y Tutor 2',1,"
			+ " DECODE( ( SELECT COUNT(*) FROM TLTUTORES "
			+ " WHERE X_TUTOR=FAM.X_TUTOR1  ),1,'Tutor 1','Tutor 2') ,NULL) "
			+ "FROM TLFAMILIASALU F"
			+ " WHERE X_FAMILIA=FAM.X_FAMILIA) domicilioDe " 
			+ "   FROM   ( SELECT DISTINCT X_FAMILIA FROM TLALUMNOS ALU, TLMATALU MUA"
			+ "  WHERE ALU.X_ALUMNO=MUA.X_ALUMNO  AND X_CENTRO = :idCentro"
			+ "  AND MUA.C_ANNO = DECODE(:annio,-2,MUA.C_ANNO,:annio)"
			+ "  AND L_HISTORICO ='N'"
			+ "  AND NVL(mua.x_estgenmatr,-1) NOT IN (tlf_parametro('ESTGENM_ANU'),tlf_parametro('ESTGENM_TMV'))) ALUM,"
			+ "  TLFAMILIASALU FAM "
			+ "   WHERE  ALUM.X_FAMILIA = FAM.X_FAMILIA  ORDER BY nombreTutor1 ",nativeQuery=true)
	List<FamiliaAlumnoProjection> findFamiliasAlumnadoCentro(@Param("idCentro")Long idCentro, @Param("annio") int annio );
	
	@Query(value="SELECT DISTINCT TLF_NOMBRE( TUT.T_NOMBRE,TUT.T_APELLIDO1, TUT.T_APELLIDO2 ) AS nombre,"
			+ " CASE WHEN TUT.C_NUMIDE IS NULL THEN   'Sin documentación'   "
			+ " ELSE "
			+ "   CASE TUT.C_TIPIDE WHEN 'D' THEN  'DNI'"
			+ "   WHEN 'P' THEN   'Pasaporte' "
			+ "    ELSE   'Otros' "
			+ "    END "
			+ " END tipoIDE, "
			+ " TUT.C_NUMIDE AS dni,"
			+ " NVL(FAL.T_TELEFONO,TUT.T_TELEFONO) AS telefono,"
			+ " NVL(FAL.T_TELEFONOURG,TUT.T_TELEFONOURG) AS telefonoUrgencia,"
			+ " TLF_DIR_TUTOR(TUT.X_TUTOR) AS domicilio,"
			+ " TUT.X_TUTOR AS id,mua.c_resultado AS resultado"
			+ " FROM TLTUTORES TUT, TLFAMILIASALU FAL, TLALUMNOS ALU, TLMATALU MUA"
			+ " WHERE (TUT.X_TUTOR = FAL.X_TUTOR1 OR TUT.X_TUTOR = FAL.X_TUTOR2)"
			+ " AND FAL.X_FAMILIA = ALU.X_FAMILIA"
			+ " AND ALU.X_ALUMNO = MUA.X_ALUMNO"
			+ " AND NVL(MUA.C_RESULTADO,100)>1"
			+ " AND MUA.C_ANNO = :annio"
			+ " AND MUA.X_CENTRO = :idCentro"
			+ " ORDER BY TLF_NOMBRE( TUT.T_NOMBRE,TUT.T_APELLIDO1, TUT.T_APELLIDO2 )",nativeQuery=true)
	List<TutorFamiliaProjection>findTutoresFamiliaCentro(@Param("idCentro")Long idCentro, @Param("annio")int annio);
			
	List<FAlumno> findByFamiliaId(Long idFamilia);
	
	List<FAlumno> findByFamiliaIdTutor1OrFamiliaIdTutor2(Long idTutor,Long IdTutor);
	List <FAlumno> findDistinctByFamiliaIdTutor1OrFamiliaIdTutor2AndMatriculasIdCentro(Long idTutor,Long IdTutor,Long idCentro);
	
}
