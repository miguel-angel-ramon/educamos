package es.jccm.edu.buzon.adapter.out.repository.buzonAlumnado;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.buzon.application.domain.buzon.BuzonAlumnado;
import es.jccm.edu.buzon.application.domain.buzon.QBuzonAlumnado;
import es.jccm.edu.buzon.application.domain.buzonCentro.projection.AlumnoUnidadProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface BuzonAlumnadoRepository extends AbstractRepository<BuzonAlumnado, Long, QBuzonAlumnado>{
	
	
	@Query(value = "SELECT * FROM DELPHOS.ESC_BUZON_ALUMNADO ESC WHERE ESC.OID_PERSONA = :oidPersona AND ESC.LG_ESTADO = 1", nativeQuery = true)
	BuzonAlumnado findByOidPersona(Long oidPersona);
		
	@Transactional
	@Modifying
	@Query(value = "UPDATE DELPHOS.ESC_BUZON_ALUMNADO buz"
			+ " SET buz.LG_ESTADO = 0"
			+ " WHERE buz.OID_PERSONA = :oidPersona AND buz.CENTRO = :centro", nativeQuery = true)
	void deleteVigencia(Long oidPersona, String centro);
	
	@Modifying
	@Query(value = "UPDATE DELPHOS.ESC_BUZON_ALUMNADO buz"
			+ " SET buz.F_ALTA = :fechaAlta"
			+ " SET buz.F_BAJA = :fechaBaja"
			+ " WHERE buz.OID_PERSONA = :oidPersona AND buz.CENTRO = :centro", nativeQuery = true)
	void updateVigencias(Date fechaAlta, Date fechaBaja, Long oidPersona, String centro);
	
	@Query(value = "SELECT tl.X_MATRICULA AS idMatricula, tl.X_ALUMNO AS idAlumno, tl.X_UNIDAD AS idUnidad, unicen.T_NOMBRE AS nombreUnidad, ofegen.D_OFERTAMATRIG AS curso, p.oid AS idPersona, p.T_NOMBRE AS nombre, p.T_APELLIDO1 AS apellido1, p.T_APELLIDO2 AS apellido2 FROM DELPHOS.TLMATALU tl "
			+ "INNER JOIN DELPHOS.TLUNIDADESCEN unicen ON unicen.X_UNIDAD = tl.X_UNIDAD "
			+ "INNER JOIN DELPHOS.TLOFERTASUNIDAD ou ON ou.X_UNIDAD = tl.X_UNIDAD "
			+ "INNER JOIN DELPHOS.TLOFEMATRCEN ofe ON ofe.X_OFERTAMATRIC = ou.X_OFERTAMATRIC "
			+ "INNER JOIN DELPHOS.TLOFEMATRGEN ofegen ON ofegen.X_OFERTAMATRIG = ofe.X_OFERTAMATRIG "
			+ "INNER JOIN DELPHOS.TLALUMNOS alu ON alu.X_ALUMNO = tl.X_ALUMNO "
			+ "INNER JOIN DELPHOS.TLPERSONAS per ON per.X_PERSONA = alu.X_PERSONA "
			+ "INNER JOIN DELPHOS_MODACC.PERSONAS p ON per.X_PERSONA = p.X_PERSONA "
			+ "WHERE tl.X_CENTRO = :idCentro AND tl.C_ANNO = :anno", nativeQuery = true)
	List<AlumnoUnidadProjection> getAlumnosUnidadesByCentro(@Param("idCentro") Long idCentro, @Param("anno") Long anno);
}
