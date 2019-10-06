package com.cbx.biz.impl;

import com.cbx.biz.DepartmentBiz;
import com.cbx.dao.DepartmentDao;
import com.cbx.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DepartmentBizImpl implements DepartmentBiz {
    @Autowired
     private DepartmentDao departmentDao;
    public void add(Department department) {
               departmentDao.insert(department);
    }

    public void edit(Department department) {
          departmentDao.update(department);
    }

    public void remove(String sn) {
       departmentDao.delete(sn);
    }

    public Department get(String sn) {
        return departmentDao.select(sn);
    }

    public List<Department> getAll() {
        return departmentDao.selectAll();
    }
}
