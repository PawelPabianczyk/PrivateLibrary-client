package GUI.controllers;

import GUI.UserHolder;
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
import models.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
    private Button btnExit;

    @FXML
    private Button btnSave;

    private ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female", "Rather not say");

    @FXML
    public ChoiceBox<String> choiceGender;

    //TODO sprawdzanie poprawnosci wprowadzonych danych
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = UserHolder.getInstance().getUser();
        tfFirstName.setText(user.getFirstName());
        tfLastName.setText(user.getLastName());
        tfCountry.setText(user.getCountry());
        choiceGender.setValue(user.getGender());
        choiceGender.setItems(genderList);
    }

    public void backToProfile(MouseEvent mouseEvent) throws IOException {
        Parent profil = FXMLLoader.load(getClass().getResource("../views/home.fxml"));
        Scene profilScene = new Scene(profil);
        Stage window = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(profilScene);
        window.show();
    }

    public void save(MouseEvent mouseEvent) throws IOException {
        User user = UserHolder.getInstance().getUser();
        user.setCountry(tfCountry.getText());
        user.setGender(choiceGender.getValue());
        user.setFirstName(tfFirstName.getText());
        user.setLastName(tfLastName.getText());

        try {
            Socket socket = new Socket("localhost", 4444);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject("POST personal data");
            outputStream.writeObject(user);

            socket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        backToProfile(mouseEvent);
    }
}
