package com.planitautomation.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Entry point Maven Surefire runs: a JUnit 5 Platform Suite that discovers every
 * .feature file on the classpath (src/test/resources/features) and wires it up to the
 * step definitions and hooks under com.planitautomation.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.planitautomation")
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty, junit:target/cucumber-reports/cucumber.xml")
public class RunCucumberTest {
}
