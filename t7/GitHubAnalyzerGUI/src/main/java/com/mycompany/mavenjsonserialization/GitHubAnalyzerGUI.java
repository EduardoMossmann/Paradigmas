package com.mycompany.mavenjsonserialization;

import java.io.*;
import java.net.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class GitHubAnalyzerGUI extends Application {
    
    public static class DataEntry{
        private final SimpleStringProperty URLs;
        private final SimpleStringProperty nCommits;
        private final SimpleStringProperty mTam;
        
        private DataEntry(String URLs, String nCommits, String mTam){
            this.URLs = new SimpleStringProperty(URLs);
            this.nCommits = new SimpleStringProperty(nCommits);
            this.mTam = new SimpleStringProperty(mTam);
        }
        
        public SimpleStringProperty URLsProperty(){ return URLs; }
        public SimpleStringProperty nCommitsProperty(){ return nCommits; }
        public SimpleStringProperty mTamProperty(){ return mTam; }

        public SimpleStringProperty getURLs() { return URLs; }
        public SimpleStringProperty getnCommits() { return nCommits; }
        public SimpleStringProperty getmTam() { return mTam; }
        
        public void setURLs(String s) { URLs.set(s); }
        public void setnCommits(String s) { nCommits.set(s); }
        public void setmTam(String s) { mTam.set(s); }
        
    }
    private final ObservableList<DataEntry> data 
            = FXCollections.observableArrayList();
    
    TableView<DataEntry> table = new TableView();
    
    Menu menuFile = new Menu("File");
    MenuItem Open = new MenuItem("Open");
    MenuItem Exit = new MenuItem("Exit");
    
    Menu Tools = new Menu("Tools");
    MenuItem CommitAnal = new MenuItem("Commit Analyzer");
    
    Menu Help = new Menu("Help");
    MenuItem About = new MenuItem("About");
    MenuBar menuBar = new MenuBar();
    
    List<String> gitURLList = new ArrayList<>();
    List<String> nCommitsList = new ArrayList<>();
    List<String> avgLengthList = new ArrayList<>();
    
    public void initMenu(){
        menuFile.getItems().addAll(Open, Exit);
        Tools.getItems().addAll(CommitAnal);
        Help.getItems().addAll(About);
        menuBar.getMenus().addAll(menuFile, Tools, Help);
    }
    
    public void initTable(){
        TableColumn URLsCol = new TableColumn("URLs");
        TableColumn nCommitsCol = new TableColumn("nCommits");
        TableColumn avgLengthCol = new TableColumn("AverageLength");
        
        URLsCol.setCellValueFactory(
                new PropertyValueFactory<DataEntry, String>("URLs"));
        
        nCommitsCol.setCellValueFactory(
                new PropertyValueFactory<DataEntry, String>("nCommits"));
       
        avgLengthCol.setCellValueFactory(
                new PropertyValueFactory<DataEntry, String>("mTam"));
        
        table.getColumns().addAll(URLsCol,nCommitsCol, avgLengthCol);
        table.setItems(data);
    }
    public void aboutWindow() {
        Alert aboutW = new Alert(AlertType.INFORMATION);
        aboutW.setTitle("About");
        aboutW.setHeaderText("GitHubAnalyzerGUI");
        aboutW.setContentText("\n\nby: Eduardo Mossmann");
        aboutW.initModality(Modality.WINDOW_MODAL);
        aboutW.showAndWait();
    }
    
    public void openFile(File selectedFile) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(selectedFile.getAbsolutePath()));
  
        String readString;
        while((readString = in.readLine()) != null){
            gitURLList.add(readString);
            DataEntry Data = new DataEntry(readString, "-", "-");
            data.add(Data);
            fillTableView();
        }    
    }
    public void fillTableView(){
        
    }
 
    public void mainIteractions(final Stage primaryStage){
        Open.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
               FileChooser fileC = new FileChooser();
               fileC.setTitle("Escolha o arquivo texto");
               File selectedFile = fileC.showOpenDialog(primaryStage);
               if(selectedFile != null)
                   try {
                       openFile(selectedFile);
                    } catch (Exception ex) {
                        Logger.getLogger(GitHubAnalyzerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        });
        CommitAnal.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int cont = 0;
                for(String s: gitURLList){
                    ThreadGson t = new ThreadGson(s, nCommitsList, avgLengthList);
                    t.start();
                    try{
                        t.join();
                    } catch(InterruptedException e){
                        System.err.println("Erro ao aguardar thread");
                    }
                    table.getItems().get(cont).setnCommits(nCommitsList.get(cont));
                    table.getItems().get(cont).setmTam(avgLengthList.get(cont));
                    cont++;
                }  
            }
        });
        Exit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.exit(0);

            }
        });
        About.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                aboutWindow();
            }
        });
    }
    
    public void start(Stage primaryStage) throws Exception {
        initMenu();
        initTable();
        mainIteractions(primaryStage);
        mainStage(primaryStage);
    }
    public void mainStage(Stage primaryStage){
        
        VBox vb = new VBox();
        vb.getChildren().addAll(menuBar, table);
        primaryStage.setScene(new Scene(vb, 800,600));
        primaryStage.show();
    }
    
    public static void main(String[] args) throws IOException {
        launch(args);
    }
}