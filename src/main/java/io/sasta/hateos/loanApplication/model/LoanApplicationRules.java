package io.sasta.hateos.loanApplication.model;

/**
 * Created by Dev on 14/10/2017.
 */
public class LoanApplicationRules {

  public static void recalculateLoanApplicationState(final LoanApplication loanApplication) {
    LoanApplicationState state;
    if (loanApplication.getAmount() < 500) {
      if (loanApplication.getDocuments().containsKey(DocumentType.APPLICATION_FORM)) {
        state = LoanApplicationState.READ_TO_PROCESS;
      } else {
        state = LoanApplicationState.SEND_APPLICATIONFORM;
      }
    } else if (loanApplication.getAmount() < 5000) {
      if (loanApplication.getDocuments().containsKey(DocumentType.APPLICATION_FORM)) {
        if (loanApplication.getDocuments().containsKey(DocumentType.DRIVERS_LICENSE)) {
          if (loanApplication.getDocuments().containsKey(DocumentType.UTILITY_BILL)) {
            state = LoanApplicationState.READ_TO_PROCESS;
          } else {
            state = LoanApplicationState.SEND_UTILITY_BILL;
          }
        } else {
          state = LoanApplicationState.SEND_DRIVERS_LICENSE;
        }
      } else {
        state = LoanApplicationState.SEND_APPLICATIONFORM;
      }
    } else {
      if (loanApplication.getDocuments().containsKey(DocumentType.APPLICATION_FORM)) {
        if (loanApplication.getDocuments().containsKey(DocumentType.DRIVERS_LICENSE)) {
          if (loanApplication.getDocuments().containsKey(DocumentType.UTILITY_BILL)) {
            if (loanApplication.getDocuments().containsKey(DocumentType.PASSPORT)) {
              state = LoanApplicationState.READ_TO_PROCESS;
            } else {
              state = LoanApplicationState.SEND_PASSPORT;
            }
          } else {
            state = LoanApplicationState.SEND_UTILITY_BILL;
          }
        } else {
          state = LoanApplicationState.SEND_DRIVERS_LICENSE;
        }
      } else {
        state = LoanApplicationState.SEND_APPLICATIONFORM;
      }
    }
    loanApplication.setCurrentState(state);
  }
}
