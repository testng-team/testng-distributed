package org.testng.distributed;

import com.beust.jcommander.Parameter;

public class DistributedArgs {

  public static final String MASTER = "-master";
  @Parameter(names = MASTER, description = "Host where the master is", hidden = true)
  public String master;

  public static final String SLAVE = "-slave";
  @Parameter(names = SLAVE, description = "Host where the slave is", hidden = true)
  public String slave;

}
