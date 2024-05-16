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

import com.erichsteiger.eopt.bo.TextShadowEffect;
import com.erichsteiger.eopt.bo.screenobject.MediaViewBO;
import com.erichsteiger.eopt.bo.slide.SlideBO;
import com.fasterxml.jackson.databind.SerializationFeature;

public class SlideInfoIO extends AbstractIO {

  private static final Logger LOGGER = LoggerFactory.getLogger(SlideInfoIO.class);

  public SlideBO readJson(File file) {
    if (file.exists()) {
      try {
        // mapper.disable(null)
        SlideBO bo = mapper.readValue(file, SlideBO.class);
        bo = convertBO(bo);
        return bo;
      } catch (IOException e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    return null;
  }

  @SuppressWarnings("removal")
  private SlideBO convertBO(SlideBO bo) {
    if (bo.getVersion().compareTo(SlideBO.CURRENT_VERSION) < 0) {
      LOGGER.info("need to convert from version {}", bo.getVersion());
      bo.getScreenObjects().addAll(bo.getLabels());

      if (bo.getShowDemo().booleanValue()) {
        bo.getScreenObjects().add(new MediaViewBO(bo.getShowVideo()));
      }
      if (bo.getTitle() != null && bo.getTitle().getTextShadow().booleanValue()) {
        bo.getTitle().setShadowEffect(TextShadowEffect.BASIC);
      }
    }

    bo.setVersion(SlideBO.CURRENT_VERSION);

    // fields are not in use anymore
    bo.setShowVideo(null);
    bo.setShowDemo(null);
    return bo;

  }

  public void writeJsonFile(File file, SlideBO info) {
    try {
      mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
      mapper.writeValue(file, info);
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

}
