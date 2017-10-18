package io.sasta.hateos.loanApplication.message;

import io.sasta.hateos.loanApplication.model.LoanApplication;
import io.sasta.hateos.loanApplication.model.LoanApplicationResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response extends ResourceSupport {
  private String status;
  private LoanApplication loanApplication;
  private LoanApplicationResult loanApplicationResult;
}
