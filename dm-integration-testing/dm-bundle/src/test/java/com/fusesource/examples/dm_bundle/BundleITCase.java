package com.fusesource.examples.dm_bundle;

import static com.fusesource.examples.testUtils.SmxMavenIntegrationTestSupport.bundlePath;
import static com.fusesource.examples.testUtils.SmxMavenIntegrationTestSupport.servicemixDistributionConfiguration;
import static org.ops4j.pax.exam.CoreOptions.bundle;
import static org.ops4j.pax.exam.CoreOptions.options;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests that a bundle can be deployed by verifying that one of its services is responding.
 * 
 * @author jkorab
 */
@RunWith(JUnit4TestRunner.class)
public class BundleITCase {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Inject
	private Hello helloService;
	
	@Inject
	private BundleContext bundleContext;

	@Test
	public void testBlueprintServiceAvailable() throws Exception {
		Assert.assertNotNull(helloService);
		Assert.assertTrue(helloService.hello().startsWith("Hi from Camel"));
	}

	/**
	 * Verifies that the bundle has been installed and is in the
	 * {@link Bundle#ACTIVE} state.
	 */
	@Test
	public void testBundleSucessfullyInstalled() {
		assertBundleActive("dm-bundle"); // artifactId of the current project
	}

	/**
	 * Asserts that the named bundle is registered with the container's
	 * {@link BundleContext} and is in the {@link Bundle#ACTIVE} state.
	 * 
	 * @param bundleName
	 *            The symbolic name of the bundle to be looked up.
	 */
	protected void assertBundleActive(String bundleName) {
		Bundle[] bundles = bundleContext.getBundles();
		boolean found = false;
		boolean active = false;

		for (Bundle bundle : bundles) {
			if (bundle.getSymbolicName().equals(bundleName)) {
				found = true;
				if (bundle.getState() == Bundle.ACTIVE) {
					active = true;
				}
				break;
			}
		}
		Assert.assertTrue(bundleName + " not found in container", found);
		Assert.assertTrue(bundleName + " not active", active);
	}

	@Configuration
	public Option[] config() {
		return options(servicemixDistributionConfiguration(), 
				bundle(bundlePath()));
	}
}