package ru.myrating.application.service.dto.payment;

public class PaymentResultDbDTO {
    private PaymentUserDTO paymentUserDTO;
    private PaymentDetailsDTO paymentDetailsDTO;

    public PaymentResultDbDTO(PaymentUserDTO paymentUserDTO, PaymentDetailsDTO paymentDetailsDTO) {
        this.paymentUserDTO = paymentUserDTO;
        this.paymentDetailsDTO = paymentDetailsDTO;
    }

    public PaymentUserDTO getPaymentUserDTO() {
        return paymentUserDTO;
    }

    public void setPaymentUserDTO(PaymentUserDTO paymentUserDTO) {
        this.paymentUserDTO = paymentUserDTO;
    }

    public PaymentDetailsDTO getPaymentDetailsDTO() {
        return paymentDetailsDTO;
    }

    public void setPaymentDetailsDTO(PaymentDetailsDTO paymentDetailsDTO) {
        this.paymentDetailsDTO = paymentDetailsDTO;
    }
}
