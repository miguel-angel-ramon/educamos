package es.jccm.edu.architecture;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.GeneralCodingRules;
import com.tngtech.archunit.library.freeze.FreezingArchRule;

public class ReglasGenerales {

	@Test
	public void noUsarSystemouts() {
		JavaClasses importedClasses = new ClassFileImporter().importPackages(BasePath.BASEPACKAGE);
		FreezingArchRule.freeze(GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS).check(importedClasses);
	}
	
	@Test
	public void noUsarJavaUtilLogging() {
		JavaClasses importedClasses = new ClassFileImporter().importPackages(BasePath.BASEPACKAGE);
		GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING.check(importedClasses);
	}
	
	@Test
	public void noUsarJodTime() {
		JavaClasses importedClasses = new ClassFileImporter().importPackages(BasePath.BASEPACKAGE);
		GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME.check(importedClasses);
	}
}
