package com.company.hrm.action;

import com.company.hrm.common.ResResult;
import com.company.hrm.common.ServiceConst;
import com.company.hrm.common.SpringIOC;
import com.company.hrm.dao.pojo.User;
import com.company.hrm.service.iService.IUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class UserAction {
    @Autowired
    private IUserService userService;
    @RequestMapping("UserRegistServlet.do")
    public @ResponseBody ResResult UserRegist(String username,String password) throws JsonProcessingException {
        boolean flag = userService.isExist(username);
        User user = (User) SpringIOC.getCtx().getBean("user");
        user.setUsername(username);
        user.setUserpassword(password);
        user.setPriority(ServiceConst.DEFAULT_PRIORITY);
        String msg = userService.regist(user);
        boolean flag2 = msg.equals("success");
        ResResult result = flag2?ResResult.success():ResResult.error(500,"user regist error");
        return result;
    }
    @RequestMapping("UserRegisterExistServlet.do")
    public @ResponseBody ResResult UserRegisterExist(String username) throws JsonProcessingException {
        boolean flag = userService.isExist(username);
        ResResult result = flag?ResResult.error(404,"user already regist"):ResResult.success();
        return result;
    }
    @RequestMapping(value = "UserLoginServlet.do",method = RequestMethod.POST)

    public @ResponseBody ResResult UserLogin(HttpSession session, String username, String password) throws IOException {
        System.out.println(username);
        System.out.println(password);
        User user = userService.login(username, password);
        ResResult<User> result = null;
        if(user != null){
            session.setAttribute("username", user.getUsername());
            result = ResResult.success("login success", user);
        }else{
            result = ResResult.error(500, "password error");
        }
        return result;
    }
    @RequestMapping("UserExistServlet.do")

    public @ResponseBody ResResult UserExist(String username) throws JsonProcessingException {
        boolean flag = userService.isExist(username);
        ResResult result = flag?ResResult.success():ResResult.error(404,"no such user");
        return result;
    }
}
