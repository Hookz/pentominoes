import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
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

//TODO don't use pentominoes, use groups of blocks instead
//Use the result of the algorithm with as format int[][][] (size = 2*actual size) that has the colorIDs for the object than display it as groups of blocks.
//add a slider to select the (highest) layer that you want to see

public class FX3D extends Application {
    final static Color BACKGROUND_COLOR = Color.rgb(220, 220, 220);
    final static Color CONTAINER_COLOR = Color.rgb(0, 0, 0, 0.2);
    final static Color EDGE_COLOR = Color.rgb(0, 0, 0, 0.5);
    final static int ROTATE_SPEED = 10;
    final static int SCREEN_WIDTH = 1920;
    final static int SCREEN_HEIGHT = 1000;

    static Stage mainStage;
    static GridPane topGrid = new GridPane();
    static Group twoDGroup = new Group();
    //SmartGroup helps with rotations
    static SmartGroup threeDGroup = new SmartGroup();

    static HBox root = new HBox();
    static Scene mainScene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, true);
    static SubScene twoD = new SubScene(twoDGroup, SCREEN_WIDTH*.2, SCREEN_HEIGHT);
    static SubScene threeD = new SubScene(threeDGroup, SCREEN_WIDTH*.8, SCREEN_HEIGHT);

    //some variables for mouse rotation
    private static double anchorX, anchorY;
    private static double anchorAngleX = 0;
    private static double anchorAngleY = 0;
    private static final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private static final DoubleProperty angleY = new SimpleDoubleProperty(0);

    //Setup camera
    static Camera camera = new PerspectiveCamera();

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
    final static ProgressIndicator[] pins = new ProgressIndicator[1];
    final static ProgressIndicator pin = pins[0] = new ProgressIndicator();

    //Parcel selection UI
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
    /*END*/

    //create group of parcels
    static ArrayList<UIParcel> parcels = new ArrayList<UIParcel>();

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

        setupUIPostElements(mainStage);
    }

    public static void setupUIPreElements(Stage stage){
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

        //-1 will make it display an animated disk, set to 1 to show that it's done
        //pin is the progress indicator
        pin.setProgress(-1);

        topGrid.add(scoringLabel, 0, 0);
        topGrid.add(modeSelection, 0, 1);
        topGrid.add(startButton, 0, 8);
        twoDGroup.getChildren().add(topGrid);
        /*END*/

        //Set materials
        container_material.setDiffuseColor(CONTAINER_COLOR);
        edge_material.setDiffuseColor(EDGE_COLOR);
    }

    public static void setupUIElements(Stage stage, int[][][] resultBoxesArray){
        //TODO formalize color range
        //give every filled in field a box representation and keep color in mind
        int colorStart = 0;
        int colorEnd = 40;

        for(int x=0; x<resultBoxesArray.length; x++){
            for(int y=0; y<resultBoxesArray[x].length; y++){
                for(int z=0; z<resultBoxesArray[x][y].length; z++){
                    int currentValue = resultBoxesArray[x][y][z];

                    //if this field is filled
                    if(currentValue!=0){
                        //update color range
                        if(colorEnd<215){
                            colorStart+=40;
                            
                        }

                        //50 is used because that is the size that is given for each cell in the array
                        Box cellBox = new UIParcel(x*50, y*50, z*50, 50, 50, 50, );
                    }
                }
            }
        }
    }

    public static void setupUIPostElements(Stage stage){
        //Create container (note: Has to be created after adding all the other objects in order to use transparency (I know, javaFX can be crappy))
        Box container = new Box(Wrapper.CONTAINER_WIDTH, Wrapper.CONTAINER_HEIGHT, Wrapper.CONTAINER_DEPTH);
        container.setTranslateX(Wrapper.CONTAINER_WIDTH/2);
        container.setTranslateY(Wrapper.CONTAINER_HEIGHT/2);
        container.setTranslateZ(Wrapper.CONTAINER_DEPTH/2);
        container.setMaterial(container_material);
        threeDGroup.getChildren().add(container);

        //Setup camera (so that you can have the container at the origin and can still see it well
        //The +200 comes from the compensation for the 2D subscene on the left
        camera.setTranslateX(-SCREEN_WIDTH/2+Wrapper.CONTAINER_WIDTH/2+200);
        camera.setTranslateY(-SCREEN_HEIGHT/2+Wrapper.CONTAINER_HEIGHT/2);
        camera.setTranslateZ(-Wrapper.CONTAINER_DEPTH/0.5);

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
            //Show loading circle (that was created at the start)
            topGrid.add(pin, 0, 9);

            //TODO use values from the textFields as input

            //TODO start calculations
            //TODO pin.setProgress(1); when it's done

        });

        threeD.setCamera(camera);
        stage.setTitle("Filling 3D objects");
        threeD.setFill(BACKGROUND_COLOR);
        stage.setScene(mainScene);
        stage.show();
    }

    public static void createBoxLines(double contW, double contH, double contD, double x, double y, double z) {
        //You call this method to create a box with a size and location you put in
        //This method calls the createLine method for all the sides of your rectangle
        Point3D p1 = new Point3D(x, y, z);
        Point3D p2 = new Point3D(contW + x, y, z);
        Point3D p3 = new Point3D(x, contH + y, z);
        Point3D p4 = new Point3D(contW + x, contH + y, z);
        createLine(p1, p2);
        createLine(p1, p3);
        createLine(p3, p4);
        createLine(p2, p4);

        Point3D p5 = new Point3D(x, y, contD + z);
        Point3D p6 = new Point3D(contW + x, y, contD + z);
        Point3D p7 = new Point3D(x, contH + y, contD + z);
        Point3D p8 = new Point3D(contW + x, contH + y, contD + z);
        createLine(p5, p6);
        createLine(p5, p7);
        createLine(p7, p8);
        createLine(p6, p8);

        createLine(p1, p5);
        createLine(p2, p6);
        createLine(p3, p7);
        createLine(p4, p8);
    }

    public static void createLine(Point3D origin, Point3D target) {
        //creates a line from one point3d to another
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Cylinder line = new Cylinder(EDGE_WIDTH, height);

        line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

        line.setMaterial(edge_material);

        threeDGroup.getChildren().add(line);
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
            xRotate = new Rotate(0, SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 0, Rotate.X_AXIS),
            yRotate = new Rotate(0, SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 0, Rotate.Y_AXIS)
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
