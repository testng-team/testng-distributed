package org.testng.distributed.support;

import org.osgi.framework.Version;

public interface SuiteDispatcherAdapterFactory {

  boolean accept(Version version);

  SuiteDispatcherAdapter createSuiteDispatcherAdapter();
}
