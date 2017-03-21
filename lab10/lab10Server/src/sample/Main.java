package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Lab 10 Server");

        GridPane layout = new GridPane();
        VBox vb = new VBox();
        vb.setPadding(new Insets(30,30,30,30));
        vb.setSpacing(20);

        TextArea tArea = new TextArea();
        tArea.setText("");
        Button exit = new Button();
        exit.setText("Exit");
        exit.setOnAction(event -> System.exit(0));

        layout.add(tArea,0,0);
        layout.add(exit,0,1);

        vb.getChildren().add(layout);

        primaryStage.setScene(new Scene(vb, 300, 275));
        primaryStage.show();

        Runnable keepRunning = new Runnable(){
          @Override
            public void run(){
              while(true){
                  try{
                      ServerSocket serverSocket = new ServerSocket(8888);
                      while(true){
                          Socket client = serverSocket.accept();
                          BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                          String username = br.readLine();
                          String message = br.readLine();
                          String before = tArea.getText();
                          tArea.setText(before + username + ": " + message + "\n");
                          client.close();
                      }
                  }catch (IOException ex){
                      ex.printStackTrace();
                  }
              }
          }
        };

        Thread keepRunningThread = new Thread(keepRunning);
        keepRunningThread.start();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
