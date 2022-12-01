package by.bsuir.lab3.server.service;

import by.bsuir.lab3.server.model.AuthType;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private static final AuthService INSTANCE = new AuthService();

    private final Map<SocketAddress, AuthType> users;

    private AuthService() {
        users = new HashMap<>();
    }

    public static AuthService getInstance() {
        return INSTANCE;
    }

    public AuthType getAuthType(SocketAddress user) {
        if (!users.containsKey(user)) {
            users.put(user, AuthType.UnAUTH);
        }

        return users.get(user);
    }

    public void setAuthType(SocketAddress user, AuthType type) {
        users.put(user, type);
    }
}
