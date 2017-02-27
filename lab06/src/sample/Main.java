package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root,800,500, Color.WHITE);
        canvas = new Canvas();

        canvas.setHeight(500);
        canvas.setWidth(800);

        root.getChildren().add(canvas);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lab 06");
        primaryStage.show();
        draw(root);
    }

    private void draw(Group root){
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //barChart
        int xPosHousing = 50;
        int xPosCommercial = 65;
        double highestValue = 0;
        for (int i = 0; i < avgHousingPricesByYear.length; i++){
           if (highestValue < avgHousingPricesByYear[i]){
               highestValue = avgHousingPricesByYear[i];
           }
        }

        for (int i = 0; i < avgCommercialPricesByYear.length; i++){
            if (highestValue < avgCommercialPricesByYear[i]){
                highestValue = avgCommercialPricesByYear[i];
            }
        }

        for (int i = 0; i < avgHousingPricesByYear.length; i++){
            double height = (avgHousingPricesByYear[i]/highestValue)*400;
            double yPos = (400-height) + 50;
            gc.setFill(Color.RED);
            gc.fillRect(xPosHousing, yPos, 15, height);
            xPosHousing += 30;
        }

        for (int i = 0; i < avgCommercialPricesByYear.length; i++){
            double height = (avgCommercialPricesByYear[i]/highestValue)*400;
            double yPos = (400-height) + 50;
            gc.setFill(Color.BLUE);
            gc.fillRect(xPosCommercial, yPos, 15, height);
            xPosCommercial += 30;
        }

        int totalSize = 0;

        for (int i = 0; i < purchasesByAgeGroup.length;i++){
            totalSize += purchasesByAgeGroup[i];
        }

        double startAngle = 0.0;
        for (int i = 0; i < pieColours.length; i++){
            double arcAngle = (((double)purchasesByAgeGroup[i]*360.0)/(double)totalSize);
            gc.setFill(pieColours[i]);
            gc.fillArc(350,50,400,400, startAngle, arcAngle, ArcType.ROUND);
            startAngle += arcAngle;
        }
    }

    private static double[] avgHousingPricesByYear = { 247381.0, 264171.4, 287715.3, 294736.1, 308431.4, 322635.9, 340253.0, 363153.7 };
    private static double[] avgCommercialPricesByYear = { 1121585.3, 1219479.5, 1246354.2, 1295364.8, 1335932.6, 1472362.0, 1583521.9, 1613246.3 };
    private static String[] ageGroups = { "18-25", "26-35", "36-45", "46-55", "56-65", "65+" };
    private static int[] purchasesByAgeGroup = { 648, 1021, 2453, 3173, 1868, 2247 };
    private static Color[] pieColours = { Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM };

    public static void main(String[] args) {
        launch(args);
    }
}
