package es.jccm.edu.documentosGC.adapter.out.repositories.firmantesdigital;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import es.jccm.edu.documentosGC.application.domain.firmantesdigital.entities.AdjuntosFirmantes;
import es.jccm.edu.documentosGC.application.domain.firmantesdigital.entities.QAdjuntosFirmantes;
import es.jccm.edu.documentosGC.application.domain.firmantesdigital.projection.DetalleFirmanteProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface AdjuntosFirmantesRepository extends AbstractRepository<AdjuntosFirmantes, Long, QAdjuntosFirmantes>  {

    void deleteAllByAdjuntoId(Long idAdjunto);

	@Query(value ="select count (*) "
			+ "from dgc_adjunto_firmantes "
			+ "where id_adjunto = :idAdjunto "
			+ "and lg_firmado = 0 ", nativeQuery = true)
	int algunFirmanteSinFirma(Long idAdjunto);

	Optional<AdjuntosFirmantes> findByAdjuntoIdAndEmpleadoId(Long idAdjunto, Long idEmpleado);

	@Query(value ="select id, "
			+ "       tipo, "
			+ "       documento,  "
			+ "       estado,  "
			+ "       usuario, "
			+ "       orden, "
			+ "       fecha, "
			+ "       firmado  "
			+ "from  "
			+ "(select fir.id_firmante id, tdoc.ds_descripcion tipo, doc.ds_descripcion documento, est.ds_nombre estado, "
			+ "       tlf_nombre(emp.nombre, emp.apellido1, emp.apellido2) usuario, "
			+ "       fir.nu_orden orden, fir.fh_firma fecha, fir.lg_firmado firmado, "
			+ "       his2.fh_registro, MAX(his2.fh_registro) OVER (PARTITION BY his2.id_documento) AS fh_registromax, "
			+ "       doc.id_documento "
			+ "from   dgc_adjunto_firmantes fir, dgc_historial_adjuntos adj, dgc_documento_historial his, dgc_documentos doc, dgc_tipos_documentos tdoc, tlempleados emp, "
			+ "       DGC_DOCUMENTO_HISTORIAL his2, DGC_ESTADOS_FLUJO flu, DGC_ESTADOS est "
			+ "where  fir.id_adjunto = adj.id_adjunto "
			+ "and    adj.id_historial = his.id_historial "
			+ "and    his.id_documento = doc.id_documento "
			+ "and    doc.id_tipo_documento = tdoc.id_tipo_documento "
			+ "and    fir.x_empleado = emp.x_empleado "
			+ "and    doc.id_documento = his2.id_documento "
			+ "and    his2.id_flujo = flu.id_flujo "
			+ "and    flu.id_estado_des = est.id_estado "
			+ "and    fir.id_adjunto = :idAdjunto) "
			+ "where  fh_registro = fh_registromax "
			+ "order  by orden, fecha, usuario ", nativeQuery = true)
	List<DetalleFirmanteProjection> getDetalleFirmantes(Long idAdjunto);
	
	@Query(value="select T_VALPAR from tlpargen where t_nompar = 'VALCERFIR'",nativeQuery = true)
	List<String> getEntornoFirma();

	@Query(value="select count(*) total from DGC_ADJUNTO_FIRMANTES where id_adjunto = :idAdjunto and lg_firmado = 1 ",nativeQuery = true)
	Integer getNumeroFirmados(Long idAdjunto);


	
}
