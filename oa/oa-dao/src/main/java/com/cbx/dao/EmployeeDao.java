package com.cbx.dao;

import com.cbx.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDao {
    void insert(Employee employee);
    void update(Employee employee);
    void delete(String sn);
    Employee select(String sn);
   List<Employee> selectAll();
//   两个参数不明确，所以必须使用一个@Param注解，以便在映射文件中调用
   List<Employee> selectByDepartmentAndPost(@Param("dsn") String dsn,@Param("post") String post);
}
