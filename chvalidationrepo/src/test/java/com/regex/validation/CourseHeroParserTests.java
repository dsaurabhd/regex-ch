package com.regex.validation;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.assertTrue;

public class CourseHeroParserTests {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void checkCourseDept() throws Exception {
        CourseHeroParser parser = new CourseHeroParser();
        ChPackage chPackage = parser.parseCourseHeroString("CS111 2016 Fall");
        assertTrue( chPackage.getDepartment().equals("CS") );
        assertTrue( chPackage.getCourseNumber() == 111 );

        ChPackage chPackage2 = parser.parseCourseHeroString("CS-111 Fall 2016");
        assertTrue( chPackage2.getDepartment().equals("CS") );
        assertTrue( chPackage2.getCourseNumber() == 111 );

        ChPackage chPackage3 = parser.parseCourseHeroString("CS 111 F2016");
        assertTrue( chPackage3.getDepartment().equals("CS") );
        assertTrue( chPackage3.getCourseNumber() == 111 );
    }

   /* @ParameterizedTest
    @ValueSource(strings = {"CS111 2016 Fall", "CS-111 Fall 2016", "CS 111 F2016"})
    void parseStrings(String input) throws Exception {
        CourseHeroParser parser = new CourseHeroParser();
        ChPackage chPackage = parser.parseCourseHeroString("input");
    }*/
}
