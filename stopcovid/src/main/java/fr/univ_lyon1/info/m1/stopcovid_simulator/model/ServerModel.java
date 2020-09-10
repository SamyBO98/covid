package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import java.util.ArrayList;

public class ServerModel {
    private final ArrayList<UserModel> users;

    /**
     * Constructor.
     * `users` : New instance.
     */
    public ServerModel() {
        users = new ArrayList<>();
    }

    /**
     * Create a new `UserModel` and add it to `users`.
     *
     * @return The `UserModel` created
     */
    public UserModel createUser() {
        var user = new UserModel();
        users.add(user);

        return user;
    }
}
