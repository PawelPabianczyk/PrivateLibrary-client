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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.User;

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
    public Label lError;
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
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setTitle("Welcome");
        window.setScene(loginScene);
        window.show();
    }

    public void register(MouseEvent mouseEvent) throws IOException {

        if (!isUsernameHasMin8Letters(tfUsername.getText())) {
            lError.setText("Username is incorrect!");
        } else if (!isTheSameString(pfPassword.getText(), pfConfirm.getText())) {
            lError.setText("Passwords are different!");
        } else if (tfCountry.getText().length() <=0
                || tfFirstName.getText().length() <=0
                || tfLastName.getText().length() <=0) {
            lError.setText("All fields are required!");
        } else {
            User user = new User();
            user.setUsername(tfUsername.getText());
            user.setPassword(pfPassword.getText());
            user.setCountry(tfCountry.getText());
            user.setGender(choiceGender.getValue());
            user.setFirstName(tfFirstName.getText());
            user.setLastName(tfLastName.getText());

            try {
                Socket socket = new Socket("localhost", 4444);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject("POST register");
                outputStream.writeObject(user);

                socket.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            login(mouseEvent);
        }
        clearForm();
    }

    private void clearForm() {
        tfUsername.clear();
        tfFirstName.clear();
        tfLastName.clear();
        tfCountry.clear();
        pfConfirm.clear();
        pfPassword.clear();
        choiceGender.setValue("Rather not say");
    }

    public Boolean isTheSameString(String string1, String string2) {
        if (string1.equals(string2))
            return true;
        return false;
    }

    public Boolean isUsernameHasMin8Letters(String username) {
        if (username.length() >= 8)
            return true;
        return false;
    }
}
