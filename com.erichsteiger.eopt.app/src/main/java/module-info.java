
module eopt {
  requires org.slf4j;
  requires transitive javafx.graphics;
  requires javafx.controls;
  requires eopt.bo;
  requires eopt.dao;

  exports com.erichsteiger.eopt.app;
}