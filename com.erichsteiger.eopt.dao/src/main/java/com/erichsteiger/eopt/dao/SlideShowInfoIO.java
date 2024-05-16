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
package com.erichsteiger.eopt.dao;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.erichsteiger.eopt.bo.Version;
import com.erichsteiger.eopt.bo.slideshow.SlideShowBO;
import com.erichsteiger.eopt.bo.slideshow.SlideShowType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;

public class SlideShowInfoIO extends AbstractIO {
  private static final Logger LOGGER = LoggerFactory.getLogger(SlideShowInfoIO.class);
  private static final Version CURRENT_VERSION = new Version("1.5");

  public SlideShowBO readJson(File file) {
    if (file.exists()) {
      try {
        SlideShowBO bo = mapper.readValue(file, SlideShowBO.class);
        return convertBO(bo);
      } catch (IOException e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    return null;
  }

  @SuppressWarnings("removal")
  private SlideShowBO convertBO(SlideShowBO bo) {
    if (bo.getVersion().compareTo(CURRENT_VERSION) < 0) {
      LOGGER.info("need to convert from version {}", bo.getVersion());
      bo.setVersion(CURRENT_VERSION);
      if (bo.getPresentationType() == SlideShowType.MYTH_SLIDE_SHOW) {
        bo.setPresentationType(SlideShowType.EXTENDED);
      }
    }
    return bo;
  }

  public void writeJsonFile(File file, SlideShowBO info) {
    try {
      mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
      mapper.writeValue(file, info);
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  public String toJson(SlideShowBO presentingFileInfo) {
    try {
      mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
      return mapper.writeValueAsString(presentingFileInfo);
    } catch (JsonProcessingException e) {
      LOGGER.error(e.getMessage(), e);
    }
    return null;
  }

  public SlideShowBO toBO(String text) {
    try {
      return mapper.readValue(text, SlideShowBO.class);
    } catch (JsonProcessingException e) {
      LOGGER.error(e.getMessage(), e);
    }
    return null;
  }

  public SlideShowBO createNewBO() {
    SlideShowBO bo = new SlideShowBO();
    bo.setVersion(CURRENT_VERSION);
    bo.getToolBar().setVisible(false);
    bo.getFooter().setFooterVisible(false);
    return bo;
  }

}
