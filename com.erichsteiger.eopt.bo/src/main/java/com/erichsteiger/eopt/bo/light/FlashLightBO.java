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
package com.erichsteiger.eopt.bo.light;

public class FlashLightBO {
  private Boolean lightOn = Boolean.FALSE;
  private String lightColor1 = "WHITE";
  private FlashLightType flashLightType = FlashLightType.SPOT;

  private double lightIntensity = 300.0;
  private int delay = 1000;

  public Boolean getLightOn() {
    return lightOn;
  }

  public void setLightOn(Boolean lightOn) {
    this.lightOn = lightOn;
  }

  public String getLightColor1() {
    return lightColor1;
  }

  public void setLightColor1(String lightColor1) {
    this.lightColor1 = lightColor1;
  }

  public FlashLightType getFlashLightType() {
    return flashLightType;
  }

  public void setFlashLightType(FlashLightType flashLightType) {
    this.flashLightType = flashLightType;
  }

  public double getLightIntensity() {
    return lightIntensity;
  }

  public void setLightIntensity(double lightIntensity) {
    this.lightIntensity = lightIntensity;
  }

  public int getDelay() {
    return delay;
  }

  public void setDelay(int delay) {
    this.delay = delay;
  }
}
