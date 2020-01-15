import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;

/*
To get JavaFX working follow the following steps:
1. Install JavaFX
2. Go to project structure -> libraries -> add -> JavaFX install location -> select lib -> apply
3. Go to configuration (of the file (next to the run button)) -> VM options -> paste --module-path "C:\Program Files\Java\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml
 */

/*
Sources
Mouse zoom - https://www.youtube.com/watch?v=SiPfsZA_GeI - 21/12
Mouse rotation - https://www.youtube.com/watch?v=yinIKzg7duc - 21/12
Keyboard rotation (start) - https://www.youtube.com/watch?v=dNtZVVJ-lBg&list=PLhs1urmduZ295Ryetga7CNOqDymN_rhB_&index=4 - 18/12
Object outline - https://stackoverflow.com/questions/42984225/javafx-shape3d-with-border - 18/12
 */

public class FX3D extends Application {
    final static Color BACKGROUND_COLOR = Color.rgb(220, 220, 220);
    final static Color CONTAINER_COLOR = Color.rgb(0, 0, 0, 0.2);
    final static Color EDGE_COLOR = Color.rgb(0, 0, 0, 0.5);
    final static Rectangle2D screenInfo = Screen.getPrimary().getBounds();
    final static int SCREEN_WIDTH = (int) screenInfo.getWidth();
    final static int SCREEN_HEIGHT = (int) screenInfo.getHeight()-100;

    final static double TWO_D_WIDTH = .25 * SCREEN_WIDTH;
    final static double THREE_D_WIDTH = .75 * SCREEN_WIDTH;

    static Stage mainStage;
    static GridPane topGrid;
    static Group twoDGroup;
    //SmartGroup helps with rotations
    static SmartGroup threeDGroup;

    static HBox root;
    static Scene mainScene;
    static SubScene twoD;
    static SubScene threeD;

    //some variables for mouse rotation
    private static double anchorX, anchorY;
    private static double anchorAngleX;
    private static double anchorAngleY;
    private static DoubleProperty angleX;
    private static DoubleProperty angleY;

    //Setup camera
    static Camera camera;

    // Visibility of the warning message
    static boolean visibleWarning = false;
    static boolean textFieldsFilled = true;

    // Slider value
    static double valueSlider = Wrapper.CONTAINER_HEIGHT/Wrapper.cellSize;

    //Create materials
    final static PhongMaterial edge_material = new PhongMaterial();
    final static PhongMaterial container_material = new PhongMaterial();

    /*DEFAULT UI COMPONENTS*/
    static Label scoringLabel;
    static Button startButton;
    static ChoiceBox typeSelection;
    static ChoiceBox shapeSelection;
    static ProgressIndicator[] pins;
    static ProgressIndicator pin;
    static Label layerLabel;
    static Slider layerSlider;

    //Parcel selection UI
    static ArrayList<TextField> parcelTextValueFields;
    static ArrayList<TextField> parcelTextAmountFields;

    static ArrayList<TextField> pentominoTextValueFields;
    static ArrayList<TextField> pentominoTextAmountFields;

    static ArrayList<Label> parcelTextValueLabels;
    static ArrayList<Label> parcelTextAmountLabels;

    static ArrayList<Label> pentominoTextValueLabels;
    static ArrayList<Label> pentominoTextAmountLabels;

    static Label ParcelAAmountLabel;
    static Label ParcelBAmountLabel;
    static Label ParcelCAmountLabel;
    static TextField ParcelAAmountTextField;
    static TextField ParcelBAmountTextField;
    static TextField ParcelCAmountTextField;

    static Label ParcelAValueLabel;
    static Label ParcelBValueLabel;
    static Label ParcelCValueLabel;
    static TextField ParcelAValueTextField;
    static TextField ParcelBValueTextField;
    static TextField ParcelCValueTextField;

    static inputDetail inputDetail1;
    static inputDetail inputDetail2;
    static inputDetail inputDetail3;

    //Pentominoe selection UI
    static Label LPentominoAmountLabel;
    static Label PPentominoAmountLabel;
    static Label TPentominoAmountLabel;
    static TextField LPentominoAmountTextField;
    static TextField PPentominoAmountTextField;
    static TextField TPentominoAmountTextField;

    static Label LPentominoValueLabel;
    static Label PPentominoValueLabel;
    static Label TPentominoValueLabel;
    static TextField LPentominoValueTextField;
    static TextField PPentominoValueTextField;
    static TextField TPentominoValueTextField;

    static Label warningLabel;
    /*END*/

    //create group of parcels
    static ArrayList<UIParcel> parcels;
    
    //keep track of UIInput during 'layering'
    static int[][][] tmpUIInput = new int[Wrapper.CONTAINER_WIDTH/Wrapper.cellSize][Wrapper.CONTAINER_HEIGHT/Wrapper.cellSize][Wrapper.CONTAINER_DEPTH/Wrapper.cellSize];

    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage stage){
        mainStage = stage;
        bootUI();
    }

    public static void bootUI(){
        setupUIPreElements(mainStage);
        setupSlider(mainStage);
        setupUIElements(mainStage);
        setupUIPostElements(mainStage);
    }

    public static void updateUI(){
        updateUIPreElements(mainStage);
        updateTmpUIInput();
        updateUIElements(mainStage);
        updateUIPostElements(mainStage);
    }

    public static void setupUIComponents(){
        //Setup grids, groups, scenes, camera and such so that the scene is made from scratch
        topGrid = new GridPane();
        twoDGroup = new Group();
        threeDGroup = new SmartGroup();
        root = new HBox();
        mainScene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, true);
        twoD = new SubScene(twoDGroup, SCREEN_WIDTH*.25, SCREEN_HEIGHT, false, SceneAntialiasing.BALANCED);
        threeD = new SubScene(threeDGroup, SCREEN_WIDTH*.75, SCREEN_HEIGHT, true, SceneAntialiasing.BALANCED);
        anchorAngleX = 0;
        anchorAngleY = 0;
        angleX = new SimpleDoubleProperty(0);
        angleY = new SimpleDoubleProperty(0);
        camera = new PerspectiveCamera();
        pins = new ProgressIndicator[1];
        pin = pins[0] = new ProgressIndicator();
        parcels = new ArrayList<UIParcel>();

        parcelTextValueFields = new ArrayList<>();
        parcelTextAmountFields = new ArrayList<>();

        pentominoTextValueFields = new ArrayList<>();
        pentominoTextAmountFields = new ArrayList<>();

        parcelTextValueLabels = new ArrayList<>();
        parcelTextAmountLabels = new ArrayList<>();

        pentominoTextValueLabels = new ArrayList<>();
        pentominoTextAmountLabels = new ArrayList<>();
    }

    public static void setupUIPreElements(Stage stage){
        setupUIComponents();

        //add subscenes to scene
        root.getChildren().addAll(twoD, threeD);
        root.setSpacing(10);
        root.setPadding(new Insets(20, 20, 20, 20));

        /*START Setup top menu*/
        //Setup grid
        topGrid.setHgap(10);
        topGrid.setVgap(10);

        //Setup items
        //Add scoring label
        scoringLabel = new Label("Score: " + Wrapper.score);
        startButton = new Button("Start");

        typeSelection = new ChoiceBox(FXCollections.observableArrayList(
                "A", "B", "C", "D", "General"
        ));

        typeSelection.setValue("");

        shapeSelection = new ChoiceBox(FXCollections.observableArrayList(
                "Parcels", "Pentominoes"
        ));

        shapeSelection.setValue("");

        //Parcel selection UI
        ParcelAAmountLabel = new Label("Amount of parcel A: ");
        ParcelBAmountLabel = new Label("Amount of parcel B: ");
        ParcelCAmountLabel = new Label("Amount of parcel C: ");
        ParcelAAmountTextField = new TextField();
        ParcelBAmountTextField = new TextField();
        ParcelCAmountTextField = new TextField();

        ParcelAValueLabel = new Label("Value of parcel A: ");
        ParcelBValueLabel = new Label("Value of parcel B: ");
        ParcelCValueLabel = new Label("Value of parcel C: ");
        ParcelAValueTextField = new TextField();
        ParcelBValueTextField = new TextField();
        ParcelCValueTextField = new TextField();

        //Pentominoe selection UI
        LPentominoAmountLabel = new Label("Amount of L pentominoes: ");
        PPentominoAmountLabel = new Label("Amount of P pentominoes: ");
        TPentominoAmountLabel = new Label("Amount of T pentominoes: ");
        LPentominoAmountTextField = new TextField();
        PPentominoAmountTextField = new TextField();
        TPentominoAmountTextField = new TextField();

        LPentominoValueLabel = new Label("Value of L pentominoes: ");
        PPentominoValueLabel = new Label("Value of P pentominoes: ");
        TPentominoValueLabel = new Label("Value of T pentominoes: ");
        LPentominoValueTextField = new TextField();
        PPentominoValueTextField = new TextField();
        TPentominoValueTextField = new TextField();

        warningLabel = new Label("Fill in every field correctly!");
        warningLabel.setFont(Font.font(null, FontWeight.BOLD, 14.0));
        warningLabel.setTextFill(Color.rgb(255, 80, 80));
        warningLabel.setVisible(false);

        //-1 will make it display an animated disk, set to 1 to show that it's done
        //pin is the progress indicator
        pin.setProgress(-1);

        parcelTextAmountFields.add(ParcelAAmountTextField);
        parcelTextAmountFields.add(ParcelBAmountTextField);
        parcelTextAmountFields.add(ParcelCAmountTextField);

        parcelTextValueFields.add(ParcelAValueTextField);
        parcelTextValueFields.add(ParcelBValueTextField);
        parcelTextValueFields.add(ParcelCValueTextField);

        pentominoTextAmountFields.add(LPentominoAmountTextField);
        pentominoTextAmountFields.add(PPentominoAmountTextField);
        pentominoTextAmountFields.add(TPentominoAmountTextField);

        pentominoTextValueFields.add(LPentominoValueTextField);
        pentominoTextValueFields.add(PPentominoValueTextField);
        pentominoTextValueFields.add(TPentominoValueTextField);

        parcelTextValueLabels.add(ParcelAValueLabel);
        parcelTextValueLabels.add(ParcelBValueLabel);
        parcelTextValueLabels.add(ParcelCValueLabel);

        parcelTextAmountLabels.add(ParcelAAmountLabel);
        parcelTextAmountLabels.add(ParcelBAmountLabel);
        parcelTextAmountLabels.add(ParcelCAmountLabel);

        pentominoTextValueLabels.add(LPentominoValueLabel);
        pentominoTextValueLabels.add(PPentominoValueLabel);
        pentominoTextValueLabels.add(TPentominoValueLabel);

        pentominoTextAmountLabels.add(LPentominoAmountLabel);
        pentominoTextAmountLabels.add(PPentominoAmountLabel);
        pentominoTextAmountLabels.add(TPentominoAmountLabel);

        topGrid.add(scoringLabel, 2, 0);
        topGrid.add(typeSelection, 0, 0);
        topGrid.add(shapeSelection, 0, 1);
        topGrid.add(warningLabel, 1, 1);
        topGrid.add(startButton, 0, 9);
        twoDGroup.getChildren().add(topGrid);
        /*END*/

        //Set materials
        container_material.setDiffuseColor(CONTAINER_COLOR);
        edge_material.setDiffuseColor(EDGE_COLOR);
    }

    public static void setupUIElements(Stage stage){
        addContainer();
    }

    public static void setupUIPostElements(Stage stage){
        //Setup camera (so that you can have the container at the origin and can still see it well
        camera.setTranslateX((-SCREEN_WIDTH/2+Wrapper.CONTAINER_WIDTH/2) + TWO_D_WIDTH/2);
        camera.setTranslateY(-SCREEN_HEIGHT/2+Wrapper.CONTAINER_HEIGHT/2);
        camera.setTranslateZ(-Wrapper.CONTAINER_DEPTH/0.22);

        //Setup mouse rotation
        initMouseControl(threeDGroup, threeD, stage);

        //Set eventListener for mode selection
        shapeSelection.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            //check what mode was selected and show the corresponding options
            if(newValue.equals("Parcels")){
                //remove other option
                if(oldValue.equals("Pentominoes")){
                    topGrid.getChildren().removeAll(LPentominoAmountLabel, PPentominoAmountLabel, TPentominoAmountLabel, LPentominoAmountTextField, PPentominoAmountTextField, TPentominoAmountTextField, LPentominoValueLabel, PPentominoValueLabel, TPentominoValueLabel, LPentominoValueTextField, PPentominoValueTextField, TPentominoValueTextField);
                }

                //add labels and text fields
                topGrid.add(ParcelAAmountLabel, 0, 2);
                topGrid.add(ParcelAAmountTextField, 1, 2);

                topGrid.add(ParcelBAmountLabel, 0, 4);
                topGrid.add(ParcelBAmountTextField, 1, 4);

                topGrid.add(ParcelCAmountLabel, 0, 6);
                topGrid.add(ParcelCAmountTextField, 1, 6);

                topGrid.add(ParcelAValueLabel, 0, 3);
                topGrid.add(ParcelAValueTextField, 1, 3);

                topGrid.add(ParcelBValueLabel, 0, 5);
                topGrid.add(ParcelBValueTextField, 1, 5);

                topGrid.add(ParcelCValueLabel, 0, 7);
                topGrid.add(ParcelCValueTextField, 1, 7);

            } else if (newValue.equals("Pentominoes")){
                //remove other option
                if(oldValue.equals("Parcels")){
                    topGrid.getChildren().removeAll(ParcelAAmountLabel, ParcelBAmountLabel, ParcelCAmountLabel, ParcelAAmountTextField, ParcelBAmountTextField, ParcelCAmountTextField, ParcelAValueLabel, ParcelBValueLabel, ParcelCValueLabel, ParcelAValueTextField, ParcelBValueTextField, ParcelCValueTextField);
                }

                //add labels and text fields
                topGrid.add(LPentominoAmountLabel, 0, 2);
                topGrid.add(LPentominoAmountTextField, 1, 2);

                topGrid.add(PPentominoAmountLabel, 0, 4);
                topGrid.add(PPentominoAmountTextField, 1, 4);

                topGrid.add(TPentominoAmountLabel, 0, 6);
                topGrid.add(TPentominoAmountTextField, 1, 6);

                topGrid.add(LPentominoValueLabel, 0, 3);
                topGrid.add(LPentominoValueTextField, 1, 3);

                topGrid.add(PPentominoValueLabel, 0, 5);
                topGrid.add(PPentominoValueTextField, 1, 5);

                topGrid.add(TPentominoValueLabel, 0, 7);
                topGrid.add(TPentominoValueTextField, 1, 7);
            } else if (newValue.equals("")){
                //remove all
                topGrid.getChildren().removeAll(ParcelAAmountLabel, ParcelBAmountLabel, ParcelCAmountLabel, ParcelAAmountTextField, ParcelBAmountTextField, ParcelCAmountTextField, ParcelAValueLabel, ParcelBValueLabel, ParcelCValueLabel, ParcelAValueTextField, ParcelBValueTextField, ParcelCValueTextField);
                topGrid.getChildren().removeAll(LPentominoAmountLabel, PPentominoAmountLabel, TPentominoAmountLabel, LPentominoAmountTextField, PPentominoAmountTextField, TPentominoAmountTextField, LPentominoValueLabel, PPentominoValueLabel, TPentominoValueLabel, LPentominoValueTextField, PPentominoValueTextField, TPentominoValueTextField);
            }
        });

        //Set eventListener for mode selection
        typeSelection.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if(newValue.equals("A")){

                //Exact cover for parcels
                
                //Remove parcel and pentominoe options
                for (TextField parcelAmountField : parcelTextAmountFields){
                    topGrid.getChildren().remove(parcelAmountField);
                }
                for (TextField parcelValueField : parcelTextValueFields){
                    topGrid.getChildren().remove(parcelValueField);
                }

                for (TextField pentominoeAmountField : pentominoTextAmountFields){
                    topGrid.getChildren().remove(pentominoeAmountField);
                }
                for (TextField pentominoeValueField : pentominoTextValueFields){
                    topGrid.getChildren().remove(pentominoeValueField);
                }

                for (Label parcelAmountLabel : parcelTextAmountLabels){
                    topGrid.getChildren().remove(parcelAmountLabel);
                }
                for (Label parcelValueLabel : parcelTextValueLabels){
                    topGrid.getChildren().remove(parcelValueLabel);
                }

                for (Label pentominoeAmountLabel : pentominoTextAmountLabels){
                    topGrid.getChildren().remove(pentominoeAmountLabel);
                }
                for (Label pentominoeValueLabel : pentominoTextValueLabels){
                    topGrid.getChildren().remove(pentominoeValueLabel);
                }


                topGrid.getChildren().remove(shapeSelection);


            } else if(newValue.equals("B")){
                //Optimization for parcels
                topGrid.getChildren().remove(shapeSelection);
                topGrid.add(shapeSelection, 0, 1);
                //set to empty first so there will always be a change
                shapeSelection.getSelectionModel().select("");
                shapeSelection.getSelectionModel().select("Parcels");
                //Remove amount options
                topGrid.getChildren().removeAll(parcelTextAmountLabels);
                topGrid.getChildren().removeAll(parcelTextAmountFields);
                shapeSelection.setDisable(true);

            } else if(newValue.equals("C")){
                //Exact cover for pentominoes

                //Remove parcel and pentominoe options
                for (TextField parcelAmountField : parcelTextAmountFields){
                    topGrid.getChildren().remove(parcelAmountField);
                }
                for (TextField parcelValueField : parcelTextValueFields){
                    topGrid.getChildren().remove(parcelValueField);
                }

                for (TextField pentominoeAmountField : pentominoTextAmountFields){
                    topGrid.getChildren().remove(pentominoeAmountField);
                }
                for (TextField pentominoeValueField : pentominoTextValueFields){
                    topGrid.getChildren().remove(pentominoeValueField);
                }

                for (Label parcelAmountLabel : parcelTextAmountLabels){
                    topGrid.getChildren().remove(parcelAmountLabel);
                }
                for (Label parcelValueLabel : parcelTextValueLabels){
                    topGrid.getChildren().remove(parcelValueLabel);
                }

                for (Label pentominoeAmountLabel : pentominoTextAmountLabels){
                    topGrid.getChildren().remove(pentominoeAmountLabel);
                }
                for (Label pentominoeValueLabel : pentominoTextValueLabels){
                    topGrid.getChildren().remove(pentominoeValueLabel);
                }

                topGrid.getChildren().remove(shapeSelection);
                
            } else if(newValue.equals("D")){
                //Optimization for pentominoes
                topGrid.getChildren().remove(shapeSelection);
                topGrid.add(shapeSelection, 0, 1);
                //set to empty first so there will always be a change
                shapeSelection.getSelectionModel().select("");
                shapeSelection.getSelectionModel().select("Pentominoes");
                //Remove amount options
                topGrid.getChildren().removeAll(pentominoTextAmountLabels);
                topGrid.getChildren().removeAll(pentominoTextAmountFields);
                shapeSelection.setDisable(true);

            } else if(newValue.equals("General")){
                //Show all
                topGrid.getChildren().remove(shapeSelection);
                topGrid.add(shapeSelection, 0, 1);
                shapeSelection.getSelectionModel().select("");
                shapeSelection.getSelectionModel().select("Parcels");
                shapeSelection.setDisable(false);
            }
        });

        //Set evenListener for start button
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event-> {
            //show loading icon
            pin.setVisible(true);

            //Check if the input is valid
            textFieldsFilled = true;
            boolean validInput = false;

            // Retrieve selected option (Parcels or Pentominoes) and pass along value to Wrapper
            Wrapper.inputType = shapeSelection.getValue().toString();
            Wrapper.problemType = typeSelection.getValue().toString();

            //If it's exact cover, there's no need to pass the values
            if (Wrapper.problemType.equals("A") || Wrapper.problemType.equals("C")){
                validInput = true;
            } else {
                // Depending on the selected option, instantiate the corresponding inputDetail objects
                if (Wrapper.inputType.equals("Parcels")) {
                    for(TextField field : parcelTextAmountFields){
                        //if a field is empty, don't accept the input
                        if(field.getText().trim().isEmpty()){
                            textFieldsFilled = false;
                        }
                    }

                    for(TextField field : parcelTextValueFields){
                        //if a field is empty, don't accept the input
                        if(field.getText().trim().isEmpty()){
                            textFieldsFilled = false;
                        }
                    }

                    if(textFieldsFilled){
                        // A
                        inputDetail1 = new inputDetail("A", Integer.parseInt(ParcelAAmountTextField.getText()), Float.parseFloat(ParcelAValueTextField.getText()));
                        // B
                        inputDetail2 = new inputDetail("B", Integer.parseInt(ParcelBAmountTextField.getText()), Float.parseFloat(ParcelBValueTextField.getText()));
                        // C
                        inputDetail3 = new inputDetail("C", Integer.parseInt(ParcelCAmountTextField.getText()), Float.parseFloat(ParcelCValueTextField.getText()));
                    }
                } else if (Wrapper.inputType.equals("Pentominoes")) {
                    for(TextField field : pentominoTextAmountFields){
                        //if a field is empty, don't accept the input
                        if(field.getText().trim().isEmpty()){
                            textFieldsFilled = false;
                        }
                    }

                    for(TextField field : pentominoTextValueFields){
                        //if a field is empty, don't accept the input
                        if(field.getText().trim().isEmpty()){
                            textFieldsFilled = false;
                        }
                    }

                    if(textFieldsFilled){
                        // L
                        inputDetail1 = new inputDetail("L", Integer.parseInt(LPentominoAmountTextField.getText()), Float.parseFloat(LPentominoValueTextField.getText()));
                        // P
                        inputDetail2 = new inputDetail("P", Integer.parseInt(PPentominoAmountTextField.getText()), Float.parseFloat(PPentominoValueTextField.getText()));
                        // T
                        inputDetail3 = new inputDetail("T", Integer.parseInt(TPentominoAmountTextField.getText()), Float.parseFloat(TPentominoValueTextField.getText()));
                    }
                }
            }

            if(textFieldsFilled){
                //Load input
                validInput = true;

                // Fill inputDetails[]
                Wrapper.inputDetails[0] = inputDetail1;
                Wrapper.inputDetails[1] = inputDetail2;
                Wrapper.inputDetails[2] = inputDetail3;
            } else {
                // Show warning
                visibleWarning = true;
            }

            if(validInput){
                // Hide warning
                visibleWarning = false;

                //TODO start calculations
            }

            //TODO remove after testing
            test.giveInput();
        });

        threeD.setCamera(camera);
        stage.setTitle("Filling 3D objects");
        threeD.setFill(BACKGROUND_COLOR);
        stage.setScene(mainScene);
        stage.show();
    }

    public static void setupSlider(Stage stage){
        layerLabel = new Label("Choose the amount of layers to view:");
        layerSlider = new Slider(0, Wrapper.CONTAINER_HEIGHT/Wrapper.cellSize, valueSlider);
        layerSlider.setShowTickLabels(true);
        layerSlider.setShowTickMarks(true);
        layerSlider.setBlockIncrement(1);
        layerSlider.setMajorTickUnit(1);
        layerSlider.setMinorTickCount(0);
        layerSlider.setSnapToTicks(true);

        //hide until needed
        layerLabel.setVisible(false);
        layerSlider.setVisible(false);

        topGrid.add(layerLabel, 0, 10);
        topGrid.add(layerSlider, 0, 11);

        layerSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                // Fetch the value of the slider
                valueSlider = newValue.intValue();

                // Set all x and z values above the specified y value to 0 while coping the rest
                updateTmpUIInput();

                updateUI();
            }
        });
    }

    static void updateUIPreElements(Stage stage){
        warningLabel.setVisible(visibleWarning);
        pin.setVisible(false);

        layerLabel.setVisible(true);
        layerSlider.setVisible(true);
    }

    static void updateUIElements(Stage stage){
        //clear current 3D elements
        threeDGroup.getChildren().clear();
        parcels.clear();

        int red = 0;
        int green = 0;
        int blue = 0;

        //give every filled in field a box representation and keep color in mind
        //create all the boxes
        for(int x=0; x<tmpUIInput.length; x++){
            for(int y=0; y<tmpUIInput[x].length; y++){
                for(int z=0; z<tmpUIInput[x][y].length; z++){
                    int currentValue = tmpUIInput[x][y][z];

                    //if this field is filled
                    if(currentValue!=0){
                        //update color range
                        if(currentValue==1){
                            red = 50;
                            green = 180;
                            blue = 165;
                        } else if (currentValue==2){
                            red = 170;
                            green = 220;
                            blue = 40;
                        } else {
                            red = 220;
                            green = 50;
                            blue = 40;
                        }

                        UIParcel cellBox = new UIParcel(x*Wrapper.cellSize, y*Wrapper.cellSize, z*Wrapper.cellSize, Wrapper.cellSize, Wrapper.cellSize, Wrapper.cellSize, red, green, blue);
                        parcels.add(cellBox);
                    }
                }
            }
        }

        //show them
        threeDGroup.getChildren().addAll(parcels);

        //MUST BE DONE AFTER ADDING THE BOXES, otherwise they'll be invisible (thanks javaFX)
        addContainer();
    }

    static void updateUIPostElements(Stage stage){

    }

    static void updateTmpUIInput(){
        // Set all x and z values above the specified y value (valueSlider) to 0 while coping the rest
        for(int x = 0; x < tmpUIInput.length; x++) {
            for(int y = 0; y < tmpUIInput[x].length; y++) {
                for(int z = 0; z < tmpUIInput[x][y].length; z++) {
                    tmpUIInput[x][y][z] = Wrapper.UIInput[x][y][z];
                }
            }
        }

        for(int x = 0; x < tmpUIInput.length; x++) {
            for (int y = 0; y < tmpUIInput[x].length - (int) valueSlider; y++) {
                for (int z = 0; z < tmpUIInput[x][y].length; z++) {
                    tmpUIInput[x][y][z] = 0;
                }
            }
        }

    }

    static void addContainer(){
        //Create container (note: Has to be created after adding all the other objects in order to use transparency (I know, javaFX can be crappy))
        int containerPadding = 20;
        Box container = new Box(Wrapper.CONTAINER_WIDTH+containerPadding, Wrapper.CONTAINER_HEIGHT+containerPadding, Wrapper.CONTAINER_DEPTH+containerPadding);
        container.setTranslateX(Wrapper.CONTAINER_WIDTH/2);
        container.setTranslateY(Wrapper.CONTAINER_HEIGHT/2);
        container.setTranslateZ(Wrapper.CONTAINER_DEPTH/2);
        container.setMaterial(container_material);
        threeDGroup.getChildren().add(container);
    }

    static class SmartGroup extends Group {
        Rotate rotateDelta;
        Transform rotation = new Rotate();
    }

    //Needed for mouse rotation
    private static void initMouseControl(SmartGroup contentGroup, SubScene scene, Stage stage){
        //Prepare X and Y axis rotation transformation obejcts
        Rotate xRotate;
        Rotate yRotate;
        //Add both transformation to the container
        contentGroup.getTransforms().addAll(
                xRotate = new Rotate(0, Wrapper.CONTAINER_WIDTH/2, Wrapper.CONTAINER_HEIGHT/2, Wrapper.CONTAINER_DEPTH/2, Rotate.X_AXIS),
                yRotate = new Rotate(0, Wrapper.CONTAINER_WIDTH/2, Wrapper.CONTAINER_HEIGHT/2, Wrapper.CONTAINER_DEPTH/2, Rotate.Y_AXIS)
        );
        /*Bind Double property angleX/angleY with corresponding transformation.
        When we update angleX / angleY, the transform will also be auto updated.*/
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();

            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + (anchorX - event.getSceneX()));
        });

        stage.addEventHandler(ScrollEvent.SCROLL, event ->{
            double movement = event.getDeltaY();
            contentGroup.translateZProperty().set(contentGroup.getTranslateZ() - movement);
        });
    }
}
