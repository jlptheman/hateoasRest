package io.sasta.hateos.loanApplication.repository;

import io.sasta.hateos.loanApplication.model.LoanApplication;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dev on 13/10/2017.
 */
public class LoanApplicationInMemoryRepo implements LoanApplicationRepo {
  private final List<LoanApplication> loanApplications = new ArrayList<>();

  public LoanApplicationInMemoryRepo() {
  }

  public List<LoanApplication> findAll() {
    return this.loanApplications;
  }

  public LoanApplication findOne(String id) {
    for (LoanApplication loanApplication : this.loanApplications) {
      if (loanApplication.getLoanId().equals(id)) {
        return loanApplication;
      }
    }
    return null;
  }

  public void insert(LoanApplication loanApplication) {
    if(!loanApplications.contains(loanApplication)){
      loanApplications.add(loanApplication);
    }
  }

  public void update(LoanApplication loanApplication) {
    if(loanApplications.contains(loanApplication)){
      loanApplications.remove(loanApplication);
      loanApplications.add(loanApplication);
    }
  }
}