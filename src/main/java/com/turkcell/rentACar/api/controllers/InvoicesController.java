package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.dtos.get.GetInvoiceDto;
import com.turkcell.rentACar.entities.dtos.list.InvoiceListDto;
import com.turkcell.rentACar.entities.requests.create.CreateInvoiceRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {

    private final InvoiceService invoiceService;

    public InvoicesController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/getAll")
    public DataResult<List<InvoiceListDto>> getAll(){
        return this.invoiceService.getAll();
    }

    @GetMapping("/getInvoicesByCreationDates")
    public DataResult<List<InvoiceListDto>> getInvoicesByCreationDates(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate firstDate,
                                                                       @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate secondDate){
        return this.invoiceService.getInvoicesByCreationDates(firstDate,secondDate);
    }

    @GetMapping("/getInvoicesByCustomer")
    public DataResult<List<InvoiceListDto>> getInvoicesByCustomer(@RequestParam int customerId) throws BusinessException {
        return this.invoiceService.getInvoicesByCustomer(customerId);
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest) throws BusinessException {
        return this.invoiceService.add(createInvoiceRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam int invoiceId) throws BusinessException {
        return this.invoiceService.delete(invoiceId);
    }

    @GetMapping("/getById")
    public DataResult<GetInvoiceDto> getById(@RequestParam int invoiceId) throws BusinessException {
        return this.invoiceService.getById(invoiceId);
    }
}
