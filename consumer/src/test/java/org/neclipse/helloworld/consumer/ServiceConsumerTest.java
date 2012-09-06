package org.neclipse.helloworld.consumer;

import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neclipse.helloworld.service.HelloWorldService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.junit.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.*;

@RunWith( JUnit4TestRunner.class )
@ExamReactorStrategy( AllConfinedStagedReactorFactory.class )
public class ServiceConsumerTest {

    @Configuration
    public Option[] config()
    {
        return options(
            junitBundles(),
            mavenBundle().groupId( "org.neclipse.helloworld" )
                .artifactId( "publish" ).version( "0.0.1-SNAPSHOT" ),

            mavenBundle().groupId( "org.neclipse.helloworld" )
                .artifactId( "service" ).version( "0.0.1-SNAPSHOT" )
        );
    }

    @Inject
    BundleContext ctx;

    @Test
    public void withBC()
        throws Exception
    {
        assertThat( ctx, is( notNullValue() ) );
        System.out.println( "BundleContext of bundle injected: "
                            + ctx.getBundle().getSymbolicName()
        );

        fetchService( ctx );

        // Iterating through the provisioned/installed bundles
        for( Bundle bundle : ctx.getBundles() ) {
            System.out.println( "State: " + bundle.getState() + ", ID:" + bundle.getSymbolicName() );
        }
    }

    private void fetchService(BundleContext c)
        throws InterruptedException
    {
        // be somewhat graceful. Should use ServiceTracker e.g. instead.
        Thread.sleep( 1000 );

        ServiceReference reference = c.getServiceReference( HelloWorldService.class.getName() );
        if( reference != null ) {
            System.out.println( "Service bundle: " + reference.getBundle().getSymbolicName() );
        }
        else {
            fail("Service not Found!");
        }
    }

}
