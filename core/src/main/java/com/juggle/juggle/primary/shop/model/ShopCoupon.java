package com.juggle.juggle.primary.shop.model;

import com.juggle.juggle.framework.common.validation.meta.State;
import com.juggle.juggle.framework.data.entity.general.AuditEntity;
import com.juggle.juggle.primary.Constants;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "shop_coupon")
public class ShopCoupon extends AuditEntity {
    private Long productId;

    @State(Constants.SHOP_COUPON_TYPE.class)
    private String type;

    @NotBlank
    private String name;

    private String detail;

    private BigDecimal satisfyAmount;

    private BigDecimal reductionAmount;

    private Date startTime;

    private Date endTime;

    @NotNull
    private Integer count;

    private Integer take;

    @State(Constants.SHOP_COUPON_STATUS.class)
    private String status;

    public Long getProductId(){
        return productId;
    }

    public void setProductId(Long productId){
        this.productId = productId;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDetail(){
        return detail;
    }

    public void setDetail(String detail){
        this.detail = detail;
    }

    public BigDecimal getSatisfyAmount(){
        return satisfyAmount;
    }

    public void setSatisfyAmount(BigDecimal satisfyAmount){
        this.satisfyAmount = satisfyAmount;
    }

    public BigDecimal getReductionAmount(){
        return reductionAmount;
    }

    public void setReductionAmount(BigDecimal reductionAmount){
        this.reductionAmount = reductionAmount;
    }

    public Date getStartTime(){
        return startTime;
    }

    public void setStartTime(Date startTime){
        this.startTime = startTime;
    }

    public Date getEndTime(){
        return endTime;
    }

    public void setEndTime(Date endTime){
        this.endTime = endTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getTake() {
        return take;
    }

    public void setTake(Integer take) {
        this.take = take;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
