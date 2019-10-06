package com.cbx.controller;

import com.cbx.biz.GloabalBiz;
import com.cbx.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class GlobalController {
    @Autowired
    private GloabalBiz gloabalBiz;
    @RequestMapping("/to_login")
    public String toLogin(){
//        去登陆界面
        return "login";
    }
    @RequestMapping("/login")
    public String login(HttpSession session, @RequestParam String sn, @RequestParam String password){
        Employee employee=gloabalBiz.login(sn,password);
        if(employee==null){
              return "redirect:to_login";
        }
        session.setAttribute("employee",employee);
        return "redirect:self";
    }
    @RequestMapping("/self")
    public String self(){
              return "self";
    }
//    退出
    @RequestMapping("/quit")
    public String quit(HttpSession session){
        session.setAttribute("employee",null);
        return "redirect:self";
    }
//  修改密码
@RequestMapping("/to_change_password")
public String toChangePassword(){
    return "change_password";
}
    @RequestMapping("/change_password")
    public String toChangePassword(HttpSession session,@RequestParam String old,@RequestParam String new1,@RequestParam String new2){
            Employee employee=(Employee)session.getAttribute("employee");
            if(employee.getPassword().equals(old)){
                      if(new1.equals(new2)){
                          employee.setPassword(new1);
                          gloabalBiz.changePassword(employee);
                          return "redirect:self";
                      }
            }
        return "redirect:to_change_password";
    }
}
