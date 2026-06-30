package es.jccm.edu.alumnos.adapter.out.repository.historicoDomiciliosAlumno;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.JPQLQuery;

import es.jccm.edu.alumnos.application.domain.historicoDomiciliosAlumno.DomicilioAlumno;
import es.jccm.edu.alumnos.application.domain.historicoDomiciliosAlumno.HistoricoDomicilioAlumnoDTO;
import es.jccm.edu.alumnos.application.domain.historicoDomiciliosAlumno.QDomicilioAlumno;
import static com.querydsl.core.types.Projections.constructor;

@Repository
public class HistDomiciliosAlumnoRepositoryImpl extends QuerydslRepositorySupport implements HistDomiciliosAlumnoRepository{

	public HistDomiciliosAlumnoRepositoryImpl() {
		super(DomicilioAlumno.class);
		
	}

	@Override
	public List<HistoricoDomicilioAlumnoDTO> findDomiciliosAlumnoById(Long idAlumno) {
		QDomicilioAlumno domicilio=QDomicilioAlumno.domicilioAlumno;
		
		JPQLQuery<HistoricoDomicilioAlumnoDTO> query=from(domicilio).
				select(constructor(HistoricoDomicilioAlumnoDTO.class, domicilio.id,domicilio.fechaDomicilio,domicilio.familia.tutor1.nombre,
						domicilio.familia.tutor1.apellido1,domicilio.familia.tutor1.apellido2,domicilio.familia.tutor2.nombre,domicilio.familia.tutor2.apellido1,
						domicilio.familia.tutor2.apellido2, domicilio.tipoVia.descripcion, domicilio.calle, domicilio.numeroCalle, domicilio.escalera, domicilio.piso,
						domicilio.letra,domicilio.cPostal,domicilio.localidad.descripcion,domicilio.municipio.descripcion,domicilio.provincia.descripcion)).
				where(domicilio.idAlumno.eq(idAlumno)).orderBy(domicilio.fechaDomicilio.desc());
		
		
						
		return query.fetch();
	}

}
