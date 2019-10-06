package com.cbx.biz.impl;

import com.cbx.biz.EmployeeBiz;
import com.cbx.dao.EmployeeDao;
import com.cbx.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeBizImpl implements EmployeeBiz {
    @Autowired
    private EmployeeDao employeeDao;
    public void add(Employee employee) {
        employeeDao.insert(employee);
    }

    public void edit(Employee employee) {
//        设置默认登录密码
        employee.setPassword("000000");
       employeeDao.update(employee);
    }

    public void remove(String sn) {
              employeeDao.delete(sn);
    }

    public Employee get(String sn) {
        return employeeDao.select(sn);
    }

    public List<Employee> getAll() {
        return employeeDao.selectAll();
    }
}
