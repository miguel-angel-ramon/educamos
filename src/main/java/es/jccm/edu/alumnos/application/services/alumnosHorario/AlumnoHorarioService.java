package es.jccm.edu.alumnos.application.services.alumnosHorario;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.alumnos.adapter.out.repository.alumnosHorario.AlumnoHorarioRepository;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.AlumnoAndFaltasList;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.AlumnoDetalle;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.AlumnoHorario;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.Alumnos;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.AsignaturaAlumno;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.GrupoActividadAlumno;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.HorarioSemanalAlumno;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.ListaAlumnosGrupoActividad;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.MateriaAlumno;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.AlumnoAndFaltasProjection;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.AlumnoDetalleProjection;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.AlumnoHorarioProjection;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.AlumnosProjection;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.AsignaturaProjection;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.HorarioSemanalAlumnoProjection;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.ListaAlumnosGrupoActividadProjection;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.TutorAlumnoProjection;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.TutorProjection;
import es.jccm.edu.alumnos.application.ports.in.alumnosHorario.IAlumnoHorarioService;
import es.jccm.edu.shared.application.domain.error.ApiNotFoundException;

@Service
public class AlumnoHorarioService implements IAlumnoHorarioService {

	@Autowired
	private AlumnoHorarioRepository alumnoHorarioRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	public Long countAlumnoHorarioByAsignatura(Long idUnidad, Long idMateria) {
		return alumnoHorarioRepository.countByAsignatura(idUnidad, idMateria);
	}

	@Override
	public List<AlumnoHorario> getAlumnosHorarioByFaltasRecurrentes(Long idUnidad, Long idMateria) {
		return alumnoHorarioRepository.findAlumnosHorarioByFaltasRecurrentes(idUnidad, idMateria);
	}

	@Override
	public List<AlumnoHorario> getAlumnosHorarioByRetrasosRecurrentes(Long idUnidad, Long idMateria) {
		return alumnoHorarioRepository.findAlumnosHorarioByRetrasosRecurrentes(idUnidad, idMateria);
	}

	@Override
	public List<AlumnoAndFaltasList> getAlumnosAndFaltas(Long idTramo, Long idGrupo, String fecha) {

		Converter<Blob, byte[]> blobToByteArray = new AbstractConverter<Blob, byte[]>() {
			@Override
			protected byte[] convert(Blob blob) {
				if (blob != null) {
					try {
						byte[] bytes = blob.getBytes(1, (int) blob.length());
						blob.free();
						return bytes;
					} catch (SQLException e) {
						throw new ApiNotFoundException(e.getMessage()); // extends from runtimeexception
					}
				}
				return new byte[0];
			}
		};

		modelMapper.addConverter(blobToByteArray);

		List<AlumnoAndFaltasProjection> alumnos = alumnoHorarioRepository.getAlumnosAndFaltas(idTramo, idGrupo, fecha);

		return alumnos.stream().map(alumnoAndFalta -> modelMapper.map(alumnoAndFalta, AlumnoAndFaltasList.class))
				.collect(Collectors.toList());
	}

	@Transactional
	public AlumnoHorario getAlumnosHorariosDetalle(Long idMatricula, Long idMatMatricula) {
		try {
			AlumnoHorarioProjection alumn_p = alumnoHorarioRepository.findAlumnosHorarioDetalle(idMatricula, idMatMatricula);
			AlumnoHorario alumn = modelMapper.map(alumn_p, AlumnoHorario.class);
			Blob img = alumnoHorarioRepository.getAlumnoFoto(alumn.getIdescolar());
			setFoto(img, alumn);
			return alumn;
		} catch (Exception e) {
			
			throw e;
		}
	}

	private AlumnoHorario setFoto(Blob img, AlumnoHorario alumn) {
		try {
			if (img != null) {
				alumn.setFoto(img.getBytes(1, (int) img.length()));
			}
		} catch (SQLException e) {
			
		}
		return alumn;
	}
	
	@Transactional
	public AlumnoDetalle getAlumnoDetalle(Long idEmpleado, Long idMatricula, Integer anno) throws Exception {
		try {
			
			AlumnoDetalleProjection alumn_p = alumnoHorarioRepository.findAlumnoDetalle(idMatricula);
			AlumnoDetalle alumn = modelMapper.map(alumn_p, AlumnoDetalle.class);
			
			Blob img = alumnoHorarioRepository.getAlumnoFoto(alumn.getIdescolar());

			if (img != null) {
				alumn.setFoto(img.getBytes(1, (int) img.length()));
			}
			 
			List<GrupoActividadAlumno> gruposActividad = alumnoHorarioRepository.getGruposActividadAlumno(idEmpleado, alumn.getIdAlumno(), anno)
					.stream().map(x -> modelMapper.map(x, GrupoActividadAlumno.class)).collect(Collectors.toList());
			alumn.setGruposActividad(gruposActividad);
			
			return alumn;
		} catch (Exception e) {
			try {
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			throw e;
		}
	}
	
	@Transactional
	public void setAlumnoObservacion (Long idMatMatricula, String observacion, Long idMatricula) {
		alumnoHorarioRepository.SetAlumnosObservacion(idMatMatricula, observacion, idMatricula);
	}
	
	@Override
	public Page<Alumnos> getMisAlumnos(Long idEmpleado, Long anno, Integer page, Integer numItems, Long idGrupoActividad, String nombre, String order, Long idCentro) {

		try {
			Converter<Blob, byte[]> blobToByteArray = new AbstractConverter<Blob, byte[]>() {
				@Override
				protected byte[] convert(Blob blob) {
					if (blob != null) {
						try {
							byte[] bytes = blob.getBytes(1, (int) blob.length());
							blob.free();
							return bytes;
						} catch (SQLException e) {
							throw new ApiNotFoundException(e.getMessage()); // extends from runtimeexception
						}
					}
					return new byte[0];
				}
			};
			
			modelMapper.addConverter(blobToByteArray);
			Pageable paging;

			
			paging = PageRequest.of(page, numItems);
			
			Page<AlumnosProjection> alumnosList = alumnoHorarioRepository.getAlumnosByGrupo(idEmpleado, anno, order ,idGrupoActividad, nombre, idCentro, paging);
	
			Page<Alumnos> alumnosOut = alumnosList.map(x -> modelMapper.map(x, Alumnos.class));
				
			return alumnosOut;
			
		} catch (Exception e) {
			
			throw e;
		}
	}
	
	@Override
	public List<ListaAlumnosGrupoActividad> getListaAlumnosGrupoActividad(Long idGrupoActividad, Long idEmpleado) {
		List<ListaAlumnosGrupoActividadProjection> lisAlu = alumnoHorarioRepository.getListaAlumnosGrupoActividad(idGrupoActividad, idEmpleado);

		return lisAlu.stream().map(x -> modelMapper.map(x, ListaAlumnosGrupoActividad.class)).collect(Collectors.toList());
	}
	
	@Override
	public Page<Alumnos> getMisAlumnosByUnidad(Long idEmpleado, Long anno, Integer page, Integer numItems, Long idUnidad, String nombre, String order) {

		try {
			Converter<Blob, byte[]> blobToByteArray = new AbstractConverter<Blob, byte[]>() {
				@Override
				protected byte[] convert(Blob blob) {
					if (blob != null) {
						try {
							byte[] bytes = blob.getBytes(1, (int) blob.length());
							blob.free();
							return bytes;
						} catch (SQLException e) {
							throw new ApiNotFoundException(e.getMessage()); // extends from runtimeexception
						}
					}
					return new byte[0];
				}
			};
			
			modelMapper.addConverter(blobToByteArray);
			Pageable paging = PageRequest.of(page, numItems);	;
			
			
			Page<AlumnosProjection> alumnosList = alumnoHorarioRepository.getAlumnosByUnidad(idEmpleado, anno, idUnidad, nombre, order, paging);
	
			Page<Alumnos> alumnosOut = alumnosList.map(x -> modelMapper.map(x, Alumnos.class));
			
			return alumnosOut;
			
		} catch (Exception e) {
			
			throw e;
		}
	}
	
	public AlumnoDetalle getAlumnosDetalleYMaterias(Long idEmpleado, Long idMatricula, Integer anno) throws Exception {
		try {
			
			AlumnoDetalleProjection alumn_p = alumnoHorarioRepository.findAlumnoDetalle(idMatricula);
			AlumnoDetalle alumn = modelMapper.map(alumn_p, AlumnoDetalle.class);
			
			Blob img = alumnoHorarioRepository.getAlumnoFoto(alumn.getIdescolar());

			if (img != null) {
				alumn.setFoto(img.getBytes(1, (int) img.length()));
			}
			 
			List<MateriaAlumno> materias = alumnoHorarioRepository.getMateriasByUnidadAlumno(idEmpleado, alumn.getIdAlumno(), anno)
					.stream().map(x -> modelMapper.map(x, MateriaAlumno.class)).collect(Collectors.toList());
			alumn.setMaterias(materias);
			
			return alumn;
		} catch (Exception e) {
			try {
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			throw e;
		}
	}
	
	@Override
	public List<ListaAlumnosGrupoActividad> getListaAlumnosByUnidad(Long idUnidad, Long idEmpleado) {
		
		List<ListaAlumnosGrupoActividadProjection> lisAlu = alumnoHorarioRepository.getListaAlumnosByUnidad(idUnidad, idEmpleado);

		return lisAlu.stream().map(x -> modelMapper.map(x, ListaAlumnosGrupoActividad.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<HorarioSemanalAlumno> getHorarioSemanalAlumno(Long idMatricula) {
	    
	    List<HorarioSemanalAlumnoProjection> lisHor = alumnoHorarioRepository.getHorarioSemanalAlumno(idMatricula);

	    if (lisHor == null || lisHor.isEmpty()) {
	        return Collections.emptyList();
	    }

	    return lisHor.stream()
	                 .map(x -> modelMapper.map(x, HorarioSemanalAlumno.class))
	                 .collect(Collectors.toList());
	}
	
	@Override
	public List<AsignaturaAlumno> getAsignaturaAlumno(Long idMatricula) {
	    
	    List<AsignaturaProjection> lisAsig = alumnoHorarioRepository.getAsignaturaAlumno(idMatricula);

	    if (lisAsig == null || lisAsig.isEmpty()) {
	        return Collections.emptyList();
	    }

	    return lisAsig.stream()
	                 .map(x -> modelMapper.map(x, AsignaturaAlumno.class))
	                 .collect(Collectors.toList());
	}
	
	public TutorAlumnoProjection obtenerTutorYHorario(Long idMatricula) {
		TutorAlumnoProjection tutor = alumnoHorarioRepository.getInfoTutorHorario(idMatricula);
        if (tutor == null ) {
	        return null;
	    }

	    return tutor;
    }


	
}
