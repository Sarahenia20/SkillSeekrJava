module com.skillseekr {

    requires jdk.httpserver;
    requires javafx.fxml;
    requires javafx.controls;
    requires org.controlsfx.controls;
    requires mail;
    requires java.sql;
    requires fontawesomefx;
    requires java.desktop;
    requires org.apache.commons.io;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.auth;
    requires com.google.api.client.extensions.jetty.auth;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires com.google.api.services.gmail;
    requires org.apache.commons.codec;
    requires jbcrypt;
    requires java.prefs;
    requires twilio;


    opens com.skillseekr to javafx.fxml; // Opening the com.skillseekr package
    opens com.skillseekr.Offer to javafx.fxml;
    opens com.skillseekr.Hire to javafx.fxml;

    opens com.skillseekr.Claims to javafx.fxml;
    opens com.skillseekr.Projects to javafx.fxml;
    opens com.skillseekr.User to javafx.fxml;
    // Opening the com.skillseekr.Offer package

    opens com.skillseekr.Models.User to javafx.base;
    opens com.skillseekr.Models.Offers to javafx.base;
    opens com.skillseekr.Models.Hire to javafx.base;
    opens com.skillseekr.Models.Claims to javafx.base;


    exports com.skillseekr;
    exports com.skillseekr.Offer;
    exports com.skillseekr.Hire;
    exports com.skillseekr.Claims;
}