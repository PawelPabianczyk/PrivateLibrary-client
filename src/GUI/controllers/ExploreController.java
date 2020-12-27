package GUI.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Book;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ExploreController  implements Initializable {

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, String> title;

    @FXML
    private TableColumn<Book, String> author;

    @FXML
    private TableColumn<Book, String> genre;

    @FXML
    private TableColumn<Book, String> owner;

    @FXML
    private TableColumn<Book, LocalDate> dateAdded;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final ObservableList<Book> data = FXCollections.observableArrayList(getBooks());
        title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        author.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        genre.setCellValueFactory(new PropertyValueFactory<Book, String>("genre"));
        dateAdded.setCellValueFactory(new PropertyValueFactory<Book, LocalDate>("dateAdded"));
        owner.setCellValueFactory(new PropertyValueFactory<Book, String>("owner"));
        booksTable.setItems(data);
    }

    private ArrayList<Book> getBooks(){

        try {
            Socket socket = new Socket("localhost", 4444);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject("get all books");

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ArrayList<Book> books = (ArrayList<Book>) inputStream.readObject();

            return books;

        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
