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
package com.erichsteiger.eopt.bo.screenobject;

import com.erichsteiger.eopt.bo.Position;

public class MediaViewBO extends AbstractScreenObject {

  private String videoPath;
  private Boolean maximizeToPlay = Boolean.TRUE;
  private Boolean minimizeAfterPlay = Boolean.TRUE;
  private Boolean autoPlay = Boolean.FALSE;
  private Boolean autoRepeat = Boolean.FALSE;
  private int replaySpeed = 1;
  private double width = 0.2;
  private Position maximizedPosition = new Position();
  private double mazimizedWidth = 1;

  public MediaViewBO() {

  }

  public MediaViewBO(String videoPath) {
    this.setVideoPath(videoPath);
    getPosition().setX(0.75);
    getPosition().setY(0.1);
  }

  public String getVideoPath() {
    return videoPath;
  }

  public void setVideoPath(String videoPath) {
    this.videoPath = videoPath;
  }

  public Boolean getMaximizeToPlay() {
    return maximizeToPlay;
  }

  public void setMaximizeToPlay(Boolean maximizeToPlay) {
    this.maximizeToPlay = maximizeToPlay;
  }

  public int getReplaySpeed() {
    return replaySpeed;
  }

  public void setReplaySpeed(int replaySpeed) {
    this.replaySpeed = replaySpeed;
  }

  public Boolean getMinimizeAfterPlay() {
    return minimizeAfterPlay;
  }

  public void setMinimizeAfterPlay(Boolean minimizeAfterPlay) {
    this.minimizeAfterPlay = minimizeAfterPlay;
  }

  public Boolean getAutoPlay() {
    return autoPlay;
  }

  public void setAutoPlay(Boolean autoPlay) {
    this.autoPlay = autoPlay;
  }

  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public Position getMaximizedPosition() {
    return maximizedPosition;
  }

  public void setMaximizedPosition(Position maximizedPosition) {
    this.maximizedPosition = maximizedPosition;
  }

  public double getMazimizedWidth() {
    return mazimizedWidth;
  }

  public void setMazimizedWidth(double mazimizedWidth) {
    this.mazimizedWidth = mazimizedWidth;
  }

  public Boolean getAutoRepeat() {
    return autoRepeat;
  }

  public void setAutoRepeat(Boolean autoRepeat) {
    this.autoRepeat = autoRepeat;
  }

  @Override
  public String toString() {
    return "MediaViewBO file: " + videoPath;
  }
}
