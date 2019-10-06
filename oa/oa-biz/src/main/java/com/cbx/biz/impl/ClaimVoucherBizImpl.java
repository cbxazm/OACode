package com.cbx.biz.impl;

import com.cbx.biz.ClaimVoucherBiz;
import com.cbx.dao.ClaimVoucherDao;
import com.cbx.dao.ClaimVoucherItemDao;
import com.cbx.dao.DealRecordDao;
import com.cbx.dao.EmployeeDao;
import com.cbx.entity.ClaimVoucher;
import com.cbx.entity.ClaimVoucherItem;
import com.cbx.entity.DealRecord;
import com.cbx.entity.Employee;
import com.cbx.global.Contant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ClaimVoucherBizImpl implements ClaimVoucherBiz {
    @Autowired
    private ClaimVoucherDao claimVoucherDao;
    @Autowired
    private ClaimVoucherItemDao claimVoucherItemDao;
    @Autowired
    private DealRecordDao dealRecordDao;
    @Autowired
    private EmployeeDao employeeDao;
    public void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        claimVoucher.setCreateTime(new Date());
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        claimVoucherDao.insert(claimVoucher);
        for (ClaimVoucherItem item : items) {
            item.setClaimVoucherId(claimVoucher.getId());
            claimVoucherItemDao.insert(item);
        }
    }

    public ClaimVoucher get(int id) {
        return claimVoucherDao.select(id);
    }

    public List<ClaimVoucherItem> getItem(int cvid) {
        return claimVoucherItemDao.selectByClaimVoucher(cvid);
    }

    public List<DealRecord> getRecords(int cvid) {
        return dealRecordDao.selectByClaimVoucher(cvid);
    }

    public List<ClaimVoucher> getForSelf(String sn) {
        return claimVoucherDao.selectByCreateSn(sn);
    }

    public List<ClaimVoucher> getForDeal(String sn) {
        return claimVoucherDao.selectByNextDealSn(sn);
    }

    public void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        claimVoucherDao.update(claimVoucher);
        //插入条目之前先进行判断

        List<ClaimVoucherItem> olds = claimVoucherItemDao.selectByClaimVoucher(claimVoucher.getId());
        for (ClaimVoucherItem old : olds) {
            boolean isHave = false;
            for (ClaimVoucherItem item : items) {
                if (item.getId() == old.getId()) {
                    isHave = true;
                    break;
                }
            }
            if (!isHave) {
                //删除不要的条目
                claimVoucherItemDao.delete(old.getId());
            }
        }
        for (ClaimVoucherItem item : items) {
            item.setClaimVoucherId(claimVoucher.getId());
            if (item.getId() > 0) {
                claimVoucherItemDao.update(item);
            } else {
                claimVoucherItemDao.insert(item);
            }
        }
    }

    public void submit(int id) {
        ClaimVoucher claimVoucher = claimVoucherDao.select(id);
        Employee employee=employeeDao.select(claimVoucher.getCreateSn());
           claimVoucher.setStatus(Contant.CLAIMVOUCHER_SUBMIT);
           claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(employee.getDepartmentSn(),Contant.POST_FM).get(0).getSn());
           claimVoucherDao.update(claimVoucher);
           //修改记录
           DealRecord dealRecord=new DealRecord();
           dealRecord.setDealWay(Contant.DEAL_SUBMIT);
           dealRecord.setDealSn(employee.getSn());
           dealRecord.setClaimVoucherId(id);
           dealRecord.setDealResult(Contant.CLAIMVOUCHER_SUBMIT);
           dealRecord.setDealTime(new Date());
           dealRecord.setComment("无");
           dealRecordDao.insert(dealRecord);
    }

    public void deal(DealRecord dealRecord) {
        ClaimVoucher claimVoucher = claimVoucherDao.select(dealRecord.getClaimVoucherId());
        //获取当前的处理人
        Employee employee=employeeDao.select(dealRecord.getDealSn());
              if(dealRecord.getDealWay().equals(Contant.DEAL_PASS)){
                  //报销单通过审核
                   if(claimVoucher.getTotalAmount()<=Contant.LIMIT_CHECK||employee.getPost().equals(Contant.POST_GM)){
                             //报销单金额小于5000，或者是总经理的身份，那么久不需要复审
                       claimVoucher.setStatus(Contant.CLAIMVOUCHER_APPROVED);
                       //不管你是哪个部门的，第一个参数直接填写Null
                       claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null,Contant.POST_CASHIER).get(0).getSn());
                          dealRecord.setDealTime(new Date());
                          dealRecord.setDealResult(Contant.CLAIMVOUCHER_APPROVED);
                   }else{
                       claimVoucher.setStatus(Contant.CLAIMVOUCHER_RECHECK);
//                       设置待处理人是总经理
                       claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null,Contant.POST_GM).get(0).getSn());
                     dealRecord.setDealTime(new Date());
                     dealRecord.setDealResult(Contant.CLAIMVOUCHER_RECHECK);
                   }
              }else if(dealRecord.getDealWay().equals(Contant.DEAL_BACK)){
                  //报销单被打回
                   claimVoucher.setStatus(Contant.CLAIMVOUCHER_BACK);
                   claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
                   dealRecord.setDealTime(new Date());
                   dealRecord.setDealResult(Contant.CLAIMVOUCHER_BACK);

              }else if(dealRecord.getDealWay().equals(Contant.DEAL_REJECT)){
                  //报销单被拒绝
                  claimVoucher.setStatus(Contant.CLAIMVOUCHER_TERMINATED);
                  claimVoucher.setNextDealSn(null);
                 dealRecord.setDealTime(new Date());
                 dealRecord.setDealResult(Contant.CLAIMVOUCHER_TERMINATED);
              }else if(dealRecord.getDealWay().equals(Contant.DEAL_PAID)){
                  //打款操作
                  claimVoucher.setStatus(Contant.CLAIMVOUCHER_PAID);
                  claimVoucher.setNextDealSn(null);
                  dealRecord.setDealTime(new Date());
                  dealRecord.setDealResult(Contant.CLAIMVOUCHER_PAID);
              }
              claimVoucherDao.update(claimVoucher);
              dealRecordDao.insert(dealRecord);
    }
}
