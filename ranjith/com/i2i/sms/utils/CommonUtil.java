package com.i2i.sms.utils;


/**
*
* Class implemented to validate the name, number, range of number and upperCase character.
* 
*/
public final class CommonUtil{
    
    private CommonUtil() {
    
    }

    /**
    * <p>
    * It validates whether the given name is valid or not.
    * It checks whether it contains any special characters or numbers rather than lowercase,uppercase letters and spaces.
    * </p>
    *
    * @param dob
    *         Student name given as String.
    *
    * @return Boolean type as true if the given name is valid or else returns false.
    */
    public static boolean isValidString(String str) {
        boolean isValidString = true;
        for(int i=0;i < str.length();i++) {
            if(str.charAt(i) >= 65 && str.charAt(i) <= 90) {
                continue;
            } else if(str.charAt(i) >= 97 && str.charAt(i) <= 122) {
                continue;
            } else if(str.charAt(i) == ' ') {
                continue;
            } else {
                isValidString = false;
                break;
            }
        }
      return isValidString;
    }
  

    /**
    * <p>
    * Validates whether the given positive or negative number is integer or not.
    * </p>
    *
    * @param number
    *        The number to check whether it is integer or not, passed as String.
    *        Both the positive or negative number can be passed to check whether it is integer or not.
    *
    * @return Boolean type as true if number is integer or else false
    */
    public static boolean isValidNumber(String number) {
        boolean validIntegerOrNot = true;
        for(int i=0;i<number.length();i++) {
            if(number.charAt(0)=='-') {
                continue;
            } else {
                if(number.charAt(i) >= 48 && number.charAt(i) <= 57) {
                    continue;
                } else {
                    validIntegerOrNot=false;
                    break;
                }
            }
        }
        return validIntegerOrNot;
    }
   
    /**
    * <p>
    * Validates the given integer is within the given range.
    * The range that have to be checked is passed as second, third parameter as integer.
    * </p>
    *
    * @param number
    *        The integer that has to be checked whether it is in given range, passed as first parameter.
    * @param startRange
    *        The starting range should be given as integer.
    *        The starting range should be less than the end Range.
    *        The starting range will be inclusive.
    * @param endRange
    *        The end range should be given as integer.
    *        The end range should be greater than the end Range.
    *        The end range will be inclusive.
    *
    * @return Boolean type as true if the given integer is within range between start Range and end Range or else false
    *         If start Range is greater than end Range then it returns false
    */
    public static boolean isValidRangeOfNumber(int number, int startRange, int endRange) {
        if(number >= startRange && number <= endRange) {
            return true;
        } else {
            return false;
        }
    }

    /**
    * <p>
    * It validates whether the given character is uppercase alphabetical or not.
    * </p>
    *
    * @param character
    *        The character that need to check whether it uppercase alphabetical or not as character type.
    *
    * @return Boolean type as true if the given character is uppercase alphabetic or else false
    */
    public static boolean isUppercaseCharacter(char character) {  
        if(character >= 65 && character <= 90 ) {
            return true;
        } else {
            return false;
        }
    }
  
}