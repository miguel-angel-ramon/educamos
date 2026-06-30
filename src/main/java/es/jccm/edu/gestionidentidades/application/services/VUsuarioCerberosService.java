package es.jccm.edu.gestionidentidades.application.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import es.jccm.edu.gestionidentidades.adapter.in.rest.apicerberos.model.VUsuarioCerberosDto;
import es.jccm.edu.gestionidentidades.adapter.out.repository.VUsuarioCerberosRepository;
import es.jccm.edu.gestionidentidades.application.domain.VUsuarioCerberos;
import es.jccm.edu.gestionidentidades.application.ports.in.IVUsuarioCerberosService;
import es.jccm.edu.gestionidentidades.application.ports.out.exceptions.InvalidNifException;
import es.jccm.edu.gestionidentidades.application.ports.out.exceptions.ResultSetException;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.AplicacionUsuarioJwt;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.AuthoritiesJwt;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.RolUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;


@Service
public class VUsuarioCerberosService implements IVUsuarioCerberosService{

	 @Autowired
	 VUsuarioCerberosRepository vUsuarioCerberosRepository;
	 
	 @Autowired
	 ModelMapper modelMapper;
	 
	 @Autowired
	 IDatosUsuarioJwtService datosUsuarioJwtService;
	 
	 @Override
	 public VUsuarioCerberosDto getDatosUsuarioByLogin(String login) throws ResultSetException {
		 
		 String loginDecodificado = decodificarCadenas(login);
		 List<VUsuarioCerberos> datosUsuario = vUsuarioCerberosRepository.findByLogin(loginDecodificado);
		 
		 if(datosUsuario.size() > 1) {
			 throw new ResultSetException("Se ha encontrado más de un registro para el login: " + loginDecodificado);
		 }else if(datosUsuario.isEmpty()){
			 return null;
		 }else {
			 VUsuarioCerberosDto vUsuarioCerberosDTO = convertToDto(datosUsuario.get(0));
			 vUsuarioCerberosDTO.setActivo(isTrueOrFalse(datosUsuario.get(0).getIsActive()));
			 vUsuarioCerberosDTO.setBloqueado(isTrueOrFalse(datosUsuario.get(0).getIsBlocked()));
			 vUsuarioCerberosDTO.setDocente(isTrueOrFalse(datosUsuario.get(0).getIsTeacher()));
			 vUsuarioCerberosDTO.setAlumno(isTrueOrFalse(datosUsuario.get(0).getIsEstudiante()));
			 return appendDatosUsuarioCerberos(vUsuarioCerberosDTO);
		 }     
	 }
	 
	 @Override
	 public Page<VUsuarioCerberosDto> getDatosUsuarioByQuerydsl(Predicate predicate, Pageable pageable) {
	     
	     Page<VUsuarioCerberos> datosUsuarioPage = vUsuarioCerberosRepository.findAll(predicate, pageable);
	       
	     // Se convierte cada VUsuarioCerberos de la Page en VUsuarioCerberosDto
	     List<VUsuarioCerberosDto> dtos = datosUsuarioPage.getContent().stream()
	         .map(this::convertToDto)
	         .map(this::appendDatosUsuarioCerberos)
	         .collect(Collectors.toList());
	     
//	     for (VUsuarioCerberosDto vUsuarioCerberosDto : dtos) {
//	    	 vUsuarioCerberosDto.setActivo(isTrueOrFalse(datosUsuarioPage.getContent().get(0).getIsActive()));
//			 vUsuarioCerberosDto.setBloqueado(isTrueOrFalse(datosUsuarioPage.getContent().get(0).getIsBlocked()));
//			 vUsuarioCerberosDto.setDocente(isTrueOrFalse(datosUsuarioPage.getContent().get(0).getIsTeacher()));
//			 vUsuarioCerberosDto.setAlumno(isTrueOrFalse(datosUsuarioPage.getContent().get(0).getIsEstudiante()));
//	    	 appendDatosUsuarioCerberos(vUsuarioCerberosDto);
//		}
	     
	     for (int i = 0;i < dtos.size();i++) {
	    	 dtos.get(i).setActivo(isTrueOrFalse(datosUsuarioPage.getContent().get(i).getIsActive()));
			 dtos.get(i).setBloqueado(isTrueOrFalse(datosUsuarioPage.getContent().get(i).getIsBlocked()));
			 dtos.get(i).setDocente(isTrueOrFalse(datosUsuarioPage.getContent().get(i).getIsTeacher()));
			 dtos.get(i).setAlumno(isTrueOrFalse(datosUsuarioPage.getContent().get(i).getIsEstudiante()));
	    	 appendDatosUsuarioCerberos(dtos.get(i));
		}
	     
	     // se cambia de lista de DTOs en Page
	     Page<VUsuarioCerberosDto> dtoPage = new PageImpl<>(dtos, pageable, datosUsuarioPage.getTotalElements());
	     
	     return dtoPage;
	 }
	 

	 @Override
	 public VUsuarioCerberosDto getDatosUsuarioByUidLdap(String uidLdap) throws ResultSetException{
		 
		 String uidLdapDecodificado = decodificarCadenas(uidLdap);
		 List<VUsuarioCerberos> datosUsuario = vUsuarioCerberosRepository.findByUidLdap(uidLdapDecodificado);
		 
		 if(datosUsuario.size() > 1) {
			 throw new ResultSetException("Se ha encontrado más de un registro para el uidLdap: " + uidLdapDecodificado);
		 }else if(datosUsuario.isEmpty()){
			 return null;
		 }else {
			 VUsuarioCerberosDto vUsuarioCerberosDTO = convertToDto(datosUsuario.get(0));
			 vUsuarioCerberosDTO.setUidLdap(uidLdapDecodificado);
			 vUsuarioCerberosDTO.setActivo(isTrueOrFalse(datosUsuario.get(0).getIsActive()));
			 vUsuarioCerberosDTO.setBloqueado(isTrueOrFalse(datosUsuario.get(0).getIsBlocked()));
			 vUsuarioCerberosDTO.setDocente(isTrueOrFalse(datosUsuario.get(0).getIsTeacher()));
			 vUsuarioCerberosDTO.setAlumno(isTrueOrFalse(datosUsuario.get(0).getIsEstudiante()));
			 return appendDatosUsuarioCerberos(vUsuarioCerberosDTO);
		 } 
	 }
	 
	 public VUsuarioCerberosDto getDatosUsuarioByNif(String nif) throws ResultSetException, InvalidNifException {
		 if (!validarNif(nif)) {
			 throw new InvalidNifException("El");
		 }
		 
		 List<VUsuarioCerberos> datosUsuario = vUsuarioCerberosRepository.findByIdentificacion(nif);
		 
		 if(datosUsuario.isEmpty()) {
			 return null;
		 }else if(datosUsuario.size() > 1) {
			 throw new ResultSetException("Se ha encontrado más de un registro para el nif: " + nif);
		 }
		 
		 VUsuarioCerberosDto vUsuarioCerberosDTO = convertToDto(datosUsuario.get(0));
		 vUsuarioCerberosDTO.setActivo(isTrueOrFalse(datosUsuario.get(0).getIsActive()));
		 vUsuarioCerberosDTO.setBloqueado(isTrueOrFalse(datosUsuario.get(0).getIsBlocked()));
		 vUsuarioCerberosDTO.setDocente(isTrueOrFalse(datosUsuario.get(0).getIsTeacher()));
		 vUsuarioCerberosDTO.setAlumno(isTrueOrFalse(datosUsuario.get(0).getIsEstudiante()));
		 
	     return appendDatosUsuarioCerberos(vUsuarioCerberosDTO);
	 }
	
	public VUsuarioCerberosDto appendDatosUsuarioCerberos(VUsuarioCerberosDto vUsuarioCerberosDto) {
        DatosUsuarioJwt datosUsuarioJwt = datosUsuarioJwtService.getDatosUsuarioByOidUsuarioUsingRepository(vUsuarioCerberosDto.getOidUsername());
        vUsuarioCerberosDto.setIdUsuarioDelphos(datosUsuarioJwt.getXUsuarioDelphos());
        vUsuarioCerberosDto.setIdUsuarioComunica(datosUsuarioJwt.getXUsuarioComunica());
        vUsuarioCerberosDto.setUsuarioDelphos(datosUsuarioJwt.getUsuarioDelphos());
        vUsuarioCerberosDto.setUsuarioComunica(datosUsuarioJwt.getUsuarioComunica());
        vUsuarioCerberosDto.setIdEmpleadoDelphos(datosUsuarioJwt.getIdEmpleadoDelphos());
        vUsuarioCerberosDto.setIdEmpleadoComunica(datosUsuarioJwt.getIdEmpleadoComunica());
        vUsuarioCerberosDto.setNif(datosUsuarioJwt.getNif());
        vUsuarioCerberosDto.setEmail(datosUsuarioJwt.getEmail());
                
        appendAplicacionesUsuario(vUsuarioCerberosDto);
        appendRolesUsuario(vUsuarioCerberosDto);

        return vUsuarioCerberosDto;
    }
	
	private void appendAplicacionesUsuario(VUsuarioCerberosDto vUsuarioCerberosDto) {
		List<AplicacionUsuarioJwt> aplicacionesUsuario = datosUsuarioJwtService.getAplicacionesUsuario(vUsuarioCerberosDto.getOidUsername().toString());	        
        vUsuarioCerberosDto.setAplicaciones(aplicacionesUsuario);
    }

    private void appendRolesUsuario(VUsuarioCerberosDto vUsuarioCerberosDto) {
    	List<RolUsuarioJwt> rolesUsuario = datosUsuarioJwtService.getRolesUsuario(vUsuarioCerberosDto.getOidUsername().toString(), vUsuarioCerberosDto.getNif());
        vUsuarioCerberosDto.setRoles(rolesUsuario);
        
        AuthoritiesJwt authorities = new AuthoritiesJwt();
        List<String> authoritiesList = new ArrayList<>();
        rolesUsuario.forEach(rol -> authoritiesList.add("ROLE_" + rol.getCodigoPerfil()));

        authorities.setAuthorities(authoritiesList);        
        vUsuarioCerberosDto.setAuthorities(authorities);
        }
	 	 
	 private VUsuarioCerberosDto convertToDto(VUsuarioCerberos vUsuarioCerberos) {
	    	VUsuarioCerberosDto vUsuarioCerberoDTO = modelMapper.map(vUsuarioCerberos, VUsuarioCerberosDto.class);
	        return vUsuarioCerberoDTO;
	 }
	 
	 public Boolean isTrueOrFalse(String datoATransformar) {
		 if("S".equals(datoATransformar)) {
			 return true;
		 }else if("N".equals(datoATransformar)) {
			 return false;
		 }else {
			 return null;
		 }
	 }
	 
	    public static String decodificarCadenas(String cadena) {
	        String resultado = cadena;
	        Pattern pattern = Pattern.compile("&[^;]+;");
	        Matcher matcher = pattern.matcher(cadena);
	        Map<String,String> reemplazos = new HashMap<>();
	        reemplazos.put("&ntilde;", "ñ");
	        reemplazos.put("&aacute;", "á");
	        reemplazos.put("&eacute;", "é");
	        reemplazos.put("&iacute;", "í");
	        reemplazos.put("&oacute;", "ó");
	        reemplazos.put("&uacute;", "ú");
	        reemplazos.put("&Ntilde;", "Ñ");
	        reemplazos.put("&Aacute;", "Á");
	        reemplazos.put("&Eacute;", "É");
	        reemplazos.put("&Iacute;", "Í");
	        reemplazos.put("&Oacute;", "Ó");
	        reemplazos.put("&Uacute;", "Ú");
	        while (matcher.find()) {
	            String subcadena = matcher.group();
	            resultado = resultado.replaceAll(subcadena, reemplazos.get(subcadena));
	        }
	        
	        return resultado;
	    }
	 
	 public boolean validarNif(String nif) {
	        // Expresión regular para verificar el formato del DNI
	        if (!nif.matches("\\d{8}[A-HJ-NP-TV-Z]")) {
	            return false;
	        }
	        //Calcula la letra correspondiente al número del DNI y comprueba si es la correspondiente.
	        return "TRWAGMYFPDXBNJZSQVHLCKE".charAt(Integer.parseInt(nif.substring(0, 8)) % 23) == Character.toUpperCase(nif.charAt(8));
	    }
}
