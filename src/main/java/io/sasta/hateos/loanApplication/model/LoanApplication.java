package io.sasta.hateos.loanApplication.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoanApplication extends ResourceSupport {
  private String loanId;
  private long amount;
  private LoanApplicationState currentState = LoanApplicationState.SEND_APPLICATIONFORM;
  private HashMap<DocumentType, Document> documents = new HashMap<>();

  @Override
  public boolean equals(Object object)
  {
    boolean sameSame = false;

    if (object != null && object instanceof LoanApplication)
    {
      sameSame = this.loanId.equals(((LoanApplication) object).loanId);
    }

    return sameSame;
  }
}
