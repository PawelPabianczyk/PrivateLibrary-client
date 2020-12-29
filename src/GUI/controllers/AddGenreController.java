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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Genre;
import models.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class AddGenreController implements Initializable {

    @FXML
    private TextField tfName;

    @FXML
    private ChoiceBox<String> choiceType;

    @FXML
    private TextArea taDescription;

    private ObservableList<String> typeList = FXCollections.observableArrayList("Fiction", "Nonfiction");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceType.setValue("Fiction");
        choiceType.setItems(typeList);
    }

    @FXML
    void save(MouseEvent event) throws IOException {
        Genre genre = new Genre();
        genre.setName(tfName.getText());
        genre.setType(choiceType.getValue());
        genre.setDescription(taDescription.getText());

        try {
            Socket socket = new Socket("localhost", 4444);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject("POST new genre");
            outputStream.writeObject(genre);

            socket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        clearForm();
    }

    private void clearForm(){
        this.tfName.clear();
        this.taDescription.clear();
        this.choiceType.setValue("Fiction");
    }

}
