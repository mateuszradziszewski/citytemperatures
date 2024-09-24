package com.kyotutechnology.citytemperatures;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.jmolecules.archunit.JMoleculesArchitectureRules;

@AnalyzeClasses(packages = "com.kyotutechnology.citytemperatures")
class JMoleculesRulesUnitTest {

  @ArchTest ArchRule hexagonalArchitecture = JMoleculesArchitectureRules.ensureHexagonal();
}
