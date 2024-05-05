package com.skillseekr.Claims;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;


public class ClaimsController {

        @FXML
        private AnchorPane contentPane;

        @FXML
        private void handleUsersButtonClick() {
            try {
                AnchorPane showUserPane = FXMLLoader.load(getClass().getResource("/Claims.fxml"));
                contentPane.getChildren().setAll(showUserPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
