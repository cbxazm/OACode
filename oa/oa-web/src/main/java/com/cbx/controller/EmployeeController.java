package com.cbx.controller;

import com.cbx.biz.DepartmentBiz;
import com.cbx.biz.EmployeeBiz;
import com.cbx.entity.Employee;
import com.cbx.global.Contant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
       private DepartmentBiz departmentBiz;
    @Autowired
       private EmployeeBiz  employeeBiz;
    @RequestMapping("/list")
    public String list(Map<String,Object> map){
        map.put("list",employeeBiz.getAll());
        return "employee_list";
    }
    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object> map){
        //在页面会有modelAttribute属性将employee填充
        map.put("employee",new Employee());
//        添加部门信息到页面去
        map.put("dlist",departmentBiz.getAll());
        map.put("plist", Contant.getPosts());
                return "employee_add";
    }
    @RequestMapping("/add")
    public String add(Employee employee){
        employeeBiz.add(employee);
        return "redirect:list";
    }
//    配置params就是请求路径必须带一个sn的值过来
    @RequestMapping(value = "/to_update",params = "sn")
    public String toUpdate(String sn,Map<String,Object> map){
        map.put("employee",employeeBiz.get(sn));
        map.put("dlist",departmentBiz.getAll());
        map.put("plist",Contant.getPosts());
        return "employee_update";
    }
    @RequestMapping("/update")
    public String update(Employee employee){
        employeeBiz.edit(employee);
        return "redirect:list";
    }
    @RequestMapping(value = "/remove",params = "sn")
    public String remove(String sn){
        employeeBiz.remove(sn);
        return "redirect:list";
    }
}
