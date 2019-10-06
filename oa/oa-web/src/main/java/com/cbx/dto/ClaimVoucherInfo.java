package com.cbx.dto;

import com.cbx.entity.ClaimVoucher;
import com.cbx.entity.ClaimVoucherItem;

import java.util.List;
//页面与控制器之间的数据传递
public class ClaimVoucherInfo {
    //收集报销单信息
    private ClaimVoucher claimVoucher;
    //收集条目信息
    private List<ClaimVoucherItem> items;

    public ClaimVoucher getClaimVoucher() {
        return claimVoucher;
    }

    public void setClaimVoucher(ClaimVoucher claimVoucher) {
        this.claimVoucher = claimVoucher;
    }

    public List<ClaimVoucherItem> getItems() {
        return items;
    }

    public void setItems(List<ClaimVoucherItem> items) {
        this.items = items;
    }
}
