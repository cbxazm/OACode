package com.cbx.biz.impl;

import com.cbx.biz.GloabalBiz;
import com.cbx.dao.EmployeeDao;
import com.cbx.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlobalBizImpl implements GloabalBiz {
    @Autowired
    private EmployeeDao employeeDao;
    public Employee login(String sn, String password) {
        Employee employee = employeeDao.select(sn);
        if(employee!=null&&employee.getPassword().equals(password)){
            return employee;
        }
        return null;
    }

    public void changePassword(Employee employee) {
        employeeDao.update(employee);
    }
}
