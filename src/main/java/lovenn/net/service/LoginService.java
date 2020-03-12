package lovenn.net.service;

import lovenn.net.domian.Login;

public interface LoginService {

    /**
     * 检查是否登录
     */
    String checkLoginStatus(Login login);

    /**
     * 更新登录状态
     */
    void updateLoginStatus(Login login);
}
