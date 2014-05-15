package com.tutucha.event;

public class SimpleEventListener implements EventListener {

  public SimpleEventListener() {
    super();
  }

  public void fire(AccessEvent event) {
    System.err.println(event);
  }

}
