package es.jccm.edu.alumnos.adapter.out.repository.alumnosHorario;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.alumnos.application.domain.alumnosHorario.QTutor;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.Tutor;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.TlefDetalleProjection;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.TutorProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface TutoresRepository extends AbstractRepository<Tutor, Integer, QTutor> {

	@Query(value = "SELECT TUT.X_TUTOR idTutor, TUT.T_NOMBRE nombre, TUT.T_APELLIDO1 apellido1, "
			+ "TUT.T_APELLIDO2 apellido2, TUT.C_NUMIDE numide, "
			+ "TUT.T_TELEFONO telefono, TUT.T_TELEFONOURG tlefnourg, USU.X_USUARIO idUsuarioComunica "
			+ "FROM DELPHOS_SEGEDU.TLALUMNOS ALU, DELPHOS_SEGEDU.TLFAMILIASALU FAL, DELPHOS_SEGEDU.TLTUTORES TUT, DELPHOS_SEGEDU.TLUSUARIOS USU ,DELPHOS_SEGEDU.TLEMPLEADOS EMP "
			+ "WHERE ALU.X_ALUMNO = :idAlumno AND FAL.X_FAMILIA = ALU.X_FAMILIA "
			+ "AND (TUT.X_TUTOR = FAL.X_TUTOR1 OR TUT.X_TUTOR = FAL.X_TUTOR2) "
			+ "AND EMP.C_NUMIDE = TUT.C_NUMIDE "
			+ "AND EMP.X_EMPLEADO = USU.X_EMPLEADO", nativeQuery = true)
	List<TutorProjection> tutoresByAlumno(@Param("idAlumno") Long idAlumno);

	@Query(value = "select tel.X_TIPTEL idTipo, tel.T_TELEFONO telefono "
			+ "from DELPHOS_SEGEDU.tltiptel tip, DELPHOS_SEGEDU.tltelusu tel" + " where tip.X_TIPTEL=tel.X_TIPTEL"
			+ " and tel.x_usuario = (select x_usuario from DELPHOS_SEGEDU.tlusuarios "
			+ "where x_empleado = (select x_empleado from DELPHOS_SEGEDU.tlempleados where c_numide = :numide))", nativeQuery = true)
	List<TlefDetalleProjection> telefonosByTutor(@Param("numide") String idTutor);

}
