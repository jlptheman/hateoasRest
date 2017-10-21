package io.sasta.hateos.loanApplication.repository;

import io.sasta.hateos.loanApplication.model.LoanApplication;
import java.util.List;

public interface LoanApplicationRepo {
  List<LoanApplication> findAll();
  LoanApplication findOne(String id);
  void insert(LoanApplication loanApplication);
  void update(LoanApplication loanApplication);

  void deleteOne(String id);
}
