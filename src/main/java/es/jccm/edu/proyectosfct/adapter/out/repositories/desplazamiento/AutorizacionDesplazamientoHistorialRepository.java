package es.jccm.edu.proyectosfct.adapter.out.repositories.desplazamiento;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionDesplazamiento;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionDesplazamientoHistorial;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.QAutorizacionDesplazamientoHistorial;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.projection.HistoricoFlujoAutorizacionProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface AutorizacionDesplazamientoHistorialRepository extends AbstractRepository<AutorizacionDesplazamientoHistorial, Long, QAutorizacionDesplazamientoHistorial> {

    @Query(value = " select emp.nombre ||' '|| emp.apellido1 ||' '|| emp.apellido2 AS NOMBRE,        "
    	      + "            est.ds_nombre AS ESTADO,   "
    	      + "            his.fh_registro AS FECHA,  "
    	      + "            his.tx_observaciones AS OBSERVACIONES,  "
    	      + "            decode(his.fh_registro,MAX(his.fh_registro) OVER (PARTITION BY his.id_aut_des),'S','N') AS ACTUAL,  "
    	      + "            est.ds_abrev AS abreviatura  "
    	      + "      from FCT_AUTDES_HISTORIAL his ,   "
    	      + "           fct_autorizaciones_flujo flu,  "
    	      + "           FCT_ESTADOS_AUTORIZACIONES est,  "
    	      + "           tlusuarios usu,  "
    	      + "           tlempleados emp  "
    	      + "      where his.id_aut_des = :id  "
    	      + "      and flu.id_tipo_autorizacion = :idTipo  "
    	      + "      and his.id_autorizacion_flujo = flu.id_autorizacion_flujo  "
    	      + "      and flu.id_estado_des = est.id_estado_autorizacion   "
    	      + "      and usu.x_usuario = his.x_usuario   "
    	      + "      and emp.x_empleado = usu.x_empleado  "
    	      + "      order by FECHA desc ", nativeQuery = true)
    	 List<HistoricoFlujoAutorizacionProjection> getHistoricoFlujoAutorizacion(Long id, Long idTipo);
    
    @Query(value = " select emp.nombre ||' '|| emp.apellido1 ||' '|| emp.apellido2 AS NOMBRE,        "
  	      + "            est.ds_nombre AS ESTADO,   "
  	      + "            his.fh_registro AS FECHA,  "
  	      + "            his.tx_observaciones AS OBSERVACIONES,  "
  	      + "            decode(his.fh_registro,MAX(his.fh_registro) OVER (PARTITION BY his.id_aut_extpro),'S','N') AS ACTUAL,  "
  	      + "            est.ds_abrev AS abreviatura  "
  	      + "      from fct_autextfue_historial his ,   "
  	      + "           fct_autorizaciones_flujo flu,  "
  	      + "           FCT_ESTADOS_AUTORIZACIONES est,  "
  	      + "           tlusuarios usu,  "
  	      + "           tlempleados emp  "
  	      + "      where his.id_aut_extpro = :id  "
  	      + "      and flu.id_tipo_autorizacion = :idTipo  "
  	      + "      and his.id_autorizacion_flujo = flu.id_autorizacion_flujo  "
  	      + "      and flu.id_estado_des = est.id_estado_autorizacion   "
  	      + "      and usu.x_usuario = his.x_usuario   "
  	      + "      and emp.x_empleado = usu.x_empleado  "
  	      + "      order by FECHA desc ", nativeQuery = true)
  	 List<HistoricoFlujoAutorizacionProjection> getHistoricoFlujoAutorizacionExtraordinario(Long id, Long idTipo);
    
    @Query(value = " select emp.nombre ||' '|| emp.apellido1 ||' '|| emp.apellido2 AS NOMBRE,             "
    		+ "    	        est.ds_nombre AS ESTADO,        "
    		+ "    	        his.fh_registro AS FECHA,       "
    		+ "    	        his.tx_observaciones AS OBSERVACIONES,       "
    		+ "    	        decode(his.fh_registro,MAX(his.fh_registro) OVER (PARTITION BY his.id_autorizacion_anexo),'S','N') AS ACTUAL,       "
    		+ "    	        est.ds_abrev AS abreviatura       "
    		+ "    	  from fct_aneaut_historial his ,        "
    		+ "    	       fct_autorizaciones_flujo flu,       "
    		+ "    	       FCT_ESTADOS_AUTORIZACIONES est,       "
    		+ "    	       tlusuarios usu,       "
    		+ "    	       tlempleados emp       "
    		+ "    	  where his.id_autorizacion_anexo= :id       "
    		+ "    	  and flu.id_tipo_autorizacion = :idTipo       "
    		+ "    	  and his.id_autorizacion_flujo = flu.id_autorizacion_flujo       "
    		+ "    	  and flu.id_estado_des = est.id_estado_autorizacion        "
    		+ "    	  and usu.x_usuario = his.x_usuario        "
    		+ "    	  and emp.x_empleado = usu.x_empleado       "
    		+ "    	  order by FECHA desc ", nativeQuery = true)
    	 List<HistoricoFlujoAutorizacionProjection> getHistoricoFlujoAutorizacionAnexos(Long id, Long idTipo);

	void deleteByAutorizacionDesplazamiento(AutorizacionDesplazamiento autDes);

}
