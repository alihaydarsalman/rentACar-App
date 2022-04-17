package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CardInfoService;
import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.adapters.cardControlAdapters.abstracts.CardControlService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.CardInfoDao;
import com.turkcell.rentACar.entities.dtos.get.GetCardInfoDto;
import com.turkcell.rentACar.entities.dtos.list.CardInfoListDto;
import com.turkcell.rentACar.entities.requests.create.CreateCardInfoRequest;
import com.turkcell.rentACar.entities.sourceEntities.CardInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardInfoManager implements CardInfoService {

    private final ModelMapperService modelMapperService;
    private final CardInfoDao cardInfoDao;
    private CustomerService customerService;
    private CardControlService cardControlService;

    @Autowired
    public CardInfoManager(ModelMapperService modelMapperService, CardInfoDao cardInfoDao,
                           CustomerService customerService, CardControlService cardControlService) {
        this.modelMapperService = modelMapperService;
        this.cardInfoDao = cardInfoDao;
        this.customerService = customerService;
        this.cardControlService = cardControlService;
    }

    @Override
    public DataResult<List<CardInfoListDto>> getAll() {

        List<CardInfo> cardInfos = this.cardInfoDao.findAll();
        List<CardInfoListDto> result = cardInfos.stream()
                .map(cardInfo -> this.modelMapperService.forDto().map(cardInfo, CardInfoListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public DataResult<GetCardInfoDto> getById(int cardId) throws BusinessException {

        isCardExistsByCardInfoId(cardId);

        CardInfo cardInfo = this.cardInfoDao.getById(cardId);
        GetCardInfoDto result = this.modelMapperService.forDto().map(cardInfo,GetCardInfoDto.class);

        return new SuccessDataResult<>(result, BusinessMessages.SUCCESS_GET);
    }

    @Override
    public Result add(CreateCardInfoRequest createCardInfoRequest) throws BusinessException {

        isCardVerifiedByBank(createCardInfoRequest);
        isCardExistsByCardNumber(createCardInfoRequest.getCardNumber());
        checkIfExpirationYearIsValid(createCardInfoRequest.getExpirationYear());
        this.customerService.isCustomerExistsByCustomerId(createCardInfoRequest.getUserId());

        CardInfo cardInfo = this.modelMapperService.forRequest().map(createCardInfoRequest, CardInfo.class);

        cardInfo.setCardInfoId(0);
        cardInfo.setCustomer(this.customerService.getCustomerByCustomerId(createCardInfoRequest.getUserId()));
        this.cardInfoDao.save(cardInfo);

        return new SuccessResult(BusinessMessages.SUCCESS_ADD);
    }

    @Override
    public Result delete(int cardId) throws BusinessException {

        isCardExistsByCardInfoId(cardId);

        this.cardInfoDao.deleteById(cardId);

        return new SuccessResult(BusinessMessages.SUCCESS_DELETE);
    }

    private void isCardExistsByCardNumber(String cardNumber) throws BusinessException {
        if (this.cardInfoDao.existsCardInfoByCardNumber(cardNumber)){
            throw new BusinessException(BusinessMessages.CARD_IS_ALREADY_EXISTS);
        }
    }

    private void isCardExistsByCardInfoId(int id) throws BusinessException {
        if (!this.cardInfoDao.existsById(id)){
            throw new BusinessException(BusinessMessages.ERROR_CARD_NOT_FOUND);
        }
    }

    private void isCardVerifiedByBank(CreateCardInfoRequest createCardInfoRequest) throws BusinessException {
        if(!this.cardControlService.saveCard(createCardInfoRequest).isSuccess()){
            throw new BusinessException(BusinessMessages.VERIFY_FAIL);
        }
    }

    private void checkIfExpirationYearIsValid(int year) throws BusinessException {
        int currentYear = LocalDate.now().getYear();
        if (year<currentYear){
            throw new BusinessException(BusinessMessages.ERROR_EXPIRATION_YEAR);
        }
    }
}
