package com.yurtar.subman.sidebar;

public enum Page {
  HOME("Home"),
  SUBSCRIPTIONS("Subscriptions"),
  SETTINGS("Settings");

  private final String title;
  Page(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }
}
