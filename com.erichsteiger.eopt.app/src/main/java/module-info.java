
module com.erichsteiger.eopt.app {
  requires org.slf4j;
  requires transitive javafx.graphics;
  requires javafx.controls;
  requires transitive javafx.media;
  requires com.kodedu.terminalfx;

  requires transitive com.erichsteiger.eopt.bo;
  requires com.erichsteiger.eopt.dao;

  exports com.erichsteiger.eopt.app;
}