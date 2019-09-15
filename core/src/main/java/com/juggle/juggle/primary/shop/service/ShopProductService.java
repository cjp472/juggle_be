package com.juggle.juggle.primary.shop.service;

import com.juggle.juggle.framework.cache.meta.Cache;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.setting.dao.DictionaryDao;
import com.juggle.juggle.primary.setting.model.Dictionary;
import com.juggle.juggle.primary.shop.dao.*;
import com.juggle.juggle.primary.shop.dto.*;
import com.juggle.juggle.primary.shop.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShopProductService extends BaseCRUDService<ShopProduct> {
    @Autowired
    private ShopProductDao dao;

    @Autowired
    private ShopTagDao shopTagDao;

    @Autowired
    private DictionaryDao dictionaryDao;

    @Autowired
    private ShopProductSkuDao shopProductSkuDao;

    @Autowired
    private ShopProductSkuTypeDao shopProductSkuTypeDao;

    @Autowired
    private ShopProductCommentDao shopProductCommentDao;

    @Override
    protected IRepo<ShopProduct> getRepo() {
        return dao;
    }

    @Override
    protected void onCreate(ShopProduct entity) {
        if(null==entity.getActualPrice()){
            entity.setActualPrice(entity.getPrice());
        }
        entity.setReward(getReward(entity.getActualPrice()));
    }

    @Override
    protected void onUpdate(ShopProduct entity) {
        if(null==entity.getActualPrice()){
            entity.setActualPrice(entity.getPrice());
        }
        entity.setReward(getReward(entity.getActualPrice()));
    }

    public void enable(Long id){
        ShopProduct shopProduct = dao.findOne(id);
        shopProduct.setEnabled(true);
        update(id,shopProduct);
    }

    public void disable(Long id){
        ShopProduct shopProduct = dao.findOne(id);
        shopProduct.setEnabled(false);
        update(id,shopProduct);
    }

    @Cache
    public List<ShopProductRecommend> readAllRecommend() throws Exception{
        List<ShopProduct> products = dao.findAllByRecommendAndEnabled(true,true);
        List<ShopProductRecommend> productRecommends = new ArrayList<>();
        for (ShopProduct product : products) {
            ShopProductRecommend productRecommend = new ShopProductRecommend();
            PropertyCopyUtil.getInstance().copyPropertiesWithIgnores(productRecommend,product,"tags");
            if(!StringUtils.isEmpty(product.getTags())){
                List<Long> tagIds = Arrays.asList(StringUtils.split(product.getTags(),",")).stream().map(Long::valueOf).collect(Collectors.toList());
                List<ShopTag> shopTags = shopTagDao.findAllByIdInAndEnabled(tagIds,true);
                List<ShopTagDto> tags = new ArrayList<>();
                for (ShopTag shopTag : shopTags) {
                    ShopTagDto dto = new ShopTagDto();
                    dto.setName(shopTag.getName());
                    dto.setTheme(shopTag.getTheme());
                    tags.add(dto);
                }
                productRecommend.setTags(tags);
            }
            productRecommends.add(productRecommend);
        }
        return productRecommends;
    }

    @Cache
    public ShopProductDetail readDetail(Long id){
        ShopProduct shopProduct = dao.findOne(id);
        ShopProductDetail detail = new ShopProductDetail();
        try{
            PropertyCopyUtil.getInstance().copyProperties(detail,shopProduct);
        }catch (Exception e){
            throw new ServiceException(1001,"PropertyCopyUtil解析失败");
        }
        if(!StringUtils.isEmpty(shopProduct.getSlides())){
            List<String> slideList = Arrays.asList(StringUtils.split(shopProduct.getSlides(),","));
            detail.setSlideList(slideList);
        }
        if(!StringUtils.isEmpty(shopProduct.getDetails())){
            List<String> detailList = Arrays.asList(StringUtils.split(shopProduct.getDetails(),","));
            detail.setDetailList(detailList);
        }
        if(!StringUtils.isEmpty(shopProduct.getTags())){
            List<Long> tagIds;
            tagIds = Arrays.asList(StringUtils.split(shopProduct.getTags(),",")).stream().map(Long::valueOf).collect(Collectors.toList());
            List<ShopTag> tagArr = shopTagDao.findAllByIdInAndEnabled(tagIds,true);
            List<ShopTagDto> tags = new ArrayList<>();
            for (ShopTag shopTag : tagArr) {
                ShopTagDto dto = new ShopTagDto();
                dto.setTheme(shopTag.getTheme());
                dto.setName(shopTag.getName());
                tags.add(dto);
            }
            detail.setTagList(tags);
        }
        List<ShopProductComment> comments = shopProductCommentDao.findFirst20ByProductIdOrderByIdDesc(detail.getId());
        detail.setCommentList(comments);
        List<ShopProductSkuGroup> groups = new ArrayList<>();
        List<ShopProductSku> skus = shopProductSkuDao.findAllByProductId(detail.getId());
        for (ShopProductSku shopProductSku : skus) {
            Optional<ShopProductSkuGroup> skuGroup = groups.stream().filter(group->group.getId().equals(shopProductSku.getTypeId())).findFirst();
            if(skuGroup.isPresent()){
                ShopProductSkuGroup group = skuGroup.get();
                ShopProductSkuDto dto = new ShopProductSkuDto();
                dto.setId(shopProductSku.getId());
                dto.setName(shopProductSku.getName());
                group.getSkus().add(dto);
            }else{
                ShopProductSkuType type = shopProductSkuTypeDao.findOne(shopProductSku.getTypeId());
                ShopProductSkuGroup group = new ShopProductSkuGroup();
                group.setId(shopProductSku.getTypeId());
                group.setName(type.getName());
                groups.add(group);
                List<ShopProductSkuDto> dtos = new ArrayList<>();
                ShopProductSkuDto dto = new ShopProductSkuDto();
                dto.setId(shopProductSku.getId());
                dto.setName(shopProductSku.getName());
                dtos.add(dto);
                group.setSkus(dtos);
            }
        }
        detail.setSkuGroups(groups);
        return detail;
    }

    private BigDecimal getReward(BigDecimal actualPrice){
        Dictionary ratio = dictionaryDao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.SYSTEM,"RATIO_REWARD_SHOP");
        Dictionary rebate = dictionaryDao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.SYSTEM,"REBATE_REWARD_SHOP");
        return actualPrice.multiply(new BigDecimal(ratio.getDictValue())).multiply(new BigDecimal(rebate.getDictValue()));
    }
}
