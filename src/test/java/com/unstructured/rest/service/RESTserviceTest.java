package com.unstructured.rest.service;

import org.testng.annotations.Test;

public class RESTserviceTest {

  @Test
  public void getStatus() {
    RESTservice restservice = new RESTservice();
    System.out.print(restservice.getRouteList());
  }
}
