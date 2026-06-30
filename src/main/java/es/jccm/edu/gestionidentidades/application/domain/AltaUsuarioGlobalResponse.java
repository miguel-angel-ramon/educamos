package es.jccm.edu.gestionidentidades.application.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AltaUsuarioGlobalResponse{
	private Usuario usuarioGlobal;
	private AsignacionNuevasCredenciales asignacion;
}