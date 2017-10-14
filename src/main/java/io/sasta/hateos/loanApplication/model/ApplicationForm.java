package io.sasta.hateos.loanApplication.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

public class ApplicationForm extends Document {
  @Getter @Setter
  private long amount;

  public ApplicationForm(String data, DocumentType type, long amount) {
    super(data, type);
    this.amount = amount;
  }

  public static ApplicationForm parse(String data) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    HashMap<String,Object> map = mapper.readValue(data, HashMap.class);
    long amount = Long.parseLong(map.get("amount").toString());
    return new ApplicationForm(data, DocumentType.APPLICATION_FORM, amount);
  }
}
