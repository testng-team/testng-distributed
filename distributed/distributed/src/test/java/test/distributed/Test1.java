package test.distributed;

import java.io.Serializable;

import org.testng.annotations.Test;

public class Test1 implements Serializable {
  @Test
  public void f1() {
//    ppp("f1");
  }

  private void ppp(String s) {
    System.out.println("[Test1] " + s);
  }
}
