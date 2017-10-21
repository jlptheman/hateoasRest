package io.sasta.hateos.loanApplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Command {
  private ClientActions id;
  private ActionTypes type;
  private RESTVerbs verb;
}
