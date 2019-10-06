package com.cbx.dao;

import com.cbx.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DepartmentDao {
    void insert(Department department);
    void update(Department department);
    void delete(String sn);
    Department select(String sn);
   List<Department> selectAll();
}
