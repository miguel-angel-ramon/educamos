package es.jccm.edu.ausencias.adapter.out.repository.profesores;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.ausencias.application.domain.profesores.AusenciasProfesores;
import es.jccm.edu.ausencias.application.domain.profesores.QAusenciasProfesores;
import es.jccm.edu.ausencias.application.domain.profesores.projection.AusenciasProfesoresProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface AusenciasProfesoresRepository
		extends AbstractRepository<AusenciasProfesores, Integer, QAusenciasProfesores> {
	@Query(value = "SELECT AUS.X_AUSENCIA idAusencia, AUS.X_EMPLEADO idEmpleado, MOT.D_MOTIVO motivo, AUS.F_INICIO fechaInicioAusencia, AUS.F_FINAL fechaFinAusencia, "
			+ "TLF_NOMBRE(EMP.NOMBRE, EMP.APELLIDO1, EMP.APELLIDO2) nombre, EMP.TELEFONO telefono, EMP.T_CORREO_E correo "
			+ "FROM TLAUSENCIAS AUS, TLCENTROS CEN, TLCURSOACA ANA, TLEMPLEADOS EMP, TLMOTAUS MOT "
			+ "WHERE AUS.X_CENTRO = CEN.X_CENTRO "
			+ "AND AUS.X_EMPLEADO = EMP.X_EMPLEADO "
			+ "AND AUS.C_MOTIVO = MOT.C_MOTIVO "
			+ "AND CEN.C_CODIGO = :codCentro "
			+ "AND ANA.L_VISIBLE= 'S' "
			+ "AND ANA.C_ANNO = :anno "
			+ "AND to_date(:fecha, 'DD/MM/YYYY') >= AUS.F_INICIO "
			+ "AND (to_date(:fecha, 'DD/MM/YYYY') <= AUS.F_FINAL OR AUS.F_FINAL IS NULL) "
			+ "ORDER BY AUS.F_INICIO, nombre, AUS.F_INICIO", nativeQuery = true)
	List<AusenciasProfesoresProjection> getAusenciasProfesores(@Param("codCentro") Long codCentro, @Param("anno") Integer anno, @Param("fecha") String fecha);
}
