package com.kodilla.StockTaking;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.function.Predicate;

import static com.kodilla.StockTaking.App.getOutcomePath;

public class InventorySheet extends Application {

    RecordsDatabase recordsDatabase = new RecordsDatabase();
    PNDatabase pnDatabase = new PNDatabase();

    TableView<Record> table;
    TextField searchField;
    TextField addressInput;
    TextField pnInput;

    ObservableList<Record> records;

    public InventorySheet(FileUploaderSavedData savedData, String team) {
        this.pnDatabase.create(savedData);
        this.recordsDatabase.create(pnDatabase, savedData, team);

        ObservableList<Record> records = FXCollections.observableArrayList();
        records.addAll(recordsDatabase.getRecordsList());
        this.records = records;
    }

    @Override
    public void start(Stage primaryStage) {

    }

    public Scene getScene() {

        //Text fields
        searchField = new TextField();
        searchField.setPromptText("Search");
        searchField.setAlignment(Pos.CENTER);
        searchField.setMinWidth(50);

        pnInput = new TextField();
        pnInput.setPromptText("Part Number");
        pnInput.setMinWidth(100);
        pnInput.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("\\d{0,11}()?")) ? change : null));

        addressInput = new TextField();
        addressInput.setPromptText("Address");
        addressInput.setMinWidth(100);


        //Buttons

        Button nextButton = new Button("->");
        nextButton.setOnAction(event -> nextAddress());

        Button previousButton = new Button("<-");
        previousButton.setOnAction(event -> previousAddress());

        Button saveDataToFileButton = new Button("Save data to file");
        saveDataToFileButton.setOnAction(event -> {
            try {
                final Path path = Paths.get(getOutcomePath());
                Files.write(path,recordsDatabase.getStringRecordList(), StandardCharsets.UTF_8,
                        Files.exists(path) ? StandardOpenOption.WRITE : StandardOpenOption.CREATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button addButton = new Button("Add");
        addButton.setOnAction(event -> addButtonClicked());

        Button delButton = new Button("Del");
        delButton.setOnAction(event -> delButtonClicked());

        //Table

        table = new TableView<>();
        table.setEditable(true);

        TableColumn<Record, String> sLocColumn = new TableColumn<>("SLoc");
        sLocColumn.setMinWidth(50);
        sLocColumn.setCellValueFactory(new PropertyValueFactory<>("storageLocation"));

        TableColumn<Record, String> pnColumn = new TableColumn<>("PN");
        pnColumn.setMinWidth(50);
        pnColumn.setCellValueFactory(new PropertyValueFactory<>("partNumber"));

        TableColumn<Record, TextFieldTableCell> countColumn = new TableColumn<>("Count");
        countColumn.setMinWidth(50);
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));

        TableColumn<Record, String> uomColumn = new TableColumn<>("UoM");
        uomColumn.setMinWidth(50);
        uomColumn.setCellValueFactory(new PropertyValueFactory<>("unitOfMeasure"));

        //uomColumn.setCellFactory(ChoiceBoxListCell.("cm","pc","m"));

        TableColumn<Record, String> buomColumn = new TableColumn<>("BUoM");
        buomColumn.setMinWidth(50);
        buomColumn.setCellValueFactory(new PropertyValueFactory<>("basicUnitOfMeasure"));

        TableColumn<Record, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setMinWidth(50);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.cellValueFactoryProperty().get();

        TableColumn<Record, String> lhgColumn = new TableColumn<>("LHG");
        lhgColumn.setMinWidth(50);
        lhgColumn.setCellValueFactory(new PropertyValueFactory<>("logisticsHandlingGroup"));

        TableColumn<Record, String> descriptionENColumn = new TableColumn<>("EN description");
        descriptionENColumn.setMinWidth(50);
        descriptionENColumn.setCellValueFactory(new PropertyValueFactory<>("descriptionInEnglish"));

        TableColumn<Record, String> descriptionPLColumn = new TableColumn<>("PL description");
        descriptionPLColumn.setMinWidth(50);
        descriptionPLColumn.setCellValueFactory(new PropertyValueFactory<>("descriptionInPolish"));


        TableColumn<Record, String> remarksColumn = new TableColumn<>("Remarks");
        remarksColumn.setMinWidth(50);
        remarksColumn.setCellValueFactory(new PropertyValueFactory<>("remarks"));

        remarksColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        remarksColumn.setOnEditCommit(t -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setRemarks(t.getNewValue())
        );

        table.setItems(records);
        table.getColumns().addAll(sLocColumn, addressColumn, pnColumn, countColumn, uomColumn, buomColumn,
                lhgColumn, descriptionENColumn, descriptionPLColumn, remarksColumn);

        table.getSelectionModel().setCellSelectionEnabled(true);
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        FilteredList<Record> filteredList = new FilteredList<>(records, e -> true);

        searchField.textProperty().addListener(((observable, oldValue, newValue) -> filteredList.setPredicate((Predicate<? super Record>) record -> {

            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseValue = newValue.toLowerCase();

            if (recordsDatabase.containAddress(newValue.toUpperCase())) {
                addressInput.setText(searchField.getText().toUpperCase());
            }

            return record.getAddress().toLowerCase().contains(lowerCaseValue);
        })));

        SortedList<Record> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);

        //TableCell

        table.getSelectionModel().selectFirst();
        table.setTableMenuButtonVisible(true);

        table.requestFocus();
        table.getFocusModel().focus(table.getSelectionModel().getSelectedIndex(), table.getColumns().get(3));

        table.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2 && table.getSelectionModel().getSelectedCells().get(0).getTableColumn().equals(addressColumn)) {
                TablePosition pos = table.getSelectionModel().getSelectedCells().get(0);
                String val = addressColumn.getCellData(pos.getRow());
                searchField.setText("");
                searchField.setText(val);
            }
        });

        //Menus

        // Instructions Menu

        Resources resources = new Resources();
        Menu instructionsMenu = new Menu("_Instructions");

        MenuItem instrSafetyRules = new MenuItem("Safety Rules");
        instrSafetyRules.setOnAction(event -> getHostServices().showDocument(resources.getInstrSafetyRules()));

        MenuItem instrHighlights = new MenuItem("Highlights");
        instrHighlights.setOnAction(event -> getHostServices().showDocument(resources.getInstrHighlights()));

        MenuItem instrCountingRules = new MenuItem("Counting Rules");
        instrCountingRules.setOnAction(event -> getHostServices().showDocument(resources.getInstrCountingRules()));

        MenuItem instrPalletLayout = new MenuItem("Pallet layout");
        instrPalletLayout.setOnAction(event -> getHostServices().showDocument(resources.getInstrPalletLayout()));

        instructionsMenu.getItems().addAll(instrSafetyRules, instrHighlights, instrCountingRules, instrPalletLayout);

        // Map menu
        Menu helpMenu = new Menu("_Help");
        Menu callHelpMenu = new Menu("Call help");
        MenuItem callCoordinatorHelp = new MenuItem("Coordinator");
        MenuItem callEngHelp = new MenuItem("Engineer");
        MenuItem callSoftHelp = new MenuItem("Software support");
        callHelpMenu.getItems().addAll(callCoordinatorHelp, callEngHelp, callSoftHelp);

        MenuItem aboutMenu = new MenuItem("About");
        helpMenu.getItems().addAll(callHelpMenu, aboutMenu);

        Menu mapMenu = new Menu("_Map");

        MenuItem mapWholeArea = new MenuItem("Whole area");
        mapWholeArea.setOnAction((event -> getHostServices().showDocument(resources.getMapMain())));

        MenuItem mapCentralWarehouse = new MenuItem("Central Warehouse");
        mapCentralWarehouse.setOnAction(event -> getHostServices().showDocument(resources.getMapCentral()));

        MenuItem mapChem = new MenuItem("Chemistry Warehouse");
        mapChem.setOnAction(event -> getHostServices().showDocument(resources.getMapChem()));

        MenuItem mapCkdIndoor = new MenuItem("CKD indoor");
        mapCkdIndoor.setOnAction(event -> getHostServices().showDocument(resources.getMapCKD_1()));

        MenuItem mapShed = new MenuItem("WB Shed");
        mapShed.setOnAction(event -> getHostServices().showDocument(resources.getMapShed()));

        MenuItem mapCkdOutdoor = new MenuItem("CKD outdoor");
        mapCkdOutdoor.setOnAction(event -> getHostServices().showDocument(resources.getMapCKD_2()));

        MenuItem mapFrames = new MenuItem("Frames");
        mapFrames.setOnAction(event -> getHostServices().showDocument(resources.getMapFrames()));

        MenuItem mapTent1 = new MenuItem("Tent 1");
        mapTent1.setOnAction(event -> getHostServices().showDocument(resources.getMapN1()));

        MenuItem mapTent2 = new MenuItem("Tent 2");
        mapTent2.setOnAction(event -> getHostServices().showDocument(resources.getMapN2()));

        MenuItem mapGlass = new MenuItem("Glass");
        mapGlass.setOnAction(event -> getHostServices().showDocument(resources.getMapGlass()));

        MenuItem mapWc = new MenuItem("WC hall");
        mapWc.setOnAction(event -> getHostServices().showDocument(resources.getMapWC()));

        mapMenu.getItems().addAll(
                mapWholeArea, new SeparatorMenuItem(),
                mapCentralWarehouse, mapChem, mapCkdIndoor, new SeparatorMenuItem(),
                mapCkdOutdoor, mapShed, mapFrames, new SeparatorMenuItem(),
                mapTent1, new SeparatorMenuItem(),
                mapTent2, new SeparatorMenuItem(),
                mapGlass, new SeparatorMenuItem(),
                mapWc);
        // Settings menu

        Menu settingsMenu = new Menu("_Settings");
        MenuItem fontSettings = new MenuItem("Font Size");
        MenuItem sortSettings = new MenuItem("Sort");
        Menu viewMenu = new Menu("View");
        MenuItem treeView = new MenuItem("Tree view");
        MenuItem addressView = new MenuItem("Address view");
        MenuItem allItemsView = new MenuItem("List view");
        viewMenu.getItems().addAll(treeView, addressView, allItemsView);
        settingsMenu.getItems().addAll(fontSettings, sortSettings, viewMenu);

        MenuBar bar = new MenuBar();
        bar.getMenus().addAll(settingsMenu, instructionsMenu, mapMenu, helpMenu);

        //Structure

        HBox upperHBox = new HBox();
        upperHBox.setPadding(new Insets(10, 10, 10, 10));
        upperHBox.setSpacing(10);
        upperHBox.getChildren().addAll(previousButton, searchField, nextButton,saveDataToFileButton);

        HBox bottomHBox = new HBox();
        bottomHBox.setPadding(new Insets(10, 10, 10, 10));
        bottomHBox.setSpacing(10);
        bottomHBox.getChildren().addAll(addressInput, pnInput, addButton, delButton);

        VBox vBox1 = new VBox();
        vBox1.getChildren().addAll(bar, upperHBox, table, bottomHBox);

        return new Scene(vBox1, 1000, 400);
    }

    private void previousAddress() {
        List<String> listOfAddresses = recordsDatabase.addressesList;
        String address = searchField.getText().toUpperCase();
        if (listOfAddresses.contains(address) && (listOfAddresses.indexOf(address) - 1) >= 0) {
            searchField.setText(listOfAddresses.get(listOfAddresses.indexOf(address) - 1));
        }
    }

    private void nextAddress() {

        List<String> listOfAddresses = recordsDatabase.addressesList;
        String address = searchField.getText().toUpperCase();

        if (listOfAddresses.contains(address) && (listOfAddresses.indexOf(address) + 1) <= listOfAddresses.size()) {
            searchField.setText(listOfAddresses.get(listOfAddresses.indexOf(address) + 1));
        }
    }

    private void addButtonClicked() {

        String address = addressInput.getText().toUpperCase();
        String pn = pnInput.getText();

        boolean addressExist = recordsDatabase.containAddress(address);
        boolean pnExist = pnDatabase.containsPN(pn);

        if (pnExist && addressExist) {
            Record record = new Record(address, pn, pnDatabase, true);
            if (!recordsDatabase.contains(record)) {
                recordsDatabase.addRecord(record);
                records.clear();
                records.addAll(recordsDatabase.getRecordsList());
                searchField.setText(addressInput.getText());
                pnInput.clear();
                addressInput.clear();
            } else {
                AlertBox.display("Wrong data!", "Record exist!");
            }
        } else if (!pnExist) {
            AlertBox.display("Wrong data!", "This number does not exist");
        } else if (!addressExist) {
            AlertBox.display("Wrong data!", "This address does not exist or is out of range");
        }
    }

    private void delButtonClicked() {
        ObservableList<Record> recordSelected = table.getSelectionModel().getSelectedItems();

        recordSelected.forEach(record -> {
            if (record.isCreateByUser()) {
                recordsDatabase.remove(record);
                records.clear();
                records.addAll(recordsDatabase.getRecordsList());
                searchField.setText(addressInput.getText());

            } else {
                AlertBox.display("Forbidden action!", "Can not remove record not created by the user!");
            }
        });

        pnInput.clear();
        addressInput.clear();
    }

}