package com.mjc.school.service;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTag;
import com.tngtech.archunit.junit.ArchTest;

import static com.tngtech.archunit.base.DescribedPredicate.describe;
import static com.tngtech.archunit.core.domain.JavaModifier.ABSTRACT;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

@ArchTag("architecture")
@AnalyzeClasses(packagesOf = ServiceLayerArchitectureTest.class, importOptions = DoNotIncludeTests.class)
class ServiceLayerArchitectureTest {

    @ArchTest
    void classes_annotated_with_Service_should_implement_BaseService_interface(
        final JavaClasses serviceLayerClasses
    ) {
        classes()
            .that()
            .areAnnotatedWith("org.springframework.stereotype.Service")
            .should()
            .implement("com.mjc.school.service.BaseService")
            .because("it's general requirement for the service - to be annotated with @Service and implement BaseService interface")
            .check(serviceLayerClasses);
    }

    @ArchTest
    void classes_implementing_BaseService_interface_should_be_annotated_with_Service(
        final JavaClasses serviceLayerClasses
    ) {
        classes()
            .that()
            .implement("com.mjc.school.service.BaseService")
            .should()
            .beAnnotatedWith("org.springframework.stereotype.Service")
            .orShould()
            .haveModifier(ABSTRACT)
            .because("it's general requirement for the service - to be annotated with @Service and implement BaseService interface")
            .check(serviceLayerClasses);
    }

    @ArchTest
    void services_should_not_be_annotated_with_Transactional_from_javax_or_jakarta_packages(
        final JavaClasses serviceLayerClasses
    ) {
        noClasses()
            .should()
            .beAnnotatedWith("javax.transaction.Transactional")
            .orShould()
            .beAnnotatedWith("jakarta.transaction.Transactional")
            .because("org.springframework.transaction.annotation.Transactional should be used instead")
            .check(serviceLayerClasses);
    }

    @ArchTest
    void services_methods_should_not_be_annotated_with_Transactional_from_javax_or_jakarta_packages(
        final JavaClasses serviceLayerClasses
    ) {
        noMethods()
            .should()
            .beAnnotatedWith("javax.transaction.Transactional")
            .orShould()
            .beAnnotatedWith("jakarta.transaction.Transactional")
            .because("org.springframework.transaction.annotation.Transactional should be used instead")
            .check(serviceLayerClasses);
    }

    @ArchTest
    void services_methods_should_not_return_entities_directly(
        final JavaClasses serviceLayerClasses
    ) {
        noMethods()
            .that()
            .areDeclaredInClassesThat()
            .implement("com.mjc.school.service.BaseService")
            .and()
            .arePublic()
            .and()
            .areNotStatic()
            .should()
            .haveRawReturnType(
                describe(
                    "assignable to base entity",
                    javaClass -> javaClass.isAssignableTo("com.mjc.school.repository.model.BaseEntity")
                )
            )
            .check(serviceLayerClasses);
    }

    @ArchTest
    void services_should_not_depend_on_concrete_repository_implementation(
        final JavaClasses serviceLayerClasses
    ) {
        noClasses()
            .should()
            .dependOnClassesThat(
                describe(
                    "are concrete repository implementations",
                    javaClass -> javaClass.isAssignableTo("com.mjc.school.repository.BaseRepository")
                        && !(javaClass.isInterface() || javaClass.getModifiers().contains(ABSTRACT))
                )
            )
            .because("interfaces or abstract classes should be used for dependency injection")
            .check(serviceLayerClasses);
    }

    @ArchTest
    void at_least_one_class_annotated_with_Service_should_be_present_in_module_packages(
        final JavaClasses serviceLayerClasses
    ) {
        classes()
            .that()
            .areAnnotatedWith("org.springframework.stereotype.Service")
            .should()
            .resideInAPackage("com.mjc.school.service..")
            .because("at least one class annotated with @Service should be observed")
            .check(serviceLayerClasses);
    }
}
