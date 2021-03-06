package com.cbx.controller;

import com.cbx.biz.ClaimVoucherBiz;
import com.cbx.dto.ClaimVoucherInfo;
import com.cbx.entity.DealRecord;
import com.cbx.entity.Employee;
import com.cbx.global.Contant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;
@Controller
@RequestMapping("/claim_voucher")
public class ClaimVoucherController {
    @Autowired
 private ClaimVoucherBiz claimVoucherBiz;
    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object> map){
        map.put("items", Contant.getItems());
//        System.out.println(map.get(0));
        //这个页面填写的信息都存储在info的map集合中
        map.put("info",new ClaimVoucherInfo());
        return "claim_voucher_add";
    }
    @RequestMapping("/add")
    public String add(HttpSession session, ClaimVoucherInfo info){
        Employee employee = (Employee)session.getAttribute("employee");
        //设置该报销单的创建者编号
        info.getClaimVoucher().setCreateSn(employee.getSn());
        claimVoucherBiz.save(info.getClaimVoucher(),info.getItems());
        return "redirect:deal";
    }
    @RequestMapping("/detail")
    public String detail(int id, Map<String,Object> map){
        map.put("claimVoucher",claimVoucherBiz.get(id));
        map.put("items",claimVoucherBiz.getItem(id));
        map.put("records",claimVoucherBiz.getRecords(id));
        return "claim_voucher_detail";
    }
    @RequestMapping("/self")
    public String self(HttpSession session, Map<String,Object> map){
        Employee employee = (Employee)session.getAttribute("employee");
        map.put("list",claimVoucherBiz.getForSelf(employee.getSn()));
        return "claim_voucher_self";
    }
    @RequestMapping("/deal")
    public String deal(HttpSession session, Map<String,Object> map){
        Employee employee = (Employee)session.getAttribute("employee");
        map.put("list",claimVoucherBiz.getForDeal(employee.getSn()));
//        System.out.println(employee);
//        System.out.println(employee.getSn());
        return "claim_voucher_deal";
    }

    @RequestMapping("/to_update")
//    这里定义的id用于接收页面传递过来的id
    public String toUpdate(int id,Map<String,Object> map){
        map.put("items", Contant.getItems());
        ClaimVoucherInfo info =new ClaimVoucherInfo();
        info.setClaimVoucher(claimVoucherBiz.get(id));//获取报销单的信息
//        System.out.println(claimVoucherBiz.get(id).getId());
        info.setItems(claimVoucherBiz.getItem(id));//获取了该报销单的报销单条目集合
        map.put("info",info);
        return "claim_voucher_update";
    }
    @RequestMapping("/update")
    public String update(HttpSession session, ClaimVoucherInfo info){
        Employee employee = (Employee)session.getAttribute("employee");
        info.getClaimVoucher().setCreateSn(employee.getSn());//设置报销单的编号
        claimVoucherBiz.update(info.getClaimVoucher(),info.getItems());
        return "redirect:deal";
    }
    @RequestMapping("/submit")
    public String submit(int id){
        claimVoucherBiz.submit(id);
        return "redirect:deal";
    }
    @RequestMapping("/to_check")
    public String toCheck(int id,Map<String,Object> map){
        map.put("claimVoucher",claimVoucherBiz.get(id));
        map.put("items",claimVoucherBiz.getItem(id));
        map.put("records",claimVoucherBiz.getRecords(id));
//        下面的控制器可以接收到DealRecord对象
        DealRecord dealRecord=new DealRecord();
        dealRecord.setClaimVoucherId(id);
        map.put("record",dealRecord);
//        System.out.println(map.get("record").toString());
        return "claim_voucher_check";
    }
    @RequestMapping("/check")
    public String check(HttpSession session,DealRecord dealRecord){
         Employee employee=(Employee) session.getAttribute("employee");
         dealRecord.setDealSn(employee.getSn());
         claimVoucherBiz.deal(dealRecord);
         return "redirect:deal";
    }
}
