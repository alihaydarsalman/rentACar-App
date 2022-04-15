package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.dtos.get.GetInvoiceDto;
import com.turkcell.rentACar.entities.dtos.list.InvoiceListDto;
import com.turkcell.rentACar.entities.requests.create.CreateInvoiceRequest;
import com.turkcell.rentACar.entities.sourceEntities.Invoice;
import com.turkcell.rentACar.entities.sourceEntities.Rental;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceService {

    DataResult<List<InvoiceListDto>> getAll();

    Invoice add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException;

    DataResult<GetInvoiceDto> getById(int invoiceId) throws BusinessException;

    DataResult<List<InvoiceListDto>> getInvoicesByCreationDates(LocalDate firstDate, LocalDate secondDate);

    DataResult<List<InvoiceListDto>> getInvoicesByCustomer(int customerId) throws BusinessException;

    double calculateTotalPriceOfRental(Rental rental) throws BusinessException;
}
