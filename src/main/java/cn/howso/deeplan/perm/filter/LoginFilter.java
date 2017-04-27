package cn.howso.deeplan.perm.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import cn.howso.deeplan.util.WebUtils;

public class LoginFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object arg2) throws Exception {
        //已登录或者是登录请求则允许访问
        if (SecurityUtils.getSubject().getPrincipal() != null || isLoginRequest(req, resp)) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest req, ServletResponse resp) throws Exception {
        HttpServletResponse response = (HttpServletResponse) resp; 
        HttpServletRequest request = (HttpServletRequest) req;
        if (WebUtils.isAjax(request)) {
            response.setHeader("Status-Code", "500");
            response.getWriter().print("未登录");
        } else {
            this.saveRequestAndRedirectToLogin(req, resp);
        }
        return false;
    }

}