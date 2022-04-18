package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.api.models.PaymentModel;
import com.turkcell.rentACar.api.models.UpdatePaymentModel;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.dtos.get.GetPaymentDto;
import com.turkcell.rentACar.entities.dtos.list.PaymentListDto;

import java.util.List;

public interface PaymentService {

    DataResult<List<PaymentListDto>> getAll();

    DataResult<GetPaymentDto> getById(int paymentId) throws BusinessException;

    DataResult<List<PaymentListDto>> getPaymentsByRental(int rentId) throws BusinessException;

    DataResult<List<PaymentListDto>> getPaymentByCustomer(int customerId) throws BusinessException;

    Result addPaymentForIndividualCustomer(PaymentModel paymentModel) throws BusinessException;

    Result addPaymentForCorporateCustomer(PaymentModel paymentModel) throws BusinessException;

    Result addPaymentForDelay(UpdatePaymentModel updatePaymentModel) throws BusinessException;

    Result delete(int paymentId) throws BusinessException;
}
