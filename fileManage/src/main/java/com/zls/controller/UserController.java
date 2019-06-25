package com.zls.controller;

import com.zls.domain.User;
import com.zls.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @RequestMapping("/registUser")
    @ResponseBody
    public String  registUser(@Valid User user){
        System.out.println(user);
        userService.registUser(user);
        return "OK";
    }

    @RequestMapping("/checkLogin")
    @ResponseBody
    public String checkLogin(HttpServletRequest request, @Valid User user){


        System.out.println("-----------------------------------------------------------------");
        System.out.println(user);
        System.out.println("11"+request);
        User user1 = userService.checkLogin(user);
        if (user1 != null)
        {
            request.getSession().setAttribute("user",user1);
            request.getSession().setMaxInactiveInterval(1600);
            return "OK";
        }
        return "NO";
    }

    @RequestMapping("/test")
    public void test(){
        System.out.println(111);
    }

}
