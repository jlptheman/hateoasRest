package io.sasta.hateos.loanApplication.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import io.sasta.hateos.loanApplication.message.Response;
import io.sasta.hateos.loanApplication.model.ApplicationForm;
import io.sasta.hateos.loanApplication.model.Document;
import io.sasta.hateos.loanApplication.model.DriversLicense;
import io.sasta.hateos.loanApplication.model.LoanApplication;
import io.sasta.hateos.loanApplication.model.LoanApplicationRules;
import io.sasta.hateos.loanApplication.model.LoanApplicationState;
import io.sasta.hateos.loanApplication.model.Passport;
import io.sasta.hateos.loanApplication.model.UtilityBill;
import io.sasta.hateos.loanApplication.repository.LoanApplicationRepo;
import java.io.IOException;
import java.util.UUID;
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
    LoanApplication loanApplication = new LoanApplication();
    loanApplication.setLoanId(UUID.randomUUID().toString());
    repository.insert(loanApplication);

    loanApplication.add(linkTo(methodOn(LoanApplicationController.class).getLoanApplicationByLoanApplicationId(loanApplication.getLoanId())).withRel(loanApplication.getCurrentState().toString()));;
    loanApplication
        .add(linkTo(methodOn(LoanApplicationController.class).setApplicationFormByApplicationId(
            loanApplication.getLoanId(), ""))
            .withRel(loanApplication.getCurrentState().toString()));

    Response response = new Response("Done", loanApplication);
    return response;
  }

  @RequestMapping(value = "v1/loan/{id}")
  public Response getLoanApplicationByLoanApplicationId(@PathVariable String id) {
    LoanApplication loanApplication = repository.findOne(id);

    loanApplication.removeLinks();
    loanApplication.add(linkTo(methodOn(LoanApplicationController.class).getLoanApplicationByLoanApplicationId(loanApplication.getLoanId())).withRel(loanApplication.getCurrentState().toString()));
    addLinkBasedOnState(loanApplication);

    Response response = new Response("Done", loanApplication);
    return response;
  }

  @RequestMapping(value = "v1/loan/applicationform/{id}", method = RequestMethod.POST)
  public Response setApplicationFormByApplicationId(@PathVariable String id, @RequestBody String data) {
    ApplicationForm document = null;
    Response response = null;
    LoanApplication loanApplication = repository.findOne(id);
    try {
      document = ApplicationForm.parse(data);
      loanApplication.getDocuments().put(document.getType(), document);
      loanApplication.setAmount(document.getAmount());
      LoanApplicationRules.recalculateLoanApplicationState(loanApplication);
      loanApplication.removeLinks();
      loanApplication.add(linkTo(methodOn(LoanApplicationController.class).getLoanApplicationByLoanApplicationId(loanApplication.getLoanId())).withRel(loanApplication.getCurrentState().toString()));
      addLinkBasedOnState(loanApplication);
      response = new Response("Done", loanApplication);
    } catch (IOException e) {
      e.printStackTrace();
      response = new Response("Not Completed", loanApplication);
    }
    return response;
  }

  @RequestMapping(value = "v1/loan/driverslicense/{id}", method = RequestMethod.POST)
  public Response setDriversLicenseByApplicationId(@PathVariable String id, @RequestBody String data) {
    Document document = null;
    Response response = null;
    LoanApplication loanApplication = repository.findOne(id);
    try {
      document = DriversLicense.parse(data);
      loanApplication.getDocuments().put(document.getType(), document);
      response = new Response("Done", null);
    } catch (IOException e) {
      e.printStackTrace();
      response = new Response("Not Completed", loanApplication);
    }
    return response;
  }

  @RequestMapping(value = "v1/loan/utilitybill/{id}", method = RequestMethod.POST)
  public Response setUtilityByLoadApplicationId(@PathVariable String id, @RequestBody String data) {
    Document document = null;
    Response response = null;
    LoanApplication loanApplication = repository.findOne(id);
    try {
      document = UtilityBill.parse(data);
      loanApplication.getDocuments().put(document.getType(), document);
      response = new Response("Done", null);
    } catch (IOException e) {
      e.printStackTrace();
      response = new Response("Not Completed", loanApplication);
    }
    return response;
  }

  @RequestMapping(value = "v1/loan/passport/{id}", method = RequestMethod.POST)
  public Response setPassportByApplicationId(@PathVariable String id, @RequestBody String data) {
    Document document = null;
    Response response = null;
    LoanApplication loanApplication = repository.findOne(id);
    try {
      document = Passport.parse(data);
      loanApplication.getDocuments().put(document.getType(), document);
      response = new Response("Done", null);
    } catch (IOException e) {
      e.printStackTrace();
      response = new Response("Not Completed", loanApplication);
    }
    return response;
  }


  private void addLinkBasedOnState(final LoanApplication loanApplication) {
    if(loanApplication.getCurrentState() == LoanApplicationState.SEND_APPLICATIONFORM){
      loanApplication.add(linkTo(methodOn(LoanApplicationController.class).getLoanApplicationByLoanApplicationId(loanApplication.getLoanId())).withRel(loanApplication.getCurrentState().toString()));
      loanApplication
          .add(linkTo(methodOn(LoanApplicationController.class).setApplicationFormByApplicationId(
              loanApplication.getLoanId(), ""))
              .withRel(loanApplication.getCurrentState().toString()));
    } else
    if(loanApplication.getCurrentState() == LoanApplicationState.SEND_DRIVERS_LICENSE){
      loanApplication.add(linkTo(methodOn(LoanApplicationController.class).getLoanApplicationByLoanApplicationId(loanApplication.getLoanId())).withRel(loanApplication.getCurrentState().toString()));
      loanApplication
          .add(linkTo(methodOn(LoanApplicationController.class).setDriversLicenseByApplicationId(
              loanApplication.getLoanId(), ""))
              .withRel(loanApplication.getCurrentState().toString()));
    } else
    if(loanApplication.getCurrentState() == LoanApplicationState.SEND_UTILITY_BILL){
      loanApplication.add(linkTo(methodOn(LoanApplicationController.class).getLoanApplicationByLoanApplicationId(loanApplication.getLoanId())).withRel(loanApplication.getCurrentState().toString()));
      loanApplication
          .add(linkTo(methodOn(LoanApplicationController.class).setUtilityByLoadApplicationId(
              loanApplication.getLoanId(), ""))
              .withRel(loanApplication.getCurrentState().toString()));
    } else
    if(loanApplication.getCurrentState() == LoanApplicationState.SEND_PASSPORT){
      loanApplication.add(linkTo(methodOn(LoanApplicationController.class).getLoanApplicationByLoanApplicationId(loanApplication.getLoanId())).withRel(loanApplication.getCurrentState().toString()));
      loanApplication
          .add(linkTo(methodOn(LoanApplicationController.class).setPassportByApplicationId(
              loanApplication.getLoanId(), ""))
              .withRel(loanApplication.getCurrentState().toString()));
    }
  }

}