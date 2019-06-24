package EnadeJAVA;

import java.awt.JobAttributes;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
    
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
  


public class EnadeUFSMExplorer extends Application { 
    
    public static class DataEntry{
        
        private SimpleStringProperty  B;
        private SimpleStringProperty  C;
        private SimpleStringProperty  D;
        private SimpleStringProperty  E;
        private SimpleStringProperty  F;
        private SimpleStringProperty  I;
        private SimpleStringProperty  J;
        private SimpleStringProperty  K;
        private SimpleStringProperty  L;
        
        private DataEntry(String B, String C, String D, 
                          String E, String F, String I, 
                          String J, String K, String L) {
           this.B = new SimpleStringProperty(B);
           this.C = new SimpleStringProperty(C);
           this.D = new SimpleStringProperty(D);
           this.E = new SimpleStringProperty(E);
           this.F = new SimpleStringProperty(F);
           this.I = new SimpleStringProperty(I);
           this.J = new SimpleStringProperty(J);
           this.K = new SimpleStringProperty(K);
           this.L = new SimpleStringProperty(L);

        }   
        
        public SimpleStringProperty BProperty(){ return B; }
        public SimpleStringProperty CProperty(){ return C; }
        public SimpleStringProperty DProperty(){ return D; }
        public SimpleStringProperty EProperty(){ return E; }
        public SimpleStringProperty FProperty(){ return F; }
        public SimpleStringProperty IProperty(){ return I; }
        public SimpleStringProperty JProperty(){ return J; }
        public SimpleStringProperty KProperty(){ return K; }
        public SimpleStringProperty LProperty(){ return L; }
        
        public SimpleStringProperty getB() { return B; }
        public SimpleStringProperty getC() { return C; }
        public SimpleStringProperty getD() { return D; }
        public SimpleStringProperty getE() { return E; }
        public SimpleStringProperty getF() { return F; }
        public SimpleStringProperty getI() { return I; }
        public SimpleStringProperty getJ() { return J; }
        public SimpleStringProperty getK() { return K; }
        public SimpleStringProperty getL() { return L; }
        
        public void setB(String s) { B.set(s); }
        public void setC(String s) { C.set(s); }
        public void setD(String s) { D.set(s); }
        public void setE(String s) { E.set(s); }
        public void setF(String s) { F.set(s); }
        public void setI(String s) { I.set(s); }
        public void setJ(String s) { J.set(s); }
        public void setK(String s) { K.set(s); }
        public void setL(String s) { L.set(s); }
    }
    
    private final ObservableList<DataEntry> data =
     FXCollections.observableArrayList();
    
    

    Menu File = new Menu("File");
    MenuItem Reload = new MenuItem("Reload");
    MenuItem Source = new MenuItem("Source");
    MenuItem Exit = new MenuItem("Exit");
    
    Menu Help = new Menu("Help");
    MenuItem About = new MenuItem("About");
    MenuBar menuBar = new MenuBar();
        
    ArrayList<String> colH;
    ArrayList<String> colR;
    
    TableView<DataEntry> table = new TableView();
    List<String> archiveList = new ArrayList<> ();
    
    String src = "https://docs.google.com/spreadsheets/u/1/d/e/2PACX-1vTO06Jdr3J1kPYoTPRkdUaq8XuslvSD5--FPMht-ilVBT1gExJXDPTiX0P3FsrxV5VKUZJrIUtH1wvN/pub?gid=0&single=true&output=csv";
    String fileName = "enade.csv";

    public static void main(String[] args) {
        launch(args);
    }
  
    public void initMenu(){
        File.getItems().addAll(Reload, Source, Exit);
        Help.getItems().addAll(About);
        menuBar.getMenus().addAll(File,Help);
    }
    public void initTable(){
       TableColumn BCol = new TableColumn("B");
       BCol.setCellValueFactory(
            new PropertyValueFactory<DataEntry,String>("B"));
       TableColumn CCol = new TableColumn("C");
       CCol.setCellValueFactory(
            new PropertyValueFactory<DataEntry,String>("C"));
       TableColumn DCol = new TableColumn("D");
       DCol.setCellValueFactory(
            new PropertyValueFactory<DataEntry,String>("D"));
       TableColumn ECol = new TableColumn("E");
       ECol.setCellValueFactory(
            new PropertyValueFactory<DataEntry,String>("E"));
       TableColumn FCol = new TableColumn("F");
       FCol.setCellValueFactory(
            new PropertyValueFactory<DataEntry,String>("F"));
       TableColumn ICol = new TableColumn("I");
       ICol.setCellValueFactory(
            new PropertyValueFactory<DataEntry,String>("I"));
       TableColumn JCol = new TableColumn("J");
       JCol.setCellValueFactory(
            new PropertyValueFactory<DataEntry,String>("J"));
       TableColumn KCol = new TableColumn("K");
       KCol.setCellValueFactory(
            new PropertyValueFactory<DataEntry,String>("K"));
       TableColumn LCol = new TableColumn("L");
       LCol.setCellValueFactory(
            new PropertyValueFactory<DataEntry,String>("L"));
       
       table.getColumns().addAll(BCol,CCol,DCol,ECol,FCol,ICol,JCol,KCol,LCol);

       table.setItems(data);
    }
    

    public void start(Stage primaryStage) throws Exception {
           initMenu();
           initTable();
           openFileCSV();
           principal(primaryStage);
           
    }
    
    public String takeSubstituteOff(String StringWithSubstitute, char substitute){
        String response = "";    
        char[] Array = StringWithSubstitute.toCharArray();

        for(char c: Array){
            if(c == substitute)
                c = ',';
            response += c;
        }
        
        return response;
    }  
    
    public void fillTableWithArchive(BufferedReader arq) throws IOException{
            String lineRead;
            char charRead;    
            
            final int numberCols = 18;
            
            char substituteChar = (char) 29;
            int commaCounter;
            boolean inQuotes;
         
            colH = new ArrayList<>();
            colR = new ArrayList<>();
            
            while(arq.ready()){
                lineRead = "";
                inQuotes = false;
                commaCounter = 0;
                while(commaCounter < numberCols - 1  && arq.ready()){
                   charRead = (char) arq.read();
                   if(charRead == '"')
                       inQuotes ^= true;
                   else if(charRead == ','){
                       if(inQuotes)
                            charRead = substituteChar;
                       else
                           commaCounter++;
                   }
                   if(charRead != '"')
                     lineRead += charRead;
                }
                
                System.out.println(lineRead);
                
                String fieldsFromCSVwSubstitute[] = lineRead.split(",", -1);
                String fieldsFromCSV[] = new String[numberCols];
                int i;
                for(i = 0; i < numberCols; i ++){
                    fieldsFromCSV[i] = takeSubstituteOff(fieldsFromCSVwSubstitute[i], substituteChar);
                }
                
                colH.add(fieldsFromCSV[7]);
                colR.add(fieldsFromCSV[17]);
                
                DataEntry Data = new DataEntry(fieldsFromCSV[1],
                fieldsFromCSV[2], fieldsFromCSV[3], fieldsFromCSV[4],
                fieldsFromCSV[5], fieldsFromCSV[8], fieldsFromCSV[9],
                fieldsFromCSV[10], fieldsFromCSV[11]);
                data.add(Data);                    
            }       
    }
    
    public void openOnlineArchive(FileReader arq, BufferedReader readArq) throws IOException{
        try{
            URL downloadSite = new URL(src);
            BufferedInputStream inputStream = new BufferedInputStream(downloadSite.openStream());
            FileOutputStream outputFile = new FileOutputStream(fileName);
            byte dataRead[] = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(dataRead, 0, 1024)) != -1) {
              outputFile.write(dataRead, 0, byteContent);
             }    
            arq = new FileReader(fileName);
            readArq = new BufferedReader(arq);
            }
       catch(MalformedURLException m){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error ocurred!");
            alert.setContentText("The URL wasn't valid");
            alert.showAndWait();
        }catch(IOException s){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error ocurred!");
            alert.setContentText("The archive couldn't be downloaded");
            alert.showAndWait();
        }
        if(readArq != null)
            fillTableWithArchive(readArq);
    }
    
     public void openFileCSV() throws IOException{
         FileReader arq = null;
         BufferedReader readArq = null;
         try{
            arq = new FileReader(fileName);
            readArq = new BufferedReader(arq);
        }catch(IOException e){
           openOnlineArchive(arq, readArq);
        }
        if(readArq != null)
            fillTableWithArchive(readArq);
    }
    
    public void setSource(){
        TextInputDialog sourceInput = new TextInputDialog("");
        sourceInput.setTitle("Text Input Dialog");
        sourceInput.setHeaderText("Look, a Text Input Dialog");
        sourceInput.setContentText("Please enter your name:");
        
        Optional<String> result = sourceInput.showAndWait();
        result.ifPresent(sourceValue -> src = sourceValue);
    }
    
    public void aboutWindow(){
        Alert aboutW = new Alert(AlertType.INFORMATION);
        aboutW.setTitle("About");
        aboutW.setHeaderText("EnadeUFSMExplorer");
        aboutW.setContentText("\n\nby: Eduardo Mossmann");
        aboutW.showAndWait();
        aboutW.initModality(Modality.WINDOW_MODAL);
    }
    
    public void mainIteractions() throws IOException{
            Reload.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    try {
                        openFileCSV();
                    } catch (IOException ex) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("An error ocurred!");
                        alert.setContentText("The archive couldn't be opened");
                        alert.showAndWait();
                    }
                   
                }
             });
            Source.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    setSource();
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
            table.setOnMousePressed(new EventHandler<MouseEvent>() {
               public void handle(MouseEvent event){
                   Alert selectedData = new Alert(AlertType.NONE);
                   selectedData.setTitle("Selected Column");
                   selectedData.setHeaderText("Data from the question: ");
                   
                   int rowIndex = table.getSelectionModel().getSelectedIndex();
                   
                   selectedData.setContentText("Ano: " + table.getItems().get(rowIndex).getB().get() +
                           "\n\nProva: " + table.getItems().get(rowIndex).getC().get() +
                           "\n\nTipo Questão: " + table.getItems().get(rowIndex).getD().get() +
                           "\n\nId Questão: " + table.getItems().get(rowIndex).getE().get()  +
                           "\n\nObjeto: " + table.getItems().get(rowIndex).getF().get() +
                           "\n\nGabarito: " + colH.get(rowIndex) +
                           "\n\nAcertos Curso: " + table.getItems().get(rowIndex).getI().get() +
                           "\n\nAcertos Região: " + table.getItems().get(rowIndex).getJ().get()  +
                           "\n\nAcertos Brasil: " + table.getItems().get(rowIndex).getK().get()  +
                           "\n\nDif. (Curso-Brasil): " + table.getItems().get(rowIndex).getL().get());
                   
                    ButtonType imageButton = new ButtonType("Show Image");
                    ButtonType OK = new ButtonType("OK");
                    selectedData.getButtonTypes().setAll(imageButton, OK);
                    
                    Optional<ButtonType> result = selectedData.showAndWait();
                    while(result.get() == imageButton){
                        Alert imageAlert = new Alert(AlertType.NONE);
                        
                        Image imageURL = new Image("https://drive.google.com/uc?export=download&id=1aJcrgt7DQOXjoa2GwuaH-kgi0is-xgru");
                        ImageView imageView = new ImageView(imageURL);
                        imageAlert.setGraphic(imageView);
                        
                        imageAlert.getButtonTypes().setAll(OK);
                        imageAlert.showAndWait();
                        result = selectedData.showAndWait();
                    }
                    
                    selectedData.initModality(Modality.WINDOW_MODAL);
               } 
            });
    }
    public void principal(Stage primaryStage) throws IOException{
            
            mainIteractions();
            
            VBox vb = new VBox();
            vb.getChildren().addAll(menuBar, table);
            primaryStage.setScene(new Scene(vb, 1000, 420));
            primaryStage.show();

      }
}