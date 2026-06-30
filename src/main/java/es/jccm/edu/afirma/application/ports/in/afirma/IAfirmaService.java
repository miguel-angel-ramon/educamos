package es.jccm.edu.afirma.application.ports.in.afirma;

import es.jccm.edu.afirma.adapter.in.rest.afirma.model.DocumentoFirmadoDto;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import java.io.IOException;

public interface IAfirmaService {

    DocumentoFirmadoDto verificarFirma(String signBase64, String documentoBase64) throws SOAPException, ParserConfigurationException, IOException, SAXException;

    Integer verificarFirma2(String firmaBase64, String documentoBase64, String nif) throws SOAPException, ParserConfigurationException, IOException, SAXException;

}
