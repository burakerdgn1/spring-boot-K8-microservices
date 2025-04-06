package com.burak.message.functions;

import com.burak.message.dto.AccountsMsgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

   private static final Logger log= LoggerFactory.getLogger(MessageFunctions.class);

   @Bean //in order to monitor this function by spring cloud functions
   public Function<AccountsMsgDto,AccountsMsgDto> email(){
     return accountsMsgDto -> {
       log.info("Sending email with the details: "+accountsMsgDto.toString());
       return accountsMsgDto;
     };
   }

  @Bean //in order to monitor this function by spring cloud functions
  public Function<AccountsMsgDto,Long> sms(){
    return accountsMsgDto -> {
      log.info("Sending sms with the details: "+accountsMsgDto.toString());
      return accountsMsgDto.accountNumber();
    };
  }


}
