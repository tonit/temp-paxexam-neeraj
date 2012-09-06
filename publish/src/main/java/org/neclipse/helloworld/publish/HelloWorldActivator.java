package org.neclipse.helloworld.publish;

import org.neclipse.helloworld.service.HelloWorldService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class HelloWorldActivator implements BundleActivator {

	public void start(BundleContext bundleContext) throws Exception {
		registerService(bundleContext);
	}

	private void registerService(BundleContext bundleContext) {
        System.out.println("Registering service: " +  HelloWorldService.class.getName());
        bundleContext.registerService(HelloWorldService.class, new HelloWorldServiceImpl(), null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
	}
}
