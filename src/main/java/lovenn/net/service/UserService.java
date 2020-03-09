package lovenn.net.service;

import lovenn.net.domian.User;

public interface UserService {

    User getUser(String name);

    void login(User user);

    boolean isLogin(String name);
}
