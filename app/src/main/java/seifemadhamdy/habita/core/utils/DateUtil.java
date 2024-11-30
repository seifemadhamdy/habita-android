package seifemadhamdy.habita.core.utils;

import java.time.LocalDate;

public class DateUtil {
  public static String getCurrentDate() {
    return LocalDate.now().toString();
  }
}
