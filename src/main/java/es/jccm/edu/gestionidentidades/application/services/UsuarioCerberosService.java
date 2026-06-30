package es.jccm.edu.gestionidentidades.application.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.gestionidentidades.adapter.in.rest.apicerberos.model.VUsuarioCerberosDto;
import es.jccm.edu.gestionidentidades.adapter.out.repository.VUsuarioCerberosRepository;
import es.jccm.edu.gestionidentidades.application.domain.VUsuarioCerberos;
import es.jccm.edu.gestionidentidades.application.ports.in.IUsuarioCerberosService;
import es.jccm.edu.gestionidentidades.application.ports.out.exceptions.InvalidNifException;
import es.jccm.edu.gestionidentidades.application.ports.out.exceptions.ResultSetException;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.AplicacionUsuarioJwt;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.AuthoritiesJwt;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.RolUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioCerberosService implements IUsuarioCerberosService{

	 @Autowired
	 VUsuarioCerberosRepository vUsuarioCerberosRepository;
	 
	 @Autowired
	 ModelMapper modelMapper;
	 
	 @Autowired
	 IDatosUsuarioJwtService datosUsuarioJwtService;
	 
	 @Override
	 public VUsuarioCerberosDto getDatosUsuarioByLogin(String login) throws ResultSetException {
		 String loginDecodificado = decodificarCadenas(login);
		 List<VUsuarioCerberos> datosUsuario = vUsuarioCerberosRepository.findByLoginByQuery(loginDecodificado);
		 
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
	 public VUsuarioCerberosDto getDatosUsuarioByUidLdap(String uidLdap) throws ResultSetException{
		 
		 String uidLdapDecodificado = decodificarCadenas(uidLdap);
		 List<VUsuarioCerberos> datosUsuario = vUsuarioCerberosRepository.findByUidLdapByQuery(uidLdapDecodificado);
		 
		 if(datosUsuario.size() > 1) {
			 throw new ResultSetException("Se ha encontrado más de un registro para el uidLdap: " + uidLdapDecodificado);
		 }else if(datosUsuario.isEmpty()){
			 return null;
		 }else {
			 VUsuarioCerberosDto vUsuarioCerberosDTO = convertToDto(datosUsuario.get(0));
			 vUsuarioCerberosDTO.setUidLdap(uidLdap);
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
		 
		 List<VUsuarioCerberos> datosUsuario = vUsuarioCerberosRepository.findByIdentificacionByQuery(nif);
		 
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
	 
	 public VUsuarioCerberosDto getDatosUsuarioByIdentificador(String identificador) {
		 
		 String cadenaDecodificada = decodificarCadenas(identificador);
		 List<VUsuarioCerberos> datosUsuario = vUsuarioCerberosRepository.findByIdentificadorByQuery(cadenaDecodificada);
		 
		 if(datosUsuario.isEmpty()){
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
	 
	 public VUsuarioCerberosDto getDatosUsuarioByIdentificador2(String identificador) {
		 
		 String cadenaDecodificada = decodificarCadenas(identificador);
		 List<VUsuarioCerberos> datosUsuario = vUsuarioCerberosRepository.findByLoginByQuery(cadenaDecodificada);
		 
		 
		 if(datosUsuario.isEmpty()){
		
			 datosUsuario = vUsuarioCerberosRepository.findByUidLdapByQuery(cadenaDecodificada);
			 
			 if(datosUsuario.isEmpty()){
				 return null;
			 }else {
				 VUsuarioCerberosDto vUsuarioCerberosDTO = convertToDto(datosUsuario.get(0));
				 vUsuarioCerberosDTO.setActivo(isTrueOrFalse(datosUsuario.get(0).getIsActive()));
				 vUsuarioCerberosDTO.setBloqueado(isTrueOrFalse(datosUsuario.get(0).getIsBlocked()));
				 vUsuarioCerberosDTO.setDocente(isTrueOrFalse(datosUsuario.get(0).getIsTeacher()));
				 vUsuarioCerberosDTO.setAlumno(isTrueOrFalse(datosUsuario.get(0).getIsEstudiante()));
				 return appendDatosUsuarioCerberos(vUsuarioCerberosDTO);
			 }
		 }else {
			 VUsuarioCerberosDto vUsuarioCerberosDTO = convertToDto(datosUsuario.get(0));
			 vUsuarioCerberosDTO.setActivo(isTrueOrFalse(datosUsuario.get(0).getIsActive()));
			 vUsuarioCerberosDTO.setBloqueado(isTrueOrFalse(datosUsuario.get(0).getIsBlocked()));
			 vUsuarioCerberosDTO.setDocente(isTrueOrFalse(datosUsuario.get(0).getIsTeacher()));
			 vUsuarioCerberosDTO.setAlumno(isTrueOrFalse(datosUsuario.get(0).getIsEstudiante()));
			 return appendDatosUsuarioCerberos(vUsuarioCerberosDTO);
		 }     
	 }
	 
	/* private VUsuarioCerberosDto returndatosUsuario (List<VUsuarioCerberos> datosUsuario, String identificador) throws ResultSetException {
		 if(datosUsuario.size() > 1) {
			 throw new ResultSetException("Se ha encontrado más de un registro para el identificador: " + identificador);
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
	 }*/
	 
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
        vUsuarioCerberosDto.setFNacimiento(datosUsuarioJwt.getFNacimiento()); 
        appendAplicacionesUsuario(vUsuarioCerberosDto);
        appendRolesUsuario(vUsuarioCerberosDto);
        appendSecurityLevel(vUsuarioCerberosDto);
        
        return vUsuarioCerberosDto;
    }
	
	private void appendSecurityLevel(VUsuarioCerberosDto vUsuarioCerberosDto) {
		
		Long nivelSeguridad = datosUsuarioJwtService.getSecurityLevel(vUsuarioCerberosDto.getOidUsername().toString());	 
		if (nivelSeguridad == null) {
			
			log.info("El usuario "+vUsuarioCerberosDto.getOidUsername()+" no tiene ningún rol asignado asignado.");
			
			if(vUsuarioCerberosDto.getIdEmpleadoDelphos() != null) {
				nivelSeguridad = datosUsuarioJwtService.getSecurityLevelEmpleado(vUsuarioCerberosDto.getIdEmpleadoDelphos().toString());
			}else {
				log.info("El usuario "+vUsuarioCerberosDto.getOidUsername()+" no tiene ningún puesto de trabajo activo en SSCC o DDPP.");
				nivelSeguridad = 1L;
			}

		}
		if (nivelSeguridad != null && nivelSeguridad > 1) {
	        Long excepcionNivel = datosUsuarioJwtService.getSecurityLevelExcepcion(vUsuarioCerberosDto.getOidUsername().toString());
	        if (excepcionNivel != null) {
	            nivelSeguridad = excepcionNivel; 
	        }
	    }

        vUsuarioCerberosDto.setSecurityLevel(nivelSeguridad);
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
/*	 
	 public String replaceSpecialCharacters(String identificador) {
		 if(identificador.contains("%¡&")) {
	        List<String> subcadenas = new ArrayList<>();
	        Pattern pattern = Pattern.compile("&[^;]+;");
	        Matcher matcher = pattern.matcher(identificador);
	        while (matcher.find()) {
	        	matcher.
	            String subcadena = matcher.group();
	            subcadenas.add(subcadena);
	        }
	        return subcadenas;
		    }
		 }else {
			 return identificador;
		 }
	 }
*/	 
	 
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

        // Permite DNI (8 dígitos + letra) o NIE (X/Y/Z + 7 dígitos + letra)
        if (!nif.matches("([XYZ]\\d{7}|\\d{8})[A-HJ-NP-TV-Z]")) {
            return false;
        }

        String numeroBase = nif.substring(0, nif.length() - 1).toUpperCase();
        char letra = Character.toUpperCase(nif.charAt(nif.length() - 1));

        // Si es NIE, convertir la letra inicial a número
        if (numeroBase.charAt(0) == 'X') {
            numeroBase = "0" + numeroBase.substring(1);
        } else if (numeroBase.charAt(0) == 'Y') {
            numeroBase = "1" + numeroBase.substring(1);
        } else if (numeroBase.charAt(0) == 'Z') {
            numeroBase = "2" + numeroBase.substring(1);
        }

        // Calcula la letra correspondiente
        char letraCalculada = "TRWAGMYFPDXBNJZSQVHLCKE"
                .charAt(Integer.parseInt(numeroBase) % 23);

        return letraCalculada == letra;
    }

}
