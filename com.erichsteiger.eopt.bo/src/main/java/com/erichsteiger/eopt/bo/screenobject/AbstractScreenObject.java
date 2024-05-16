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
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = LabelBO.class, name = "label"),
    @JsonSubTypes.Type(value = ImageBO.class, name = "image"),
    @JsonSubTypes.Type(value = TerminalViewBO.class, name = "terminal"),
    @JsonSubTypes.Type(value = MediaViewBO.class, name = "mediaView"),
    @JsonSubTypes.Type(value = MediaViewBO.class, name = "MediaViewBO") })
public abstract class AbstractScreenObject {
  private Position position = new Position();

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

}
