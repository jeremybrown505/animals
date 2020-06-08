package edu.cnm.deepdive.animals.model;

import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Animal {

  @Expose
  private String name;

  private Taxonomy taxonomy;

  private String location;

  private Speed speed;

  private String diet;

  private String lifespan;

  @Expose
  @SerializedName("image")
  private String url;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Taxonomy getTaxonomy() {
    return taxonomy;
  }

  public void setTaxonomy(Taxonomy taxonomy) {
    this.taxonomy = taxonomy;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Speed getSpeed() {
    return speed;
  }

  public void setSpeed(Speed speed) {
    this.speed = speed;
  }

  public String getDiet() {
    return diet;
  }

  public void setDiet(String diet) {
    this.diet = diet;
  }

  public String getLifespan() {
    return lifespan;
  }

  public void setLifespan(String lifespan) {
    this.lifespan = lifespan;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @NonNull
  @Override
  public String toString() {
    return getName();
  }
}
