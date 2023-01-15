package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.api.models.PaymentModel;
import com.turkcell.rentACar.api.models.UpdatePaymentModel;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.entities.dtos.get.GetPaymentDto;
import com.turkcell.rentACar.entities.dtos.list.PaymentListDto;
import com.turkcell.rentACar.business.abstracts.PaymentService;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    private final PaymentService paymentService;

    public PaymentsController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/getAll")
    public DataResult<List<PaymentListDto>> getAll(){
        return this.paymentService.getAll();
    }

    @GetMapping("/getById")
    public DataResult<GetPaymentDto> getById(@RequestParam int paymentId) throws BusinessException {
        return this.paymentService.getById(paymentId);
    }

    @GetMapping("/getPaymentsByRental")
    public DataResult<List<PaymentListDto>> getPaymentsByRental(@RequestParam int rentId) throws BusinessException{
        return this.paymentService.getPaymentsByRental(rentId);
    }

    @GetMapping("/getPaymentsByCustomer")
    public DataResult<List<PaymentListDto>> getPaymentByCustomer(@RequestParam int customerId) throws BusinessException{
        return this.paymentService.getPaymentByCustomer(customerId);
    }

    @PostMapping("/addPaymentForIndividualCustomer")
    public Result addPaymentForIndividualCustomer(@RequestBody @Valid PaymentModel paymentModel) throws BusinessException{
        return this.paymentService.addPaymentForIndividualCustomer(paymentModel);
    }

    @PostMapping("/addPaymentForCorporateCustomer")
    public Result addPaymentForCorporateCustomer(@RequestBody @Valid PaymentModel paymentModel) throws BusinessException{
        return this.paymentService.addPaymentForCorporateCustomer(paymentModel);
    }

    @PostMapping("/addPaymentForDelay")
    public Result addPaymentForDelay(@RequestBody @Valid UpdatePaymentModel updatePaymentModel) throws BusinessException{
        return this.paymentService.addPaymentForDelay(updatePaymentModel);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam int paymentId) throws BusinessException{
        return this.paymentService.delete(paymentId);
    }
}
