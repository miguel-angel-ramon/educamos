package es.jccm.edu.reportsDelphos.application.ports.in;

import java.io.IOException;

public interface IReportsDelphosService {

	byte[] descargaReportsDelphos(String tParam) throws IOException ;

}
