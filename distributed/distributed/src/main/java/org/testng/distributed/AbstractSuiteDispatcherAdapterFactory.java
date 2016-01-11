package org.testng.distributed;

import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;
import org.testng.distributed.support.SuiteDispatcherAdapterFactory;

public abstract class AbstractSuiteDispatcherAdapterFactory implements SuiteDispatcherAdapterFactory {

  @Override
  public boolean accept(Version version) {
    return version == null || getAcceptableVersions().includes(version);
  }

  protected abstract VersionRange getAcceptableVersions();
}
