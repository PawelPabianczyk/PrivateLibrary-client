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

public class BooksController implements Initializable {
    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, String> title;

    @FXML
    private TableColumn<Book, String> author;

    @FXML
    private TableColumn<Book, String> genre;

    @FXML
    private TableColumn<Book, LocalDate> publishDate;

    @FXML
    private TableColumn<Book, String> status;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final ObservableList<Book> data = FXCollections.observableArrayList(getBooks());
        title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        author.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        genre.setCellValueFactory(new PropertyValueFactory<Book, String>("genre"));
        publishDate.setCellValueFactory(new PropertyValueFactory<Book, LocalDate>("publishDate"));
        status.setCellValueFactory(new PropertyValueFactory<Book, String>("status"));
        booksTable.setItems(data);
    }

    private ArrayList<Book> getBooks(){

        try {
            Socket socket = new Socket("localhost", 4444);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject("get books");

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ArrayList<Book> books = (ArrayList<Book>) inputStream.readObject();

            return books;

        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
