package es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities;

import lombok.Data;
import java.util.List;

@Data
public class ListadoTablaAnexoIX {

    String codModulo;

    String horasEmpresa;

    List<String> aprendizaje;

    List<String> intEmpresa;

    List<String> aprobada;

    List<String> gradoSuperacion;

    List<String> motivacion;

}
