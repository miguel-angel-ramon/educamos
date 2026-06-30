package es.jccm.edu.buzon.adapter.out.repository.buzonCentro;

import java.util.Date;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.buzon.application.domain.buzonCentro.BuzonCentro;
import es.jccm.edu.buzon.application.domain.buzonCentro.QBuzonCentro;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface BuzonCentroRepository extends AbstractRepository<BuzonCentro, Long, QBuzonCentro>{
	
	
	@Query(value = "SELECT * FROM DELPHOS.ESC_BUZON_CENTROS ESC WHERE ESC.OID_PERSONA = :oidPersona AND ESC.LG_ESTADO = 1", nativeQuery = true)
	BuzonCentro findByOidPersona(Long oidPersona);
		
	@Transactional
	@Modifying
	@Query(value = "UPDATE DELPHOS.ESC_BUZON_CENTROS buz"
			+ " SET buz.LG_ESTADO = 0"
			+ " WHERE buz.OID_PERSONA = :oidPersona AND buz.CENTRO = :centro", nativeQuery = true)
	void deleteVigencia(Long oidPersona, String centro);
	
	@Modifying
	@Query(value = "UPDATE DELPHOS.ESC_BUZON_CENTROS buz"
			+ " SET buz.F_ALTA = :fechaAlta"
			+ " SET buz.F_BAJA = :fechaBaja"
			+ " WHERE buz.OID_PERSONA = :oidPersona AND buz.CENTRO = :centro", nativeQuery = true)
	void updateVigencias(Date fechaAlta, Date fechaBaja, Long oidPersona, String centro);
}
