package ru.kafedra.util;

import ru.kafedra.model.User;

public class SessionManager {
    private static User _currentUser;

    public static void setCurrentUser(User user) { _currentUser = user; }
    public static User getCurrentUser() { return _currentUser; }
    public static void clear() { _currentUser = null; }
}