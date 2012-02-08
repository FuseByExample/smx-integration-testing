This project demonstrates a number of mechanisms for testing OSGi artifacts based on SpringDM that are intended to be deployed against ServiceMix. It is intended to demonstrate:

* integration testing of OSGi bundles and Karaf features being deployed into a Karaf container
* integration testing of Spring-DM Camel routes
* unit testing of the same Camel routes outside of an OSGi container

The integration testing approach expands on that defined in [Advanced integration testing with Pax Exam Karaf](http://iocanel.blogspot.com/2012/01/advanced-integration-testing-with-pax.html).

Project layout
==============
The Maven projects contained within are as follows:

* _servicemix-test-utils_ - Contains utility classes for use with [Pax Exam Karaf](https://github.com/openengsb/labs-paxexam-karaf), that simplify the configuration of an integration with ServiceMix.
* _dm-bundle_ - Contains a SpringDM bundle with a Camel route to be tested. Includes a [pax-exam](http://team.ops4j.org/wiki/display/paxexam/Pax+Exam) integration test for the bundle to check that it deploys as expected.
* _dm-features_ - Defines a `features.xml` which deploys the `dm-bundle`; tests the feature as a [pax-exam](http://team.ops4j.org/wiki/display/paxexam/Pax+Exam) integration test.

Prerequisites
=============

When building the project for the first time, run `$> mvn install` in `servicemix-test-utils` before building the top-level projects.

Approach
========

Integration tests are executed by the [maven-failsafe-plugin](http://maven.apache.org/plugins/maven-failsafe-plugin/) at the `integration-test` phase, which is run after packaging but before deployment; any project which fails at this stage will not have its artifacts deployed. 

The convention applied is the default of the plugin, which defines integration tests in the `src/test/java` directory, with names matching `*ITCase.java`. This avoids clashes with the standard JUnit conventions (`*Test.java`) applied to unit tests via the [maven-surefire-plugin](http://maven.apache.org/plugins/maven-surefire-plugin/). Build artifacts are sourced from the `target` directory.

The `dm-bundle` project defines a number of files of interest that provide maximum reuse of the core source artifacts, while enabling ease of testing:

* the `src/main/resources/META-INF/spring` is automatically scanned by the SpringDM framework when the bundle is deployed in a container for any files matching `*.xml`; these are then treated as standard Spring `beans` definitions. The project defines two files (this definition is a standard way of doing things):
    * `dm-bundle-context.xml` - this is a standard Spring file with a Camel context that defines a route. Spring `${}` property placeholders are used, but the file intentionally does not define a `PropertyPlaceHolderConfigurer`.
    * `dm-bundle-osgi-context.xml` - this defines the SpringDM OSGi bits (defined via the `osgi` and `osgix` namespaces). When loaded into a container, this will provide all of the necessary hooks into the configuration service and service registry.
* the `src/test/resources/` directory is used to define configuration that will be used by unit tests only. It contains:
    * `dm-bundle-test-context.xml` - a substitute for `META-INF/spring/dm-bundle-osgi-context.xml`; to be used in conjunction with `META-INF/spring/dm-bundle-context.xml`. It defines a `PropertyPlaceHolderConfigurer` that will fill in the `${}` placeholders.
    * `test.properties` - the properties used to fill those placeholders.