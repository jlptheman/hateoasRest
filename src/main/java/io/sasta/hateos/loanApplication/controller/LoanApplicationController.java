package io.sasta.hateos.loanApplication.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import io.sasta.hateos.loanApplication.message.Response;
import io.sasta.hateos.loanApplication.model.LoanApplication;
import io.sasta.hateos.loanApplication.model.LoanApplicationRules;
import io.sasta.hateos.loanApplication.model.LoanApplicationState;
import io.sasta.hateos.loanApplication.repository.LoanApplicationRepo;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanApplicationController {

  @Autowired
  private LoanApplicationRepo repository;

  @RequestMapping(value = "v1/loan/new")
  public Response getNewLoanApplication() {
    LoanApplication loanApplication = new LoanApplication();
    loanApplication.setLoanId(UUID.randomUUID().toString());
    loanApplication.setCurrentState(LoanApplicationRules.getCurrentState(loanApplication));
    repository.insert(loanApplication);

    loanApplication.removeLinks();
    loanApplication.add(linkTo(methodOn(LoanApplicationController.class).getLoanApplicationByLoanApplicationId(loanApplication.getLoanId())).withRel(loanApplication.getCurrentState().toString()));;
    loanApplication
        .add(linkTo(methodOn(LoanApplicationController.class).setApplicationFormByApplicationId(
            loanApplication.getLoanId()))
            .withRel(loanApplication.getCurrentState().toString()));

    Response response = new Response("Done", loanApplication);
    return response;
  }

  @RequestMapping(value = "v1/loan/{id}")
  public Response getLoanApplicationByLoanApplicationId(@PathVariable String id) {
    LoanApplication loanApplication = repository.findOne(id);

    loanApplication.removeLinks();
    if(loanApplication.getCurrentState() == LoanApplicationState.SEND_APPLICATIONFORM){
      loanApplication.add(linkTo(methodOn(LoanApplicationController.class).getLoanApplicationByLoanApplicationId(loanApplication.getLoanId())).withRel(loanApplication.getCurrentState().toString()));;
      loanApplication
          .add(linkTo(methodOn(LoanApplicationController.class).setApplicationFormByApplicationId(
              loanApplication.getLoanId()))
              .withRel(loanApplication.getCurrentState().toString()));
    } else
    if(loanApplication.getCurrentState() == LoanApplicationState.SEND_DRIVERS_LICENSE){
      loanApplication.add(linkTo(methodOn(LoanApplicationController.class).getLoanApplicationByLoanApplicationId(loanApplication.getLoanId())).withRel(loanApplication.getCurrentState().toString()));;
      loanApplication
          .add(linkTo(methodOn(LoanApplicationController.class).setDriversLicenseByApplicationId(
              loanApplication.getLoanId()))
              .withRel(loanApplication.getCurrentState().toString()));
    } else
    if(loanApplication.getCurrentState() == LoanApplicationState.SEND_UTILITY_BILL){
      loanApplication.add(linkTo(methodOn(LoanApplicationController.class).getLoanApplicationByLoanApplicationId(loanApplication.getLoanId())).withRel(loanApplication.getCurrentState().toString()));;
      loanApplication
          .add(linkTo(methodOn(LoanApplicationController.class).setUtilityByLoadApplicationId(
              loanApplication.getLoanId()))
              .withRel(loanApplication.getCurrentState().toString()));
    } else
    if(loanApplication.getCurrentState() == LoanApplicationState.SEND_PASSPORT){
      loanApplication.add(linkTo(methodOn(LoanApplicationController.class).getLoanApplicationByLoanApplicationId(loanApplication.getLoanId())).withRel(loanApplication.getCurrentState().toString()));;
      loanApplication
          .add(linkTo(methodOn(LoanApplicationController.class).setPassportByApplicationId(
              loanApplication.getLoanId()))
              .withRel(loanApplication.getCurrentState().toString()));
    }

    Response response = new Response("Done", loanApplication);
    return response;
  }

  @RequestMapping(value = "v1/loan/applicationform/{id}")
  public Response setApplicationFormByApplicationId(@PathVariable String id) {
    Response response = new Response("Done", null);
    return response;
  }

  @RequestMapping(value = "v1/loan/driverslicense/{id}")
  public Response setDriversLicenseByApplicationId(@PathVariable String id) {
    Response response = new Response("Done", null);
    return response;
  }

  @RequestMapping(value = "v1/loan/utilitybill/{id}")
  public Response setUtilityByLoadApplicationId(@PathVariable String id) {
    Response response = new Response("Done", null);
    return response;
  }

  @RequestMapping(value = "v1/loan/passport/{id}")
  public Response setPassportByApplicationId(@PathVariable String id) {
    Response response = new Response("Done", null);
    return response;
  }

}