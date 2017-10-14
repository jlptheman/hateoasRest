package io.sasta.hateos.loanApplication.model;

/**
 * Created by Dev on 14/10/2017.
 */
public class LoanApplicationRules {

  public static LoanApplicationState getCurrentState(final LoanApplication loanApplication) {
    LoanApplicationState state = LoanApplicationState.SEND_APPLICATIONFORM;
    if (loanApplication.getAmount() < 500) {
      if (loanApplication.getEntities().contains(Document.APPLICATION_FORM)) {
        state = LoanApplicationState.READ_TO_PROCESS;
      } else {
        state = LoanApplicationState.SEND_APPLICATIONFORM;
      }
    } else if (loanApplication.getAmount() < 5000) {
      if (loanApplication.getEntities().contains(Document.APPLICATION_FORM)) {
        if (loanApplication.getEntities().contains(Document.DRIVERS_LICENSE)) {
          if (loanApplication.getEntities().contains(Document.UTILITY_BILL)) {
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
      if (loanApplication.getEntities().contains(Document.APPLICATION_FORM)) {
        if (loanApplication.getEntities().contains(Document.DRIVERS_LICENSE)) {
          if (loanApplication.getEntities().contains(Document.UTILITY_BILL)) {
            if (loanApplication.getEntities().contains(Document.PASSPORT)) {
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
    return state;
  }
}
