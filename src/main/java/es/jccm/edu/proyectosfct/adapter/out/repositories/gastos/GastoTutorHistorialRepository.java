package es.jccm.edu.proyectosfct.adapter.out.repositories.gastos;

import java.util.List;

import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoTutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoTutorHistorial;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.QGastoTutorHistorial;
import es.jccm.edu.proyectosfct.application.domain.gastos.projection.HistoricoFlujoGastosProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface GastoTutorHistorialRepository extends AbstractRepository<GastoTutorHistorial, Long, QGastoTutorHistorial> {
	
    @Query(value = " select emp.nombre ||' '|| emp.apellido1 ||' '|| emp.apellido2 AS NOMBRE,       "
    		+ "      est.ds_nombre AS ESTADO,  "
    		+ "      his.fh_registro AS FECHA, "
    		+ "      his.tx_observaciones AS OBSERVACIONES, "
    		+ "      decode(his.fh_registro,MAX(his.fh_registro) OVER (PARTITION BY his.id_gastos_tutor),'S','N') AS ACTUAL, "
    		+ "      est.ds_abrev AS abreviatura "
    		+ "from fct_gastos_tutor_historial his ,  "
    		+ "     fct_gastos_flujo flu, "
    		+ "     fct_estados_gastos est, "
    		+ "     tlusuarios usu, "
    		+ "     tlempleados emp "
    		+ "where id_gastos_tutor = :id "
    		+ "and flu.id_tipos_gastos = :idTipo "
    		+ "and his.id_gastos_flujo = flu.id_gastos_flujo "
    		+ "and flu.id_estado_des = est.id_estado_gasto  "
    		+ "and usu.x_usuario = his.x_usuario  "
    		+ "and emp.x_empleado = usu.x_empleado "
    		+ "and flu.id_tipos_gastos = 1 "    		
    		+ "UNION "
    		+ "select emp.nombre ||' '|| emp.apellido1 ||' '|| emp.apellido2 AS NOMBRE,       "
    		+ "      est.ds_nombre AS ESTADO,  "
    		+ "      his.fh_registro AS FECHA, "
    		+ "      his.tx_observaciones AS OBSERVACIONES, "
    		+ "      decode(his.fh_registro,MAX(his.fh_registro) OVER (PARTITION BY his.id_gastos_alumnado),'S','N') AS ACTUAL, "
    		+ "      est.ds_abrev AS abreviatura "
    		+ "from fct_gastos_alumnado_historial his ,  "
    		+ "     fct_gastos_flujo flu, "
    		+ "     fct_estados_gastos est, "
    		+ "     tlusuarios usu, "
    		+ "     tlempleados emp "
    		+ "where id_gastos_alumnado = :id "
    		+ "and flu.id_tipos_gastos = :idTipo "
    		+ "and his.id_gastos_flujo = flu.id_gastos_flujo "
    		+ "and flu.id_estado_des = est.id_estado_gasto  "
    		+ "and usu.x_usuario = his.x_usuario  "
    		+ "and emp.x_empleado = usu.x_empleado "
    		+ "and flu.id_tipos_gastos = 2 "
    		+ "UNION "
      		+ "select emp.nombre ||' '|| emp.apellido1 ||' '|| emp.apellido2 AS NOMBRE,       "
    		+ "      est.ds_nombre AS ESTADO,  "
    		+ "      his.fh_registro AS FECHA, "
    		+ "      his.tx_observaciones AS OBSERVACIONES, "
    		+ "      decode(his.fh_registro,MAX(his.fh_registro) OVER (PARTITION BY his.id_gastos_alumnado),'S','N') AS ACTUAL, "
    		+ "      est.ds_abrev AS abreviatura "
    		+ "from fct_gastos_alumnado_historial his ,  "
    		+ "     fct_gastos_flujo flu, "
    		+ "     fct_estados_gastos est, "
    		+ "     delphos_segedu.tlusuarios usu, "
    		+ "     delphos_segedu.tlempleados emp "
    		+ "where id_gastos_alumnado = :id "
    		+ "and flu.id_tipos_gastos = :idTipo "
    		+ "and his.id_gastos_flujo = flu.id_gastos_flujo "
    		+ "and flu.id_estado_des = est.id_estado_gasto  "
    		+ "and usu.x_usuario = his.x_usuario  "
    		+ "and emp.x_empleado = usu.x_empleado "
    		+ "and flu.id_tipos_gastos = 2 "
    		+ "and not exists (select 1 from tlusuarios where x_usuario = usu.x_usuario) "
    		+ "UNION "
    		+ "select emp.nombre ||' '|| emp.apellido1 ||' '|| emp.apellido2 AS NOMBRE,       "
    		+ "      est.ds_nombre AS ESTADO,  "
    		+ "      his.fh_registro AS FECHA, "
    		+ "      his.tx_observaciones AS OBSERVACIONES, "
    		+ "      decode(his.fh_registro,MAX(his.fh_registro) OVER (PARTITION BY his.id_gastos_anexos),'S','N') AS ACTUAL, "
    		+ "      est.ds_abrev AS abreviatura "
    		+ "from fct_anexos_historial his ,  "
    		+ "     fct_gastos_flujo flu, "
    		+ "     fct_estados_gastos est, "
    		+ "     tlusuarios usu, "
    		+ "     tlempleados emp "
    		+ "where id_gastos_anexos = :id "
    		+ "and flu.id_tipos_gastos = :idTipo "
    		+ "and his.id_gastos_flujo = flu.id_gastos_flujo "
    		+ "and flu.id_estado_des = est.id_estado_gasto  "
    		+ "and usu.x_usuario = his.x_usuario  "
    		+ "and emp.x_empleado = usu.x_empleado "
    		+ "and flu.id_tipos_gastos in (3,4) "
    		+ "order by FECHA desc ", nativeQuery = true)
	List<HistoricoFlujoGastosProjection> getHistoricoFlujoGastos(@Param("id") Long id, 
													             @Param("idTipo") Long idTipo);


	void deleteByGastoTutor(GastoTutor gastoTutor);
}
