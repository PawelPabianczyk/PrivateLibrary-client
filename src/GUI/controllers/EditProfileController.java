package GUI.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditProfileController implements Initializable {

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfCountry;

    @FXML
    private TextField tfFavouriteGenre;

    @FXML
    private TextField tfFavouriteAuthor;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnSave;

    private ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female", "Rather not say");

    @FXML
    public ChoiceBox<String> choiceGender;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceGender.setValue("Rather not say");
        choiceGender.setItems(genderList);
    }

    public void backToProfile(MouseEvent mouseEvent) throws IOException {
        Parent profil = FXMLLoader.load(getClass().getResource("../views/home.fxml"));
        Scene profilScene = new Scene(profil);
        Stage window = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(profilScene);
        window.show();
    }

    public void save(MouseEvent mouseEvent) {
    }
}
