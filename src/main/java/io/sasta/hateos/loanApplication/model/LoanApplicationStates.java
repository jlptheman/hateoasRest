package io.sasta.hateos.loanApplication.model;

import java.util.HashMap;
import lombok.Getter;

public enum LoanApplicationStates {
  NEW                           (new HashMap<ClientActions, Command>(){{ put(ClientActions.SEND_APPLICATION_FORM, new Command(ClientActions.SEND_APPLICATION_FORM, ActionTypes.EVENT, RESTVerbs.POST));}}),

  SEND_LOAN_APPLICATION_ID      (new HashMap<ClientActions, Command>(){{ put(ClientActions.SEND_APPLICATION_FORM, new Command(ClientActions.SEND_APPLICATION_FORM, ActionTypes.EVENT, RESTVerbs.POST));}}),
  SEND_APPLICATION_FORM         (new HashMap<ClientActions, Command>(){{ put(ClientActions.SEND_APPLICATION_FORM, new Command(ClientActions.SEND_APPLICATION_FORM, ActionTypes.EVENT, RESTVerbs.POST));}}),
  SEND_DRIVERS_LICENSE          (new HashMap<ClientActions, Command>(){{ put(ClientActions.SEND_DRIVERS_LICENSE, new Command(ClientActions.SEND_DRIVERS_LICENSE, ActionTypes.EVENT, RESTVerbs.POST));
                                  put(ClientActions.CANCEL_APPLICATION, new Command(ClientActions.CANCEL_APPLICATION, ActionTypes.USER, RESTVerbs.DELETE));}}),
  SEND_UTILITY_BILL             (new HashMap<ClientActions, Command>(){{ put(ClientActions.SEND_UTILITY_BILL, new Command(ClientActions.SEND_UTILITY_BILL, ActionTypes.EVENT, RESTVerbs.POST));}}),
  SEND_PASSPORT                 (new HashMap<ClientActions, Command>(){{ put(ClientActions.SEND_PASSPORT, new Command(ClientActions.SEND_PASSPORT, ActionTypes.EVENT, RESTVerbs.POST));}}),

  READY_TO_PROCESS              (null),
  PROCESSED                     (new HashMap<ClientActions, Command>(){{ put(ClientActions.SEND_APPLICATION_FORM, new Command(ClientActions.SEND_APPLICATION_FORM, ActionTypes.EVENT, RESTVerbs.POST));}});

  @Getter
  private final HashMap<ClientActions, Command> commands;



  LoanApplicationStates(
      HashMap<ClientActions, Command> commands) {
    this.commands = commands;
  }
}
