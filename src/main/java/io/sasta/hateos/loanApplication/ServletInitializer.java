package io.sasta.hateos.loanApplication;

import io.sasta.hateos.loanApplication.repository.LoanApplicationInMemoryRepo;
import io.sasta.hateos.loanApplication.repository.LoanApplicationRepo;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

public class ServletInitializer  extends SpringBootServletInitializer {
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(LoanApplicationMain.class);
  }
}