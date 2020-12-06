package GUI.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    private ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female", "Rather not say");

    @FXML
    public ChoiceBox<String> choiceGender;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceGender.setValue("Rather not say");
        choiceGender.setItems(genderList);
    }

    public void login(MouseEvent mouseEvent) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
        Scene loginScene = new Scene(login);
        Stage window = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setTitle("Welcome");
        window.setScene(loginScene);
        window.show();
    }

}
