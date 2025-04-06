package com.burak.accounts.service.client;

import com.burak.accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "loans",url = "http://loans:8090",fallback = LoansFallback.class)
@Primary
public interface LoansFeignClient {
  @GetMapping(value = "/api/fetch",consumes = "application/json")
  public ResponseEntity<LoansDto> fetchLoanDetails(@RequestHeader("bank-correlation-id") String correlationId,  @RequestParam String mobileNumber);

}
