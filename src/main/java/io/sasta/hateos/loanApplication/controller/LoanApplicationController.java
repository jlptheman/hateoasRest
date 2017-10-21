package io.sasta.hateos.loanApplication.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import io.sasta.hateos.loanApplication.message.Response;
import io.sasta.hateos.loanApplication.model.ApplicationForm;
import io.sasta.hateos.loanApplication.model.ClientActions;
import io.sasta.hateos.loanApplication.model.Command;
import io.sasta.hateos.loanApplication.model.Document;
import io.sasta.hateos.loanApplication.model.DriversLicense;
import io.sasta.hateos.loanApplication.model.LoanApplication;
import io.sasta.hateos.loanApplication.model.LoanApplicationEvents;
import io.sasta.hateos.loanApplication.model.Passport;
import io.sasta.hateos.loanApplication.model.UtilityBill;
import io.sasta.hateos.loanApplication.repository.LoanApplicationRepo;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanApplicationController {

  @Autowired
  private LoanApplicationRepo repository;

  @RequestMapping(value = "v1/loan")
  public Response getNewLoanApplication() {
    Response response = createNewLoanApplication();
    return response;
  }

  private Response createNewLoanApplication() {
    LoanApplication loanApplication = new LoanApplication();
    repository.insert(loanApplication);
    Response response = new Response();
    response.setStatus("Done");
    addLinkBasedOnState(loanApplication, response);
    return response;
  }

  @RequestMapping(value = "v1/loan/{id}")
  public Response getLoanApplicationByLoanApplicationId(@PathVariable String id) {
    try {
      LoanApplication loanApplication = repository.findOne(id);
      Response response = new Response();
      response.setStatus("Done");
      addLinkBasedOnState(loanApplication, response);
      return response;
    } catch (Exception e) {
      return createNewLoanApplication();
    }
  }

  @RequestMapping(value = "v1/loan/applicationform/{id}", method = RequestMethod.POST)
  public Response setApplicationFormByApplicationId(@PathVariable String id, @RequestBody String data) {
    return processNewDocument(id, data, LoanApplicationEvents.APPLICATION_FORM_RECEIVED);
  }

  @RequestMapping(value = "v1/loan/driverslicense/{id}", method = RequestMethod.POST)
  public Response setDriversLicenseByApplicationId(@PathVariable String id, @RequestBody String data) {
    return processNewDocument(id, data, LoanApplicationEvents.DRIVERS_LICENSE_RECEIVED);
  }

  @RequestMapping(value = "v1/loan/utilitybill/{id}", method = RequestMethod.POST)
  public Response setUtilityByLoadApplicationId(@PathVariable String id, @RequestBody String data) {
    return processNewDocument(id, data, LoanApplicationEvents.UTILITY_BILL_RECEIVED);
  }

  @RequestMapping(value = "v1/loan/passport/{id}", method = RequestMethod.POST)
  public Response setPassportByApplicationId(@PathVariable String id, @RequestBody String data) {
    return processNewDocument(id, data, LoanApplicationEvents.PASSPORT_RECEIVED);
  }

  @RequestMapping(value = "v1/loan/{id}", method = RequestMethod.DELETE)
  public Response deleteLoanApplication(@PathVariable String id) {
    repository.deleteOne(id);
    return createNewLoanApplication();
  }

  private Response processNewDocument(final String id, final String data, final LoanApplicationEvents loanApplicationEvent) {
    Document document = null;
    LoanApplication loanApplication = repository.findOne(id);
    Response response = new Response();
    if(loanApplication.getStateMachine().sendEvent(loanApplicationEvent)) {
      try {
        if(loanApplicationEvent == LoanApplicationEvents.APPLICATION_FORM_RECEIVED) {
          document = ApplicationForm.parse(data);
        } else if(loanApplicationEvent == LoanApplicationEvents.DRIVERS_LICENSE_RECEIVED) {
          document = DriversLicense.parse(data);
        } else if(loanApplicationEvent == LoanApplicationEvents.UTILITY_BILL_RECEIVED) {
          document = UtilityBill.parse(data);
        } else if(loanApplicationEvent == LoanApplicationEvents.PASSPORT_RECEIVED) {
          document = Passport.parse(data);
        }
        loanApplication.getDocuments().put(document.getType(), document);
        repository.update(loanApplication);
        response.setDocuments(loanApplication.getDocuments());
        addLinkBasedOnState(loanApplication, response);
        response.setStatus("Done");
      } catch (IOException e) {
        e.printStackTrace();
        response = createNewLoanApplication();
        response.setStatus("Not Completed: " + e.getMessage());
      }
    }else {
      response = createNewLoanApplication();
      response.setStatus("Invalid Action");
    }
    return response;
  }

  private void addLinkBasedOnState(final LoanApplication loanApplication, final Response response) {
    response.setLinkMetadata(loanApplication.getStateMachine().getState().getId().getCommands());
    for (Command command : loanApplication.getStateMachine().getState().getId().getCommands().values()) {
      if(command.getId() == ClientActions.SEND_APPLICATION_FORM) {
        response
            .add(linkTo(methodOn(LoanApplicationController.class).setApplicationFormByApplicationId(
                loanApplication.getLoanId(), ""))
                .withRel(command.getId().toString()));
      } else if(command.getId() == ClientActions.SEND_UTILITY_BILL) {
        response
            .add(linkTo(methodOn(LoanApplicationController.class).setUtilityByLoadApplicationId(
                loanApplication.getLoanId(), ""))
                .withRel(command.getId().toString()));
      } else if(command.getId() == ClientActions.SEND_DRIVERS_LICENSE) {
        response
            .add(linkTo(methodOn(LoanApplicationController.class).setDriversLicenseByApplicationId(
                loanApplication.getLoanId(), ""))
                .withRel(command.getId().toString()));
      } else if(command.getId() == ClientActions.SEND_PASSPORT) {
        response
            .add(linkTo(methodOn(LoanApplicationController.class).setPassportByApplicationId(
                loanApplication.getLoanId(), ""))
                .withRel(command.getId().toString()));
      } else if(command.getId() == ClientActions.CANCEL_APPLICATION) {
        response
            .add(linkTo(methodOn(LoanApplicationController.class).deleteLoanApplication(
                loanApplication.getLoanId()))
                .withRel(command.getId().toString()));
      }
    }
  }


}