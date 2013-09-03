package com.ibm.tivoli.cars;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import junit.framework.TestCase;

public class DateFormatTest extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testCase1() throws Exception {
    // 2010-10-25T05:46:03.281193Z
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    calendar.setTime(new Date());
    // Convert GMT TimeZone
    Date ts = calendar.getTime();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SSS");
    SimpleDateFormat timezoneFormat = new SimpleDateFormat("Z");

    String tz = timezoneFormat.format(ts);
    tz = String.format("%s:%s", tz.substring(0, 3), tz.substring(3));
    String creationTime = dateFormat.format(ts) + "T" + timeFormat.format(ts) + tz;
    assertEquals("", creationTime);
  }

}
