package com.burak.accounts.service.impl;

import com.burak.accounts.dto.AccountsDto;
import com.burak.accounts.dto.CardsDto;
import com.burak.accounts.dto.CustomerDetailsDto;
import com.burak.accounts.dto.LoansDto;
import com.burak.accounts.entity.Accounts;
import com.burak.accounts.entity.Customer;
import com.burak.accounts.exception.ResourceNotFoundException;
import com.burak.accounts.mapper.AccountsMapper;
import com.burak.accounts.mapper.CustomerMapper;
import com.burak.accounts.repository.AccountsRepository;
import com.burak.accounts.repository.CustomerRepository;
import com.burak.accounts.service.ICustomersService;
import com.burak.accounts.service.client.CardsFeignClient;
import com.burak.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

  private AccountsRepository accountsRepository;
  private CustomerRepository customerRepository;
  private CardsFeignClient cardsFeignClient;
  private LoansFeignClient loansFeignClient;

  /**
   * @param mobileNumber - Input Mobile Number
   * @return Customer Details based on a given mobileNumber
   */
  @Override
  public CustomerDetailsDto fetchCustomerDetails(String mobileNumber,String correlationId) {
    Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
      () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
    );
    Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
      () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
    );

    CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
    customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

    ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId,mobileNumber);
    if(null!=loansDtoResponseEntity){
      customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
    }

    ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId,mobileNumber);
    if(null!=cardsDtoResponseEntity){
      customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
    }

    return customerDetailsDto;

  }
}
