package es.jccm.edu.alumnos.adapter.out.repository.programas;

import static com.querydsl.core.types.Projections.constructor;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPQLQuery;

import es.jccm.edu.alumnos.application.domain.programas.AlumnoProgramaDTO;
import es.jccm.edu.alumnos.application.domain.programas.PAlumno;
import es.jccm.edu.alumnos.application.domain.programas.QPEstadoMatricula;
import es.jccm.edu.alumnos.application.domain.programas.QPMatricula;
import es.jccm.edu.alumnos.application.domain.programas.projection.AlumnoProgProjection;

@Repository
public class AlumnosProgramaRepositoryImpl  extends QuerydslRepositorySupport implements AlumnosProgramaRepository{

	public AlumnosProgramaRepositoryImpl() {
		super(PAlumno.class);
		}

	@Override
	public List<AlumnoProgramaDTO> findAlumnoProgramaByCurso(Long ofertaMatriculacion, int annio, Optional<Long> unidad,
			Optional<Long> programa) {
	
	QPMatricula matricula=QPMatricula.pMatricula;
	QPEstadoMatricula estadoMatricula=QPEstadoMatricula.pEstadoMatricula;
	
	BooleanBuilder predicate= buildPredicate(ofertaMatriculacion,annio,unidad,programa);
	StringExpression estado = buildCase(matricula);
	
	JPQLQuery <AlumnoProgramaDTO>	query= from(matricula).
			select(constructor(AlumnoProgramaDTO.class,matricula.alumno.id,matricula.id,matricula.alumno.nombre,matricula.alumno.apellido1,matricula.alumno.apellido2,matricula.unidad.nombreUnidad,
					estado,matricula.resultado))
			.leftJoin(matricula.estadoMatricula, estadoMatricula)
			.where(predicate)
			.orderBy(matricula.alumno.apellido1.asc(),matricula.alumno.apellido2.asc(),matricula.alumno.nombre.asc());
			
		return query.fetch();
	}
	
	
	private StringExpression buildCase(QPMatricula matricula) {
		StringExpression estado= new CaseBuilder()
				.when(matricula.estadoMatricula.resultado.eq(1)).then(matricula.estadoMatricula.descResultado.concat(" (")
						.concat( matricula.f_promocion.stringValue())   .concat(")"))
				.otherwise(matricula.estadoMatricula.descResultado);
		return estado;
	}

	private BooleanBuilder buildPredicate(Long ofertaMatriculacion, int annio, Optional<Long> unidad,
			Optional<Long> programa) {
		
		QPMatricula matricula=QPMatricula.pMatricula;
		BooleanBuilder predicate=new BooleanBuilder();	
		
		predicate.and(matricula.ofertaMatriculacion.eq(ofertaMatriculacion).and(matricula.annio.eq(annio)));
		if (unidad.isPresent()) predicate.and(matricula.unidad.id.eq(unidad.get()));
		if (programa.isPresent() && programa.get() ==104) 	predicate.and(matricula.diversificacion.endsWithIgnoreCase("N"));
		
		return predicate;
	}

	@Override
	public Long findIdCentro(Long ofertaMatriculacion) {
		QPMatricula matricula=QPMatricula.pMatricula;
		JPQLQuery <Long>	query= from(matricula).select(matricula.idCentro).where(matricula.ofertaMatriculacion.eq(ofertaMatriculacion));
		
		return query.fetchFirst();
	}

}
