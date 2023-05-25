module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires jakarta.persistence;
    requires com.google.gson;
    requires org.hibernate.orm.core;
    requires annotations;

    opens application to javafx.fxml;
    opens modelo.VO;
    opens util;

    exports application;
}