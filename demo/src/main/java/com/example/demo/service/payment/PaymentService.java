package com.example.demo.service.payment;

import com.example.demo.dto.payment.CreatePaymentDto;
import com.example.demo.dto.payment.UpdatePaymentDto;
import com.example.demo.integration.payment.PaymentIntegrationService;
import com.example.demo.model.payment.Payment;
import com.example.demo.model.payment.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentIntegrationService paymentIntegrationServiceInt;

    public Payment createPayment(CreatePaymentDto source) throws PaymentServiceException{
        try {
            Payment payment = new Payment();

            //default parameters
            payment.setObjectTypeId(4L);
            payment.setStatus(PaymentStatus.PENDING);
            payment.setCreatedWhen(new Date());
            payment.setCancellationDate(new Date());
            payment.setDescription("DEFAULT_DESCRIPTION");

            //custom parameters
            payment.setParentId(source.getParentId()); // temp
            payment.setAmount(source.getAmount());
            payment.setCreatedBy(source.getCreatedBy());
            payment.setPaymentMethod(source.getPaymentMethod());
            payment.setName("payment-" + source.getAmount().toString() + "-" + (new SimpleDateFormat("yyyyMMddHHmmss").format(payment.getCreatedWhen())));

            try {
                paymentIntegrationServiceInt.createPayment(payment);
                return payment;
            } catch (PaymentIntegrationService.PaymentIntegrationException e){
                throw new PaymentServiceException(e);
            }

        }
        catch (Exception e){
            throw new PaymentServiceException(e);
        }

    }

    public Payment getPayment(Long id) throws PaymentServiceException {
        try{ return paymentIntegrationServiceInt.getPayment(id); }
        catch (Exception e){ throw new PaymentServiceException(e); }
    }

    public List<Payment> getBillingAccountPayments(Long accountId) throws PaymentServiceException {
        try{ return paymentIntegrationServiceInt.getBillingAccountPayments(accountId); }
        catch (Exception e){ throw new PaymentServiceException(e); }
    }

    public Payment updatePayment(UpdatePaymentDto source, Long id) throws PaymentServiceException {
        try{ return paymentIntegrationServiceInt.updatePayment(source, id); }
        catch (Exception e){ throw new PaymentServiceException(e); }
    }

    public Payment cancelPayment(Long id) throws PaymentServiceException {
        try{ return paymentIntegrationServiceInt.cancelPayment(id); }
        catch (Exception e){ throw new PaymentServiceException(e); }
    }

    public void deletePayment(Long id) throws PaymentServiceException {
        try{ paymentIntegrationServiceInt.deletePayment(id); }
        catch (Exception e){ throw new PaymentServiceException(e); }
    }

    public static class PaymentServiceException extends Exception {
        public PaymentServiceException(Exception cause) {
            super(cause.getMessage(), cause);
        }
    }
}
