package models;

import java.io.File;

public class User {

  public int id;
  public String name;
  public File photo;

  public User() {
  }

  public User(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public User(int id, String name, File photo) {
    this.id = id;
    this.name = name;
    this.photo = photo;
  }

}
