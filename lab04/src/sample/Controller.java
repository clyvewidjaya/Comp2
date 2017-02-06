package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.Date;

public class Controller {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField fullnameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private DatePicker dateField;

    public void login(ActionEvent event){
        String username = usernameField.getText();
        String password = passwordField.getText();
        String fullName = fullnameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        LocalDate dateInsert = dateField.getValue();

        System.out.println("Username = " + username + "\nPassword = " + password + "\nFull Name = " + fullName + "\nEmail = " + email);
        System.out.println("Phone = " + String.valueOf(phone).replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3"));
        System.out.println("Date = " + dateInsert);
    }
}
