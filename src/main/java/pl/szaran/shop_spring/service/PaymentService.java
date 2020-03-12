package pl.szaran.shop_spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szaran.shop_spring.model.Payment;
import pl.szaran.shop_spring.model.dto.PaymentDTO;
import pl.szaran.shop_spring.model.enums.EPayment;
import pl.szaran.shop_spring.repository.PaymentRepository;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public void addPaymentsToDB() {

        var availablePayments = EnumSet.allOf(EPayment.class);

        var dbPayments = getEPayments();

        for (EPayment p : availablePayments
        ) {
            Payment payment;

            if (!dbPayments.contains(p)) {

                PaymentDTO paymentDTO = PaymentDTO.builder().payment(p).build();
                payment = ModelMapper.fromPaymentDTOToPayment(paymentDTO);
                paymentRepository.save(payment);
                System.out.println("PAYMENT " + p.name() + " ADDED TO DB");
            }
        }
    }

    public List<PaymentDTO> getPayments() {
        return paymentRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromPaymentToPaymentDTO)
                .collect(Collectors.toList());
    }

    public List<EPayment> getEPayments() {
        return paymentRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromPaymentToPaymentDTO)
                .map(p -> p.getPayment())
                .collect(Collectors.toList());
    }
}
