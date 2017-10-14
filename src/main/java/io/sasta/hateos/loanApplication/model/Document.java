package io.sasta.hateos.loanApplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Document {
  private String data;
  private DocumentType type;
  private String property1;
  private String property2;

  public static Document APPLICATION_FORM = new Document("", DocumentType.APPLICATION_FORM, "","");
  public static Document DRIVERS_LICENSE = new Document("", DocumentType.DRIVERS_LICENSE, "","");
  public static Document UTILITY_BILL = new Document("", DocumentType.UTILITY_BILL, "","");
  public static Document PASSPORT = new Document("", DocumentType.PASSPORT, "","");
  @Override
  public boolean equals(Object object)
  {
    boolean sameSame = false;

    if (object != null && object instanceof Document)
    {
      sameSame = this.type.equals(((Document) object).type);
    }

    return sameSame;
  }
}
