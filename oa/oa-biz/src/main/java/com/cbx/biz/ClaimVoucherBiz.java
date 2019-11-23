package com.cbx.biz;

import com.cbx.entity.ClaimVoucher;
import com.cbx.entity.ClaimVoucherItem;
import com.cbx.entity.DealRecord;

import java.util.List;

public interface ClaimVoucherBiz {
    void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);
    ClaimVoucher get(int id);
    List<ClaimVoucherItem> getItem(int cvid);
    List<DealRecord> getRecords(int cvid);
//    List<ClaimVoucher> getForSelf(String sn);
    //获取个人报销单（传入员工编号）
       List<ClaimVoucher> getForSelf(String sn);
       //获取待处理报销单（传入员工编号）
       List<ClaimVoucher> getForDeal(String sn);
   void update(ClaimVoucher claimVoucher,List<ClaimVoucherItem> items);
   //提交报销单
   void submit(int id);
   //审核以及打款
   void deal(DealRecord dealRecord);
}
