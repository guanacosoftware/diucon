package ar.com.guanaco.diucon;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("ar.com.guanaco.diucon");

        noClasses()
            .that()
                .resideInAnyPackage("ar.com.guanaco.diucon.service..")
            .or()
                .resideInAnyPackage("ar.com.guanaco.diucon.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..ar.com.guanaco.diucon.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
