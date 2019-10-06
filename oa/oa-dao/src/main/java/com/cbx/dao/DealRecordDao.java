package com.cbx.dao;

import com.cbx.entity.DealRecord;

import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DealRecordDao {
//    处理记录不能删除，类似于日志
    void insert(DealRecord dealRecord);
    List<DealRecord> selectByClaimVoucher(int cvid);
}
