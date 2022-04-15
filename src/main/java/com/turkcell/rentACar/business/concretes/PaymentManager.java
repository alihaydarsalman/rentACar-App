package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.api.models.PaymentModel;
import com.turkcell.rentACar.business.abstracts.*;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.paymentManagement.adapters.posAdapters.abstracts.PosService;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.PaymentDao;
import com.turkcell.rentACar.entities.dtos.get.GetPaymentDto;
import com.turkcell.rentACar.entities.dtos.list.PaymentListDto;
import com.turkcell.rentACar.entities.requests.create.CreateInvoiceRequest;
import com.turkcell.rentACar.entities.requests.create.CreatePaymentRequest;
import com.turkcell.rentACar.entities.sourceEntities.Invoice;
import com.turkcell.rentACar.entities.sourceEntities.Payment;
import com.turkcell.rentACar.entities.sourceEntities.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentManager implements PaymentService {

    private final ModelMapperService modelMapperService;
    private final PaymentDao paymentDao;
    private RentalService rentalService;
    private UserService userService;
    private PosService posService;
    private CarService carService;
    private InvoiceService invoiceService;

    @Autowired
    public PaymentManager(ModelMapperService modelMapperService, PaymentDao paymentDao,
                          RentalService rentalService, UserService userService,
                          PosService posService, CarService carService, InvoiceService invoiceService) {
        this.modelMapperService = modelMapperService;
        this.paymentDao = paymentDao;
        this.rentalService = rentalService;
        this.userService = userService;
        this.posService = posService;
        this.carService = carService;
        this.invoiceService = invoiceService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = BusinessException.class)
    public Result addPaymentForIndividualCustomer(PaymentModel paymentModel) throws BusinessException {

        Rental rental = this.modelMapperService.forRequest().map(paymentModel.getCreateRentalRequest(), Rental.class);

        rental.setCar(this.carService.getCarByCarId(paymentModel.getCreateRentalRequest().getCarId()));

        this.rentalService.setAdditionForRental(rental,paymentModel.getCreateRentalRequest());

        double totalPayment = this.invoiceService.calculateTotalPriceOfRental(rental);

        isPaymentVerified(totalPayment, paymentModel.getCreatePaymentRequest());

        successorForIndividualCustomer(paymentModel);

        return new SuccessResult(BusinessMessages.SUCCESS_ADD);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = BusinessException.class)
    public Result addPaymentForCorporateCustomer(PaymentModel paymentModel) throws BusinessException {

        Rental rental = this.modelMapperService.forRequest().map(paymentModel.getCreateRentalRequest(),Rental.class);

        rental.setCar(this.carService.getCarByCarId(paymentModel.getCreateRentalRequest().getCarId()));

        double totalPayment = this.invoiceService.calculateTotalPriceOfRental(rental);

        isPaymentVerified(totalPayment, paymentModel.getCreatePaymentRequest());

        successorForCorporateCustomer(paymentModel);

        return new SuccessResult(BusinessMessages.SUCCESS_ADD);
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = BusinessException.class)
    public void successorForIndividualCustomer(PaymentModel paymentModel) throws BusinessException {

        Rental rental = this.rentalService.addRentalForIndividualCustomer(paymentModel.getCreateRentalRequest());

        CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest();
        createInvoiceRequest.setRentId(rental.getRentId());

        Invoice invoice = this.invoiceService.add(createInvoiceRequest);

        Payment payment = this.modelMapperService.forRequest().map(paymentModel.getCreatePaymentRequest(), Payment.class);
        payment.setInvoice(invoice);
        payment.setPaymentAmount(invoice.getTotalPrice());

        this.paymentDao.save(payment);
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = BusinessException.class)
    public void successorForCorporateCustomer(PaymentModel paymentModel) throws BusinessException {

        Rental rental = this.rentalService.addRentalForCorporateCustomer(paymentModel.getCreateRentalRequest());

        CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest();
        createInvoiceRequest.setRentId(rental.getRentId());

        Invoice invoice = this.invoiceService.add(createInvoiceRequest);

        Payment payment = this.modelMapperService.forRequest().map(paymentModel.getCreatePaymentRequest(), Payment.class);
        payment.setInvoice(invoice);
        payment.setPaymentAmount(invoice.getTotalPrice());

        this.paymentDao.save(payment);
    }

    @Override
    public DataResult<List<PaymentListDto>> getAll() {

        List<Payment> payments = this.paymentDao.findAll();
        List<PaymentListDto> result = payments.stream()
                .map(payment -> this.modelMapperService.forDto().map(payment,PaymentListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public DataResult<GetPaymentDto> getById(int paymentId) throws BusinessException {

        isPaymentExistsByPaymentId(paymentId);

        Payment payment = this.paymentDao.getById(paymentId);
        GetPaymentDto result = this.modelMapperService.forDto().map(payment,GetPaymentDto.class);

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_GET);
    }


    @Override
    public DataResult<List<PaymentListDto>> getPaymentsByRental(int rentId) throws BusinessException {

        isPaymentExistsByRental(rentId);
        this.rentalService.isRentalExistsByRentalId(rentId);

        List<Payment> payments = this.paymentDao.findAllByInvoice_Rental_RentId(rentId);
        List<PaymentListDto> result = payments.stream()
                .map(payment -> this.modelMapperService.forDto().map(payment,PaymentListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public DataResult<List<PaymentListDto>> getPaymentByCustomer(int customerId) throws BusinessException {

        isPaymentExistsByCustomer(customerId);
        this.userService.isExistsUserByUserId(customerId);

        List<Payment> payments = this.paymentDao.findAllByInvoice_Customer_UserId(customerId);
        List<PaymentListDto> result = payments.stream()
                .map(payment -> this.modelMapperService.forDto().map(payment,PaymentListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public Result delete(int paymentId) throws BusinessException {

        isPaymentExistsByPaymentId(paymentId);

        this.paymentDao.deleteById(paymentId);

        return new SuccessResult(BusinessMessages.SUCCESS_DELETE);
    }


    private void isPaymentExistsByPaymentId(int payId) throws BusinessException {
        if(!this.paymentDao.existsById(payId)){
            throw new BusinessException(BusinessMessages.ERROR_PAYMENT_NOT_FOUND);
        }
    }

    private void isPaymentExistsByRental(int rentId) throws BusinessException {
        if(!this.paymentDao.existsPaymentByInvoice_Rental_RentId(rentId)){
            throw new BusinessException(BusinessMessages.PAYMENT_NOT_FOUND_BY_RENTAL);
        }
    }

    private void isPaymentExistsByCustomer(int custId) throws BusinessException {
        if(!this.paymentDao.existsPaymentByInvoice_Customer_UserId(custId)){
            throw new BusinessException(BusinessMessages.PAYMENT_NOT_FOUND_BY_CUSTOMER);
        }
    }

    private void isPaymentVerified(double paymentAmount, CreatePaymentRequest createPaymentRequest) throws BusinessException {
        if(!this.posService.makePayment(paymentAmount,createPaymentRequest).isSuccess()){
            throw new BusinessException(BusinessMessages.PAYMENT_FAIL);
        }
    }

}
