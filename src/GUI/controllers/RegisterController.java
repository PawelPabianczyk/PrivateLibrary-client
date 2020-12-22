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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.User;
import sun.nio.cs.ext.ISO2022_CN;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    public TextField tfUsername;
    public TextField tfFirstName;
    public TextField tfLastName;
    public TextField tfCountry;
    public PasswordField pfConfirm;
    public PasswordField pfPassword;
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

    public void register(MouseEvent mouseEvent) {
        User user = new User();
        user.username = tfUsername.getText();
        user.password = pfPassword.getText();
        user.country = tfCountry.getText();
        user.gender = choiceGender.getValue();
        user.firstName = tfFirstName.getText();
        user.lastName = tfLastName.getText();
        user.favouriteAuthor = null;
        user.favouriteGenre = null;

        try {
            Socket socket = new Socket("localhost", 4444);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject("sending register data");
            outputStream.writeObject(user);

            socket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        tfUsername.clear();
        tfFirstName.clear();
        tfLastName.clear();
        tfCountry.clear();
        pfConfirm.clear();
        pfPassword.clear();
        choiceGender.setValue("Rather not say");
    }
}
