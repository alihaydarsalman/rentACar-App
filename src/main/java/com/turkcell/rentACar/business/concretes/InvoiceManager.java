package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.abstracts.RentalService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.InvoiceDao;
import com.turkcell.rentACar.entities.dtos.get.GetInvoiceDto;
import com.turkcell.rentACar.entities.dtos.list.InvoiceListDto;
import com.turkcell.rentACar.entities.requests.create.CreateInvoiceRequest;
import com.turkcell.rentACar.entities.sourceEntities.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceManager implements InvoiceService {


    private final ModelMapperService modelMapperService;
    private final InvoiceDao invoiceDao;
    private RentalService rentalService;
    private CustomerService customerService;
    private CarService carService;

    public InvoiceManager(ModelMapperService modelMapperService, InvoiceDao invoiceDao,
                          @Lazy RentalService rentalService, @Lazy CustomerService customerService, CarService carService) {
        this.modelMapperService = modelMapperService;
        this.invoiceDao = invoiceDao;
        this.rentalService = rentalService;
        this.customerService = customerService;
        this.carService = carService;
    }

    @Override
    public DataResult<List<InvoiceListDto>> getAll() {

        List<Invoice> invoices = this.invoiceDao.findAll();
        List<InvoiceListDto> result = invoices.stream()
                .map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException {

        Rental rental = this.rentalService.getByRentalId(createInvoiceRequest.getRentId());
        Customer customer = this.customerService.getCustomerByCustomerId(rental.getCustomer().getUserId());

        this.rentalService.isRentalExistsByRentalId(createInvoiceRequest.getRentId());

        Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest,Invoice.class);
        invoice.setRental(rental);
        invoice.setCustomer(customer);
        invoice.setRentDate(rental.getRentDate());
        invoice.setRentReturnDate(rental.getRentReturnDate());
        invoice.setInvoiceNo(invoiceNumberCreator(rental));
        invoice.setTotalRentalDays(calculateTotalRentDays(rental.getRentDate(), rental.getRentReturnDate()));
        invoice.setTotalPrice(calculateTotalPriceOfRental(rental));
        //invoice.setCreationDate(LocalDate.now());
        invoice.setInvoiceId(0);

        this.invoiceDao.save(invoice);

        return new SuccessResult(BusinessMessages.SUCCESS_ADD);
    }

    @Override
    public Result delete(int invoiceId) throws BusinessException {

        isInvoiceExistsByInvoiceId(invoiceId);

        this.invoiceDao.deleteById(invoiceId);

        return new SuccessResult(BusinessMessages.SUCCESS_DELETE);
    }

    @Override
    public DataResult<GetInvoiceDto> getById(int invoiceId) throws BusinessException {

        isInvoiceExistsByInvoiceId(invoiceId);

        Invoice invoice = this.invoiceDao.getById(invoiceId);
        GetInvoiceDto result = this.modelMapperService.forDto().map(invoice,GetInvoiceDto.class);

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_GET);
    }

    private void isInvoiceExistsByInvoiceId(int id) throws BusinessException {
        if(!this.invoiceDao.existsById(id)){
            throw new BusinessException(BusinessMessages.ERROR_INVOICE_NOT_FOUND);
        }
    }

    @Override
    public DataResult<List<InvoiceListDto>> getInvoicesByCreationDates(LocalDate firstDate, LocalDate secondDate) {

        List<Invoice> invoices = this.invoiceDao.findAllByCreationDateBetween(firstDate, secondDate);
        List<InvoiceListDto> result = invoices.stream()
                .map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public DataResult<List<InvoiceListDto>> getInvoicesByCustomer(int customerId) throws BusinessException {

        isInvoiceExistsByCustomerIdOnInvoiceTable(customerId);

        List<Invoice> invoices = this.invoiceDao.findAllByCustomer_UserId(customerId);
        List<InvoiceListDto> result = invoices.stream()
                .map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.SUCCESS_LIST);
    }

    private String invoiceNumberCreator(Rental rental) throws BusinessException {

        String invoiceNumber = String.valueOf(rental.getCustomer().getUserId())+
                String.valueOf(rental.getRentId())+
                String.valueOf(rental.getRentDate().getYear()) +
                String.valueOf(rental.getRentDate().getMonthValue()) +
                String.valueOf(rental.getRentDate().getDayOfMonth());

        return invoiceNumber;
    }

    private int calculateTotalRentDays(LocalDate rentDate, LocalDate returnDate){

        if (ChronoUnit.DAYS.between(rentDate,returnDate) == 0){
            return 1;
        }

        return  (int)(ChronoUnit.DAYS.between(rentDate,returnDate));
    }

    private double calculateTotalPriceOfRental(Rental rental) throws BusinessException {

        Car car = this.carService.getCarByCarId(rental.getCar().getCarId());

        double totalPrice = 0;
        double additionsPrice = 0;
        double rentalPrice = calculateTotalRentDays(rental.getRentDate(), rental.getRentReturnDate()) * (car.getDailyPrice());
        double diffCityPrice = 0;
        for (Addition addition: rental.getAdditionList()){
            additionsPrice += addition.getAdditionDailyPrice() * calculateTotalRentDays(rental.getRentDate(), rental.getRentReturnDate());
        }

        if(rental.getToCity().getCityId() != rental.getFromCity().getCityId()){
            diffCityPrice=750;
        }

        totalPrice = rentalPrice+ additionsPrice+ diffCityPrice;

        return totalPrice;
    }

    private void isInvoiceExistsByCustomerIdOnInvoiceTable(int id) throws BusinessException{
        if(!this.invoiceDao.existsByCustomer_UserId(id)){
            throw new BusinessException(BusinessMessages.INVOICE_NOT_FOUND_BY_CUSTOMER);
        }
    }
}
