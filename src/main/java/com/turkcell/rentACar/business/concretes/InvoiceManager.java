package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.api.models.UpdatePaymentModel;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.abstracts.RentalService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.generators.InvoiceNoGenerator;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
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
    private final RentalService rentalService;
    private final CustomerService customerService;
    private final CarService carService;

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
    public Invoice add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException {

        Rental rental = this.rentalService.getByRentalId(createInvoiceRequest.getRentId());
        Customer customer = this.customerService.getCustomerByCustomerId(rental.getCustomer().getUserId());

        this.rentalService.isRentalExistsByRentalId(createInvoiceRequest.getRentId());

        Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest,Invoice.class);
        invoice.setRental(rental);
        invoice.setCustomer(customer);
        invoice.setRentDate(rental.getRentDate());
        invoice.setRentReturnDate(rental.getRentReturnDate());
        invoice.setInvoiceNo(invoiceNumberCreator(rental));
        invoice.setTotalRentalDays(calculateTotalRentDays(invoice.getRentDate(), invoice.getRentReturnDate()));
        invoice.setTotalPrice(calculateTotalPriceOfRental(rental));
        invoice.setInvoiceId(0);

        this.invoiceDao.save(invoice);

        return invoice;
    }

    @Override
    public Invoice addInvoiceForDelay(Rental rental, UpdatePaymentModel updatePaymentModel) throws BusinessException {

        Customer customer = this.customerService.getCustomerByCustomerId(rental.getCustomer().getUserId());

        this.rentalService.isRentalExistsByRentalId(rental.getRentId());

        Invoice invoice = this.modelMapperService.forRequest().map(rental,Invoice.class);
        invoice.setRental(rental);
        invoice.setCustomer(customer);
        invoice.setInvoiceNo(invoiceNumberCreator(rental));
        invoice.setRentDate(rental.getRentReturnDate());
        invoice.setRentReturnDate(updatePaymentModel.getUpdateRentalRequestForDelay().getDelayedReturnDate());
        invoice.setTotalRentalDays(calculateTotalRentDaysForDelay(invoice.getRentDate(),invoice.getRentReturnDate()));
        invoice.setTotalPrice(calculateTotalPriceForDelay(invoice.getRentDate(),invoice.getRentReturnDate(),rental.getRentId()));
        invoice.setInvoiceId(0);

        this.invoiceDao.save(invoice);

        return invoice;
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

        String currInvoiceNo = InvoiceNoGenerator.generate();

        while (true){
            if (!this.invoiceDao.existsByInvoiceNo(currInvoiceNo)){
                return currInvoiceNo;
            }
            currInvoiceNo = InvoiceNoGenerator.generate();
        }
    }
    private int calculateTotalRentDays(LocalDate rentDate, LocalDate returnDate){

        if (ChronoUnit.DAYS.between(rentDate,returnDate) == 0){
            return 1;
        }
        return  (int)(ChronoUnit.DAYS.between(rentDate,returnDate));
    }

    @Override
    public int calculateTotalRentDaysForDelay(LocalDate expectedReturnDate, LocalDate actualReturnDate){

        if (ChronoUnit.DAYS.between(expectedReturnDate,actualReturnDate) == 0){
            return 1;
        }
        return  (int)(ChronoUnit.DAYS.between(expectedReturnDate,actualReturnDate));
    }


    @Override
    public double calculateTotalPriceOfRental(Rental rental) throws BusinessException {

        Car car = this.carService.getCarByCarId(rental.getCar().getCarId());

        double totalPrice = 0;
        double additionsPrice = 0;
        double rentalPrice = calculateTotalRentDays(rental.getRentDate(), rental.getRentReturnDate()) * (car.getDailyPrice());
        double diffCityPrice = 0;
        for (Addition addition: rental.getAdditionList()){
            additionsPrice += addition.getAdditionDailyPrice() * calculateTotalRentDays(rental.getRentDate(), rental.getRentReturnDate());
        }

        if(rental.getToCity().getCityId() != rental.getFromCity().getCityId()){
            diffCityPrice= Invoice.diffCityPrice;
        }

        totalPrice = rentalPrice+ additionsPrice + diffCityPrice;

        return totalPrice;
    }

    @Override
    public double calculateTotalPriceForDelay(LocalDate expectedReturnDate, LocalDate actualReturnDate, int rentId) throws BusinessException {

        isRentDaysValid(expectedReturnDate,actualReturnDate);

        Rental rental = this.rentalService.getByRentalId(rentId);
        Car car = this.carService.getCarByCarId(rental.getCar().getCarId());

        double totalPrice=0;
        double additionsPrice=0;
        double rentalPrice = calculateTotalRentDaysForDelay(expectedReturnDate,actualReturnDate) * (car.getDailyPrice());

        for(Addition addition: rental.getAdditionList()){
            additionsPrice+= addition.getAdditionDailyPrice() * calculateTotalRentDaysForDelay(expectedReturnDate,actualReturnDate);
        }

        totalPrice = rentalPrice + additionsPrice;

        return totalPrice;
    }

    private void isInvoiceExistsByCustomerIdOnInvoiceTable(int id) throws BusinessException{
        if(!this.invoiceDao.existsByCustomer_UserId(id)){
            throw new BusinessException(BusinessMessages.INVOICE_NOT_FOUND_BY_CUSTOMER);
        }
    }

    private void isRentDaysValid(LocalDate expectedReturnDate, LocalDate actualReturnDate) throws BusinessException {
        if (expectedReturnDate.isAfter(actualReturnDate) || expectedReturnDate.isEqual(actualReturnDate)){
            throw new BusinessException(BusinessMessages.ERROR_INVALID_DELAYED_DATE);
        }
    }
}
