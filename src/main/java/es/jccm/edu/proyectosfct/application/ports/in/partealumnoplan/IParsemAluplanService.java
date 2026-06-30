package es.jccm.edu.proyectosfct.application.ports.in.partealumnoplan;

import es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan.model.InfoParteSemanalDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan.model.ParsemAluplanDto;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.ParsemAluplan;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IParsemAluplanService {

    ParsemAluplanDto crearParsemAluplan(ParsemAluplan parsemAluplan);

    ParsemAluplanDto actualizarParsemAluplan(Long id, ParsemAluplanDto parsemAluplanDto);

    boolean borrarParsemAluplan(Long id);

    Optional<ParsemAluplanDto> buscarPorId(Long id);

    Optional<Long> obtenerIdParsemPorConvProyYFecha(Long idConvProyAlu, Date fechaInicioSem);

    Optional<ParsemAluplanDto> obtenerParsemAluplanDtoPorConvProyYFecha(Long idConvProyAlu, Date fechaInicio);

    List<ParsemAluplanDto> obtenerPartesSemanales(Long idConvProyAlu, List<String> fechas);

	List<InfoParteSemanalDto> getInfoParteSemanal(Long idConvProyAluPar, List<String> fechasPar);
}
