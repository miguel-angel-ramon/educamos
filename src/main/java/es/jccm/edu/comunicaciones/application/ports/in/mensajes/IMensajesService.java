package es.jccm.edu.comunicaciones.application.ports.in.mensajes;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Page;

import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model.ColectivoDto;
import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model.MiembrosColectivoDto;
import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.projection.GrupoDeAlumnosProjection;
import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.projection.OfertaMatriculaProjection;
import es.jccm.edu.comunicaciones.application.domain.mensajes.Mensaje;
import es.jccm.edu.comunicaciones.application.domain.mensajes.MensajeDestinatario;
import es.jccm.edu.comunicaciones.application.domain.mensajes.MensajeDetalle;
import es.jccm.edu.comunicaciones.application.domain.mensajes.Miembro;

public interface IMensajesService {

	Page<Mensaje> getMensajesRecibidos(String idUsuario, int page, int numItems, int idColectivo, String leido);

	Page<Mensaje> getMensajesEnviados(String idUsuario, int page, int numItems, int idColectivo, String leido, Long xUsuario);

	MensajeDetalle getDetalleMensaje(String idUsuario, String idMensaje, Boolean enviado) throws SQLException;

	void responderMensaje(String idUsuario, MensajeDetalle mensaje);

	void enviarMensaje(String idUsuario, MensajeDetalle mensaje, String idCentro, String idMatricula);

	void postEliminarMensajes(String idUsuario, List<Mensaje> mensajes, String tipoEliminacion);

	void setMensajeLeido(String idUsuario, String idDestinatarioMensaje);
	
	List<ColectivoDto> getColectivos(Long xPerfil);
	
	List<Miembro> getMiembrosColectivo(MiembrosColectivoDto datos);
	
	Page<Mensaje> getMensajesArchivados(String idUsuario, int page, int numItems, String idColectivo, String leido);
	
	void postArchivarMensajesRecibidos(String idUsuario, List<Mensaje> mensajes);
	
	void postRestaurarMensajesArchivados(String idUsuario, List<Mensaje> mensajes);
	
	List<MensajeDestinatario> getDestinatariosMensajeEnviado(String idUsuario, String idMensaje);

	List<OfertaMatriculaProjection> getOfertaMatriculabyCodCentro(Long codigoCentro, Long anyo);

	List<GrupoDeAlumnosProjection> getGrupoDeAlumnos(Long codigoCentro, Long anyo);

}
