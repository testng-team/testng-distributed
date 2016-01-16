package test.distributed;

import java.io.Serializable;

import org.testng.annotations.Test;

public class Test2 implements Serializable {
  @Test
  public void f2() {
//    ppp("f2");
  }

  private void ppp(String s) {
    System.out.println("[Test2] " + s);
  }
}
