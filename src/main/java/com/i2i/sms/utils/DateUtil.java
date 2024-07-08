package com.i2i.sms.utils;

import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.Period;

/**
*
* Class implemented to get difference between two dates and to validate the date format.
* 
*/
public final class DateUtil {

  private DateUtil() {
  }

  /**
  * <p>
  * Calculates the difference between the present date and user given date.
  * If you want to calculate difference between two different date, the second date can be passed as second parameter.
  * Pass second parameter as null to calculate the difference between the present date and user given date.
  * </p>
  * @param userDate
  *        Date must be in format of "yyyy-MM-dd" as LocalDate.
  *        StartDate must be lesser than endDate to get years in positive or else it returns years in negative.
  * @param endDate
  *        Date must be in format of "yyyy-MM-dd" as String type.
  *        Pass the second date to calculate difference between two dates, or else pass null to calculate the
  *        difference between present date and user given date.
  * @return Returns number of year as Integer
  */
  public static int getDifferenceBetweenDateByYears(LocalDate userDate, String endDate) {
      
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate presentDate;
      if(endDate == null) {
          presentDate = LocalDate.now();
      } else {
          presentDate = LocalDate.parse(endDate, formatter);
      }
      Period period = Period.between(userDate, presentDate);
      return period.getYears();
  }

   /**
   * <p>
   * Validates the given string is in "dd/MM/yyyy" date format.
   * </p>
   * @param userDate
   *        String passed as parameter to check if it is in correct "dd/MM/yyyy" format.
   * @return Date if given String in "dd/MM/yyyy" format or else null
   */
   public static LocalDate validateDateFormat(String userDate) {
       try {
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
           return LocalDate.parse(userDate, formatter);
       } catch (DateTimeParseException e) {
           return null;
       }
   }
}