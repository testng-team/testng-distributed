package org.testng.distributed;

import java.util.Arrays;
import java.util.List;

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

  private String m_slavefileName = null;
  private String m_masterfileName = null;

  public static void main(String[] args) throws ParameterException {
    CommandLineArgs cla = new CommandLineArgs();
    DistributedArgs distArgs = new DistributedArgs();
    RemoteArgs ra = new RemoteArgs();
    new JCommander(Arrays.asList(cla, ra, distArgs), args);
    if (ra.version != null) {
      System.setProperty(VERSION, ra.version.toString());
    }
    DistributedTestNG distributedTestNg = new DistributedTestNG();
    initAndRun(distributedTestNg, args, cla, distArgs);
  }

  private static void initAndRun(DistributedTestNG distributedTestNg, String[] args, CommandLineArgs cla,
      DistributedArgs distArgs) {
    if (distArgs.slave != null && distArgs.master != null) {
      throw new ParameterException(DistributedArgs.SLAVE + " can't be combined with " + DistributedArgs.MASTER);
    }

    distributedTestNg.setMaster(distArgs.master);
    distributedTestNg.setSlave(distArgs.slave);
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
  public void setMaster(String fileName) {
    m_masterfileName = fileName;
  }

  /**
   * Specify if this run should be in Master-Slave mode as slave
   *
   * @param fileName
   *          remote.properties path
   */
  public void setSlave(String fileName) {
    m_slavefileName = fileName;
  }
}
