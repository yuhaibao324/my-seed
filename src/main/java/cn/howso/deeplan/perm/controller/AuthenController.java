package cn.howso.deeplan.perm.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.howso.deeplan.framework.exception.BusinessException;
import cn.howso.deeplan.framework.model.R;
import cn.howso.deeplan.log.annotation.LogAnno;
import cn.howso.deeplan.perm.constant.Const;
import cn.howso.deeplan.perm.model.User;
import cn.howso.deeplan.perm.service.UserService;
import cn.howso.deeplan.util.WebUtils;

@Controller
@RequestMapping("")
public class AuthenController {

    public AuthenController() {
        System.out.println("AuthenController");
    }

    @Resource
    private DefaultWebSessionManager sessionManager;
    @Resource
    private UserService userService;

    /*
     * @RequestMapping(value = "login", method = RequestMethod.GET)
     * @LogAnno public String login() { return "redirect:static/login/login.html"; }
     */
    /*
     * @RequestMapping(value = "/forward/index", method = RequestMethod.GET) public String index() { return
     * "static/index"; }
     */

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @LogAnno(ignore = "password")
    @ResponseBody
    public R login(User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        // Store the attributes so we can copy them to the new session after auth.
        final LinkedHashMap<Object, Object> attributes = new LinkedHashMap<Object, Object>();
        final Collection<Object> keys = session.getAttributeKeys();
        for (Object key : keys) {
            final Object value = session.getAttribute(key);
            if (value != null) {
                attributes.put(key, value);
            }
        }
        session.stop();
        subject.login(token);
        // Restore the attributes.
        session = subject.getSession();
        for (final Object key : attributes.keySet()) {
            session.setAttribute(key, attributes.get(key));
        }
        User u = userService.queryByName(user.getName());
        session.setAttribute(Const.SESSION_USER_KEY, u);
        // WebUtils.sendRedirect(request, response, "/",session.getId().toString());
        R r = R.ok("登录成功");
        r.put("sid", session.getId());
        return r;
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    @LogAnno
    public R logout(User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        SecurityUtils.getSubject().logout();
        // WebUtils.sendRedirect(request, response, "/index");
        R r = R.ok("注销成功");
        return r;
    }

}
