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
package com.erichsteiger.eopt.bo;

public class SystemInfo {

  public String javaVersion() {
    return System.getProperty("java.version");
  }

  public String javafxVersion() {
    return System.getProperty("javafx.version");
  }

  public String os() {
    return System.getProperty("os.name");
  }

  public String osVersion() {
    return System.getProperty("os.version");
  }

  public String osArch() {
    return System.getProperty("os.arch");
  }
}