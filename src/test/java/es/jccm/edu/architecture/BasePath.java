package es.jccm.edu.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;

public class BasePath {
    public static final String BASEPACKAGE="es.jccm.edu";
	
    public static JavaClasses getAllClases() {
		return new ClassFileImporter()
				.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
				.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
				.importPackages(BASEPACKAGE);
	}
	
}
