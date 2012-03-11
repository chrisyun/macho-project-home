package com.npower.dm.export;

import java.io.IOException;
import java.io.Writer;

public abstract class BaseXMLWriter {

  protected Writer writer = null;

  public BaseXMLWriter() {
    super();
  }

  public BaseXMLWriter(Writer writer) {
    super();
    this.writer = writer;
  }

  public Writer getWriter() {
    return writer;
  }

  public void setWriter(Writer writer) {
    this.writer = writer;
  }

  public  void write(String str) throws IOException {
    if (writer != null) {
      if (str != null ) {
        this.writer.write(str);
      }else {
        this.writer.write("");
      }
    
    }  
  }
}