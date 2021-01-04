package GUI.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.Author;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class AddAuthorController implements Initializable {

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfCountry;

    @FXML
    private ChoiceBox<String> choiceGender;

    @FXML
    private TextArea taBiography;

    private ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female", "Rather not say");

    //TODO sprawdzanie poprawnosci wprowadzonych danych
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceGender.setValue("Rather not say");
        choiceGender.setItems(genderList);
    }

    @FXML
    void save(MouseEvent event) throws IOException {
        Author author = new Author();
        author.setFirstName(tfFirstName.getText());
        author.setLastName(tfLastName.getText());
        author.setCountry(tfCountry.getText());
        author.setGender(choiceGender.getValue());
        author.setBiography(taBiography.getText());

        try {
            Socket socket = new Socket("localhost", 4444);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject("POST new author");
            outputStream.writeObject(author);

            socket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        clearForm();
    }

    private void clearForm() {
        this.tfFirstName.clear();
        this.tfLastName.clear();
        this.choiceGender.setValue("Rather not say");
        this.tfCountry.clear();
        this.taBiography.clear();
    }

}
