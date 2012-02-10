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
package com.fusesource.examples.dm_features;
import static com.fusesource.examples.testUtils.SmxMavenIntegrationTestSupport.featuresPath;
import static com.fusesource.examples.testUtils.SmxMavenIntegrationTestSupport.servicemixDistributionConfiguration;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.scanFeatures;

import javax.inject.Inject;

import com.fusesource.examples.dm_bundle.Hello;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;

/**
 * Feature-specific integration test.
 * 
 * @author jkorab
 */
@RunWith(JUnit4TestRunner.class)
public class FeatureITCase {
	@Inject
	private Hello helloService;

	@Test
	public void testHello() throws Exception {
		Assert.assertNotNull(helloService);
		Assert.assertTrue(helloService.hello().startsWith("Hi from Camel"));
	}

	@Configuration
	public Option[] config() {
		return options(servicemixDistributionConfiguration(),
				scanFeatures(featuresPath(), "dm-bundle"));
	}
}
