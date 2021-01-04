package GUI.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.Publisher;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPublisherController implements Initializable {

    @FXML
    private TextField tfName;

    @FXML
    private TextArea taDescription;

    @FXML
    private DatePicker dpDayOfFoundation;

    //TODO sprawdzanie poprawnosci wprowadzonych danych
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void save(MouseEvent event) throws IOException {
        Publisher publisher = new Publisher();
        publisher.setName(tfName.getText());
        publisher.setDateOfFoundation(dpDayOfFoundation.getValue());
        publisher.setDescription(taDescription.getText());

        try {
            Socket socket = new Socket("localhost", 4444);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject("POST new publisher");
            outputStream.writeObject(publisher);

            socket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        clearForm();
    }

    private void clearForm(){
        this.taDescription.clear();
        this.dpDayOfFoundation.getEditor().clear();
        this.tfName.clear();
    }
}
