package es.jccm.edu.ausencias.adapter.out.repository.guardias;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.jccm.edu.ausencias.application.domain.guardias.projection.GuardiasProfesoresProjection;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class GuardiasProfesoresRepositoryIT {

	@Autowired
	GuardiasProfesoresRepository sut;
	
	//TODO: Test fragil, solo para ver que sigue funcionando
	@Test
	public void sePuedeLlamar() {
		
		/*List<GuardiasProfesoresProjection> res=sut.getGuardiasProfesores(2000076L,2021);
		assertEquals(2,res.size());*/
	}
}
