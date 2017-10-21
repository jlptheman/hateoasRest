package io.sasta.hateos.loanApplication.message;

import io.sasta.hateos.loanApplication.model.ClientActions;
import io.sasta.hateos.loanApplication.model.Command;
import io.sasta.hateos.loanApplication.model.Document;
import io.sasta.hateos.loanApplication.model.DocumentType;
import io.sasta.hateos.loanApplication.model.LoanApplication;
import io.sasta.hateos.loanApplication.model.LoanApplicationResult;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response extends ResourceSupport {
  private String status;
  private LoanApplicationResult loanApplicationResult;
  private HashMap<ClientActions, Command> linkMetadata = new HashMap<>();
  private HashMap<DocumentType, Document> documents = new HashMap<>();
}
