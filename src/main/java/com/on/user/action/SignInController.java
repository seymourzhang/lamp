package com.on.user.action;

import com.alibaba.fastjson.JSONObject;
import com.on.user.entity.OnAUser;
import com.on.user.service.OnAUserService;
import com.on.util.action.BaseAction;
import com.on.util.common.Const;
import com.on.util.common.PageData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@SpringBootApplication
@RequestMapping("user")
public class SignInController extends BaseAction {

    @Autowired
    private OnAUserService onAUserService;

    @RequestMapping("/userLogin")
    public void userLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject json = new JSONObject();
        PageData pd = this.getPageData();
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        String userCode = pd.get("user_name").toString();
        String password = pd.get("user_password").toString();
        OnAUser verify = onAUserService.findByName(userCode);
        session.setAttribute("user", verify);
        if (verify == null) {
            json.put("Result", "Fail");
            json.put("Error", "用户不存在！");
        } else {
            if (!password.equals(verify.getPassword())) {
                json.put("Result", "Fail");
                json.put("Error", "用户名或密码有误！");
            } else {
                session.setAttribute(Const.SESSION_USER, verify);
                json.put("Result", "Success");
                json.put("Error", "");
            }
        }
        super.writeJson(json, response);
    }
}
