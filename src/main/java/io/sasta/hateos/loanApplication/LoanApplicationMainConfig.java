package io.sasta.hateos.loanApplication;

import io.sasta.hateos.loanApplication.repository.LoanApplicationInMemoryRepo;
import io.sasta.hateos.loanApplication.repository.LoanApplicationRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoanApplicationMainConfig {
  @Bean
  public LoanApplicationRepo LoanApplicationRepo(){
    return new LoanApplicationInMemoryRepo();
  }
}
