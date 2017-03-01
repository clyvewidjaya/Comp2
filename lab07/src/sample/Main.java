package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Main extends Application {
    private Canvas canvas;
    private static Map<String, Integer> weatherMap;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root, 800, 500, Color.WHITE);
        canvas = new Canvas();

        canvas.setWidth(800);
        canvas.setHeight(500);

        root.getChildren().add(canvas);
        primaryStage.setTitle("Lab 07");
        primaryStage.setScene(scene);
        primaryStage.show();

        draw(root);
    }

    private void draw(Group root) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        weatherMap = new TreeMap<>();
        try{
            FileReader reader = new FileReader("/home/clyve/IdeaProjects/lab07/src/sample/weatherwarnings-2015.csv");
            BufferedReader in = new BufferedReader(reader);

            String headerLine = in.readLine();
            int columnIndex = 5;
            int totalWeather = 0;
            int numb = 0;
            int weatherType = 0;
            String line;
            while((line = in.readLine()) != null){
                if (line.trim().length()!= 0){
                    String[] dataFields = line.split(",");
                    String key = dataFields[columnIndex];
                    if (weatherMap.containsKey(key)){
                        int oldCount = weatherMap.get(key);
                        weatherMap.put(dataFields[columnIndex], oldCount+1);
                    } else {
                        weatherMap.put(dataFields[columnIndex],1);
                        weatherType++;
                    }
                    totalWeather++;
                }
            }

            String[] weather = new String[weatherType];
            int[] total = new int[weatherType];

            Set<String> keys = weatherMap.keySet();
            Iterator<String> keyIterator = keys.iterator();

            while(keyIterator.hasNext()){
                String key = keyIterator.next();
                int count = weatherMap.get(key);
                weather[numb] = key;
                total[numb] = count;
                numb++;
            }
            double yPos = 50;
            for (int i = 0; i < weatherType; i++){
                gc.setFill(pieColours[i]);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(50, yPos, 75, 62.5);
                gc.fillRect(50, yPos, 75, 62.5);
                yPos += 112.5;
            }

            yPos = 85.5;
            for (int i = 0; i < weatherType; i++){
                Font font = new Font("Arial",15);
                gc.setFont(font);
                gc.setFill(Color.BLACK);
                gc.fillText(weather[i], 135, yPos);
                yPos += 112.5;
            }

            double startAngle = 0.0;
            for (int i = 0; i < weatherType; i++){
                double arcAngle = (((double)total[i]*360.0)/(double)totalWeather);
                gc.setFill(pieColours[i]);
                gc.setStroke(Color.BLACK);
                gc.strokeArc(350,50,400,400,startAngle,arcAngle, ArcType.ROUND);
                gc.fillArc(350,50,400,400, startAngle, arcAngle, ArcType.ROUND);
                startAngle += arcAngle;
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static Color[] pieColours = { Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.DARKSALMON};
    public static void main(String[] args) {
        launch(args);
    }
}
