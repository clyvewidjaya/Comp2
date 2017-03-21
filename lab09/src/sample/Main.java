package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Main extends Application {
    private static Canvas canvas;
    public static Stage primaryStage;
    static ArrayList<Float> data1 = new ArrayList<Float>();
    static ArrayList<Float> data2 = new ArrayList<Float>();
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Group root = new Group();
        Scene scene = new Scene(root, 800, 500, Color.WHITE);
        canvas = new Canvas();

        canvas.setWidth(800);
        canvas.setHeight(500);

        root.getChildren().add(canvas);
        primaryStage.setTitle("Lab 09");
        primaryStage.setScene(scene);
        primaryStage.show();

        //draw(root);

        //primaryStage.setTitle("Lab 09");
        //primaryStage.setScene(new Scene(root, 300, 275));
        downloadStockPrices();
        //primaryStage.show();
    }

    public static void downloadStockPrices()throws Exception{
        //URL[] urls = new URL[2];
        //urls[0] = ("http://ichart.finance.yahoo.com/table.csv?s=GOOG&a=1&b-0&c=2010&d=11&e=31&f=2015&g=m");
        try{
            URL[] urls = new URL[2];
            urls[0] = new URL("http://ichart.finance.yahoo.com/table.csv?s=GOOG&a=1&b-0&c=2010&d=11&e=31&f=2015&g=m");
            urls[1] = new URL("http://ichart.finance.yahoo.com/table.csv?s=AAPL&a=1&b-0&c=2010&d=11&e=31&f=2015&g=m");
            for (int i = 0; i < urls.length; i++){
                URLConnection conn = urls[i].openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine = in.readLine();
                String[] column = inputLine.split(",");
                String desiredColumn = "close";
                int desiredColIndex = -1;
                for (int x = 0; x < column.length;x++){
                    if(column[x].toLowerCase().equals(desiredColumn.toLowerCase())){
                        desiredColIndex = x;
                    }
                    //System.out.println("test " + x);
                }
                System.out.println(inputLine);
                System.out.println(i + ": " + desiredColIndex);

                if (desiredColIndex >= 0){
                    while((inputLine = in.readLine()) != null){
                        String[] lineData = inputLine.split(",");
                        float val = Float.parseFloat(lineData[desiredColIndex]);
                        if (i == 0){
                            data1.add(val);
                        }
                        if (i == 1){
                            data2.add(val);
                        }
                    }
                }
                in.close();
            }
            drawLinePlot(data1,data2);
        } catch (IOException ex){
            ex.printStackTrace();
        }

    }

    public static void drawLinePlot(ArrayList<Float> data1, ArrayList<Float> data2){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.strokeLine(50,450,750,450);

        gc.setStroke(Color.BLACK);
        gc.strokeLine(50,50,50,450);


        for (int i = 0; i < 2; i++){
            if (i == 0){
                plotLine(data1, Color.RED);
            } else {
                plotLine(data2, Color.BLUE);
            }
        }

    }

    public static void plotLine(ArrayList<Float> dataToDraw, Color color){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double xDist = (double)700/(double)dataToDraw.size();
        System.out.println(dataToDraw.size());
        //System.out.println(xDist);
        double insX = 50;
        Float[] data = dataToDraw.toArray(new Float[dataToDraw.size()]);
        float highestVal = 0.0f;
        for (int x = 0; x < data.length; x++){
            if (highestVal < data[x]){
                highestVal = data[x];
            }
        }
        //System.out.println(highestVal);
        for (int i = 0; i < data.length-2;i++){
            gc.setStroke(color);
            double yPos1 = ((double)data[i]/(double)highestVal)*((double)400);
            double yPos2 = ((double)data[i+1]/(double)highestVal)*((double)400);
            gc.strokeLine(insX, 450-yPos1, insX + xDist, 450-yPos2);
            insX += xDist;
            //System.out.println(data[i]);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
