package io.sasta.hateos.loanApplication.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

public class UtilityBill extends Document  {
  @Getter @Setter
  private String description;
  @Getter @Setter
  private String name;

  public UtilityBill(String data, DocumentType type, String description, String name) {
    super(data, type);
    this.description = description;
    this.name = name;
  }

  public static UtilityBill parse(String data) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    HashMap<String,Object> map = mapper.readValue(data, HashMap.class);
    String description = map.get("description").toString();
    String name = map.get("name").toString();
    return new UtilityBill(data, DocumentType.UTILITY_BILL, description, name);
  }
}
