/*
 * Copyright 2012 FuseSource
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fusesource.examples.testUtils;

import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.CoreOptions.maven;

import org.openengsb.labs.paxexam.karaf.options.KarafDistributionBaseConfigurationOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Support class for providing ServiceMix pax-exam configuration; configured via
 * properties set in the maven-failsafe-plugin.
 * 
 * @author jkorab
 */
public abstract class SmxMavenIntegrationTestSupport {

	// properties expected to be configured within the maven-failsafe-plugin
	private static final String FAILSAFE_BUILD_ARTIFACT_NAME = "failsafe.buildArtifactName";
	private static final String FAILSAFE_BUILD_DIRECTORY = "failsafe.buildDirectory";
	private static final String FAILSAFE_KARAF_VERSION = "failsafe.karafVersion";

	public static Logger log = LoggerFactory.getLogger(SmxMavenIntegrationTestSupport.class);

	/**
	 * Fetches a Karaf configuration option preconfigured to use the ServiceMix
	 * instance defined in the project POM.
	 * 
	 * @return KarafDistributionBaseConfigurationOption
	 */
	public static KarafDistributionBaseConfigurationOption servicemixDistributionConfiguration() {
		String karafVersion = System.getProperty(FAILSAFE_KARAF_VERSION);
		log.info("Karaf version: " + karafVersion);
		return karafDistributionConfiguration()
				.frameworkUrl(
						maven().groupId("org.apache.servicemix").artifactId("apache-servicemix").type("zip")
								.versionAsInProject()).karafVersion(karafVersion).name("Apache Servicemix");
	}

	/**
	 * Obtains a <code>file:</code> path to the standard location of the
	 * <code>features.xml</code> file.
	 * 
	 * @return A path
	 */
	public static String featuresPath() {
		String featuresPath = "file:" + System.getProperty(FAILSAFE_BUILD_DIRECTORY) + "/features/features.xml";
		log.info("Features path: " + featuresPath);
		return featuresPath;
	}

	/**
	 * Obtains a <code>file:</code> path to the build artifact of the project
	 * under test.
	 * 
	 * @return A path
	 */
	public static String bundlePath() {
		String bundlePath = "file:" + System.getProperty(FAILSAFE_BUILD_DIRECTORY)
				+ System.getProperty(FAILSAFE_BUILD_ARTIFACT_NAME);
		log.info("Bundle path: " + bundlePath);
		return bundlePath;
	}
}