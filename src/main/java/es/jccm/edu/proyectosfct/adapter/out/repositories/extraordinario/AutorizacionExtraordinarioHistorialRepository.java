package es.jccm.edu.proyectosfct.adapter.out.repositories.extraordinario;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import es.jccm.edu.proyectosfct.application.domain.desplazamiento.projection.HistoricoFlujoAutorizacionProjection;
import es.jccm.edu.proyectosfct.application.domain.extraordinario.entities.AutorizacionExtraordinario;
import es.jccm.edu.proyectosfct.application.domain.extraordinario.entities.AutorizacionExtraordinarioHistorial;
import es.jccm.edu.proyectosfct.application.domain.extraordinario.entities.QAutorizacionExtraordinarioHistorial;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface AutorizacionExtraordinarioHistorialRepository extends AbstractRepository<AutorizacionExtraordinarioHistorial, Long, QAutorizacionExtraordinarioHistorial> {

    @Query(value = " select emp.nombre ||' '|| emp.apellido1 ||' '|| emp.apellido2 AS NOMBRE,        "
  	      + "            est.ds_nombre AS ESTADO,   "
  	      + "            his.fh_registro AS FECHA,  "
  	      + "            his.tx_observaciones AS OBSERVACIONES,  "
  	      + "            decode(his.fh_registro,MAX(his.fh_registro) OVER (PARTITION BY his.id_aut_ext),'S','N') AS ACTUAL,  "
  	      + "            est.ds_abrev AS abreviatura  "
  	      + "      from FCT_AUTEXTFUE_HISTORIAL his ,   "
  	      + "           fct_autorizaciones_flujo flu,  "
  	      + "           FCT_ESTADOS_AUTORIZACIONES est,  "
  	      + "           tlusuarios usu,  "
  	      + "           tlempleados emp  "
  	      + "      where his.id_aut_ext = :id  "
  	      + "      and flu.id_tipo_autorizacion = :idTipo  "
  	      + "      and his.id_autorizacion_flujo = flu.id_autorizacion_flujo  "
  	      + "      and flu.id_estado_des = est.id_estado_autorizacion   "
  	      + "      and usu.x_usuario = his.x_usuario   "
  	      + "      and emp.x_empleado = usu.x_empleado  "
  	      + "      order by FECHA desc ", nativeQuery = true)
  	 List<HistoricoFlujoAutorizacionProjection> getHistoricoFlujoAutorizacion(Long id, Long idTipo);

	void deleteByAutorizacionExtraordinario(AutorizacionExtraordinario autExt);
	
}
