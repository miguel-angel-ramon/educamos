package es.jccm.edu.proyectosfct.application.services.evaluaciones;

import es.jccm.cstic.dm.rodal.documento.RespuestaInsertarDoc;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.evaluaciones.model.AlumnoEvaluacionDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.evaluaciones.model.DatosGeneralesCabeceraEvaluacionDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.evaluaciones.model.EvalProyAnexoDto;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP.EvalProyAnexoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP.ValresAprAluEmpRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos.ConveniosProyectoRepository;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities.*;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.projection.*;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.evaluaciones.model.CalificacionEvaluacionDto;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ElementoSelectProjection;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ConveniosProyecto;
import es.jccm.edu.proyectosfct.application.ports.in.evaluaciones.IEvaluacionesService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.rodal.IRodalClient;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.commons.text.StringEscapeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EvaluacionesService implements IEvaluacionesService {
  
    private static final String SIN_DATOS = "------";

    @Autowired
    private ValresAprAluEmpRepository valresAprAluEmpRepository;

    @Autowired
    private EvalProyAnexoRepository evalProyAnexoRepository;
    
    @Autowired
    private ConveniosProyectoRepository conveniosProyectoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IRodalClient rodalClient;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public List<AlumnoEvaluacionDto> getListadoAlumnosEvaluacionLOFP(Long idTutorfctdual, Long idCentro, Integer cAnno) {
        List<AlumnosEvaluacionProjection> listadoAlumnosEvaluacion = valresAprAluEmpRepository
                .findListadoAlumnosEvaluacionLOFP(idTutorfctdual, idCentro, cAnno);

        return Optional.ofNullable(listadoAlumnosEvaluacion)
                .map(listado -> listado.stream()
                        .map(entity -> modelMapper.map(entity, AlumnoEvaluacionDto.class))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Override
    public List<ModuloEvaluacion> getListadoModulosEvaluacionLOFP(Long xMatricula, Long xEmpresa) {
        List<ListadoModuloEvaluacionProjection> listadoModulosEvaluacionProjection = valresAprAluEmpRepository
                .findListadoModulosEvaluacionLOFP(xMatricula, xEmpresa);
        
        if (listadoModulosEvaluacionProjection == null || listadoModulosEvaluacionProjection.isEmpty()) {
        	listadoModulosEvaluacionProjection = valresAprAluEmpRepository
                    .findListadoModulosEvaluacionLOFPModalidad(xMatricula, xEmpresa);
        }
        

        List<ListadoModuloEvaluacion> listadoModulosEvaluacion = listadoModulosEvaluacionProjection.stream()
                .map(entity -> modelMapper.map(entity, ListadoModuloEvaluacion.class))
                .collect(Collectors.toList());

        //Mapa para guardar los diferentes módulos
        Map<Long, ModuloEvaluacion> mapaModulos = new HashMap<>();

        for (ListadoModuloEvaluacion elementoListado : listadoModulosEvaluacion) {
            Long moduloId = elementoListado.getModuloId();

            //Si existe el módulo en el listado de módulos recupera su valor, si no lo añade
            ModuloEvaluacion moduloEvaluacion = mapaModulos.computeIfAbsent(moduloId, id -> crearModulo(elementoListado));

            // Creamos el resultado de aprendizaje y lo agregamos al módulo del listado
            moduloEvaluacion.getResultadosAprendizaje().add(crearResultadoAprendizaje(elementoListado));
        }

        // Convertimos el mapa de módulos en una lista y lo ordenamos por moduloOrden
        List<ModuloEvaluacion> listaModulosOrdenada = mapaModulos.values().stream()
                .sorted(Comparator.comparing(ModuloEvaluacion::getModuloOrden))
                .collect(Collectors.toList());

        // Ordenar los RA por resultadoOrden
        listaModulosOrdenada.forEach(modulo -> modulo.getResultadosAprendizaje().sort(Comparator.comparing(ResultadoAprendizajeEvaluacion::getResultadoOrden)));

        return listaModulosOrdenada;
    }

    @Override
    public Long guardarEvaluacionLOFP(Long idEvaluacion, Long xEmpresa, Long idResultado, Long idCalificacion, Long idModuloMatricula, String motivacion){
        if(idCalificacion == -1){// Si la calificación es -1 es que se está intentando quitar la calificación, por tanto se elimina el registro
            valresAprAluEmpRepository.deleteById(idEvaluacion);
            return -1L;
        } else {
            Optional<ValresAprAluEmp> evaluacionFind = valresAprAluEmpRepository.findById(idEvaluacion);
            ValresAprAluEmp evaluacion = cargarEvaluacion(evaluacionFind, xEmpresa, idResultado, idModuloMatricula);

            if(!motivacion.isBlank()){
            	String decodedMotivacion = StringEscapeUtils.unescapeHtml4(motivacion);      
                evaluacion.setTxMotivacion(decodedMotivacion);
            } else {
                evaluacion.setTxMotivacion(null);
            }

            evaluacion.setXCalifica(idCalificacion);

            valresAprAluEmpRepository.save(evaluacion);
            return evaluacion.getIdValresAprAluEmp();
        }
    }

    @Override
    public List<CalificacionEvaluacionDto> getListadoCalificacionesEvaluacionLOFP(){
        List<CalificacionEvaluacionProjection> listadoCalificaciones = valresAprAluEmpRepository.getListadoCalificacionesEvaluacionLOFP();

        return Optional.ofNullable(listadoCalificaciones)
                .map(listado -> listado.stream()
                        .map(entity -> modelMapper.map(entity, CalificacionEvaluacionDto.class))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());

    }

    public List<DatosGeneralesCabeceraEvaluacionDto> getListadoCabecerasEvaluacion(Long xMatricula) {

        List<ElementoSelectProjection> empresas = Optional.ofNullable(
                valresAprAluEmpRepository.findEmpresasByMatricula(xMatricula)
        ).orElse(Collections.emptyList());

        List<DatosGeneralesCabeceraEvaluacionDto> listadoCabeceras = new ArrayList<>();

        for (ElementoSelectProjection empresa : empresas) {
            if (empresa == null || empresa.getId() == null) {
                continue;
            }

            Long idEmpresa = empresa.getId();
            String nombreEmpresa = Optional.ofNullable(empresa.getDescripcion()).orElse("Desconocida");

            List<DatosGeneralesCabeceraEvaluacionProjection> datosCabecera = Optional.ofNullable(
                    valresAprAluEmpRepository.findDatosGeneralesCabeceraEvaluacion(xMatricula, idEmpresa)
            ).orElse(Collections.emptyList());

            datosCabecera.stream()
                    .filter(Objects::nonNull)
                    .map(cabecera -> convertirProyeccionADto(cabecera, idEmpresa, nombreEmpresa))
                    .forEach(listadoCabeceras::add);
        }

        return listadoCabeceras.stream()
                .sorted(Comparator.comparing(DatosGeneralesCabeceraEvaluacionDto::getNombreEmpresa, Comparator.nullsLast(String::compareTo)))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public EvalProyAnexoDto procesarEvalProyAnexo(EvalProyAnexoDto anexoDto, MultipartFile file, Integer cAnno, Long xCentro, DatosUsuarioJwt datosUsuario) throws Exception {

        String entidad = "EVAL_PROY_ANEXO";
        String modulo = "MFCT";

        Optional<EvalProyAnexo> anexoOptional = evalProyAnexoRepository.findAnexoFirmado(anexoDto.getXMatricula(), anexoDto.getXEmpresa());
        EvalProyAnexo anexo = anexoOptional.orElseGet(() -> modelMapper.map(anexoDto, EvalProyAnexo.class));

        try {
            if (anexoOptional.isPresent()) {
                actualizarDocumentoEnRodal(file, modulo, entidad, anexo.getId(), datosUsuario);
            } else {
                insertarNuevoDocumentoEnRodal(file, modulo, entidad, anexo, cAnno, xCentro, datosUsuario);
            }
        } catch (Exception e) {
            throw new Exception("Error en la comunicación con Rodal: " + e.getMessage(), e);
        }
        anexo = evalProyAnexoRepository.findAnexoFirmado(anexoDto.getXMatricula(), anexoDto.getXEmpresa())
                .orElseThrow(() -> new IllegalStateException("No se encontró el anexo insertado"));

        return modelMapper.map(anexo, EvalProyAnexoDto.class);
    }


    private void actualizarDocumentoEnRodal(MultipartFile file, String modulo, String entidad, Long idEntidad, DatosUsuarioJwt datosUsuario) throws IOException, RodalExceptionService {
        boolean actualizado = rodalClient.actualizaDoc(
                file, modulo, entidad, idEntidad,"",
                datosUsuario.getXUsuarioDelphos(), datosUsuario.getXUsuarioComunica()
        );

        if (!actualizado) {
            throw new IOException("Error al actualizar el documento en Rodal");
        }
    }

    private void insertarNuevoDocumentoEnRodal(MultipartFile file, String modulo, String entidad, EvalProyAnexo anexo, Integer cAnno, Long xCentro, DatosUsuarioJwt datosUsuario) throws Exception {
        anexo.setCUsuFirma(datosUsuario.getXUsuarioDelphos());
        anexo.setFFirma(new Date());
        anexo = this.updateEvalProyAnexo(anexo);

        try{
            RespuestaInsertarDoc respuestaRodal = rodalClient.insertaDoc(
                    file, modulo, entidad, anexo.getId(), Long.valueOf(cAnno), xCentro, -1L,
                    "", datosUsuario.getXUsuarioDelphos(), datosUsuario.getXUsuarioComunica()
            );

            if (respuestaRodal == null || respuestaRodal.getIdDoc() == null) {
                throw new IOException("Error al guardar el documento en Rodal");
            }
        }catch(Exception e){
            evalProyAnexoRepository.deleteById(anexo.getId());
            throw e;
        }

    }




    @Override
    public EvalProyAnexo getEvalProyAnexoById(Long id) {
        return evalProyAnexoRepository.findById(id)
                .orElse(null);
    }

    @Override
    public EvalProyAnexoDto getEvalProyAnexoByMatriculaAndEmpresa(Long xMatricula, Long xEmpresa) {
        Optional<EvalProyAnexo> anexoOptional = evalProyAnexoRepository.findAnexoFirmado(xMatricula, xEmpresa);

        return anexoOptional.map(anexo -> modelMapper.map(anexo, EvalProyAnexoDto.class)).orElse(null);
    }

    @Override
    public EvalProyAnexo updateEvalProyAnexo(EvalProyAnexo evalProyAnexo) {
        return evalProyAnexoRepository.save(evalProyAnexo);
    }

    @Override
    @Transactional
    public Integer actualizarEstadoAnexo(Long xMatricula, Long xEmpresa, Integer lgActualizado) {
        Optional<EvalProyAnexo> anexoOptional = evalProyAnexoRepository.findAnexoFirmado(xMatricula, xEmpresa);

        if (anexoOptional.isPresent()) {
            EvalProyAnexo anexo = anexoOptional.get();

            if (!Objects.equals(anexo.getLgActualizado(), lgActualizado)) {
                anexo.setLgActualizado(lgActualizado);
                evalProyAnexoRepository.save(anexo);
            }
            return anexo.getLgActualizado();
        }
        return -1;
    }


    private ModuloEvaluacion crearModulo(ListadoModuloEvaluacion elementoListado){
        ModuloEvaluacion modulo = new ModuloEvaluacion();
        modulo.setModuloId(elementoListado.getModuloId());
        modulo.setModuloCodigo(elementoListado.getModuloCodigo());
        modulo.setModuloNombre(elementoListado.getModuloNombre());
        modulo.setModuloOrden(elementoListado.getModuloOrden());
        modulo.setModuloMatriculaId(elementoListado.getModuloMatriculaId());
        modulo.setResultadosAprendizaje(new ArrayList<>());
        return modulo;
    }

    private ResultadoAprendizajeEvaluacion crearResultadoAprendizaje(ListadoModuloEvaluacion elementoListado){
        ResultadoAprendizajeEvaluacion resultado = new ResultadoAprendizajeEvaluacion();
        resultado.setResultadoId(elementoListado.getResultadoId());
        resultado.setResultadoAbv(elementoListado.getResultadoAbv());
        resultado.setResultadoNombre(elementoListado.getResultadoNombre());
        resultado.setResultadoOrden(elementoListado.getResultadoOrden());
        resultado.setEvaluacionId(elementoListado.getEvaluacionId());
        resultado.setCalificacionId(elementoListado.getCalificacionId());
        resultado.setMotivacion(elementoListado.getMotivacion());
        return resultado;
    }

    private DatosGeneralesCabeceraEvaluacionDto convertirProyeccionADto(DatosGeneralesCabeceraEvaluacionProjection cabecera, Long idEmpresa, String nombreEmpresa) {
        DatosGeneralesCabeceraEvaluacionDto dto = new DatosGeneralesCabeceraEvaluacionDto();

        dto.setIdEmpresa(idEmpresa);
        dto.setNombreEmpresa(nombreEmpresa);
        dto.setDescripcionPlan(Optional.ofNullable(cabecera.getDescripcionPlan()).orElse("Sin descripción"));
        dto.setCurso(Optional.ofNullable(cabecera.getCurso()).orElse("Sin curso"));
        dto.setXEmpleadoTutorDual(Optional.ofNullable(cabecera.getXEmpleado()).orElse(-1L));
        dto.setNombreTutorDual(Optional.ofNullable(cabecera.getNombreTutorDual()).orElse("Sin tutor dual"));
        dto.setIdEmpleadoTutorEmpresa(Optional.ofNullable(cabecera.getIdEmpleado()).orElse(-1L));
        dto.setNombreTutorEmpresa(Optional.ofNullable(cabecera.getNombreTutorEmpresa()).orElse("Sin tutor empresa"));
        dto.setNumHorasTotalesEmpresa(Optional.ofNullable(cabecera.getNumHorasTotalesEmpresa()).orElse(0));
        dto.setNumHorasTotalesCalculadas(Optional.ofNullable(cabecera.getNumHorasTotalesCalculadas()).orElse(SIN_DATOS));   
        dto.setTlfTutorCen(Optional.ofNullable(cabecera.getTlfTutorCen()).orElse(SIN_DATOS));
        dto.setTlfTutorEmp(Optional.ofNullable(cabecera.getTlfTutorEmp()).orElse(SIN_DATOS));
        dto.setDepCen(Optional.ofNullable(cabecera.getDepCen()).orElse(SIN_DATOS));
        dto.setDepTutorEmp(Optional.ofNullable(cabecera.getDepTutorEmp()).orElse(SIN_DATOS));
        dto.setCorreoCen(Optional.ofNullable(cabecera.getCorreoCen()).orElse(SIN_DATOS));
        dto.setCorreoEmp(Optional.ofNullable(cabecera.getCorreoEmp()).orElse(SIN_DATOS));

        return dto;
    }

    private ValresAprAluEmp cargarEvaluacion(Optional<ValresAprAluEmp> evaluacionFind, Long xEmpresa, Long idResultado, Long idModuloMatricula){
        if (evaluacionFind.isPresent()) {
            return evaluacionFind.get();
        } else {
            ValresAprAluEmp nuevaEvaluacion = new ValresAprAluEmp();
            nuevaEvaluacion.setXEmpresa(xEmpresa);
            nuevaEvaluacion.setXComesp(idResultado);
            nuevaEvaluacion.setXMatMatricula(idModuloMatricula);
            return nuevaEvaluacion;
        }
    }

    public byte[] downloadAnexoIXEval(Long xEmpresa, Long idMatricula) {

        byte[] output = null;
        InputStream dbAsStream = null;

        try {
            Resource resource = resourceLoader.getResource("classpath:reports/AnexoIX_Evaluacion.jrxml");
            dbAsStream = resource.getInputStream();


            HeaderAnexoIXProjection parametrosCabecera = null;

            parametrosCabecera = getHeaderAnexoIXEval(xEmpresa,idMatricula);

            List<ListadoTablaAnexoIX> listadoTablaAnexoIX = getTableHeaderAnexoIXEvaluacion(xEmpresa, idMatricula);

            Resource resourceSubReport = resourceLoader.getResource("classpath:reports/SubReportAnexoIX_Evaluacion.jrxml");
            JasperReport subReport = JasperCompileManager.compileReport(resourceSubReport.getInputStream());


            Map<String, Object> parametersPlan = new HashMap<>();
            if(parametersPlan != null) {
                parametersPlan.put("centro",parametrosCabecera.getCentro());
                parametersPlan.put("ciclo",parametrosCabecera.getCiclo());
                parametersPlan.put("codigo", parametrosCabecera.getCodigo());
                parametersPlan.put("grupo", parametrosCabecera.getGrupo());
                parametersPlan.put("nombreAlumno",parametrosCabecera.getNombreAlumno());
                parametersPlan.put("tutor",parametrosCabecera.getNombreTutor());
                parametersPlan.put("academico",parametrosCabecera.getAcademico());
                parametersPlan.put("fecha",parametrosCabecera.getFecha());
                parametersPlan.put("orden",parametrosCabecera.getOrden());
                parametersPlan.put("regimen",parametrosCabecera.getRegimen());
                parametersPlan.put("nombreEmpresa",parametrosCabecera.getNombreEmpresa());
                parametersPlan.put("tutorEmpresa",parametrosCabecera.getTutorEmpresa());
                parametersPlan.put("periodo",parametrosCabecera.getPeriodo());
                parametersPlan.put("totalHoras",parametrosCabecera.getTotalHoras());
                parametersPlan.put("codigoEmpresa",parametrosCabecera.getCodigoEmpresa());
                parametersPlan.put("localidad",parametrosCabecera.getLocalidad());
                parametersPlan.put("listadoTablaAnexoIX" ,listadoTablaAnexoIX);
                parametersPlan.put("subReport",subReport);

            }


            JasperReport jasperReport = JasperCompileManager.compileReport(dbAsStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametersPlan, new JREmptyDataSource());

            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            exporter.exportReport();
            output = outputStream.toByteArray();
        } catch (IOException | JRException e ) {
            throw new RuntimeException(e);
        }

        return output;
    }


    @Override
    public List<ListadoTablaAnexoIX> getTableHeaderAnexoIXEvaluacion(Long xEmpresa, Long idMatricula) {

        List<ListadoTablaAnexoIX> listadoPrincipal = new ArrayList<>();
        List<TableAnexoIXHeaderProjection> listadoHeaderAnexoIX = valresAprAluEmpRepository.getTableHeaderAnexoIX(xEmpresa, idMatricula);
        
        
        if (listadoHeaderAnexoIX == null || listadoHeaderAnexoIX.isEmpty()) {
        	listadoHeaderAnexoIX = valresAprAluEmpRepository.getTableHeaderAnexoIXModalidad(xEmpresa, idMatricula);
        }
        
        if (!listadoHeaderAnexoIX.isEmpty()) {
            for (TableAnexoIXHeaderProjection header : listadoHeaderAnexoIX) {
                List<TableAnexoIXProjection> listadoAnexoIX = valresAprAluEmpRepository.getTableAnexoIXEvaluacion(header.getIdModuloCurso(),idMatricula, xEmpresa);
                ListadoTablaAnexoIX registroTabla = new ListadoTablaAnexoIX();
                registroTabla.setCodModulo(header.getCodModulo());
                registroTabla.setHorasEmpresa(header.getHorasEmpresa());
                registroTabla.setAprendizaje(listadoAnexoIX.stream().map(TableAnexoIXProjection::getAprendizaje).collect(Collectors.toList()));
                registroTabla.setIntEmpresa(listadoAnexoIX.stream().map(TableAnexoIXProjection::getIntEmpresa).collect(Collectors.toList()));
                registroTabla.setAprobada(listadoAnexoIX.stream().map(TableAnexoIXProjection::getAprobada).collect(Collectors.toList()));
                registroTabla.setGradoSuperacion(listadoAnexoIX.stream().map(TableAnexoIXProjection::getGradoSuperacion).collect(Collectors.toList()));
                registroTabla.setIntEmpresa(listadoAnexoIX.stream().map(TableAnexoIXProjection::getIntEmpresa).collect(Collectors.toList()));
                registroTabla.setMotivacion(listadoAnexoIX.stream().map(TableAnexoIXProjection::getMotivacion).collect(Collectors.toList()));

                listadoPrincipal.add(registroTabla);
            }

        }

        return listadoPrincipal;
    }

    @Override
    @Transactional
    public boolean borrarDocumento(String idEvaFirRodal) {
        Optional<EvalProyAnexo> anexoOptional = evalProyAnexoRepository.findByIdEvaFirRodal(idEvaFirRodal);

        if (anexoOptional.isPresent()) {
            String entidad = "EVAL_PROY_ANEXO";
            String modulo = "MFCT";
            evalProyAnexoRepository.delete(anexoOptional.get());
            try {
                rodalClient.borrarDocumento(idEvaFirRodal, modulo, entidad);
            } catch (RodalExceptionService e) {
                return true;
            }
            return true;
        }
        return false;
    }


    public HeaderAnexoIXProjection getHeaderAnexoIXEval(Long xEmpresa, Long idMatricula) {
        return valresAprAluEmpRepository.getHeaderAnexoIXEval(xEmpresa, idMatricula);
    }
    
	@Override
	public Integer updateHoras(Long xEmpresa, Long idMatricula, Integer horas) {
		
		List<ConveniosProyecto> conv = conveniosProyectoRepository.updateHoras(xEmpresa,idMatricula); 
		
		for (ConveniosProyecto convenio : conv) {
			convenio.setNuHorasTotales(horas);
			conveniosProyectoRepository.save(convenio);
		}		

		return 0;
	}

}
