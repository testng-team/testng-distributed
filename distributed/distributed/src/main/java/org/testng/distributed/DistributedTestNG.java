package org.testng.distributed;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.osgi.framework.Version;
import org.testng.CommandLineArgs;
import org.testng.ISuite;
import org.testng.TestNG;
import org.testng.remote.RemoteArgs;
import org.testng.remote.SuiteDispatcher;
import org.testng.remote.SuiteSlave;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class DistributedTestNG extends TestNG {
  public static final String VERSION = "testng.version";

  public static Version TESTNG_VERSION;

  private String m_slavefileName = null;
  private String m_masterfileName = null;

  public static void main(String[] args) throws ParameterException {
    CommandLineArgs cla = new CommandLineArgs();
    DistributedArgs distArgs = new DistributedArgs();
    RemoteArgs ra = new RemoteArgs();
    new JCommander(Arrays.asList(cla, ra, distArgs), args);

    DistributedTestNG distributedTestNg = new DistributedTestNG();
    initAndRun(distributedTestNg, args, cla, ra, distArgs);
  }

  private static void initAndRun(DistributedTestNG distributedTestNg, String[] args, CommandLineArgs cla,
      RemoteArgs ra, DistributedArgs distArgs) {
    if (distArgs.slave != null && distArgs.master != null) {
      throw new ParameterException(DistributedArgs.SLAVE + " can't be combined with " + DistributedArgs.MASTER);
    }

    if (ra.version != null) {
      distributedTestNg.setTestngVersion(ra.version);
    }

    distributedTestNg.setMasterPropertiesFile(distArgs.master);
    distributedTestNg.setSlavePropertiesFile(distArgs.slave);

    distributedTestNg.run();
  }

  public DistributedTestNG() {
  }

  @Override
  protected List<ISuite> runSuites() {
    //
    // Slave mode
    //
    if (m_slavefileName != null) {
      SuiteSlave slave = new SuiteSlave(m_slavefileName, this);
      slave.waitForSuites();
      return null;
    }

    //
    // Master mode
    //
    else if (m_masterfileName != null) {
      SuiteDispatcher dispatcher = new SuiteDispatcher(m_masterfileName);
      return dispatcher.dispatch(getConfiguration(), m_suites, getOutputDirectory(), getTestListeners());
    }

    return null;
  }

  /**
   * Specify if this run should be in Master-Slave mode as Master
   *
   * @param fileName
   *          remote.properties path
   */
  public void setMasterPropertiesFile(String fileName) {
    if (fileName != null) {
      try {
        Method setMasterMethod = TestNG.class.getDeclaredMethod("setMaster", String.class);
        if (setMasterMethod != null) {
          setMasterMethod.invoke(this, fileName);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    m_masterfileName = fileName;
  }

  /**
   * Specify if this run should be in Master-Slave mode as slave
   *
   * @param fileName
   *          remote.properties path
   */
  public void setSlavePropertiesFile(String fileName) {
    if (fileName != null) {
      try {
        Method setSlaveMethod = TestNG.class.getDeclaredMethod("setSlave", String.class);
        if (setSlaveMethod != null) {
          setSlaveMethod.invoke(this, fileName);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    m_slavefileName = fileName;
  }
  
  public void setTestngVersion(Version ver) {
    TESTNG_VERSION = ver;
  }
}
