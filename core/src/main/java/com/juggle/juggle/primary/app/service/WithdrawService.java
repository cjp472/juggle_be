package com.juggle.juggle.primary.app.service;

import com.juggle.juggle.common.data.AliPayParams;
import com.juggle.juggle.common.util.AliPayUtil;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.utils.ApplicationUtils;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.filter.PageResult;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.account.dao.AlipayAccountDao;
import com.juggle.juggle.primary.account.dao.BankAccountDao;
import com.juggle.juggle.primary.account.model.AlipayAccount;
import com.juggle.juggle.primary.account.model.BankAccount;
import com.juggle.juggle.primary.app.dao.WithdrawDao;
import com.juggle.juggle.primary.app.dto.WithdrawDto;
import com.juggle.juggle.primary.app.model.Withdraw;
import com.juggle.juggle.primary.setting.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class WithdrawService extends BaseCRUDService<Withdraw> {
    @Autowired
    private WithdrawDao dao;

    @Autowired
    private AlipayAccountDao alipayAccountDao;

    @Autowired
    private DictionaryService dictionaryService;

    @Override
    protected IRepo<Withdraw> getRepo() {
        return dao;
    }

    @Override
    protected void onCreate(Withdraw entity) {
        entity.setCode(generateWithdrawCode());
    }

    public PageResult searchSimplify(PageSearch pageSearch){
        PageResult pageResult = search(pageSearch);
        List<Withdraw> withdraws = pageResult.getRows();
        List<WithdrawDto> withdrawDtos = new ArrayList<>();
        for (Withdraw withdraw : withdraws) {
            WithdrawDto withdrawDto = new WithdrawDto();
            try {
                PropertyCopyUtil.getInstance().copyPropertiesWithIgnores(withdrawDto,withdraw);
                if(withdrawDto.getAccountType().equals(Constants.WITHDRAW_ACCOUNT_TYPE.ALIPAY)){
                    AlipayAccountDao accountDao = ApplicationUtils.getBean(AlipayAccountDao.class);
                    AlipayAccount alipayAccount = accountDao.findOne(withdraw.getAccountId());
                    withdrawDto.setAlipayAccount(alipayAccount);
                }else{
                    BankAccountDao accountDao = ApplicationUtils.getBean(BankAccountDao.class);
                    BankAccount bankAccount = accountDao.findOne(withdraw.getAccountId());
                    withdrawDto.setBankAccount(bankAccount);
                }
                withdrawDtos.add(withdrawDto);
            }catch (Exception e){
                throw new ServiceException(1001,"PropertyCopyUtil解析失败");
            }
        }
        pageResult.setRows(withdrawDtos);
        return pageResult;
    }

    public void withdrawToAlipay(List<Withdraw> withdraws){
        AliPayParams aliPayParams = dictionaryService.getAliPayDictionaries();
        AliPayUtil aliPayUtil = new AliPayUtil(aliPayParams,"GBK");
        for (Withdraw withdraw : withdraws) {
            AlipayAccount alipayAccount = alipayAccountDao.findOne(withdraw.getAccountId());
            aliPayUtil.toAccountTransfer(alipayAccount.getAccount(),alipayAccount.getName(),withdraw.getAmount());
        }
    }

    private static final SimpleDateFormat dateFormatOne=new SimpleDateFormat("yyyyMMddHHmmssSS");

    private static final ThreadLocalRandom random=ThreadLocalRandom.current();

    public static String generateWithdrawCode(){
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
