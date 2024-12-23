package org.lms.course;

import java.util.ArrayList;

public class CourseDB {
    public static ArrayList<Course> courseList = new ArrayList<>() {
        {
            add(new Course(1, "Course 1", "Description 1", 10, new ArrayList<Integer>() {
                {
                }
            }, new ArrayList<Integer>() {
                {
                    add(1);
                    add(2);
                    add(3);
                }
            }, new ArrayList<Integer>() {
                {
                    add(1);
                    add(2);
                    add(3);
                }
            }, new ArrayList<Integer>() {
                {
                    add(1);
                    add(2);
                    add(3);
                }
            }, new ArrayList<Integer>() {
                {
                }
            }, new ArrayList<Integer>() {
                {
                    add(1);
                }
            }));
        }
    };

}
