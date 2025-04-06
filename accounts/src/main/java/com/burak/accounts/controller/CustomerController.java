package com.burak.accounts.controller;

import com.burak.accounts.dto.CustomerDetailsDto;
import com.burak.accounts.dto.ErrorResponseDto;
import com.burak.accounts.service.ICustomersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.apache.hc.core5.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
  name = "REST API for Customers in Bank",
  description = "REST APIs in Bank to FETCH customer details"
)
@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CustomerController {

  private static final Logger logger= LoggerFactory.getLogger(CustomerController.class);



  private final ICustomersService iCustomersService;

  public CustomerController(ICustomersService iCustomersService){
    this.iCustomersService = iCustomersService;
  }

  @Operation(
    summary = "Fetch Customer Details REST API",
    description = "REST API to fetch Customer details based on a mobile number"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "HTTP Status OK"
    ),
    @ApiResponse(
      responseCode = "500",
      description = "HTTP Status Internal Server Error",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseDto.class)
      )
    )
  }
  )
  @GetMapping("/fetchCustomerDetails")
  public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestHeader("bank-correlation-id") String correlationId,
    @RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
    logger.debug("fetchCustomerDetails method started");
    CustomerDetailsDto customerDetailsDto = iCustomersService.fetchCustomerDetails(mobileNumber,correlationId);
    logger.debug("fetchCustomerDetails method ended");
    return ResponseEntity.status(HttpStatus.SC_OK).body(customerDetailsDto);

  }


}
