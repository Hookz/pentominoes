import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
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
    final static int threeDOffsetLeft = 200;
    final static int cellSize = 50;
    final static Color BACKGROUND_COLOR = Color.rgb(220, 220, 220);
    final static Color CONTAINER_COLOR = Color.rgb(0, 0, 0, 0.2);
    final static Color EDGE_COLOR = Color.rgb(0, 0, 0, 0.5);
    final static int SCREEN_WIDTH = 1920;
    final static int SCREEN_HEIGHT = 1000;

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

    // Slider value
    static double valueSlider = Wrapper.CONTAINER_HEIGHT/cellSize;

    //Create materials
    final static PhongMaterial edge_material = new PhongMaterial();
    final static PhongMaterial container_material = new PhongMaterial();

    //Edge settings
    //TODO try out different values
    final static double EDGE_WIDTH = .5;

    /*DEFAULT UI COMPONENTS*/
    static Label scoringLabel;
    static Button startButton;
    static ChoiceBox modeSelection;
    static ProgressIndicator[] pins;
    static ProgressIndicator pin;
    static Label layerLabel;
    static Slider layerSlider;

    //Parcel selection UI
    static ArrayList<TextField> parcelTextFields = new ArrayList<>();
    static ArrayList<TextField> pentominoTextFields = new ArrayList<>();

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

    // Input details
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

    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage stage){
        mainStage = stage;
        bootUI();
    }

    public static void bootUI(){
        setupUIPreElements(mainStage);
        setupUIPostElements(mainStage);
    }

    public static void updateUI(int[][][] resultBoxesArray){
        setupUIPreElements(mainStage);

        setupUIElements(mainStage, resultBoxesArray);

        setupSlider(mainStage);

        setupUIPostElements(mainStage);
    }

    public static void setupUIPreElements(Stage stage){
        //Setup grids, groups, scenes, camera and such so that the scene is made from scratch
        topGrid = new GridPane();
        twoDGroup = new Group();
        threeDGroup = new SmartGroup();
        root = new HBox();
        mainScene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, true);
        twoD = new SubScene(twoDGroup, SCREEN_WIDTH*.2, SCREEN_HEIGHT);
        threeD = new SubScene(threeDGroup, SCREEN_WIDTH*.8, SCREEN_HEIGHT, true, SceneAntialiasing.BALANCED);
        anchorAngleX = 0;
        anchorAngleY = 0;
        angleX = new SimpleDoubleProperty(0);
        angleY = new SimpleDoubleProperty(0);
        camera = new PerspectiveCamera();
        pins = new ProgressIndicator[1];
        pin = pins[0] = new ProgressIndicator();
        parcels = new ArrayList<UIParcel>();

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

        modeSelection = new ChoiceBox(FXCollections.observableArrayList(
                "Parcels", "Pentominoes"
        ));

        modeSelection.setValue("");

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
        warningLabel.setTextFill(Color.rgb(255, 0, 0));
        warningLabel.setVisible(visibleWarning);

        parcelTextFields.add(ParcelAAmountTextField);
        parcelTextFields.add(ParcelBAmountTextField);
        parcelTextFields.add(ParcelCAmountTextField);
        parcelTextFields.add(ParcelAValueTextField);
        parcelTextFields.add(ParcelBValueTextField);
        parcelTextFields.add(ParcelCValueTextField);
        pentominoTextFields.add(LPentominoAmountTextField);
        pentominoTextFields.add(PPentominoAmountTextField);
        pentominoTextFields.add(TPentominoAmountTextField);
        pentominoTextFields.add(LPentominoValueTextField);
        pentominoTextFields.add(PPentominoValueTextField);
        pentominoTextFields.add(TPentominoValueTextField);

        //-1 will make it display an animated disk, set to 1 to show that it's done
        //pin is the progress indicator
        pin.setProgress(-1);

        topGrid.add(scoringLabel, 0, 0);
        topGrid.add(modeSelection, 0, 1);
        topGrid.add(warningLabel, 1, 1);
        topGrid.add(startButton, 0, 8);
        twoDGroup.getChildren().add(topGrid);
        /*END*/

        //Set materials
        container_material.setDiffuseColor(CONTAINER_COLOR);
        edge_material.setDiffuseColor(EDGE_COLOR);
    }

    public static void setupUIElements(Stage stage, int[][][] resultBoxesArray){
        //TODO check if I can assume the IDs to be either 1, 2 or 3 if filled in or 0 if not
        int colorStart = 0;
        int colorEnd = 0;

        //give every filled in field a box representation and keep color in mind
        //create all the boxes
        for(int x=0; x<resultBoxesArray.length; x++){
            for(int y=0; y<resultBoxesArray[x].length; y++){
                for(int z=0; z<resultBoxesArray[x][y].length; z++){
                    int currentValue = resultBoxesArray[x][y][z];

                    //if this field is filled
                    if(currentValue!=0){
                        //update color range
                        if(currentValue==1){
                            colorStart = 0;
                            colorEnd = 70;
                        } else if (currentValue==2){
                            colorStart = 85;
                            colorEnd = 155;
                        } else {
                            colorStart = 170;
                            colorEnd = 255;
                        }

                        UIParcel cellBox = new UIParcel(x*cellSize, y*cellSize, z*cellSize, cellSize, cellSize, cellSize, colorStart, colorEnd);
                        parcels.add(cellBox);
                    }
                }
            }
        }

        //show them
        threeDGroup.getChildren().addAll(parcels);
    }

    public static void setupSlider(Stage stage){
        layerLabel = new Label("Choose the amount of layers to view:");
        layerSlider = new Slider(0, Wrapper.CONTAINER_HEIGHT/cellSize, valueSlider);
        layerSlider.setShowTickLabels(true);
        layerSlider.setShowTickMarks(true);
        layerSlider.setBlockIncrement(1);
        layerSlider.setMajorTickUnit(1);
        layerSlider.setMinorTickCount(0);
        layerSlider.setSnapToTicks(true);

        topGrid.add(layerLabel, 0, 10);
        topGrid.add(layerSlider, 0, 11);

        layerSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                // Fetch the value of the slider
                valueSlider = newValue.intValue();

                // Create a new 3-Dimensional array and copy the values from the original input
                int[][][] newInput = new int[Wrapper.CONTAINER_WIDTH/FX3D.cellSize][Wrapper.CONTAINER_HEIGHT/FX3D.cellSize][Wrapper.CONTAINER_DEPTH/FX3D.cellSize];

                for(int x = 0; x < newInput.length; x++) {
                    for(int y = 0; y < newInput[x].length; y++) {
                        for(int z = 0; z < newInput[x][y].length; z++) {
                            //TODO decouple from test
                            newInput[x][y][z] = test.input[x][y][z];
                        }
                    }
                }

                // Set all x and z values above the specified y value to 0
                if (valueSlider != newInput[0].length) {
                    for (int x = 0; x < newInput.length; x++) {
                        for (int y = 0; y < newInput[x].length - (int) valueSlider; y++) {
                            for (int z = 0; z < newInput[x][y].length; z++) {
                                newInput[x][y][z] = 0;
                            }
                        }
                    }
                }

                //TODO find better way to keep camera positioning and 2D UI
                updateUI(newInput);
            }
        });
    }

    public static void setupUIPostElements(Stage stage){
        //Create container (note: Has to be created after adding all the other objects in order to use transparency (I know, javaFX can be crappy))
        int containerPadding = 20;
        Box container = new Box(Wrapper.CONTAINER_WIDTH+containerPadding, Wrapper.CONTAINER_HEIGHT+containerPadding, Wrapper.CONTAINER_DEPTH+containerPadding);
        container.setTranslateX(Wrapper.CONTAINER_WIDTH/2);
        container.setTranslateY(Wrapper.CONTAINER_HEIGHT/2);
        container.setTranslateZ(Wrapper.CONTAINER_DEPTH/2);
        container.setMaterial(container_material);
        threeDGroup.getChildren().add(container);

        //Setup camera (so that you can have the container at the origin and can still see it well
        //The +threeDOffsetLeft comes from the compensation for the 2D subscene on the left
        camera.setTranslateX(-SCREEN_WIDTH/2+Wrapper.CONTAINER_WIDTH/2+threeDOffsetLeft);
        camera.setTranslateY(-SCREEN_HEIGHT/2+Wrapper.CONTAINER_HEIGHT/2);
        camera.setTranslateZ(-Wrapper.CONTAINER_DEPTH/0.3);

        //Setup mouse rotation
        initMouseControl(threeDGroup, mainScene, stage);

        //Set eventListener for mode selection
        modeSelection.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            //check what mode was selected and show the corresponding options
            if(newValue.equals("Parcels")){
                //remove other option
                if(oldValue.equals("Pentominoes")){
                    topGrid.getChildren().removeAll(LPentominoAmountLabel, PPentominoAmountLabel, TPentominoAmountLabel, LPentominoAmountTextField, PPentominoAmountTextField, TPentominoAmountTextField, LPentominoValueLabel, PPentominoValueLabel, TPentominoValueLabel, LPentominoValueTextField, PPentominoValueTextField, TPentominoValueTextField);
                }

                //add labels
                topGrid.add(ParcelAAmountLabel, 0, 2);
                topGrid.add(ParcelBAmountLabel, 0, 4);
                topGrid.add(ParcelCAmountLabel, 0, 6);

                topGrid.add(ParcelAValueLabel, 0, 3);
                topGrid.add(ParcelBValueLabel, 0, 5);
                topGrid.add(ParcelCValueLabel, 0, 7);

                //add text fields
                topGrid.add(ParcelAAmountTextField, 1, 2);
                topGrid.add(ParcelBAmountTextField, 1, 4);
                topGrid.add(ParcelCAmountTextField, 1, 6);

                topGrid.add(ParcelAValueTextField, 1, 3);
                topGrid.add(ParcelBValueTextField, 1, 5);
                topGrid.add(ParcelCValueTextField, 1, 7);

            } else if (newValue.equals("Pentominoes")){
                //remove other option
                if(oldValue.equals("Parcels")){
                    topGrid.getChildren().removeAll(ParcelAAmountLabel, ParcelBAmountLabel, ParcelCAmountLabel, ParcelAAmountTextField, ParcelBAmountTextField, ParcelCAmountTextField, ParcelAValueLabel, ParcelBValueLabel, ParcelCValueLabel, ParcelAValueTextField, ParcelBValueTextField, ParcelCValueTextField);
                }

                //add labels
                topGrid.add(LPentominoAmountLabel, 0, 2);
                topGrid.add(PPentominoAmountLabel, 0, 4);
                topGrid.add(TPentominoAmountLabel, 0, 6);

                topGrid.add(LPentominoValueLabel, 0, 3);
                topGrid.add(PPentominoValueLabel, 0, 5);
                topGrid.add(TPentominoValueLabel, 0, 7);

                //add text fields
                topGrid.add(LPentominoAmountTextField, 1, 2);
                topGrid.add(PPentominoAmountTextField, 1, 4);
                topGrid.add(TPentominoAmountTextField, 1, 6);

                topGrid.add(LPentominoValueTextField, 1, 3);
                topGrid.add(PPentominoValueTextField, 1, 5);
                topGrid.add(TPentominoValueTextField, 1, 7);
            }
        });

        //Set evenListener for start button
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event-> {
            //Check if the input isn't empty
            boolean textFieldsFilled = true;

            // Retrieve selected option (Parcels or Pentominoes) and pass along value to Wrapper
            Wrapper.inputType = modeSelection.getValue().toString();

            // Depending on the selected option, instantiate the corresponding inputDetail objects
            if (Wrapper.inputType.equals("Parcels")) {
                for(TextField field : parcelTextFields){
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
                for(TextField field : pentominoTextFields){
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

            if(textFieldsFilled){
                // Fill inputDetails[]
                Wrapper.inputDetails[0] = inputDetail1;
                Wrapper.inputDetails[1] = inputDetail2;
                Wrapper.inputDetails[2] = inputDetail3;

                //Show loading circle (that was created at the start)
                topGrid.add(pin, 0, 9);

                // Hide warning
                visibleWarning = false;

                //TODO start calculations
            } else {
                // Show warning
                visibleWarning = true;
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

    static class SmartGroup extends Group {
        Rotate rotateDelta;
        Transform rotation = new Rotate();
    }

    //Needed for mouse rotation
    private static void initMouseControl(SmartGroup contentGroup, Scene scene, Stage stage){
        Rotate xRotate;
        Rotate yRotate;
        //set the rotation origin to the center of the screen
        contentGroup.getTransforms().addAll(
            xRotate = new Rotate(0, SCREEN_WIDTH/2-threeDOffsetLeft, 50, 0, Rotate.X_AXIS),
            yRotate = new Rotate(0, SCREEN_WIDTH/2-threeDOffsetLeft, SCREEN_HEIGHT/2, 0, Rotate.Y_AXIS)
        );

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
