package br.com.tech.challenge.sistemapedido;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class ArchitectureTest {
    JavaClasses jc;

    @BeforeEach
    void init() {
        this.jc = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("br.com.tech.challenge.sistemapedido");
    }

    @Test
    void validLayers() {
        layeredArchitecture()
                .consideringOnlyDependenciesInLayers()
                .layer("Domain").definedBy("..domain..")
                .layer("Infrastructure").definedBy("..infrastructure..")
                .layer("Application").definedBy("..application..")
                .layer("UseCase").definedBy("..usecase..")
                .whereLayer("Domain").mayNotAccessAnyLayer()
                .whereLayer("Infrastructure").mayNotBeAccessedByAnyLayer()
                .whereLayer("UseCase").mayOnlyAccessLayers("Domain")
                .whereLayer("Application").mayOnlyAccessLayers("UseCase", "Domain")
                .whereLayer("Application").mayOnlyBeAccessedByLayers("Infrastructure")
                .whereLayer("UseCase").mayOnlyBeAccessedByLayers("Application")
                .check(jc);
    }
}
