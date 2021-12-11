package com.api.helper;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public final class Common {
  public static Date time(int time) {
    Calendar c = Calendar.getInstance();
    c.setTime(new Date());
    c.add(Calendar.MINUTE, time);
    return c.getTime();
  }

  public static String generateString(int length) {
    byte[] array = new byte[length];
    new Random().nextBytes(array);
    String generatedString = new String(array, Charset.forName("UTF-8"));
    return generatedString;
  }

  public static Date toDate(LocalDateTime ldt) {
    return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
  }

  public static String getRandom() {
    Random rnd = new Random();
    int number = rnd.nextInt(999999);
    return String.format("%06d", number);
}
}
