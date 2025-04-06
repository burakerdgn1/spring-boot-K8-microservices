package com.burak.accounts.controller;

import com.burak.accounts.constants.AccountsConstants;
import com.burak.accounts.dto.AccountsContactInfoDto;
import com.burak.accounts.dto.CustomerDto;
import com.burak.accounts.dto.ErrorResponseDto;
import com.burak.accounts.dto.ResponseDto;
import com.burak.accounts.service.IAccountsService;
import com.burak.accounts.service.impl.AccountsServiceImpl;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Burak Erdogan
 */

@Tag(
  name = "CRUD REST APIs for Accounts in Bank",
  description = "CRUD REST APIs in Bank to CREATE, UPDATE, FETCH AND DELETE account details"
)
@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountsController {

  private static Logger logger= LoggerFactory.getLogger(AccountsController.class);

  private final AccountsServiceImpl iAccountsService;

  public AccountsController(AccountsServiceImpl iAccountsService) {
    this.iAccountsService = iAccountsService;
  }

  @Value("${build.version}")
  private String buildVersion;

  @Autowired
  private Environment environment;

  @Autowired
  private AccountsContactInfoDto accountsContactInfoDto;

  @Operation(
    summary = "Create Account REST API",
    description = "REST API to create new Customer &  Account inside Bank"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "201",
      description = "HTTP Status CREATED"
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
  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
    iAccountsService.createAccount(customerDto);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
  }

  @Operation(
    summary = "Fetch Account Details REST API",
    description = "REST API to fetch Customer &  Account details based on a mobile number"
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
  @GetMapping("/fetch")
  public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                                                         @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                         String mobileNumber) {
    CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK).body(customerDto);
  }

  @Operation(
    summary = "Update Account Details REST API",
    description = "REST API to update Customer &  Account details based on a account number"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "HTTP Status OK"
    ),
    @ApiResponse(
      responseCode = "417",
      description = "Expectation Failed"
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
  @PutMapping("/update")
  public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
    boolean isUpdated = iAccountsService.updateAccount(customerDto);
    if(isUpdated) {
      return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    }else{
      return ResponseEntity
        .status(HttpStatus.EXPECTATION_FAILED)
        .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
    }
  }

  @Operation(
    summary = "Delete Account & Customer Details REST API",
    description = "REST API to delete Customer &  Account details based on a mobile number"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "HTTP Status OK"
    ),
    @ApiResponse(
      responseCode = "417",
      description = "Expectation Failed"
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
  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                                          @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                          String mobileNumber) {
    boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
    if(isDeleted) {
      return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    }else{
      return ResponseEntity
        .status(HttpStatus.EXPECTATION_FAILED)
        .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
    }
  }

  @Operation(
    summary = "Get Build information",
    description = "Get Build information that is deployed into accounts microservice"
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
  @Retry(name = "getBuildInfo",fallbackMethod = "getBuildInfoFallback")
  @GetMapping("/build-info")
  public ResponseEntity<String> getBuildInfo() {
    logger.debug("getBuildInfo() method called");
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(buildVersion);
  }

  public ResponseEntity<String> getBuildInfoFallback(Throwable throwable) {
    logger.debug("getBuildInfo() method called");
    return ResponseEntity
      .status(HttpStatus.OK)
      .body("0.9");
  }

  @Operation(
    summary = "Get Java version",
    description = "Get Java versions details that is installed into accounts microservice"
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
  @RateLimiter(name = "getJavaVersion",fallbackMethod = "getJavaVersionFallback")
  @GetMapping("/java-version")
  public ResponseEntity<String> getJavaVersion() {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(environment.getProperty("JAVA_HOME"));
  }

  public ResponseEntity<String> getJavaVersionFallback(Throwable throwable) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body("Java17");
  }

  @Operation(
    summary = "Get Contact Info",
    description = "Contact Info details that can be reached out in case of any issues"
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
  @GetMapping("/contact-info")
  public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(accountsContactInfoDto);
  }


}
