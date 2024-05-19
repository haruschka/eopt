package com.erichsteiger.eopt.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.erichsteiger.eopt.bo.slide.SlideBO;
import com.erichsteiger.eopt.bo.slideshow.SlideShowBO;
import com.erichsteiger.eopt.dao.SlideShowInfoIO;

public class PresentingFileHandler {
  private static final String SLIDES_SUB_DIRECTORY = "slides";
  private static final String SLIDESHOW_BO_FILE_NAME = "slideshow.bo";
  private static final Logger LOGGER = LoggerFactory.getLogger(PresentingFileHandler.class);
  private SlideShowInfoIO io = new SlideShowInfoIO();

  public File createTempFiles(SlideShowBO bo, File currentWorkFile) {
    File tempDir = createTempDir();

    io.writeJsonFile(
        new File(tempDir.getAbsolutePath() + File.separator + SLIDESHOW_BO_FILE_NAME), bo);
    File tempSlideDir = new File(tempDir.getAbsolutePath() + File.separator + SLIDES_SUB_DIRECTORY);
    tempSlideDir.mkdir();
    for (SlideBO slide : bo.getSlides()) {
      slide.setBackgroundImagePath(copySlideToTempDir(
          new File(currentWorkFile.getParentFile().getParentFile().getAbsolutePath() + File.separator
              + slide.getBackgroundImagePath()),
          tempSlideDir));

    }
    return tempDir;
  }

  public String copySlideToTempDir(File sourceFile, File tempSlideDir) {
    File targetFile = new File(
        tempSlideDir.getAbsolutePath() + File.separator + SLIDES_SUB_DIRECTORY + File.separator + sourceFile.getName());
    if (targetFile.exists()) {
      targetFile = new File(tempSlideDir.getAbsolutePath() + File.separator + SLIDES_SUB_DIRECTORY + File.separator
          + UUID.randomUUID() + sourceFile.getName());
    }
    Path copied = Paths.get(targetFile.toURI());
    Path originalPath = sourceFile.toPath();
    LOGGER.info("copy file from {} to {}", originalPath, copied);
    try {
      Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      LOGGER.error("cannot copy file {} {} {}", originalPath, copied, e);
    }
    return relativePath(targetFile, tempSlideDir);
  }

  /**
   * primitive implementation to get relative path. Works only for files in
   * subdirectory of parentFile
   */
  private String relativePath(File targetFile, File parentFile) {
    return targetFile.toString().replace(parentFile.getAbsolutePath(), ".");
  }

  private File createTempDir() {
    try {
      File tempDir = Files.createTempDirectory("eopt-").toFile();
      tempDir.deleteOnExit();
      return tempDir;
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
    }
    return null;
  }

  public void createPresentingFile(File tempDir, String targetFileName) {
    try {
      FileOutputStream fos = new FileOutputStream(targetFileName);
      ZipOutputStream zipOut = new ZipOutputStream(fos);

      for (File fileToZip : tempDir.listFiles()) {
        zipFile(fileToZip, fileToZip.getName(), zipOut);
      }

      zipOut.close();
      fos.close();
    } catch (Exception e) {
      LOGGER.error("cannot zip file on temp dir {}", tempDir.getAbsolutePath());
    }
  }

  private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
    if (fileToZip.isHidden()) {
      return;
    }
    if (fileToZip.isDirectory()) {
      if (fileName.endsWith("/")) {
        zipOut.putNextEntry(new ZipEntry(fileName));
        zipOut.closeEntry();
      } else {
        zipOut.putNextEntry(new ZipEntry(fileName + "/"));
        zipOut.closeEntry();
      }
      File[] children = fileToZip.listFiles();
      for (File childFile : children) {
        zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
      }
      return;
    }
    FileInputStream fis = new FileInputStream(fileToZip);
    ZipEntry zipEntry = new ZipEntry(fileName);
    zipOut.putNextEntry(zipEntry);
    byte[] bytes = new byte[1024];
    int length;
    while ((length = fis.read(bytes)) >= 0) {
      zipOut.write(bytes, 0, length);
    }
    fis.close();
  }

  public File openFile(File file) {
    File tempDir = createTempDir();
    try (ZipInputStream zis = new ZipInputStream(new FileInputStream(file))) {
      ZipEntry zipEntry = zis.getNextEntry();
      while (zipEntry != null) {
        unzipEntry(zipEntry, tempDir, zis);
        zipEntry = zis.getNextEntry();
      }

      zis.closeEntry();
    } catch (Exception e) {
      LOGGER.error("cannot unzip file {}", file, e);
    }
    return tempDir;
  }

  private void unzipEntry(ZipEntry zipEntry, File destDir, ZipInputStream zis) throws IOException {
    byte[] buffer = new byte[1024];
    File newFile = newFile(destDir, zipEntry);
    if (zipEntry.isDirectory()) {
      if (!newFile.isDirectory() && !newFile.mkdirs()) {
        throw new IOException("Failed to create directory " + newFile);
      }
    } else {
      File parent = newFile.getParentFile();
      if (!parent.isDirectory() && !parent.mkdirs()) {
        throw new IOException("Failed to create directory " + parent);
      }

      try (FileOutputStream fos = new FileOutputStream(newFile)) {
        int len;
        while ((len = zis.read(buffer)) > 0) {
          fos.write(buffer, 0, len);
        }
      }
    }
  }

  public File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
    File destFile = new File(destinationDir, zipEntry.getName());

    String destDirPath = destinationDir.getCanonicalPath();
    String destFilePath = destFile.getCanonicalPath();

    if (!destFilePath.startsWith(destDirPath + File.separator)) {
      throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
    }

    return destFile;
  }

  public SlideShowBO readSlidShowBO(File tempDir) {
    return io.readJson(new File(tempDir.getAbsolutePath() + File.separator + SLIDESHOW_BO_FILE_NAME));
  }
}
