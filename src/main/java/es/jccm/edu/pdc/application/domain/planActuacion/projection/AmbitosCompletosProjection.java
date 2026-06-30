package es.jccm.edu.pdc.application.domain.planActuacion.projection;

import java.util.Date;

public interface AmbitosCompletosProjection {
	
	Long getX_CENTRO();
	Long getX_COMPETENCIA();
	String getD_SECTOR();
	String getC_NIVEL();
	Long getC_PDCCOMPETENCIA();
	String getD_PDCCOMPETENCIA();
	Long getN_VALOR();
	Long getN_RESPUESTAS();
	Long getC_ANNO();
	Long getX_OBJGRPCOM();
	Long getX_OBJETIVO();
	Long getX_SUBDIMENSION();
	Long getX_TIPOBJ();
	String getL_ACTIVO();
	String getD_OBJETIVO();
	Long getX_PDCSUGNIV();
	Long getX_SUGERENCIA();
	Long getX_NIVEL();
	String getD_COMOMEJORAR();
	String getD_SUGERENCIA();
	Long getX_CUEOPC();
	String getD_CUEPRE();
	String getD_CUEOPC();
	Long getX_PDCENOBJCEN();
	String getD_OBJESPECIFICO();
	Long getX_PDCCENOBJLINACT();
	String getT_LINEAACT();
	String getD_LINEAACT();
	Date getF_INICIO();
	String getF_CREACION();
	Date getF_FIN();
	String getT_RESPONSABLE();
	String getD_INDLOGRO();
	String getD_INSTRUMENTOS();
	Integer getPORC_EJEC();
	String getF_INI_SEGUI();
	String getF_FIN_SEGUI();
	String getD_TAREAS();
	String getD_VALORACION();
	String getD_DIFICULTADES_ACCIONES();
	String getD_COMENTARIOS();
	Long getX_CUEPRE();
	
}

