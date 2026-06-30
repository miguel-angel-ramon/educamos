package es.jccm.edu.sms.application.ports.in.sms;

import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.simulacion.adapter.in.rest.usuarios.model.DatosPersonalesUsuarioDto;
import es.jccm.edu.sms.adapter.in.rest.sms.model.DocumentoFirmadoSmsDTO;
import es.jccm.edu.sms.adapter.in.rest.sms.model.DocumentoPendienteFirmaSmsDTO;

import java.io.IOException;
import java.util.List;

public interface ISmsService {


    //String sendSms(DatosPersonalesUsuarioDto datosUsuarioPersonales) throws Exception;



    void envia(DatosPersonalesUsuarioDto datosUsuarioPersonales, Long xUsuario) throws Exception;

    String getMensajeXml(DatosPersonalesUsuarioDto datosUsuarioPersonales, Long xUsuario);

    Boolean getVerificacionSmsByCodigo(String codigoVerificacion, Long xUsuario);


    List<DocumentoFirmadoSmsDTO> verificarFirmar(List<DocumentoPendienteFirmaSmsDTO> documentosBase64,  DatosUsuarioJwt xUsuario ) throws IOException;

    List<DocumentoFirmadoSmsDTO> firmarDocumentos(List<DocumentoPendienteFirmaSmsDTO> documentosBase64, DatosUsuarioJwt xUsuario) throws IOException;
    
    String getTelefonoUsuarioLogado(Long idEmpleadoComunica);
    
}
