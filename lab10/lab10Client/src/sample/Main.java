package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class Main extends Application {
    String lineUsr, lineMess;
    String hostName = "10.160.28.112";
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Lab 10 Client");

        Label usr = new Label();
        usr.setText("Username: ");

        Label mess = new Label();
        mess.setText("Message: ");

        TextField usrn = new TextField();
        usrn.setPromptText("Input Your Username");

        TextField message = new TextField();
        message.setPromptText("Input Message");

        Button send = new Button();
        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lineUsr = usrn.getText();
                usrn.setText("");

                lineMess = message.getText();
                message.setText("");
                
                try{
                    Socket socket = new Socket(hostName, 8888);
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    out.println(lineUsr);
                    out.println(lineMess);
                    out.flush();

                } catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });
        send.setText("Send");

        Button exit = new Button();
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        exit.setText("Exit");

        VBox vb = new VBox();
        vb.setPadding(new Insets(30,30,30,30));
        vb.setSpacing(10);

        GridPane text = new GridPane();
        text.add(usr,0,0);
        text.add(usrn,1,0);
        text.add(mess,0,1);
        text.add(message,1,1);
        vb.getChildren().add(text);

        vb.getChildren().add(send);
        vb.getChildren().add(exit);

        Scene scene = new Scene(vb, 300,200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
