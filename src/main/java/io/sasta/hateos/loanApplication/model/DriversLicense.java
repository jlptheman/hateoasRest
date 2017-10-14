package io.sasta.hateos.loanApplication.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javafx.collections.MappingChange.Map;
import java.io.IOException;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

public class DriversLicense extends Document  {
  @Getter @Setter
  private String number;
  @Getter @Setter
  private String name;

  public DriversLicense(String data, DocumentType type, String number, String name) {
    super(data, type);
    this.number = number;
    this.name = name;
  }

  public static DriversLicense parse(String data) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    HashMap<String,Object> map = mapper.readValue(data, HashMap.class);
    String number = map.get("number").toString();
    String name = map.get("name").toString();
    return new DriversLicense(data, DocumentType.DRIVERS_LICENSE, number, name);
  }
}
