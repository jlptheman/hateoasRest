package io.sasta.hateos.loanApplication.model;

/**
 * Created by Dev on 14/10/2017.
 */
public enum LoanApplicationState {
  SEND_LOAN_APPLICATION_ID,
  SEND_APPLICATIONFORM,
  SEND_DRIVERS_LICENSE,
  SEND_UTILITY_BILL,
  SEND_PASSPORT,

  READ_TO_PROCESS,
  PROCESSED
}
