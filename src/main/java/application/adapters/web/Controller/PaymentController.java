package application.adapters.web.Controller;

import application.adapters.mapper.mapperImpl.PaymentMapperImpl;
import application.adapters.web.presenter.PaymentDTO;
import application.domain.Payment;
import application.port.in.PaymentUseCase;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger= LoggerFactory.getLogger(PaymentController.class);
    @PostMapping()
    ResponseEntity<PaymentDTO> createPayment(@Validated @RequestBody PaymentDTO paymentDTO){
        try{
            logger.trace("Map paymentDTO "+paymentDTO.toString()+" to payment");
            logger.trace("Call save Payment function from Use Case");
            Payment payment=paymentUseCase.savePayment(paymentMapper.paymentDTOToPayment(paymentDTO));
            logger.trace("Map payment "+payment.toString()+" to paymentDTO");
            logger.debug("Map the save Payment from Use case to Post Mapper /payments with body "+paymentDTO.toString());
            return new ResponseEntity<>(paymentMapper.paymentToPaymentDTO(payment),HttpStatus.OK);
        } catch (InvalidPropertiesFormatException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }

    }
    @GetMapping("/{id}")
    ResponseEntity<PaymentDTO> getPayment(@Validated @PathVariable(name = "id")UUID id){
        try{
            logger.trace("Call get Payment function from Use Case with id: "+ id);

            Payment payment=paymentUseCase.getPayement(id);
            logger.trace("Map payment "+payment.toString()+" to paymentDTO");
            logger.debug("Map the get Payment from Use case to Get Mapper /payments/"+id);

            return new ResponseEntity<>(paymentMapper.paymentToPaymentDTO(payment),HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }

    }
    @GetMapping("")
    ResponseEntity<List<PaymentDTO>> paymentList(){
        try{
            logger.trace("Call list Payment function from Use Case");

            List<Payment> payments=paymentUseCase.listPayment();
            logger.trace("Map payment list "+payments.toString()+" to paymentDTO list");
            logger.debug("Map Paymentlist from Use case to Get Mapper /payments ");

            return new ResponseEntity<>(paymentMapper.listPaymentToListPaymentDTO(payments),HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }

    }
    @GetMapping("/user/{id}")
    ResponseEntity<List<PaymentDTO>> paymentListOfUser(@PathVariable(name = "id")String id){
        try{
            logger.trace("Call ListPayment  function from Use Case with id of user: "+ id);

            List<Payment> payments=paymentUseCase.listPayment(id);
            logger.trace("Map payment List "+payments.toString()+" to paymentDTO");
            logger.debug("Map the  PaymentList from Use case to Get Mapper /payments/user/"+id);

            return new ResponseEntity<>(paymentMapper.listPaymentToListPaymentDTO(payments),HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @GetMapping("/montantSup/{montant}")
    ResponseEntity<List<PaymentDTO>> paymentListOfMontantSup(@PathVariable(name = "montant") long montant){
        try {
            logger.trace(String.format("Call listPaymentMontantSuperieuAMontant function from Use Case with montant : "+montant));

            List<Payment> payments=paymentUseCase.listPaymentMontantSuperieuAMontant(montant);
            logger.trace("Map payment list "+payments.toString()+" to paymentDTO list");
            logger.debug(String.format("Map the paymentListOfMontantSup from Use case to Post Mapper /payments/montantSup/"+montant));
            return new ResponseEntity<>(paymentMapper.listPaymentToListPaymentDTO(payments),HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @GetMapping("/montantInf/{montant}")
    ResponseEntity<List<PaymentDTO>> paymentListOfMontantInf(@PathVariable(name = "montant") long montant){
        try {
            logger.trace(String.format("Call listPaymentMontantInferieurAMontant function from Use Case with montant : "+montant));

            List<Payment> payments=paymentUseCase.listPaymentMontantInferieurAMontant(montant);
            logger.trace("Map payment list "+payments.toString()+" to paymentDTO list");
            logger.debug(String.format("Map the paymentListOfMontantInferieur from Use case to Post Mapper /payments/montantInf/"+montant));

            return new ResponseEntity<>(paymentMapper.listPaymentToListPaymentDTO(payments),HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }

}