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
