package org.lms.user;

import java.util.ArrayList;
import java.util.List;

import static org.lms.user.User.Role;

public class UserDB {
    public static ArrayList<User> Users = new ArrayList<User>() {
        {
            add(new User(1, "John", "Doe", "1234", "john@gmail.com", Role.ADMIN));
            add(new User(2, "Jane", "Doe", "1234", "jahn@gmail.com", Role.STUDENT));
            add(new User(2, "Jane", "Doe", "1234", "jahn@gmail.com", Role.STUDENT));
        }
    };
}
