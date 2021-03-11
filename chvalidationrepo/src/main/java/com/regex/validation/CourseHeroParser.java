package com.regex.validation;

import java.util.HashSet;

public class CourseHeroParser {

  public ChPackage parseCourseHeroString(String input) throws Exception {

    if (input == null || input == "") {
      throw new Exception("Input is null/bank");
    }

    ChPackage pkg = new ChPackage();

    // Pull out department
    // Rule -> one or more alpha-numeric characters in beginning
    // Followed by a separator or numeric character
    String input1 = this.setDepartment(input, pkg);

    // Pull out course number
    // Rule -> one or more numberic characters
    // Followed by a space
    String input2 = this.setCourseNumber(input1, pkg);

    // Pull out both semester and year
    this.setSemesterYear(input2, pkg);

    return pkg;
  }

  private String setDepartment(String input, ChPackage chPackage) throws Exception {

    String deptString = "";

    char[] chars = input.toCharArray();

    HashSet<Character> delimiterSet = new HashSet<>();
    delimiterSet.add(' ');
    delimiterSet.add('-');
    delimiterSet.add(':');

    int index = 0;
    for (char a : chars) {

      // if character is alphanumeric, increment
      if (Character.isAlphabetic(a)) {
        index++;
        continue;
      } else {

        // character should be numeric or separator
        if (delimiterSet.contains(a) || Character.isDigit(a)) {
          break;
        }
      }
    }

    // if no numbers, throw exception
    if (index == 0) {
      throw new Exception("Department should be >= 1 alphabetic characters");
    }

    String deparment = input.substring(0, index);
    chPackage.setDepartment(deparment);

    int length = input.length();
    return input.substring(index, length);
  }

  private String setCourseNumber(String input, ChPackage chPackage) throws Exception {

    HashSet<Character> delimiterSet = new HashSet<>();
    delimiterSet.add(' ');
    delimiterSet.add('-');
    delimiterSet.add(':');

    char[] chars = input.toCharArray();

    if (chars.length == 0) {
      throw new Exception("No course detected");
    }

    int index = 0;
    int startindex = 0;
    for (char a : chars) {

      if (delimiterSet.contains(a) && index == 0) {

        startindex++;
        index++;

        /*  // detect end of course
        if(Character.isSpaceChar(a)) {
            break;
        }
        throw new Exception("Additional delimiter present besides space -> " + a);*/
      }

      /*else if (delimiterSet.contains(a)) {
          // skipping delimiter
          index++;
          startindex++;
      }*/

      else if (Character.isDigit(a)) {
        index++;
      } else if (Character.isSpaceChar(a)) {
        // end of course
        break;
      } else if (Character.isAlphabetic(a)) {
        throw new Exception("Course number can't contain alphabet -> " + a);
      }
    }

    if (index == 0) {
      throw new Exception("Course should be >= 1 numeric character");
    }

    int length = input.length();
    String courseString = input.substring(startindex, index);
    chPackage.setCourseNumber(Integer.parseInt(courseString));
    return input.substring(index, length);
  }

  private void setSemesterYear(String input, ChPackage chPackage) throws Exception {

    if (!input.startsWith(" ")) {
      throw new Exception("Course/Dept and sem/year are NOT separated by a space");
    }

    char[] chars = input.substring(1).toCharArray();

    if (chars.length == 0) {
      throw new Exception("No semester or year detected");
    }

    String year = "";

    if (Character.isAlphabetic(chars[0])) {

      int index = 1;

      // pull semester and then year
      for (char a : chars) {

        if (Character.isAlphabetic(a)) {

          index++;
        } else if (Character.isDigit(a) || Character.isSpaceChar(a)) {
          // end of semester
          break;
        } else {
          throw new Exception("Invalid character detected -> " + a);
        }
      }

      String semesterString = input.substring(1, index);
      chPackage.setSemester(semesterString);

      // Set year now
      int length = input.length();

      year = input.substring(index, length);

      if(year.startsWith(" ")) {
        year = year.substring(1);
      }
      try {
        chPackage.setYear(Integer.parseInt(year));
      } catch (Exception ex) {
        throw new Exception("Issue parsing year in string " + year);
      }

    } else if (Character.isDigit(chars[0])) {

      int index = 1;

      // pull semester and then year
      for (char a : chars) {

        if (Character.isDigit(a)) {

          index++;
        } else if (Character.isAlphabetic(a) || Character.isSpaceChar(a)) {
          // end of year
          break;
        } else {
          throw new Exception("Invalid character detected -> " + a);
        }
      }

      year = input.substring(1, index);
      try {
        chPackage.setYear(Integer.parseInt(year));
      } catch (Exception ex) {
        throw new Exception("Issue parsing year in string " + year);
      }

      /*// validate semester and year
      if(!(year.length() == 2 || year.length() == 4)) {
          throw new Exception("Invalid length for year" + year);
      }*/

      int length = input.length();
      String semesterString = input.substring(index, length);
      if(semesterString.startsWith(" ")) {
        semesterString = semesterString.substring(1);
      }
      chPackage.setSemester(semesterString);
    }
    else {
        throw new Exception("Semester/ Year should start with valid character( digit/ alphanumeric) ");
    }


    // validate semester and year
    if (!(year.length() == 2 || year.length() == 4)) {
      throw new Exception("Invalid length for year" + year);
    }

    HashSet<String> semesterSet = new HashSet<>();
    semesterSet.add("F");
    semesterSet.add("W");
    semesterSet.add("S");
    semesterSet.add("SU");
    semesterSet.add("FALL");
    semesterSet.add("WINTER");
    semesterSet.add("SPRING");
    semesterSet.add("SUMMER");

    String semesterUpper = (chPackage.getSemester()).toUpperCase();
    if (!semesterSet.contains(semesterUpper)) {
      throw new Exception("Semester is invalid -> " + chPackage.getSemester());
    }
  }
}
