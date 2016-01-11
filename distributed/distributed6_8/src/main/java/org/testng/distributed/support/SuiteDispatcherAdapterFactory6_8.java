package org.testng.distributed.support;

import org.osgi.framework.VersionRange;
import org.testng.distributed.AbstractSuiteDispatcherAdapterFactory;

import com.google.auto.service.AutoService;

@AutoService(SuiteDispatcherAdapterFactory.class)
public class SuiteDispatcherAdapterFactory6_8 extends AbstractSuiteDispatcherAdapterFactory {

  private static final VersionRange RANGE = new VersionRange("[6.8.1,6.9.7)");

  @Override
  public SuiteDispatcherAdapter createSuiteDispatcherAdapter() {
    return new SuiteDispatcherAdapter6_8();
  }

  @Override
  protected VersionRange getAcceptableVersions() {
    return RANGE;
  }

}
