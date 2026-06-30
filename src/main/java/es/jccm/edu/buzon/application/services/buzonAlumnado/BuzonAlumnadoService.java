package es.jccm.edu.buzon.application.services.buzonAlumnado;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.threeten.bp.LocalDate;

import es.jccm.edu.buzon.adapter.in.rest.buzon.model.AlumnoUnidadDto;
import es.jccm.edu.buzon.adapter.in.rest.buzon.model.CursoSolicitudDto;
import es.jccm.edu.buzon.adapter.in.rest.buzon.model.UsuariotBuzonDto;
import es.jccm.edu.buzon.adapter.out.repository.buzonAlumnado.BuzonAlumnadoRepository;
import es.jccm.edu.buzon.application.domain.buzon.BuzonAlumnado;
import es.jccm.edu.buzon.application.domain.buzonCentro.AlumnoUnidad;
import es.jccm.edu.buzon.application.domain.buzonCentro.projection.AlumnoUnidadProjection;
import es.jccm.edu.buzon.application.ports.in.buzonAlumnado.IBuzonAlumnado;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.simulacion.adapter.in.rest.centros.model.UsuariotDto;
import es.jccm.edu.simulacion.adapter.out.repositories.centros.UsuariotBuzonRepository;
import es.jccm.edu.simulacion.application.domain.centros.entities.UsuariotBuzon;

@Service
public class BuzonAlumnadoService implements IBuzonAlumnado {
	
	private final String ErrorAcceso = "Error al dar acceso";

	@Autowired
	UsuariotBuzonRepository usuariostRepository;
	
	@Autowired
	BuzonAlumnadoRepository buzonAlumnadoRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public Boolean isEquipoDirectivo(DatosUsuarioJwt datosUsuario) {
		Boolean isEquipoDirectivo = false;
		String nifUsuario = usuariostRepository.isEquipoDirectivo(datosUsuario.getNif());
		if(nifUsuario != null && !"".equals(nifUsuario)) isEquipoDirectivo = true;
		return isEquipoDirectivo;
	}

	@Transactional
	@Override
	public Boolean postSeleccionadosAlumnado(List<UsuariotBuzonDto> seleccionados, DatosUsuarioJwt datosUsuario) {

		try {
			seleccionados.forEach(usuario -> {
				
				usuariostRepository.updateAccesoBuzonAlumnado(usuario.getIdentificacion(), usuario.getCentro());
				BuzonAlumnado buzonAlumnado = new BuzonAlumnado(usuario.getOidPersona(), usuario.getFechaAlta(), usuario.getFechaBaja(), usuario.getCentro(), datosUsuario.getOid(), datosUsuario.getOid(),usuario.getFechaAlta(),usuario.getFechaBaja(),1l);
				buzonAlumnadoRepository.save(buzonAlumnado);
			}
			);
			return true;
		} catch (Exception e) {
			System.out.println(ErrorAcceso + e);
			return false;

		}
	}

	@Override
	public List<BuzonAlumnado> getFechasVigenciaAlumnado(List<Long> oidPersonasAccesoBuzonAlumnado) {
		List<BuzonAlumnado> fechasVigencia = new ArrayList<>();
		try {
			for(Long oid:oidPersonasAccesoBuzonAlumnado) {				
				BuzonAlumnado buzonAlumnado = buzonAlumnadoRepository.findByOidPersona(oid);
				fechasVigencia.add(buzonAlumnado);
			}
			return fechasVigencia;
		}catch (Exception e) {
			System.out.println(ErrorAcceso + e);
			return fechasVigencia;
		}
	}
	
	@Override
	public List<UsuariotDto> getPersonalAccesoBuzonAlumnado(String oidCentro) {
		List<UsuariotBuzon> usuariost = usuariostRepository.findAccesosBuzonAlumnadoByCentro(oidCentro);
		List<UsuariotDto> usuariostDto = usuariost.stream().map(x -> toUsuariotDto(x)).collect(Collectors.toList());
		return usuariostDto;
	}
	

	private UsuariotDto toUsuariotDto(UsuariotBuzon usuariot) {
		return modelMapper.map(usuariot, UsuariotDto.class);
	}
	
	@Override
	public Boolean updateFechaVigencia(Long oidPersona, String fechaBaja, String fechaAlta) {
		try {
			BuzonAlumnado buzonAlumnado = buzonAlumnadoRepository.findByOidPersona(oidPersona);
			if(fechaBaja.equals("null") || fechaBaja == null) {
				buzonAlumnado.setFechaBaja(null);		
			}else {				
				buzonAlumnado.setFechaBaja(Date.valueOf(fechaBaja));
			}
			
			if(fechaAlta.equals("null") || fechaAlta == null) {
				String fechaActual = LocalDate.now().toString();
				buzonAlumnado.setFechaAlta(Date.valueOf(fechaActual));
			}else {
				buzonAlumnado.setFechaAlta(Date.valueOf(fechaAlta));
			}
			buzonAlumnadoRepository.save(buzonAlumnado);
			return true;
		} catch (Exception e) {
			System.out.println(ErrorAcceso + e);
			return false;
		}
	}
	
	@Override
	public Boolean deleteSeleccionados(List<UsuariotDto> seleccionados) {
		try {
			seleccionados.forEach(usuario -> {
				usuariostRepository.deleteAccesoBuzonAlumnado(usuario.getIdentificacion(), usuario.getCentro());
				buzonAlumnadoRepository.deleteVigencia(usuario.getOidPersona(), usuario.getCentro());		
			});
			return true;
		} catch (Exception e) {
			System.out.println(ErrorAcceso + e);
			return false;

		}
	}

	@Override
	public List<UsuariotDto> getAlumnado(String oidCentro) {
		List<UsuariotBuzon> usuariost = usuariostRepository.findAlumnadoByCentro(oidCentro);
		List<UsuariotDto> usuariostDto = usuariost.stream().map(x -> toUsuariotDto(x)).collect(Collectors.toList());
		return usuariostDto;
	}

	@Override
	public List<AlumnoUnidadDto> getAlumnosUnidades(String oidCentro) {
		List<AlumnoUnidadProjection> alumnosProjection = buzonAlumnadoRepository.getAlumnosUnidadesByCentro(Long.valueOf(oidCentro), 2023l);
		List<AlumnoUnidad> alumnosUnidad = alumnosProjection.stream().map(alumno -> modelMapper.map(alumno, AlumnoUnidad.class)).collect(Collectors.toList());
		return alumnosUnidad.stream().map(alumno -> modelMapper.map(alumno, AlumnoUnidadDto.class)).collect(Collectors.toList());	
	}

	@Override
	public List<CursoSolicitudDto> getCursoEtapaAlumnosCentro(String oidCentro){
		//List<CursoSolicitudProjection> cursoSolicitudProjection = buzonAlumnadoRepository.getCursoEtapaAlumnosByCentro(Long.valueOf(oidCentro), 2023l);
		List<CursoSolicitudDto> cursoSolicitudDto = null; /*= cursoSolicitudProjection.stream().map(curso -> modelMapper.map(curso, CursoSolicitudDto.class)).collect(Collectors.toList());
		*/
		return cursoSolicitudDto;
	}
	
}
