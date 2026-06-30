package es.jccm.edu.buzon.application.services.buzonCentro;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.threeten.bp.LocalDate;

import es.jccm.edu.buzon.adapter.in.rest.buzon.model.UsuariotBuzonDto;
import es.jccm.edu.buzon.adapter.out.repository.buzonCentro.BuzonCentroRepository;
import es.jccm.edu.buzon.application.domain.buzonCentro.BuzonCentro;
import es.jccm.edu.buzon.application.ports.in.buzonCentro.IBuzonCentro;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.simulacion.adapter.in.rest.centros.model.UsuariotDto;
import es.jccm.edu.simulacion.adapter.out.repositories.centros.UsuariotBuzonRepository;
import es.jccm.edu.simulacion.application.domain.centros.entities.UsuariotBuzon;

@Service
public class BuzonCentroService implements IBuzonCentro {
	
	private final String ErrorAcceso = "Error al dar acceso";

	@Autowired
	UsuariotBuzonRepository usuariostRepository;
	
	@Autowired
	BuzonCentroRepository buzonCentroRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<UsuariotDto> getPersonalCentro(String oidCentro) {
		List<UsuariotBuzon> usuariost = usuariostRepository.findByCentro(oidCentro);
		List<UsuariotDto> usuariostDto = usuariost.stream().map(x -> toUsuariotDto(x)).collect(Collectors.toList());
		return usuariostDto;
	}

	@Override
	public List<UsuariotDto> getCandidatosCentro(String oidCentro) {
		List<UsuariotBuzon> usuariost = usuariostRepository.findCandidatos(oidCentro);
		List<UsuariotDto> usuariostDto = usuariost.stream().map(x -> toUsuariotDto(x)).collect(Collectors.toList());
		return usuariostDto;
	}

	@Override
	public List<UsuariotDto> getAdministradoresBuzonCentro(String oidCentro) {
		// List<Usuariot> usuariost =
		// usuariostRepository.findAdministradoresBuzonByCentro(oidCentro);
		// List<UsuariotDto> usuariostDto =
		// usuariost.stream().map(x->toUsuariotDto(x)).collect(Collectors.toList());
		return null;
	}

	private UsuariotDto toUsuariotDto(UsuariotBuzon usuariot) {
		return modelMapper.map(usuariot, UsuariotDto.class);
	}

	@Override
	public List<UsuariotDto> getDocentes(String oidCentro) {
		List<UsuariotBuzon> usuariost = usuariostRepository.findDocentesByCentro(oidCentro);
		List<UsuariotDto> usuariostDto = usuariost.stream().map(x -> toUsuariotDto(x)).collect(Collectors.toList());
		return usuariostDto;
	}

	@Override
	public List<UsuariotDto> getNoDocentes(String oidCentro) {
		List<UsuariotBuzon> usuariost = usuariostRepository.findNoDocentesByCentro(oidCentro);
		List<UsuariotDto> usuariostDto = usuariost.stream().map(x -> toUsuariotDto(x)).collect(Collectors.toList());
		return usuariostDto;
	}

	@Override
	public Boolean isDirector(DatosUsuarioJwt datosUsuario) {
		Boolean isDirector = false;
		String nifDirector = usuariostRepository.isDirector(datosUsuario.getNif());
		if(nifDirector != null && !"".equals(nifDirector)) isDirector = true;
		return isDirector;
	}

	@Transactional
	@Override
	public Boolean postSeleccionadosCentro(List<UsuariotBuzonDto> seleccionados, DatosUsuarioJwt datosUsuario) {
		try {
			seleccionados.forEach(usuario -> {
				
				usuariostRepository.updateAccesoBuzon(usuario.getIdentificacion(), usuario.getCentro());
				BuzonCentro buzonCentro = new BuzonCentro(usuario.getOidPersona(), usuario.getFechaAlta(), usuario.getFechaBaja(), usuario.getCentro(), datosUsuario.getOid(), datosUsuario.getOid(),usuario.getFechaAlta(),usuario.getFechaBaja(),1l);
				buzonCentroRepository.save(buzonCentro);
			}
			);
			return true;
		} catch (Exception e) {
			System.out.println(ErrorAcceso + e);
			return false;

		}
	}
	
	@Override
	public Boolean deleteSeleccionadosCentro(List<UsuariotDto> seleccionados) {
		try {
			seleccionados.forEach(usuario -> {
				usuariostRepository.deleteAccesoBuzon(usuario.getIdentificacion(), usuario.getCentro());
				buzonCentroRepository.deleteVigencia(usuario.getOidPersona(), usuario.getCentro());		
			});
			return true;
		} catch (Exception e) {
			System.out.println(ErrorAcceso + e);
			return false;

		}
	}
	
	@Override
	public Boolean updateFechaVigencia(Long oidPersona, String fechaBaja, String fechaAlta) {
		try {
			BuzonCentro buzonCentro = buzonCentroRepository.findByOidPersona(oidPersona);
			if(fechaBaja.equals("null") || fechaBaja == null) {
				buzonCentro.setFechaBaja(null);		
			}else {				
				buzonCentro.setFechaBaja(Date.valueOf(fechaBaja));
			}
			
			if(fechaAlta.equals("null") || fechaAlta == null) {
				String fechaActual = LocalDate.now().toString();
				buzonCentro.setFechaAlta(Date.valueOf(fechaActual));
			}else {
				buzonCentro.setFechaAlta(Date.valueOf(fechaAlta));
			}
			buzonCentroRepository.save(buzonCentro);
			return true;
		} catch (Exception e) {
			System.out.println(ErrorAcceso + e);
			return false;
		}
	}

	@Override
	public List<BuzonCentro> getFechasVigencia(List<Long> oidPersonasAccesoBuzon) {
		List<BuzonCentro> fechasVigencia = new ArrayList<>();
		try {
			for(Long oid:oidPersonasAccesoBuzon) {				
				BuzonCentro buzonCentro = buzonCentroRepository.findByOidPersona(oid);
				fechasVigencia.add(buzonCentro);
			}
			return fechasVigencia;
		}catch (Exception e) {
			System.out.println(ErrorAcceso + e);
			return fechasVigencia;
		}
	}

	@Override
	public Boolean updateFechaVigencia(BuzonCentro buzonCentroActualizado) {
		try {		
			buzonCentroRepository.updateVigencias(buzonCentroActualizado.getFechaAlta(), buzonCentroActualizado.getFechaBaja(),buzonCentroActualizado.getOidPersona(),buzonCentroActualizado.getCentro());
			return true;
		}catch (Exception e) {
			System.out.println(ErrorAcceso + e);
			return false;
		}
	}
}
