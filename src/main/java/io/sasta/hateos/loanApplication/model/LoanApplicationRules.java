package io.sasta.hateos.loanApplication.model;

/**
 * Created by Dev on 14/10/2017.
 */
public class LoanApplicationRules {

  public static void recalculateLoanApplicationState(final LoanApplication loanApplication) {
    LoanApplicationStates state;
    if (loanApplication.getAmount() < 500) {
      if (loanApplication.getDocuments().containsKey(DocumentType.APPLICATION_FORM)) {
        state = LoanApplicationStates.READY_TO_PROCESS;
      } else {
        state = LoanApplicationStates.SEND_APPLICATION_FORM;
      }
    } else if (loanApplication.getAmount() < 5000) {
      if (loanApplication.getDocuments().containsKey(DocumentType.APPLICATION_FORM)) {
        if (loanApplication.getDocuments().containsKey(DocumentType.DRIVERS_LICENSE)) {
          if (loanApplication.getDocuments().containsKey(DocumentType.UTILITY_BILL)) {
            state = LoanApplicationStates.READY_TO_PROCESS;
          } else {
            state = LoanApplicationStates.SEND_UTILITY_BILL;
          }
        } else {
          state = LoanApplicationStates.SEND_DRIVERS_LICENSE;
        }
      } else {
        state = LoanApplicationStates.SEND_APPLICATION_FORM;
      }
    } else {
      if (loanApplication.getDocuments().containsKey(DocumentType.APPLICATION_FORM)) {
        if (loanApplication.getDocuments().containsKey(DocumentType.DRIVERS_LICENSE)) {
          if (loanApplication.getDocuments().containsKey(DocumentType.UTILITY_BILL)) {
            if (loanApplication.getDocuments().containsKey(DocumentType.PASSPORT)) {
              state = LoanApplicationStates.READY_TO_PROCESS;
            } else {
              state = LoanApplicationStates.SEND_PASSPORT;
            }
          } else {
            state = LoanApplicationStates.SEND_UTILITY_BILL;
          }
        } else {
          state = LoanApplicationStates.SEND_DRIVERS_LICENSE;
        }
      } else {
        state = LoanApplicationStates.SEND_APPLICATION_FORM;
      }
    }
    //loanApplication.setCurrentState(state);
  }

  public static Boolean isValidStateTransition(final LoanApplicationStates currentState, final LoanApplicationStates newState){

    return true;
  }
}
