package me.byteful.plugin.rosenews;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.util.Objects;

public class APIData {
  public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();

  private String createdAt;
  private String description;
  private String path;
  private String title;

  public APIData() {
  }

  public APIData(String createdAt, String description, String path, String title) {
    this.createdAt = createdAt;
    this.description = description;
    this.path = path;
    this.title = title;
  }

  public static APIData fromJSON(JsonElement json) {
    return GSON.fromJson(json, APIData.class);
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public String getDescription() {
    return description;
  }

  public String getPath() {
    return path;
  }

  public String getTitle() {
    return title;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    APIData apiData = (APIData) o;
    return Objects.equals(getCreatedAt(), apiData.getCreatedAt()) && Objects.equals(getDescription(), apiData.getDescription()) && Objects.equals(getPath(), apiData.getPath()) && Objects.equals(getTitle(), apiData.getTitle());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCreatedAt(), getDescription(), getPath(), getTitle());
  }

  @Override
  public String toString() {
    return "APIData{" +
      "createdAt='" + createdAt + '\'' +
      ", description='" + description + '\'' +
      ", path='" + path + '\'' +
      ", title='" + title + '\'' +
      '}';
  }
}
