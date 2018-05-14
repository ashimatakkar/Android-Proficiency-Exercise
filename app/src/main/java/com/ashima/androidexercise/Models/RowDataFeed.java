package com.ashima.androidexercise.Models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RowDataFeed {
  @SerializedName("imageHref")
  @Expose
  private String imageHref;
  @SerializedName("description")
  @Expose
  private String description;
  @SerializedName("title")
  @Expose
  private String title;
  public void setImageHref(String imageHref){
   this.imageHref=imageHref;
  }
  public String getImageHref(){
   return imageHref;
  }
  public void setDescription(String description){
   this.description=description;
  }
  public String getDescription(){
   return description;
  }
  public void setTitle(String title){
   this.title=title;
  }
  public String getTitle(){
   return title;
  }
}