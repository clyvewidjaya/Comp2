import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

/**
 * Created by clyve on 02/03/17.
 */

public class Main extends Application {
    private TableView<StudentRecord> table;
    public ObservableList<StudentRecord> marks = FXCollections.observableArrayList();
    public static String currentFile;
    public static String currentDir;
    @Override

    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Lab 08");

        TextField sidField, assField, midField,finField;
        sidField = new TextField();
        assField = new TextField();
        midField = new TextField();
        finField = new TextField();

        BorderPane layout = new BorderPane();

        table = new TableView<>();
        //Todo: add some data to our table
        currentFile = "TestFile.csv";
        String defaultFileLocation = "/home/clyve/Comp2/lab08/TestFile.csv";
        currentDir = defaultFileLocation;
        load();
        showTable();
        layout.setCenter(table);

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");

        MenuItem newMenuItem = new MenuItem("New");
        newMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl + N"));
        newMenuItem.setOnAction( e -> {newFunc();});
        fileMenu.getItems().add(newMenuItem);

        fileMenu.getItems().add(new SeparatorMenuItem());

        MenuItem openMenuItem = new MenuItem("Open");
        openMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl + O"));
        openMenuItem.setOnAction( e -> {FileChooser fileChooser = new FileChooser();
                                        fileChooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("All Files", "*.*"));
                                        fileChooser.setTitle("Open");
                                        File fileChosen = fileChooser.showOpenDialog(primaryStage);
                                        currentFile = fileChosen.getName();
                                        currentDir = fileChosen.getAbsolutePath();
                                        try{
                                            newFunc();
                                            load();
                                            System.out.println(currentFile);
                                        } catch (IOException ex){
                                            ex.printStackTrace();
                                        }

                                        } );
        fileMenu.getItems().add(openMenuItem);

        fileMenu.getItems().add(new SeparatorMenuItem());

        MenuItem saveMenuItem = new MenuItem("Save");
        saveMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl + S"));
        saveMenuItem.setOnAction( e -> {try{
                                            save();
                                        } catch (IOException ex){
                                            ex.printStackTrace();
                                        }
                                        ;} );
        fileMenu.getItems().add(saveMenuItem);

        fileMenu.getItems().add(new SeparatorMenuItem());

        MenuItem saveAsMenuItem = new MenuItem("Save As");
        saveAsMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl + Shift + S"));
        saveAsMenuItem.setOnAction( e -> {try{
                                                FileChooser choose = new FileChooser();
                                                choose.setTitle("Save as");
                                                File chosen = choose.showSaveDialog(new Stage());

                                                //save to file
                                                currentFile = chosen.getName();
                                                currentDir = chosen.getParent();
                                                save();

                                                //System.out.println(chosen.getAbsolutePath());
                                            } catch (Exception ex){
                                                System.out.println(ex);
                                            }
                                            ;} );
        fileMenu.getItems().add(saveAsMenuItem);

        fileMenu.getItems().add(new SeparatorMenuItem());

        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl + Q"));
        exitMenuItem.setOnAction( e -> System.exit(0) );
        fileMenu.getItems().add(exitMenuItem);

        menuBar.getMenus().add(fileMenu);

        Button button1 = new Button("Add");
        button1.setStyle("-fx-font: 18 arial; -fx-base: #808080;");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String sid = sidField.getText();
                String assignment = assField.getText();
                String midterm = midField.getText();
                String finalExam = finField.getText();
                try{
                    float assignmentF = Float.parseFloat(assignment);
                    float midtermF = Float.parseFloat(midterm);
                    float finalExamF = Float.parseFloat(finalExam);
                    addData(sid, assignmentF, midtermF, finalExamF);
                    sidField.clear();
                    assField.clear();
                    midField.clear();
                    finField.clear();
                } catch (Exception ex){
                    System.out.println("no"+ex);
                }

            }
        });

        GridPane percentage = new GridPane();
        //SID
        percentage.add(new Label("SID:"), 0, 0);
        sidField.setPromptText("SID");
        percentage.add(sidField,1,0);

        //Assignment
        percentage.add(new Label("Assignments:"), 2, 0);
        assField.setPromptText("Assignments/100");
        percentage.add(assField,3,0);

        //Midterm
        percentage.add(new Label("Midterm:"), 0, 1);
        midField.setPromptText("Midterm/100");
        percentage.add(midField,1,1);

        //Assignment
        percentage.add(new Label("Final Exam:"), 2, 1);
        finField.setPromptText("Final Exam/100");
        percentage.add(finField,3,1);

        percentage.add(button1,0,4);

        layout.setTop(menuBar);
        layout.setBottom(percentage);

        Scene scene = new Scene(layout, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void save() throws IOException{
        //boolean alreadyExists = new File(out)

        BufferedWriter fileWriter;
        try{
            fileWriter = new BufferedWriter(new FileWriter(currentFile));
            marks.forEach(s -> {try {
                                    fileWriter.write(s.getSid());
                                    fileWriter.write(",");
                                    fileWriter.write(String.valueOf(s.getAssignment()));
                                    fileWriter.write(",");
                                    fileWriter.write(String.valueOf(s.getMidterm()));
                                    fileWriter.write(",");
                                    fileWriter.write(String.valueOf(s.getFinalExam()));
                                    fileWriter.write("\n");
                                    fileWriter.flush();
                                    System.out.println("SID: " + s.getSid() + " Ass : " + s.getAssignment() + " Mid : " + s.getMidterm() + "Final : " + s.getFinalExam());
                                } catch (Exception exx){
                                    System.out.println("Save Failed");
                                    exx.printStackTrace();
                                }
            });
        } catch (IOException ex){
            System.out.println("Save Failed");
            ex.printStackTrace();
        }

        //marks.forEach(s->{System.out.println("S = " + s.getSid());});

        //System.out.println(marks.listIterator());
        //System.out.println(marks.get(0).getSid());
        //FileWriter fileWriter = new
    }

    public void load() throws IOException{
        FileReader reader = new FileReader(currentDir);
        BufferedReader in = new BufferedReader(reader);

        String line;
        while((line = in.readLine()) != null){
            if (line.trim().length() != 0){
                String[] data = line.split(",");
                float assMark = Float.parseFloat(data[1]);
                float midMark = Float.parseFloat(data[2]);
                float finMark = Float.parseFloat(data[3]);
                marks.add(new StudentRecord(data[0], assMark, midMark, finMark));
            }
        }

    }

    public void addData(String sid, float ass, float mid, float fin){
        marks.add(new StudentRecord(sid, ass, mid, fin));
        //showTable();
    }

    public void showTable(){
        table.setItems(marks);
        TableColumn<StudentRecord,String> sidColumn = new TableColumn<>("SID");
        sidColumn.setMinWidth(100);
        sidColumn.setCellValueFactory(new PropertyValueFactory<>("sid"));

        TableColumn<StudentRecord,Float> assignmentColumn = new TableColumn<>("Assignment");
        assignmentColumn.setMinWidth(100);
        assignmentColumn.setCellValueFactory(new PropertyValueFactory<>("assignment"));

        TableColumn<StudentRecord,Float> midtermColumn = new TableColumn<>("Midterm");
        midtermColumn.setMinWidth(100);
        midtermColumn.setCellValueFactory(new PropertyValueFactory<>("midterm"));

        TableColumn<StudentRecord,Float> finalExamColumn = new TableColumn<>("Final Exam");
        finalExamColumn.setMinWidth(100);
        finalExamColumn.setCellValueFactory(new PropertyValueFactory<>("finalExam"));

        TableColumn<StudentRecord,Float> finalMarkColumn = new TableColumn<>("Final Mark");
        finalMarkColumn.setMinWidth(100);
        finalMarkColumn.setCellValueFactory(new PropertyValueFactory<>("finalMark"));

        TableColumn<StudentRecord,String> letterGradeColumn = new TableColumn<>("Letter Grade");
        letterGradeColumn.setMinWidth(100);
        letterGradeColumn.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));

        table.getColumns().add(sidColumn);
        table.getColumns().add(assignmentColumn);
        table.getColumns().add(midtermColumn);
        table.getColumns().add(finalExamColumn);
        table.getColumns().add(finalMarkColumn);
        table.getColumns().add(letterGradeColumn);
    }

    public void newFunc(){
        for ( int i = 0; i < table.getItems().size(); i++) {
            table.getItems().clear();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
