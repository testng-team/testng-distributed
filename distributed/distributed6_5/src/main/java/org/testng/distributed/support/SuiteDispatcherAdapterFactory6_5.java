package org.testng.distributed.support;

import org.osgi.framework.VersionRange;
import org.testng.distributed.AbstractSuiteDispatcherAdapterFactory;

import com.google.auto.service.AutoService;

@AutoService(SuiteDispatcherAdapterFactory.class)
public class SuiteDispatcherAdapterFactory6_5 extends AbstractSuiteDispatcherAdapterFactory {

  private static final VersionRange RANGE = new VersionRange("[6.5.1,6.8.1)");

  @Override
  public SuiteDispatcherAdapter createSuiteDispatcherAdapter() {
    return new SuiteDispatcherAdapter6_5();
  }

  @Override
  protected VersionRange getAcceptableVersions() {
    return RANGE;
  }

}
