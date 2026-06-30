package es.jccm.edu.architecture;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.lang.syntax.elements.ClassesShouldConjunction;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaPackage;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures.LayeredArchitecture;
import com.tngtech.archunit.library.freeze.FreezingArchRule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChequeaArquitecturaLimpiaDeCadaBcTest {
	
	@Test
	public void arquitecturaDentroDeCadaBc() {
		JavaClasses importedClasses = BasePath.getAllClases();
	    
		//cada bc es un subpaquete que reside dentro del BASEPACKAGE
	    Set<JavaPackage> bcs=importedClasses.getPackage(BasePath.BASEPACKAGE).getSubpackages();
	    
    	log.info("chequeando arquitectura de todos los bcs incluidos en el paquete base {}, hay un total de {} BCS",BasePath.BASEPACKAGE,bcs.size());

	    for(JavaPackage bc:bcs) {
	    	log.info("chequeando arquitectura interna del bc: [{}]",bc.getRelativeName());
	    	//if("totp".equals(bc.getRelativeName())) { //descomentar si quieres ejecutar en local solo 1 bc
	    		chequeaQueCapaInternaNoDependeDeLaExterna(bc);
	    		chequeaQueCapaDominioNoDependeDeAplicacion(bc);
	    		prohibidoAccesoDesdeAdaptadoresDeEntradaAServicios(bc);
	    		prohibidoAccesoDesdeServiciciosAAdaptadores(bc);
	    	//}
	    }
	}
	
	//Dado un bc, se funden las clases de dicho bc con las del paquete shared
	private JavaClasses getImportedClases(JavaPackage bcpackage) {
	    String baseBc = bcpackage.getName();
	    JavaPackage basebcs = bcpackage.getParent().get();
		String baseShared = basebcs.getPackage("shared").getName();
	    
	    // se funden las clases del paquete que tiene el bc y el paquete shared
		JavaClasses importedClasses = new ClassFileImporter().importPackages(baseBc, baseShared); 
		return importedClasses;
	}
	
	private void chequeaQueCapaInternaNoDependeDeLaExterna(JavaPackage bcpackage) {
		JavaClasses importedClasses =getImportedClases(bcpackage);
			
	    LayeredArchitecture monolitoDebilmenteAcoplado=layeredArchitecture()
			.consideringOnlyDependenciesInLayers()
			.as("capa interna-externa de bc="+bcpackage.getName())
			.layer("externa").definedBy("..adapter..")
			.layer("interna").definedBy("..application..")
			.whereLayer("interna").mayNotAccessAnyLayer();
	    
	    ArchRule nomas=FreezingArchRule.freeze(monolitoDebilmenteAcoplado); 
	    nomas.check(importedClasses);
	}
	
	
	private void chequeaQueCapaDominioNoDependeDeAplicacion(JavaPackage bcpackage) {
		JavaClasses importedClasses =getImportedClases(bcpackage);

	    LayeredArchitecture capaApplication=layeredArchitecture()
			.consideringOnlyDependenciesInLayers()
			.as("capa de aplicacion de bc="+bcpackage.getName())
			.layer("services").definedBy("..application.ports..","..application.services..")
			.layer("domain").definedBy("..application.domain..")
			.whereLayer("domain").mayOnlyBeAccessedByLayers("services");
	    
	    ArchRule nomas=FreezingArchRule.freeze(capaApplication); 

	    nomas.check(importedClasses);
	}
	
	private void prohibidoAccesoDesdeAdaptadoresDeEntradaAServicios(JavaPackage bcpackage) {
		JavaClasses importedClasses = getImportedClases(bcpackage);

		ArchRule accesoDesdeFueraViaPuertosIn=ArchRuleDefinition.noClasses().that()
		   .resideInAPackage("..adapter.in..")
		   .should().accessClassesThat()
		   .resideInAnyPackage("..services..","..ports.out..")
			.as("prohibidoAccesoDesdeAdaptadoresDeEntradaAServicios "+bcpackage.getName());
;

	    ArchRule nomas=FreezingArchRule.freeze(accesoDesdeFueraViaPuertosIn); 

		nomas.check(importedClasses);
		
	}
	
	private void prohibidoAccesoDesdeServiciciosAAdaptadores(JavaPackage bcpackage) {
		JavaClasses importedClasses = getImportedClases(bcpackage);

		ArchRule noAccesoDesdeServiciosAAdaptadores=
				ArchRuleDefinition.noClasses().that()
		   .resideInAPackage("..application.services..")
		   .should().accessClassesThat()
		   .resideInAnyPackage("..adapter..")
			.as("prohibidoAccesoDesdeServiciciosAAdaptadores "+bcpackage.getName());

	    ArchRule nomas=FreezingArchRule.freeze(noAccesoDesdeServiciosAAdaptadores); 

		nomas.check(importedClasses);
		
	}
	


}
