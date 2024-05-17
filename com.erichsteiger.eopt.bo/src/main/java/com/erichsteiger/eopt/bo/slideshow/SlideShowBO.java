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
package com.erichsteiger.eopt.bo.slideshow;

import java.util.List;

import com.erichsteiger.eopt.bo.Version;
import com.erichsteiger.eopt.bo.light.FlashLightBO;
import com.erichsteiger.eopt.bo.screenobject.LabelBO;
import com.erichsteiger.eopt.bo.toolbar.ToolBarBO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
@JsonInclude(Include.NON_NULL)
public class SlideShowBO {
  private Version version = new Version("2.0");

  private SlideShowType presentationType;
  private LabelBO title;
  private LabelBO subtitle;
  private String author;

  private SlideFooter footer = new SlideFooter();

  private Boolean startFullscreen = Boolean.TRUE;
  private Boolean showTitle = Boolean.TRUE;
  private Boolean showSubtitle = Boolean.TRUE;
  private FlashLightBO flashLight = new FlashLightBO();

  private ToolBarBO toolBar = new ToolBarBO();
  private String slidesDir = "slides";
  private SlideTransitionType slideTransitionType = SlideTransitionType.MOVE_HORIZONTALY;

  private Boolean showHiddenStamps = null;

  private List<String> childPresentations = null;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private boolean changed = false;

  public boolean isChanged() {
    return changed;
  }

  public void setChanged(boolean changed) {
    this.changed = changed;
  }

  public void setShowHiddenStamps(Boolean showHiddenStamps) {
    if (showHiddenStamps != null && showHiddenStamps.booleanValue()) {
      this.showHiddenStamps = showHiddenStamps;
    } else {
      this.showHiddenStamps = false;
    }
  }

  public SlideShowType getPresentationType() {
    return presentationType;
  }

  public void setPresentationType(SlideShowType presentationType) {
    this.presentationType = presentationType;
  }

  public LabelBO getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(LabelBO subtitle) {
    this.subtitle = subtitle;
  }

  public LabelBO getTitle() {
    return title;
  }

  public void setTitle(LabelBO title) {
    this.title = title;
  }

  public SlideFooter getFooter() {
    return footer;
  }

  public void setFooter(SlideFooter footer) {
    this.footer = footer;
  }

  @Override
  public String toString() {
    return String.format("title '%s', subtitle '%s'", title.getText(), subtitle.getText());
  }

  public Boolean getStartFullscreen() {
    return startFullscreen;
  }

  public void setStartFullscreen(Boolean startFullscreen) {
    this.startFullscreen = startFullscreen;
  }

  public Boolean getShowTitle() {
    return showTitle;
  }

  public void setShowTitle(Boolean showTitle) {
    this.showTitle = showTitle;
  }

  public ToolBarBO getToolBar() {
    return toolBar;
  }

  public void setToolBar(ToolBarBO toolBar) {
    this.toolBar = toolBar;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public FlashLightBO getFlashLight() {
    return flashLight;
  }

  public void setFlashLight(FlashLightBO flashLight) {
    this.flashLight = flashLight;
  }

  public Boolean getShowSubtitle() {
    return showSubtitle;
  }

  public void setShowSubtitle(Boolean showSubtitle) {
    this.showSubtitle = showSubtitle;
  }

  public Version getVersion() {
    return version;
  }

  public void setVersion(Version version) {
    this.version = version;
  }

  public void setSlidesDir(String slidesDir) {
    this.slidesDir = slidesDir;
  }

  public String getSlidesDir() {
    return slidesDir;
  }

  public Boolean getShowHiddenStamps() {
    return showHiddenStamps;
  }

  public List<String> getChildPresentations() {
    return childPresentations;
  }

  public void setChildPresentations(List<String> childPresentations) {
    this.childPresentations = childPresentations;
  }

  public boolean rewriteJson() {
    return false;
  }

  public SlideTransitionType getSlideTransitionType() {
    return slideTransitionType;
  }

  public void setSlideTransitionType(SlideTransitionType slideTransitionType) {
    this.slideTransitionType = slideTransitionType;
  }

}
