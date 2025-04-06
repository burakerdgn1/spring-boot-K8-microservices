package com.burak.accounts.service.client;

import com.burak.accounts.dto.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cards",url = "http://cards:9000", fallback = CardsFallback.class)
@Primary
public interface CardsFeignClient {

  @GetMapping(value = "/api/fetch",consumes = "application/json")
  public ResponseEntity<CardsDto> fetchCardDetails(@RequestHeader("bank-correlation-id") String correlationId, @RequestParam String mobileNumber);

}
