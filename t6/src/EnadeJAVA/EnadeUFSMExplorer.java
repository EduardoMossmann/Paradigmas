package EnadeJAVA;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

import javafx.stage.Stage;

public class EnadeUFSMExplorer extends Application {

    public static class DataEntry {

        private final SimpleStringProperty B;
        private final SimpleStringProperty C;
        private final SimpleStringProperty D;
        private final SimpleStringProperty E;
        private final SimpleStringProperty F;
        private final SimpleStringProperty I;
        private final SimpleStringProperty J;
        private final SimpleStringProperty K;
        private final SimpleStringProperty L;

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

        public SimpleStringProperty BProperty() {
            return B;
        }

        public SimpleStringProperty CProperty() {
            return C;
        }

        public SimpleStringProperty DProperty() {
            return D;
        }

        public SimpleStringProperty EProperty() {
            return E;
        }

        public SimpleStringProperty FProperty() {
            return F;
        }

        public SimpleStringProperty IProperty() {
            return I;
        }

        public SimpleStringProperty JProperty() {
            return J;
        }

        public SimpleStringProperty KProperty() {
            return K;
        }

        public SimpleStringProperty LProperty() {
            return L;
        }

        public SimpleStringProperty getB() {
            return B;
        }

        public SimpleStringProperty getC() {
            return C;
        }

        public SimpleStringProperty getD() {
            return D;
        }

        public SimpleStringProperty getE() {
            return E;
        }

        public SimpleStringProperty getF() {
            return F;
        }

        public SimpleStringProperty getI() {
            return I;
        }

        public SimpleStringProperty getJ() {
            return J;
        }

        public SimpleStringProperty getK() {
            return K;
        }

        public SimpleStringProperty getL() {
            return L;
        }

        public void setB(String s) {
            B.set(s);
        }

        public void setC(String s) {
            C.set(s);
        }

        public void setD(String s) {
            D.set(s);
        }

        public void setE(String s) {
            E.set(s);
        }

        public void setF(String s) {
            F.set(s);
        }

        public void setI(String s) {
            I.set(s);
        }

        public void setJ(String s) {
            J.set(s);
        }

        public void setK(String s) {
            K.set(s);
        }

        public void setL(String s) {
            L.set(s);
        }
    }

    private final ObservableList<DataEntry> data
            = FXCollections.observableArrayList();

    Menu menuFile = new Menu("File");
    MenuItem Reload = new MenuItem("Reload");
    MenuItem Source = new MenuItem("Source");
    MenuItem Exit = new MenuItem("Exit");

    Menu Help = new Menu("Help");
    MenuItem About = new MenuItem("About");
    MenuBar menuBar = new MenuBar();

    ArrayList<String> colH;
    ArrayList<String> colR;

    TableView<DataEntry> table = new TableView();
    List<String> archiveList = new ArrayList<>();
    String src = "https://docs.google.com/spreadsheets/u/1/d/e/2PACX-1vTO06Jdr3J1kPYoTPRkdUaq8XuslvSD5--FPMht-ilVBT1gExJXDPTiX0P3FsrxV5VKUZJrIUtH1wvN/pub?gid=0&single=true&output=csv";
    String fileName = "enade.csv";

    public static void main(String[] args) {
        launch(args);
    }

    public void initMenu() {
        menuFile.getItems().addAll(Reload, Source, Exit);
        Help.getItems().addAll(About);
        menuBar.getMenus().addAll(menuFile, Help);
    }

    public void initTable() {
        TableColumn BCol = new TableColumn("B");
        BCol.setCellValueFactory(
                new PropertyValueFactory<DataEntry, String>("B"));
        TableColumn CCol = new TableColumn("C");
        CCol.setCellValueFactory(
                new PropertyValueFactory<DataEntry, String>("C"));
        TableColumn DCol = new TableColumn("D");
        DCol.setCellValueFactory(
                new PropertyValueFactory<DataEntry, String>("D"));
        TableColumn ECol = new TableColumn("E");
        ECol.setCellValueFactory(
                new PropertyValueFactory<DataEntry, String>("E"));
        TableColumn FCol = new TableColumn("F");
        FCol.setCellValueFactory(
                new PropertyValueFactory<DataEntry, String>("F"));
        TableColumn ICol = new TableColumn("I");
        ICol.setCellValueFactory(
                new PropertyValueFactory<DataEntry, String>("I"));
        TableColumn JCol = new TableColumn("J");
        JCol.setCellValueFactory(
                new PropertyValueFactory<DataEntry, String>("J"));
        TableColumn KCol = new TableColumn("K");
        KCol.setCellValueFactory(
                new PropertyValueFactory<DataEntry, String>("K"));
        TableColumn LCol = new TableColumn("L");
        LCol.setCellValueFactory(
                new PropertyValueFactory<DataEntry, String>("L"));

        table.getColumns().addAll(BCol, CCol, DCol, ECol, FCol, ICol, JCol, KCol, LCol);

        table.setItems(data);
    }

    public void start(Stage primaryStage) throws Exception {
        initMenu();
        initTable();
        openFileCSV();
        mainStage(primaryStage);

    }

    public String takeSubstituteOff(String StringWithSubstitute, char substitute) {
        String response = "";
        char[] Array = StringWithSubstitute.toCharArray();

        for (char c : Array) {
            if (c == substitute) {
                c = ',';
            }
            response += c;
        }

        return response;
    }

    public void fillTableWithArchive(BufferedReader arq) throws IOException {
        String lineRead;
        String imageString = new String();
        char charRead = 0;

        final int numberCols = 18;

        char substituteChar = (char) 0;
        int commaCounter;
        boolean inQuotes;

        colH = new ArrayList<>();
        colR = new ArrayList<>();

        while (arq.ready()) {
            lineRead = "";
            imageString = "";
            inQuotes = false;
            commaCounter = 0;
            while (commaCounter < numberCols - 1 && arq.ready()) { // pega colunas conforme as virgulas
                charRead = (char) arq.read();
                if (charRead == '"') {
                    inQuotes ^= true;
                } else if (charRead == ',') {
                    if (inQuotes) {
                        charRead = substituteChar;
                    } else {
                        commaCounter++;
                    }
                }
                if (charRead != '"') {
                    lineRead += charRead;
                }
            }
            while ((charRead != '\r') && arq.ready()) { //Pega ultima coluna(imagem) que não tem a virgula
                if (charRead != ',') {
                    imageString += charRead;
                }
                charRead = (char) arq.read();
            }

            String fieldsFromCSVwSubstitute[] = lineRead.split(",", -1);
            String fieldsFromCSV[] = new String[numberCols];
            int i;
            for (i = 0; i < numberCols; i++) {
                fieldsFromCSV[i] = takeSubstituteOff(fieldsFromCSVwSubstitute[i], substituteChar);
            }

            colH.add(fieldsFromCSV[7]);
            colR.add(imageString);

            DataEntry Data = new DataEntry(fieldsFromCSV[1],
                    fieldsFromCSV[2], fieldsFromCSV[3], fieldsFromCSV[4],
                    fieldsFromCSV[5], fieldsFromCSV[8], fieldsFromCSV[9],
                    fieldsFromCSV[10], fieldsFromCSV[11]);
            data.add(Data);
        }
    }

    public void openOnlineArchive(FileReader arq, BufferedReader readArq) throws IOException {
        try {
            URL downloadSite = new URL(src);
            BufferedInputStream inputStream = new BufferedInputStream(downloadSite.openStream());
            FileOutputStream outputFile = new FileOutputStream(fileName);
            byte dataRead[] = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(dataRead, 0, 1024)) != -1) {
                outputFile.write(dataRead, 0, byteContent);
            }
        } catch (MalformedURLException m) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error ocurred!");
            alert.setContentText("The URL wasn't valid");
            alert.showAndWait();
        } catch (IOException s) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error ocurred!");
            alert.setContentText("The archive couldn't be downloaded");
            alert.showAndWait();
        }
    }

    public void openFileCSV() throws IOException {
        File fileCSV = new File(fileName);
        table.getItems().clear();
        FileReader arq = null;
        BufferedReader readArq = null;
        if(!fileCSV.exists()){
            openOnlineArchive(arq, readArq);
        }
            
        arq = new FileReader(fileName);
        readArq = new BufferedReader(arq);
        fillTableWithArchive(readArq);
    }

    public void setSource() {
        TextInputDialog sourceInput = new TextInputDialog("");
        sourceInput.setTitle("Source");
        sourceInput.setHeaderText("Put here your source");
        sourceInput.setContentText("Please enter the url:");

        Optional<String> result = sourceInput.showAndWait();
        result.ifPresent(sourceValue -> src = sourceValue);
    }

    public void aboutWindow() {
        Alert aboutW = new Alert(AlertType.INFORMATION);
        aboutW.setTitle("About");
        aboutW.setHeaderText("EnadeUFSMExplorer");
        aboutW.setContentText("\n\nby: Eduardo Mossmann");
        aboutW.showAndWait();
        aboutW.initModality(Modality.WINDOW_MODAL);
    }

    public void questionData() {
        Alert selectedData = new Alert(AlertType.NONE);
        selectedData.initModality(Modality.WINDOW_MODAL);
        selectedData.setTitle("Selected Column");
        selectedData.setHeaderText("Data from the question: ");

        int rowIndex = table.getSelectionModel().getSelectedIndex();

        selectedData.setContentText("Ano: " + table.getItems().get(rowIndex).getB().get()
                + "\n\nProva: " + table.getItems().get(rowIndex).getC().get()
                + "\n\nTipo Questão: " + table.getItems().get(rowIndex).getD().get()
                + "\n\nId Questão: " + table.getItems().get(rowIndex).getE().get()
                + "\n\nObjeto: " + table.getItems().get(rowIndex).getF().get()
                + "\n\nGabarito: " + colH.get(rowIndex)
                + "\n\nAcertos Curso: " + table.getItems().get(rowIndex).getI().get()
                + "\n\nAcertos Região: " + table.getItems().get(rowIndex).getJ().get()
                + "\n\nAcertos Brasil: " + table.getItems().get(rowIndex).getK().get()
                + "\n\nDif. (Curso-Brasil): " + table.getItems().get(rowIndex).getL().get());

        ButtonType imageButton = new ButtonType("Show Image");
        ButtonType questionCharts = new ButtonType("Charts");
        ButtonType OK = new ButtonType("OK");

        selectedData.getButtonTypes().setAll(questionCharts, imageButton, OK);
        Optional<ButtonType> result = selectedData.showAndWait();

        if (result.get() == imageButton) {
            if (colR.get(rowIndex) != "" && colR.get(rowIndex).toCharArray()[0] == 'h' && colR.get(rowIndex).toCharArray()[0] != 'u') {
                Alert imageAlert = new Alert(AlertType.NONE);
                String URL = colR.get(rowIndex);
                Image imageURL = new Image(URL);
                ImageView imageView = new ImageView(imageURL);
                imageView.setFitHeight(700);
                imageView.setPreserveRatio(true);
                imageAlert.setGraphic(imageView);

                imageAlert.getButtonTypes().setAll(OK);
                imageAlert.initModality(Modality.WINDOW_MODAL);
                imageAlert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Image error");
                alert.setHeaderText("An error ocurred!");
                alert.setContentText("The question don't have an image");
                alert.showAndWait();
            }
            questionData();

        }
        if (result.get() == questionCharts) {
            if (table.getItems().get(rowIndex).getI().get().toCharArray()[0] != '-' && table.getItems().get(rowIndex).getI().get().toCharArray()[0] != 'a') {
                Alert chartsAlert = new Alert(AlertType.NONE);

                NumberAxis numberQ = new NumberAxis();
                CategoryAxis categoryQ = new CategoryAxis();
                BarChart<String, Number> chart = new BarChart<String, Number>(categoryQ, numberQ);

                chart.setTitle("Acertos: ");

                XYChart.Series chartSeries = new XYChart.Series();
                chartSeries.setName("Acertos");

                String[] stringNumbers = {table.getItems().get(rowIndex).getI().get(), table.getItems().get(rowIndex).getJ().get(), table.getItems().get(rowIndex).getK().get()};
                stringNumbers = commaToPoint(stringNumbers);

                chartSeries.getData().add(new XYChart.Data("Curso", Float.valueOf(stringNumbers[0])));
                chartSeries.getData().add(new XYChart.Data("Região", Float.valueOf(stringNumbers[1]))); // String to Float
                chartSeries.getData().add(new XYChart.Data("Brasil", Float.valueOf(stringNumbers[2])));

                chart.getData().addAll(chartSeries);
                chartsAlert.setGraphic(chart);
                chartsAlert.getButtonTypes().setAll(OK);
                chartsAlert.initModality(Modality.WINDOW_MODAL);
                chartsAlert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Chart error");
                alert.setHeaderText("An error ocurred!");
                alert.setContentText("The question don't have the right data");
                alert.showAndWait();
            }
            questionData();
        }

    }

    public void mainIteractions() throws IOException {
        Reload.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                File fileDelete = new File(fileName);
                fileDelete.delete();
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
            public void handle(MouseEvent event) {
                questionData();
            }
        });
    }

    public String[] commaToPoint(String[] stringNumbers) {
        String[] results = new String[3];
        int i;
        for (i = 0; i < 3; i++) {
            results[i] = stringNumbers[i].replaceAll(",", ".");
        }
        return results;
    }

    public void mainStage(Stage primaryStage) throws IOException {

        mainIteractions();

        VBox vb = new VBox();
        vb.getChildren().addAll(menuBar, table);
        primaryStage.setScene(new Scene(vb, 1000, 420));
        primaryStage.show();

    }
}
