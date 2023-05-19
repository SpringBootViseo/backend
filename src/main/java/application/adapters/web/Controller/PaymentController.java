package application.adapters.web.Controller;

import application.adapters.mapper.mapperImpl.PaymentMapperImpl;
import application.adapters.web.presenter.PaymentDTO;
import application.domain.Payment;
import application.port.in.PaymentUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")

@RequestMapping("/payments")
public class PaymentController {
    private PaymentUseCase paymentUseCase;
    private PaymentMapperImpl paymentMapper;
    @PostMapping()
    ResponseEntity<PaymentDTO> createPayment(@Validated @RequestBody PaymentDTO paymentDTO){
        try{
            Payment payment=paymentUseCase.savePayment(paymentMapper.paymentDTOToPayment(paymentDTO));
            return new ResponseEntity<>(paymentMapper.paymentToPaymentDTO(payment),HttpStatus.OK);
        } catch (InvalidPropertiesFormatException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }

    }
    @GetMapping("/{id}")
    ResponseEntity<PaymentDTO> getPayment(@Validated @PathVariable(name = "id")UUID id){
        try{
            Payment payment=paymentUseCase.getPayement(id);
            return new ResponseEntity<>(paymentMapper.paymentToPaymentDTO(payment),HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }

    }
    @GetMapping("")
    ResponseEntity<List<PaymentDTO>> paymentList(){
        try{
            List<Payment> payments=paymentUseCase.listPayment();
            return new ResponseEntity<>(paymentMapper.listPaymentToListPaymentDTO(payments),HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }

    }
    @GetMapping("/user/{id}")
    ResponseEntity<List<PaymentDTO>> paymentListOfUser(@PathVariable(name = "id")String id){
        try{
            List<Payment> payments=paymentUseCase.listPayment(id);
            return new ResponseEntity<>(paymentMapper.listPaymentToListPaymentDTO(payments),HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @GetMapping("/montantSup/{montant}")
    ResponseEntity<List<PaymentDTO>> paymentListOfMontantSup(@PathVariable(name = "montant") long montant){
        try {
            List<Payment> payments=paymentUseCase.listPaymentMontantSuperieuAMontant(montant);
            return new ResponseEntity<>(paymentMapper.listPaymentToListPaymentDTO(payments),HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @GetMapping("/montantInf/{montant}")
    ResponseEntity<List<PaymentDTO>> paymentListOfMontantInf(@PathVariable(name = "montant") long montant){
        try {
            List<Payment> payments=paymentUseCase.listPaymentMontantInferieurAMontant(montant);
            return new ResponseEntity<>(paymentMapper.listPaymentToListPaymentDTO(payments),HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }

}
