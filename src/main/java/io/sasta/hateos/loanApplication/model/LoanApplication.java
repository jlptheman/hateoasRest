package io.sasta.hateos.loanApplication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.EnumSet;
import java.util.HashMap;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineBuilder.Builder;

@Data
@JsonIgnoreProperties("stateMachine")
public class LoanApplication extends ResourceSupport {
  private String loanId;
  private long amount;
  private HashMap<DocumentType, Document> documents = new HashMap<>();
  private StateMachine<LoanApplicationStates, LoanApplicationEvents> stateMachine;

  public LoanApplication(){
    stateMachine = buildMachine();
    stateMachine.start();
  }
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

  private StateMachine<LoanApplicationStates, LoanApplicationEvents> buildMachine() {

    Builder<LoanApplicationStates, LoanApplicationEvents> builder = StateMachineBuilder.builder();

    try{
    builder.configureStates()
        .withStates()
        .initial(LoanApplicationStates.SEND_APPLICATION_FORM)
        .states(EnumSet.allOf(LoanApplicationStates.class));

    builder.configureTransitions()
        .withExternal()
        .source(LoanApplicationStates.SEND_APPLICATION_FORM).target(LoanApplicationStates.SEND_DRIVERS_LICENSE)
        .event(LoanApplicationEvents.APPLICATION_FORM_RECEIVED)
        .and()
        .withExternal()
        .source(LoanApplicationStates.SEND_DRIVERS_LICENSE).target(LoanApplicationStates.SEND_UTILITY_BILL)
        .event(LoanApplicationEvents.DRIVERS_LICENSE_RECEIVED)
        .and()
        .withExternal()
        .source(LoanApplicationStates.SEND_UTILITY_BILL).target(LoanApplicationStates.SEND_PASSPORT)
        .event(LoanApplicationEvents.UTILITY_BILL_RECEIVED)
        .and()
        .withExternal()
        .source(LoanApplicationStates.SEND_PASSPORT).target(LoanApplicationStates.READY_TO_PROCESS)
        .event(LoanApplicationEvents.PASSPORT_RECEIVED)
        .and()
        .withExternal()
        .source(LoanApplicationStates.READY_TO_PROCESS).target(LoanApplicationStates.PROCESSED)
        .event(LoanApplicationEvents.PROCESSED);

    return builder.build();
    } catch (Exception e){
      // Do noting.. not sure where to catch this and what to do about it
    }
    return null;
  }
}
