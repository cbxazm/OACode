package com.cbx.dao;

import com.cbx.entity.ClaimVoucherItem;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClaimVoucherItemDao {
    void insert(ClaimVoucherItem claimVoucherItem);
    void update(ClaimVoucherItem claimVoucherItem);
    void delete(int id);
    //根据报销单编号来获取条目
    List<ClaimVoucherItem> selectByClaimVoucher(int cvid);
}
