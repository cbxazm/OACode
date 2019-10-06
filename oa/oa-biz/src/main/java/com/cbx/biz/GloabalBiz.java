package com.cbx.biz;

import com.cbx.entity.Employee;

public interface GloabalBiz {
    Employee login(String sn,String password);
    void changePassword(Employee employee);
}
