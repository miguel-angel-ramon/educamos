package es.jccm.edu.architecture;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaPackage;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures.LayeredArchitecture;
import com.tngtech.archunit.library.dependencies.SliceAssignment;
import com.tngtech.archunit.library.dependencies.SliceIdentifier;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import com.tngtech.archunit.library.freeze.FreezingArchRule;

/**
 * Chequea que no haya dependencias entre bcs, lo que permitiría extraer un bc a un ms desplegable por separado el día de mañana
 * 
 * @author jesus
 *
 */
public class AnalizaBcsIndependientesTest {


	@Test
	public void elPaqueteSharedNoDebeTenerDependenciasConNingunOtroBC() {
	    JavaClasses importedClasses = new ClassFileImporter().importPackages(BasePath.BASEPACKAGE);
    
	    Set<JavaPackage> bcs=importedClasses.getPackage(BasePath.BASEPACKAGE)
	        .getSubpackages();
	    
	    
	    LayeredArchitecture monolitoDebilmenteAcoplado=layeredArchitecture()
			.consideringOnlyDependenciesInLayers();
	    
	    for(JavaPackage bc:bcs) {
	    	monolitoDebilmenteAcoplado=
	    			monolitoDebilmenteAcoplado.layer(bc.getRelativeName()).definedBy(bc.getName()+"..");
	    }
	    
	    ArchRule regla=FreezingArchRule.freeze(monolitoDebilmenteAcoplado.whereLayer("shared").mayNotAccessAnyLayer());
	    	
	    regla.check(importedClasses);
	}


	@Test
	public void losBoundedContextsNoDebenTenerDepedenciasEntreSi() {
		JavaClasses importedClasses = BasePath.getAllClases();
	    
	    ArchRule rule=FreezingArchRule.freeze(SlicesRuleDefinition.slices()
	    		// este método ".matching", haría slices por nombre de paquete pero mete tambien al paquete shared como otro slice mas , y
	    		// no nos vale ya que si está permitido que otros bcs pueden tener dependencias con el shared
	    		//.matching("es.jccm.edu.(*)..") 
	    		
	    		.assignedFrom(new BCsSliceAsigments())
	    		
	    		.namingSlices("Bounded Context $1")
	    		.should().notDependOnEachOther());
	    

	    rule.check(importedClasses);
	}
	
	
	private static class BCsSliceAsigments implements SliceAssignment{

		@Override
		public String getDescription() {
			return "paquete base con bounded context y un paquete shared";
		}

		@Override
		public SliceIdentifier getIdentifierOf(JavaClass javaClass) {
			String nombreBc=getNombreBcPackage(BasePath.BASEPACKAGE, javaClass.getPackage());
			if(nombreBc==null || nombreBc.equals("shared")) {
				return SliceIdentifier.ignore();
			}
			return SliceIdentifier.of(nombreBc);
		}
		
		//dado un paquete busca recursivamente el subpaquete que está dentro del paquete "baseParent" que se pasa
		private String getNombreBcPackage(String baseParentPackage, JavaPackage paquete) {
			Optional<JavaPackage> padre=paquete.getParent();
			if(padre.isEmpty()) {
				return null;
			}
			if(baseParentPackage.equals(padre.get().getName())) {
				return paquete.getRelativeName();
			}else {
				return getNombreBcPackage(baseParentPackage,padre.get());
			}
			
		}
	}
	
}
