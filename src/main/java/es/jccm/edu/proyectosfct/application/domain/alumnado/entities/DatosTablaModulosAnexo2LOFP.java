package es.jccm.edu.proyectosfct.application.domain.alumnado.entities;

import lombok.Data;

import javax.persistence.Column;
import java.util.List;

@Data
public class DatosTablaModulosAnexo2LOFP {
    
    String moduloProfesional;

    String codigo;

    List<String> resultadosAprendizaje;

    List <Integer> desarrolladoCen;

    List <Integer> desarrolladoEmp;

    List<String> empresa;

    Integer imparteCen;


    List<String> actividades;
}
