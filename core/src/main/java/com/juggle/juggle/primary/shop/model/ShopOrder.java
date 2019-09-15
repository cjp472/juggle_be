package com.juggle.juggle.primary.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juggle.juggle.framework.common.validation.meta.State;
import com.juggle.juggle.framework.data.entity.general.LongEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.juggle.juggle.framework.data.json.meta.ExtView;
import com.juggle.juggle.framework.data.json.meta.Formula;
import com.juggle.juggle.framework.data.json.meta.One;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.app.model.Member;
import com.juggle.juggle.primary.app.model.MemberCert;
import com.juggle.juggle.primary.shop.forumal.CalShopOrderSnapshot;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "shop_order")
@Formula(value="snapshot",calc = CalShopOrderSnapshot.class)
@ExtView
public class ShopOrder extends LongEntity {
    @Filtered
    @One(value = "member",target = Member.class)
    private Long memberId;

    @One(value = "cert",src = "memberId",ref = "memberId",target = MemberCert.class)
    private Long productId;

    @Filtered
    private String code;

    private Integer count;

    private BigDecimal price;

    private BigDecimal amount;

    private String snapshot;

    @State(Constants.PAY_WAY.class)
    private String payWay;

    private Date completeTime;

    private String remark;

    @Filtered
    @State(Constants.SHOP_ORDER_STATUS.class)
    private String status;

    private boolean handle;

    private Date updatedTime;

    private Date createdTime;

    public Long getMemberId(){
        return memberId;
    }

    public void setMemberId(Long memberId){
        this.memberId = memberId;
    }

    public Long getProductId(){
        return productId;
    }

    public void setProductId(Long productId){
        this.productId = productId;
    }

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public Integer getCount(){
        return count;
    }

    public void setCount(Integer count){
        this.count = count;
    }

    public BigDecimal getPrice(){
        return price;
    }

    public void setPrice(BigDecimal price){
        this.price = price;
    }

    public BigDecimal getAmount(){
        return amount;
    }

    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }

    @JsonIgnore
    public String getSnapshot(){
        return snapshot;
    }

    public void setSnapshot(String snapshot){
        this.snapshot = snapshot;
    }

    public String getPayWay(){
        return payWay;
    }

    public void setPayWay(String payWay){
        this.payWay = payWay;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getRemark(){
        return remark;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public boolean isHandle() {
        return handle;
    }

    public void setHandle(boolean handle) {
        this.handle = handle;
    }

    public Date getUpdatedTime(){
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime){
        this.updatedTime = updatedTime;
    }

    public Date getCreatedTime(){
        return createdTime;
    }

    public void setCreatedTime(Date createdTime){
        this.createdTime = createdTime;
    }
}
