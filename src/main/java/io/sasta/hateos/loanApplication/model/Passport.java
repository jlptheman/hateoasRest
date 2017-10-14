package io.sasta.hateos.loanApplication.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

public class Passport extends Document  {
  @Getter @Setter
  private String number;
  @Getter @Setter
  private String name;

  public Passport(String data, DocumentType type, String number, String name) {
    super(data, type);
    this.number = number;
    this.name = name;
  }

  public static Passport parse(String data) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    HashMap<String,Object> map = mapper.readValue(data, HashMap.class);
    String number = map.get("number").toString();
    String name = map.get("name").toString();
    return new Passport(data, DocumentType.PASSPORT, number, name);
  }
}
