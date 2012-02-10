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
package com.fusesource.examples.dm_bundle;

import java.util.concurrent.TimeUnit;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Camel route unit test.
 * 
 * @author jkorab
 */
public class CamelMockedRouteTest extends CamelSpringTestSupport {

	@EndpointInject(uri = "activemq:in")
	private ProducerTemplate inbox;
	
	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/dm-bundle-context.xml", "dm-bundle-test-context.xml");
	}

	@Test
	public void testRespondToMessageUsingMocks() throws Exception {
	    context.getRouteDefinition("respondToMessage").adviceWith(context, new RouteBuilder() {
	        @Override
	        public void configure() throws Exception {
	            // intercept sending to mock:foo and do something else
	            interceptSendToEndpoint("activemq:out")
	                    .skipSendToOriginalEndpoint()
	                    .to("mock:out");
	        }
	    });
		MockEndpoint mockOut = getMockEndpoint("mock:out");
		mockOut.expectedMessageCount(1);

		inbox.sendBody("foo");
		mockOut.await(3, TimeUnit.SECONDS);
		mockOut.assertIsSatisfied();
	}

}
