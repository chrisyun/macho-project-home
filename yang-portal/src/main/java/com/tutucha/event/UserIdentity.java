package com.tutucha.event;

public class UserIdentity {
  private UserIdentityType type = null;
  
  private String id = null;

  public UserIdentity() {
    super();
  }

  public UserIdentity(UserIdentityType type, String id) {
    super();
    this.type = type;
    this.id = id;
  }

  public UserIdentityType getType() {
    return type;
  }

  public void setType(UserIdentityType type) {
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "UserIdentity [type=" + type + ", id=" + id + "]";
  }
  

}
