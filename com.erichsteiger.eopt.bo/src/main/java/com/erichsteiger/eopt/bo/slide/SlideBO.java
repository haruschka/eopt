/*
 * Copyright 2024 Erich Steiger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.erichsteiger.eopt.bo.slide;

import java.util.ArrayList;
import java.util.List;

import com.erichsteiger.eopt.bo.Version;
import com.erichsteiger.eopt.bo.screenobject.AbstractScreenObject;
import com.erichsteiger.eopt.bo.screenobject.LabelBO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class SlideBO {
  public static final Version CURRENT_VERSION = new Version("1.5");

  private Version version = CURRENT_VERSION;
  private Boolean showDemo = Boolean.FALSE;
  private String showVideo = "";
  private LabelBO title = new LabelBO();
  private LabelBO subtitle;

  private Boolean showFooter = Boolean.FALSE;
  private Boolean showSubtitle = Boolean.FALSE;
  private Boolean showTitle = Boolean.FALSE;
  private List<AbstractScreenObject> screenObjects = new ArrayList<>();

  public LabelBO getTitle() {
    return title;
  }

  public void setTitle(LabelBO title) {
    this.title = title;
  }

  public Boolean getShowDemo() {
    return showDemo;
  }

  public void setShowDemo(Boolean showDemo) {
    this.showDemo = showDemo;
  }

  public String getShowVideo() {
    return showVideo;
  }

  public void setShowVideo(String showVideo) {
    this.showVideo = showVideo;
  }

  public Boolean getShowFooter() {
    return showFooter;
  }

  public void setShowFooter(Boolean showFooter) {
    this.showFooter = showFooter;
  }

  public Boolean getShowSubtitle() {
    return showSubtitle;
  }

  public void setShowSubtitle(Boolean showSubtitle) {
    this.showSubtitle = showSubtitle;
  }

  public Boolean getShowTitle() {
    return showTitle;
  }

  public void setShowTitle(Boolean showTitle) {
    this.showTitle = showTitle;
  }

  public List<AbstractScreenObject> getScreenObjects() {
    return screenObjects;
  }

  public void setScreenObjects(List<AbstractScreenObject> screenObjects) {
    this.screenObjects = screenObjects;
  }

  public Version getVersion() {
    return version;
  }

  public void setVersion(Version version) {
    this.version = version;
  }

  public LabelBO getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(LabelBO subtitle) {
    this.subtitle = subtitle;
  }

}
