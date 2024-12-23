package org.lms.lesson;

import org.lms.user.User;

import java.util.ArrayList;

public class LessonDB {
    public static ArrayList<Lesson> Lessons = new ArrayList<Lesson>() {
        {
            add(new Lesson(1, "Lesson 1", 2021, "1234", new ArrayList<User>(), 1));
            add(new Lesson(2, "Lesson 2", 2021, "1234", new ArrayList<User>(), 2));
            add(new Lesson(3, "Lesson 3", 2021, "1234", new ArrayList<User>(), 3));
        }
    };
}
