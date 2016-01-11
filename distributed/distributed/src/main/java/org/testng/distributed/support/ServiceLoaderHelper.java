package org.testng.distributed.support;

import org.osgi.framework.Version;
import org.testng.TestNGException;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public final class ServiceLoaderHelper {

    private ServiceLoaderHelper() {}

    public static SuiteDispatcherAdapterFactory getFirst(Version version) {
        List<SuiteDispatcherAdapterFactory> factories = new ArrayList<>();
        for (SuiteDispatcherAdapterFactory factory : ServiceLoader.load(SuiteDispatcherAdapterFactory.class)) {
            if (factory.accept(version)) {
                factories.add(factory);
            }
        }
        if (factories.isEmpty()) {
            throw new TestNGException(version + " is not a supported TestNG version");
        }
        if (factories.size() > 1) {
            System.err.println("[ServiceLoaderHelper] More than one working implementation for '" + version + "', we will use the first one");
        }
        return factories.get(0);
    }
}
