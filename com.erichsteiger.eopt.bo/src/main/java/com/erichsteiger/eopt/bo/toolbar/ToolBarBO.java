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
package com.erichsteiger.eopt.bo.toolbar;

import java.util.ArrayList;
import java.util.List;

public class ToolBarBO {
  private Boolean visible = Boolean.TRUE;
  private List<ToolButtonBO> toolButtons = new ArrayList<>();
  private Boolean autoHide = Boolean.FALSE;

  public Boolean getVisible() {
    return visible;
  }

  public void setVisible(Boolean visible) {
    this.visible = visible;
  }

  public List<ToolButtonBO> getToolButtons() {
    return toolButtons;
  }

  public void setToolButtons(List<ToolButtonBO> toolButtons) {
    this.toolButtons = toolButtons;
  }

  public Boolean getAutoHide() {
    return autoHide;
  }

  public void setAutoHide(Boolean autoHide) {
    this.autoHide = autoHide;
  }
}
