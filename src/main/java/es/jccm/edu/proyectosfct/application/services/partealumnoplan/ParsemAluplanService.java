package es.jccm.edu.proyectosfct.application.services.partealumnoplan;

import es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan.model.InfoParteSemanalDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan.model.ParsemAluplanDto;
import es.jccm.edu.proyectosfct.adapter.out.repositories.partealumnoplan.ParsemAluplanRepository;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.DatosHojaSemanal;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.ParsemAluplan;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.projection.InfoParteSemanalProjection;
import es.jccm.edu.proyectosfct.application.ports.in.partealumnoplan.IParsemAluplanService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParsemAluplanService implements IParsemAluplanService {

    @Autowired
    private ParsemAluplanRepository parsemAluplanRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public ParsemAluplanDto crearParsemAluplan(ParsemAluplan parsemAluplan) {
        ParsemAluplan entidadGuardada = parsemAluplanRepository.save(parsemAluplan);
        return modelMapper.map(entidadGuardada, ParsemAluplanDto.class);
    }

    @Override
    @Transactional
    public ParsemAluplanDto actualizarParsemAluplan(Long id, ParsemAluplanDto parsemAluplanDto) {
        // Buscar la entidad existente
        ParsemAluplan existente = parsemAluplanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ParsemAluplan no encontrado con ID: " + id));

        // Actualizar los valores con los datos del DTO
        modelMapper.map(parsemAluplanDto, existente);

        // Guardar los cambios en la base de datos
        ParsemAluplan entidadActualizada = parsemAluplanRepository.save(existente);

        // Convertir la entidad actualizada a DTO y devolver
        return modelMapper.map(entidadActualizada, ParsemAluplanDto.class);
    }

    @Override
    @Transactional
    public boolean borrarParsemAluplan(Long id) {
        // Verificar si la entidad existe
        if (parsemAluplanRepository.existsById(id)) {
            // TO-DO: pendiente de establacer a null la referencia en los partes diarios asociados
            parsemAluplanRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<ParsemAluplanDto> buscarPorId(Long id) {
        // Buscar por ID y mapear a DTO si existe
        return parsemAluplanRepository.findById(id)
                .map(entidad -> modelMapper.map(entidad, ParsemAluplanDto.class));
    }

    @Override
    public Optional<Long> obtenerIdParsemPorConvProyYFecha(Long idConvProyAlu, Date fechaInicioSem) {
        // Llama al repositorio para obtener la entidad y extraer el ID
        ParsemAluplan parsemAluplan = parsemAluplanRepository.findByIdConvProyAluAndFechaInicioSem(idConvProyAlu, fechaInicioSem);
        if (parsemAluplan != null) {
            return Optional.of(parsemAluplan.getIdParsemAluplan());
        }
        return Optional.empty();
    }

    public Optional<ParsemAluplanDto> obtenerParsemAluplanDtoPorConvProyYFecha(Long idConvProyAlu, Date fechaInicio) {
        // Llama al repositorio para obtener la entidad
        ParsemAluplan parsemAluplan = parsemAluplanRepository.findByIdConvProyAluAndFechaInicioSem(idConvProyAlu, fechaInicio);

        // Convertir la entidad a DTO si existe
        return Optional.ofNullable(parsemAluplan)
                .map(entity -> modelMapper.map(entity, ParsemAluplanDto.class));
    }

    public List<ParsemAluplanDto> obtenerPartesSemanales(Long idConvProyAlu, List<String> fechas) {
        // Convertir las fechas de String a Date
        List<Date> fechasConvertidas = convertirFechasAFormatoDate(fechas);

        // Obtener las entidades desde el repositorio
        List<ParsemAluplan> partes = parsemAluplanRepository.findByIdConvProyAluAndFechas(idConvProyAlu, fechasConvertidas);

        // Formato para la conversión de fechas a String
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        // Mapear entidades a DTOs y convertir las fechas
        return partes.stream()
                .map(parte -> {
                    ParsemAluplanDto dto = modelMapper.map(parte, ParsemAluplanDto.class);

                    // Convertir fInisem a String si no es null
                    dto.setFInisem(parte.getFInisem() != null ? dateFormat.format(parte.getFInisem()) : null);

                    // Convertir fRegistro a String si no es null
                    dto.setFRegistro(parte.getFRegistro() != null ? dateFormat.format(parte.getFRegistro()) : null);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    // Método auxiliar para convertir fechas de String a Date
    private List<Date> convertirFechasAFormatoDate(List<String> fechas) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return fechas.stream()
                .map(fecha -> {
                    try {
                        return dateFormat.parse(fecha);
                    } catch (ParseException e) {
                        throw new RuntimeException("Error al convertir fecha: " + fecha, e);
                    }
                })
                .collect(Collectors.toList());
    }

	@Override
	public List<InfoParteSemanalDto> getInfoParteSemanal(Long idConvProyAluPar, List<String> fechasPar) {
		// Convertir las fechas de String a Date
        List<Date> fechasConvertidas = convertirFechasAFormatoDate(fechasPar);

        // Obtener las entidades desde el repositorio
        List<InfoParteSemanalProjection> partesProyect = parsemAluplanRepository.getInfoPartes(idConvProyAluPar, fechasConvertidas);

		return partesProyect.stream()
				.map(projection -> modelMapper.map(projection, InfoParteSemanalDto.class))
				.collect(Collectors.toList());

	}
}
