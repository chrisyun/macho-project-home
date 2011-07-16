package com.ibm.tivoli.icbc.probe.http;

import java.net.URL;

import com.ibm.tivoli.icbc.result.http.PageElementResult;

public interface HtmlElementDetector {

  public abstract PageElementResult detect(String html, String baseUri) throws Exception;

  //public abstract PageElementResult detect(URL url, int timeout) throws Exception;

}