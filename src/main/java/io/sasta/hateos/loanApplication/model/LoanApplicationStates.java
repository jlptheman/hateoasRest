package io.sasta.hateos.loanApplication.model;

/**
 * Created by Dev on 14/10/2017.
 */
public enum LoanApplicationStates {
  NEW,

  SEND_LOAN_APPLICATION_ID,
  SEND_APPLICATION_FORM,
  SEND_DRIVERS_LICENSE,
  SEND_UTILITY_BILL,
  SEND_PASSPORT,

  READY_TO_PROCESS,
  PROCESSED
}
