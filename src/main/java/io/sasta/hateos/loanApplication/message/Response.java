package io.sasta.hateos.loanApplication.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Response {
  private String status;
  private Object data;
}
