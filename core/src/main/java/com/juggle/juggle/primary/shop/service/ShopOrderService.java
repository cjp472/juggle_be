package com.juggle.juggle.primary.shop.service;

import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.common.utils.JsonUtils;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.filter.PageResult;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.filter.ValueFilter;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.app.dao.DeliveryAddressDao;
import com.juggle.juggle.primary.app.dto.DeliveryAddressDto;
import com.juggle.juggle.primary.app.model.DeliveryAddress;
import com.juggle.juggle.primary.app.model.Member;
import com.juggle.juggle.primary.shop.dao.*;
import com.juggle.juggle.primary.shop.dto.*;
import com.juggle.juggle.primary.shop.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ShopOrderService extends BaseCRUDService<ShopOrder> {
    @Autowired
    private ShopOrderDao dao;

    @Autowired
    private ShopExpressDao shopExpressDao;

    @Autowired
    private ShopExpressService shopExpressService;

    @Autowired
    private ShopProductDao shopProductDao;

    @Autowired
    private DeliveryAddressDao deliveryAddressDao;

    @Autowired
    private ShopProductSkuDao shopProductSkuDao;

    @Autowired
    private ShopProductSkuTypeDao shopProductSkuTypeDao;

    @Autowired
    private ShopProductCommentDao shopProductCommentDao;

    @Override
    protected IRepo<ShopOrder> getRepo() {
        return dao;
    }

    public PageResult searchOrder(PageSearch pageSearch){
        Member member = (Member)UserSession.getAuthorize().getUser();
        List<ValueFilter> filters = pageSearch.getFilters();
        ValueFilter valueFilter = new ValueFilter("memberId",ValueFilter.OP_EQ,member.getId());
        filters.add(valueFilter);
        pageSearch.setFilters(filters);
        PageResult pageResult = search(pageSearch);
        List<ShopOrder> rows = pageResult.getRows();
        List<ShopOrderListItem> items = new ArrayList<>();
        for (ShopOrder row : rows) {
            ShopOrderListItem item = new ShopOrderListItem();
            try {
                PropertyCopyUtil.getInstance().copyProperties(item,row);
                ShopOrderSnapshot snapshot = JsonUtils.readValue(row.getSnapshot(),ShopOrderSnapshot.class);
                ShopProduct shopProduct = snapshot.getProduct();
                List<ShopProductSku> skus = snapshot.getSkus();
                List<String> skuArr = new ArrayList<>();
                for (ShopProductSku sku : skus) {
                    ShopProductSkuType type = shopProductSkuTypeDao.findOne(sku.getTypeId());
                    skuArr.add(type.getName()+":"+sku.getName());
                }
                item.setSku(StringUtils.join(skuArr,","));
                item.setName(shopProduct.getName());
                item.setThumbnail(shopProduct.getThumbnail());
                items.add(item);
            }catch (Exception e){
                throw new ServiceException(1001,"PropertyCopyUtil解析错误");
            }
        }
        pageResult.setRows(items);
        return pageResult;
    }

    public ShopOrderDto readOrder(Long id){
        ShopOrder shopOrder = dao.findOne(id);
        ShopOrderDto shopOrderDto = new ShopOrderDto();
        try{
            PropertyCopyUtil.getInstance().copyPropertiesWithIgnores(shopOrderDto,shopOrder,"snapshot");
            ShopOrderSnapshot shopOrderSnapshot = JsonUtils.readValue(shopOrder.getSnapshot(),ShopOrderSnapshot.class);
            ShopOrderSnapshotDto snapshot = new ShopOrderSnapshotDto();
            DeliveryAddressDto address = new DeliveryAddressDto();
            PropertyCopyUtil.getInstance().copyProperties(address,shopOrderSnapshot.getAddress());
            snapshot.setAddress(address);
            List<ShopProductSkuDto> skus = new ArrayList<>();
            for (ShopProductSku shopProductSku : shopOrderSnapshot.getSkus()) {
                ShopProductSkuDto dto = new ShopProductSkuDto();
                PropertyCopyUtil.getInstance().copyProperties(dto,shopProductSku);
                ShopProductSkuType type = shopProductSkuTypeDao.findOne(shopProductSku.getTypeId());
                dto.setTypeName(type.getName());
                skus.add(dto);
            }
            snapshot.setSkus(skus);
            ShopProductDto product = new ShopProductDto();
            PropertyCopyUtil.getInstance().copyProperties(product,shopOrderSnapshot.getProduct());
            snapshot.setProduct(product);
            shopOrderDto.setSnapshot(snapshot);
            return shopOrderDto;
        }catch (Exception e){
            throw new ServiceException(1001,"PropertyCopyUtil解析错误");
        }
    }

    public ShopOrderDto createOrder(Integer count,Long productId, List<Long> skuIds, Long addressId){
        Member member = (Member) UserSession.getAuthorize().getUser();
        ShopProduct shopProduct = shopProductDao.findOne(productId);
        DeliveryAddress deliveryAddress = deliveryAddressDao.findOne(addressId);
        List<ShopProductSku> skus = shopProductSkuDao.findAllByIdInAndEnabled(skuIds,true);
        ShopOrder shopOrder = new ShopOrder();
        shopOrder.setCode(generateOrderCode());
        shopOrder.setProductId(productId);
        shopOrder.setMemberId(member.getId());
        if(null==count||count<1){
            count = 1;
        }
        shopOrder.setCount(1);
        shopOrder.setPrice(shopProduct.getActualPrice());
        shopOrder.setAmount(shopProduct.getActualPrice().multiply(BigDecimal.valueOf(count)));
        ShopOrderSnapshot snapshot = new ShopOrderSnapshot();
        snapshot.setProduct(shopProduct);
        snapshot.setAddress(deliveryAddress);
        snapshot.setSkus(skus);
        shopOrder.setSnapshot(JsonUtils.writeValue(snapshot));
        shopOrder.setStatus(Constants.SHOP_ORDER_STATUS.OPEN);
        shopOrder = create(shopOrder);
        ShopOrderDto dto = new ShopOrderDto();
        try{
            PropertyCopyUtil.getInstance().copyPropertiesWithIgnores(dto,shopOrder,"snapshot");
        }catch (Exception e){
            throw new ServiceException(1001,"PropertyCopyUtil解析失败");
        }
        return dto;
    }

    public ShopOrderDto cancelOrder(Long id,String remark){
        ShopOrder shopOrder = dao.findOne(id);
        shopOrder.setRemark(remark);
        shopOrder.setStatus(Constants.SHOP_ORDER_STATUS.CANCEL);
        shopOrder = update(id,shopOrder);
        ShopOrderDto dto = new ShopOrderDto();
        try{
            PropertyCopyUtil.getInstance().copyPropertiesWithIgnores(dto,shopOrder,"snapshot");
            return dto;
        }catch (Exception e){
            throw new ServiceException(1001,"PropertyCopyUtil解析失败");
        }
    }

    public ShopOrderDto confirmReceived(Long id){
        ShopOrder shopOrder = dao.findOne(id);
        shopOrder.setStatus(Constants.SHOP_ORDER_STATUS.RECEIVED);
        shopOrder = update(id,shopOrder);
        ShopOrderDto dto = new ShopOrderDto();
        try{
            PropertyCopyUtil.getInstance().copyPropertiesWithIgnores(dto,shopOrder,"snapshot");
            return dto;
        }catch (Exception e){
            throw new ServiceException(1001,"PropertyCopyUtil解析失败");
        }
    }

    public ShopOrderDto commentOrder(Long id,String content,String images,Integer cStar){
        Member member = (Member)UserSession.getAuthorize().getUser();
        ShopOrder shopOrder = dao.findOne(id);
        shopOrder.setStatus(Constants.SHOP_ORDER_STATUS.COMPLETE);
        shopOrder = update(id,shopOrder);
        ShopProductComment comment = new ShopProductComment();
        comment.setProductId(shopOrder.getProductId());
        comment.setMemberId(member.getId());
        comment.setAvatar(member.getAvatar());
        comment.setNickName(member.getNickName());
        comment.setContent(content);
        comment.setImages(images);
        comment.setcStar(cStar);
        shopProductCommentDao.save(comment);
        ShopOrderDto dto = new ShopOrderDto();
        try{
            PropertyCopyUtil.getInstance().copyPropertiesWithIgnores(dto,shopOrder,"snapshot");
            return dto;
        }catch (Exception e){
            throw new ServiceException(1001,"PropertyCopyUtil解析失败");
        }
    }

    public ShopOrder shipOrder(Long id,String shippedCom,String shippedNo){
        ShopOrder shopOrder = dao.findOne(id);
        if(!shopOrder.getStatus().equals(Constants.SHOP_ORDER_STATUS.PAID)){
            throw new ServiceException(1001,"订单状态已过时");
        }
        shopOrder.setStatus(Constants.SHOP_ORDER_STATUS.SHIPPED);
        ShopExpress shopExpress = shopExpressDao.findFirstByOrderId(shopOrder.getId());
        if(null==shopExpress){
            shopExpress = new ShopExpress();
        }
        shopExpress.setOrderId(shopOrder.getId());
        shopExpress.setShippedCom(shippedCom);
        shopExpress.setShippedNo(shippedNo);
        shopExpressService.createOrUpdate(shopExpress);
        return update(id,shopOrder);
    }

    public ShopOrderCensus getCensus(){
        Long memberId = (Long) UserSession.getAuthorize().getUser().getId();
        ShopOrderCensus orderCensus = new ShopOrderCensus();
        orderCensus.setOpen(dao.countAllByMemberIdAndStatus(memberId,Constants.SHOP_ORDER_STATUS.OPEN));
        orderCensus.setPaid(dao.countAllByMemberIdAndStatus(memberId,Constants.SHOP_ORDER_STATUS.PAID));
        orderCensus.setShipped(dao.countAllByMemberIdAndStatus(memberId,Constants.SHOP_ORDER_STATUS.SHIPPED));
        orderCensus.setReceived(dao.countAllByMemberIdAndStatus(memberId,Constants.SHOP_ORDER_STATUS.RECEIVED));
        orderCensus.setComplete(dao.countAllByMemberIdAndStatus(memberId,Constants.SHOP_ORDER_STATUS.COMPLETE));
        orderCensus.setCancel(dao.countAllByMemberIdAndStatus(memberId,Constants.SHOP_ORDER_STATUS.CANCEL));
        return orderCensus;
    }

    private static final SimpleDateFormat dateFormatOne=new SimpleDateFormat("yyyyMMddHHmmssSS");

    private static final ThreadLocalRandom random=ThreadLocalRandom.current();

    public static String generateOrderCode(){
        return dateFormatOne.format(new Date()) + generateNumber(4);
    }

    public static String generateNumber(final int num){
        StringBuffer sb=new StringBuffer();
        for (int i=1;i<=num;i++){
            sb.append(random.nextInt(9));
        }
        return sb.toString();
    }
}
