package es.jccm.edu.alumnos.adapter.out.repository.conductasContrarias;

import static com.querydsl.core.types.Projections.constructor;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import es.jccm.edu.alumnos.application.domain.conductasContrarias.MatriculaAlumno;
import es.jccm.edu.alumnos.application.domain.conductasContrarias.QAlumnoConductaContraria;
import es.jccm.edu.alumnos.application.domain.conductasContrarias.QConductaNegativaAlumno;
import es.jccm.edu.alumnos.application.domain.conductasContrarias.QCorreccionConductaNegativa;
import es.jccm.edu.alumnos.application.domain.conductasContrarias.QMatriculaAlumno;
import es.jccm.edu.alumnos.application.domain.conductasContrarias.dto.AlumnadoDTO;
import es.jccm.edu.alumnos.application.domain.conductasContrarias.dto.CondContrariaDTO;

@Repository
public class ConductasContrariasRepositoryImpl extends QuerydslRepositorySupport implements ConductasContrariasRepository {

	public ConductasContrariasRepositoryImpl() {
		super(MatriculaAlumno.class);
	}

	@Override
	public List<AlumnadoDTO> findAlumnadoIncidente(Long unidad, Long ofertamatric, Integer anno) {
		
		QMatriculaAlumno alumno=QMatriculaAlumno.matriculaAlumno;
		QConductaNegativaAlumno  conNegativa=QConductaNegativaAlumno.conductaNegativaAlumno;
		BooleanBuilder predicate=new BooleanBuilder();
		
		if (unidad <1) {
			predicate.and(alumno.anno.eq(anno).and(alumno.idOfertaMatriculacion.eq(ofertamatric)));
		}else {
			predicate.and((alumno.anno.eq(anno).and(alumno.idOfertaMatriculacion.eq(ofertamatric)).and(alumno.idUnidad.eq(unidad))));
		}
				
		
		JPQLQuery<AlumnadoDTO> query=from(alumno).
				select(constructor(AlumnadoDTO.class,
						alumno.alumno.nombre,alumno.alumno.apellido1,alumno.alumno.apellido2,alumno.idMatricula,
						JPAExpressions.select(conNegativa.id.count()).from(conNegativa).
						where(conNegativa.tipoCondNegativa.grupoCondNegativa.codigo.equalsIgnoreCase("CCN")
								.and(conNegativa.alumnoCondContraria.matricula.idMatricula.eq(alumno.idMatricula))),
						JPAExpressions.select(conNegativa.id.count()).from(conNegativa).
						where(conNegativa.tipoCondNegativa.grupoCondNegativa.codigo.equalsIgnoreCase("CGP")
								.and(conNegativa.alumnoCondContraria.matricula.idMatricula.eq(alumno.idMatricula))),	
					JPAExpressions.select(conNegativa.id.count()).from(conNegativa).
						where(conNegativa.tipoCondNegativa.grupoCondNegativa.codigo.equalsIgnoreCase("CAP")
								.and(conNegativa.alumnoCondContraria.matricula.idMatricula.eq(alumno.idMatricula))),	
					JPAExpressions.select(conNegativa.id.count()).from(conNegativa).
						where(conNegativa.tipoCondNegativa.grupoCondNegativa.codigo.equalsIgnoreCase("CAPG")
								.and(conNegativa.alumnoCondContraria.matricula.idMatricula.eq(alumno.idMatricula)))
						)).where(predicate);
		
		return query.fetch();
	}

	@Override
	public List<CondContrariaDTO> findCondContrariaUnidad(Long unidad, Long ofertamatric, Integer anno) {
		QAlumnoConductaContraria alumno=QAlumnoConductaContraria.alumnoConductaContraria;
		BooleanBuilder predicate=new BooleanBuilder();
		
		if(unidad<1) {
			predicate.and(alumno.matricula.anno.eq(anno).and(alumno.matricula.idOfertaMatriculacion.eq(ofertamatric)));
		}else {
			predicate.and(alumno.matricula.anno.eq(anno).and(alumno.matricula.idOfertaMatriculacion.eq(ofertamatric))
					.and(alumno.matricula.idUnidad.eq(unidad)));
		}
		
		JPQLQuery<CondContrariaDTO>query=from(alumno).
				select(constructor(CondContrariaDTO.class,
						alumno.fechaCondContraria,
						alumno.matricula.alumno.nombre,alumno.matricula.alumno.apellido1,alumno.matricula.alumno.apellido2,
						alumno.DescripcionCondContraria,
						alumno.id,
						alumno.idConductaColectiva,
						alumno.matricula.idMatricula
						)).where(predicate);
		List <CondContrariaDTO>resultados=query.fetch();
		
		for (CondContrariaDTO x : resultados) {
			x.setConductas(getConductas(x.getIdAlumnoCContraria()));
			x.setCorrecciones(getCorrecciones(x.getIdAlumnoCContraria()));
		}
			
		return resultados;
		
		}

	private List<String> getCorrecciones(Long id) {
		QCorreccionConductaNegativa correccion=QCorreccionConductaNegativa.correccionConductaNegativa;
		JPQLQuery <String> query=from(correccion).select(correccion.tipoCorrreccion.descripcion).where(correccion.alumnoConCon.id.eq(id))
				.orderBy(correccion.tipoCorrreccion.orden.desc());
	
		return query.fetch();
	}

	private List<String> getConductas(Long id) {
		QConductaNegativaAlumno conducta= QConductaNegativaAlumno.conductaNegativaAlumno;
		JPQLQuery <String> query=from(conducta).select(conducta.tipoCondNegativa.descripcion).where(conducta.alumnoCondContraria.id.eq(id))
				.orderBy(conducta.tipoCondNegativa.orden.desc());
		return query.fetch();
	}
		

}
