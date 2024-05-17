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

public class TerminalViewBO extends AbstractScreenObject {
  private Position maximizedPosition = new Position();
  private double mazimizedWidth = 1;
  private double width = 0.2;
  private double height = 0.2;
  private String title = "Demo";
  private String cmd = "";

  public TerminalViewBO() {
    // empty constructor for json serializer
  }

  @Override
  public String toString() {
    return "TerminalViewBO file: ";
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

  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCmd() {
    return cmd;
  }

  public void setCmd(String cmd) {
    this.cmd = cmd;
  }
}
