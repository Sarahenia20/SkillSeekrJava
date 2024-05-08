package com.skillseekr.Projects;

import com.skillseekr.Models.User.User;
import com.skillseekr.Models.projet.Projet;
import com.skillseekr.Services.projets.RatingService;
import com.skillseekr.Services.projets.ServiceProjet;
import com.skillseekr.User.SessionManager;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class MesProjetsController implements Initializable {
    @javafx.fxml.FXML
    private ScrollPane scrollPane;
    @javafx.fxml.FXML
    private TextField SearchField;
    @javafx.fxml.FXML
    private ComboBox<String> ComboBudget;

    private ServiceProjet serviceProjet = new ServiceProjet();
    private RatingService ratingService = new RatingService();
    private Alert alert = new Alert(Alert.AlertType.NONE);
    private User user = SessionManager.getCurrentUser();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ComboBudget.getItems().addAll("< 1000", "1000 - 5000", "5000 - 10000", "> 10000");
        try {
            affcherProjets(serviceProjet.getProjetsByIdUser(user.getId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        SearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            List<Projet> lp = null;
            try {
                lp = serviceProjet.getProjetsByIdUser(user.getId()).stream().filter(projet -> projet.getTitre().contains(newValue)).toList();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                affcherProjets(lp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        ComboBudget.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    List<Projet> lp = null;
                    try {
                        lp = serviceProjet.getProjetsByIdUser(user.getId()).stream().filter(projet -> {
                            switch (newValue) {
                                case "< 1000" -> {
                                    return projet.getBudget() < 1000;
                                }
                                case "1000 - 5000" -> {
                                    return projet.getBudget() >= 1000 && projet.getBudget() <= 5000;
                                }
                                case "5000 - 10000" -> {
                                    return projet.getBudget() >= 5000 && projet.getBudget() <= 10000;
                                }
                                case "> 10000" -> {
                                    return projet.getBudget() > 10000;
                                }
                                default -> {
                                    return false;
                                }
                            }
                        }).toList();
                        affcherProjets(lp);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    public void affcherProjets(List<Projet> projetList) throws SQLException {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(50);//


        int count = 0;
        for (Projet p : projetList) {
            VBox box = CreerCarteProjet(p);
            box.setPrefWidth(300);
            box.setPrefHeight(300);
            hBox.getChildren().add(box);
            count++;

            if (count == 3) {
                vBox.getChildren().add(hBox);
                hBox = new HBox();
                hBox.setAlignment(Pos.CENTER);
                hBox.setSpacing(50); //
                // hBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: black; -fx-border-width: 2px;");
                count = 0;
            }
        }

        if (count > 0) {
            vBox.getChildren().add(hBox);
        }

        scrollPane.setContent(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }
    public VBox CreerCarteProjet(Projet projet) throws SQLException {
        Double totale;
        Label ratingLabel = new Label("Rating: ");
        VBox box = new VBox();
        //  ImageView imageView = new ImageView(new Image(p.getImagePath()));
        box.setStyle("-fx-background-color: linear-gradient(to bottom, #BBBBBB, #999999); -fx-background-radius: 20;");

        box.setMaxSize(200, 300);
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);

        Insets I = new Insets(20, 0, 20, 0);
        box.setPadding(I);
        box.setOpacity(0.7);
        Glow glow = new Glow();
        DropShadow shadow = new DropShadow();
        box.setEffect(shadow);
        box.setUserData(projet); // set the ID as the user data for the VBox

        Label Titre = new Label(projet.getTitre());
        Label Compet = new Label(projet.getCompetences());
        Label Budget = new Label(String.format("%.2f",projet.getBudget()) + " DT");
        Label dd = new Label(projet.getDate_deb().toString());
        Label df = new Label(projet.getDate_fin().toString());

        Titre.setStyle("-fx-text-fill: #acaaad;");
        Titre.setWrapText(true);
        Titre.setAlignment(Pos.CENTER);
        Titre.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        Compet.setTextFill(Color.WHITE);
        Compet.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        Budget.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        Budget.setTextFill(Color.WHITE);

        Button btn = new Button("Modifier");
        btn.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/skillseekr/Projects/MonProjetDetails.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            MonProjetDetails controller = loader.getController();
            controller.setProjet(projet);
            ProjetsRouter.getInstance().showContent(root);
        });
        totale = ratingService.GetRatingParProjet(projet.getId(),user.getId());
        ratingLabel.setText(" Total Ratings: " + String.format("%.2f",totale));

        box.getChildren().addAll(Titre, Compet, Budget,dd ,df , ratingLabel , btn);

        return box;
    }
}
