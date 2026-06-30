package es.jccm.edu.buzon.application;

import lombok.Getter;

@Getter
public enum EstadoSolicitud {
	SIN_SOLICITAR(1, "Sin solicitar"),
	SOLICITUD_ENVIADA(2, "Solicitud enviada"),
    SOLICITUD_EN_PROGRESO(3, "Solicitud en progreso"),
    SOLICITUD_PROCESADA(4, "Solicitud procesada"),
    ACTUALIZACION_ENVIADA(5, "Actualización enviada"),
    ACTUALIZACION_EN_PROGRESO(6, "Actualización en progreso"),
    ACTUALIZACION_PROCESADA(7, "Actualización procesada");

	private int id; //ID_ESTADO EN TABLA ESTADO_SOLICITUD
    private String descripcion; //DS_ESTADO EN TABLA ESTADO_SOLICITUD
    
    EstadoSolicitud(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
        }
    
    public static EstadoSolicitud getById(int id) {
        for (EstadoSolicitud estado : values()) {
            if (estado.getId() == id) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Código de estado de solicitud no válido: " + id);
    }
	    
	    /*A partir de aqui, preguntar*/
    public int getId() {
        return id;
	    }
    
    public String getDescripcion() {
	        return descripcion;
	    }
}
