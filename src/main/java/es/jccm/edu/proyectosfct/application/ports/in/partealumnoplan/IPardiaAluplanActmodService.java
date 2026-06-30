package es.jccm.edu.proyectosfct.application.ports.in.partealumnoplan;

import es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan.model.PardiaAluplanActmodDto;


public interface IPardiaAluplanActmodService {


    PardiaAluplanActmodDto crearPardiaAluplanActmod(PardiaAluplanActmodDto pardiaAluplanActmodDto);

    boolean borrarPardiaAluplanActmod(PardiaAluplanActmodDto pardiaAluplanActmodDto);
}
