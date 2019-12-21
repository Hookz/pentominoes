import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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

public class FX3D extends Application {
    final static Color BACKGROUND_COLOR = Color.rgb(220, 220, 220);
    final static Color CONTAINER_COLOR = Color.rgb(0, 0, 0, 0.2);
    final static Color EDGE_COLOR = Color.rgb(0, 0, 0, 0.5);
    final static int ROTATE_SPEED = 10;
    final static int SCREEN_WIDTH = 1500;
    final static int SCREEN_HEIGHT = 1000;
    final static int CONTAINER_WIDTH = 200;
    final static int CONTAINER_HEIGHT = 200;
    final static int CONTAINER_DEPTH = 200;

    static Group topGroup = new Group();
    static GridPane topGrid = new GridPane();
    static Group frameGroup = new Group();
    //SmartGroup helps with rotations
    static SmartGroup contentGroup = new SmartGroup();

    //TODO move to other class (problemWrapper?)
    static double score = 0;

    //TODO
    //static int amountOfTypeX = 3;

    //Setup camera
    static Camera camera = new PerspectiveCamera();

    //Create materials
    final static PhongMaterial edge_material = new PhongMaterial();
    final static PhongMaterial container_material = new PhongMaterial();

    final static double EDGE_WIDTH = 2;

    //create group of parcels
    static ArrayList<Parcel> parcels = new ArrayList<Parcel>();

    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage stage){
        /*START Setup top menu*/

        //Setup grid
        topGrid.setHgap(10);
        topGrid.setVgap(10);

        //Setup items
        //Add scoring label
        Label scoringLabel = new Label("Score: " + score);
        Button startButton = new Button("Start");

        ChoiceBox modeSelection = new ChoiceBox(FXCollections.observableArrayList(
                "Parcels", "Pentominoes"
        ));

        modeSelection.setValue("");

        //Parcel selection UI
        Label Parcel1Label = new Label("Amount of parcel1: ");
        Label Parcel2Label = new Label("Amount of parcel2: ");
        Label Parcel3Label = new Label("Amount of parcel3: ");
        TextField Parcel1TextField = new TextField();
        TextField Parcel2TextField = new TextField();
        TextField Parcel3TextField = new TextField();

        //Pentominoe selection UI
        Label LPentominoLabel = new Label("Amount of L pentominoes: ");
        Label PPentominoLabel = new Label("Amount of P pentominoes: ");
        Label TPentominoLabel = new Label("Amount of T pentominoes: ");
        TextField LPentominoTextField = new TextField();
        TextField PPentominoTextField = new TextField();
        TextField TPentominoTextField = new TextField();

        final ProgressIndicator[] pins = new ProgressIndicator[1];
        final ProgressIndicator pin = pins[0] = new ProgressIndicator();
        //-1 will make it display an animated disk, set to 1 to show that it's done
        pin.setProgress(-1);

        topGrid.add(scoringLabel, 0, 0);
        topGrid.add(modeSelection, 0, 1);
        topGrid.add(startButton, 0, 5);
        topGroup.getChildren().add(topGrid);


        //Put the 2D elements in the top left corner and in front of the 3D elements
        topGroup.setTranslateX(-SCREEN_WIDTH/3);
        topGroup.setTranslateY(-SCREEN_HEIGHT/3);
        topGroup.setTranslateZ(-1);

        frameGroup.getChildren().add(topGroup);
        /*END*/

        //Set materials
        container_material.setDiffuseColor(CONTAINER_COLOR);
        edge_material.setDiffuseColor(EDGE_COLOR);

        //Create parcels
        //TODO change to i<amountOfTYPEParcels, for instance i<amountOfAParcels
        for(int i=0; i<4; i++){
            Parcel parcel = new Parcel(50*i, 0, 0, 50, 80, 100);
            parcels.add(parcel);
        }

        //Add the created parcels
        //TODO change to i<totalAmountOfParcels
        for(int i=0; i<4; i++){
            contentGroup.getChildren().add(parcels.get(i));
        }

        //TODO create pentomino option
        Pentomino L = new Pentomino(0, 0, 0, 'L', 1);

        //Create container (note: Has to be created after adding all the other objects in order to use transparency (I know, javaFX can be crappy))
        Box container = new Box(CONTAINER_WIDTH, CONTAINER_HEIGHT, CONTAINER_DEPTH);
        container.setTranslateX(CONTAINER_WIDTH/2);
        container.setTranslateY(CONTAINER_HEIGHT/2);
        container.setTranslateZ(CONTAINER_DEPTH/2);
        container.setMaterial(container_material);
        contentGroup.getChildren().add(container);

        //Setup camera (so that you can have the container at the origin and can still see it well
        camera.setTranslateX(-SCREEN_WIDTH/2+CONTAINER_WIDTH/2);
        camera.setTranslateY(-SCREEN_HEIGHT/2+CONTAINER_HEIGHT/2);

        //Add the 3D objects to the group that gets displayed
        frameGroup.getChildren().add(contentGroup);

        //Setup scene
        Scene scene = new Scene(frameGroup, SCREEN_WIDTH, SCREEN_HEIGHT, true);

        //Set eventListener for mode selection
        modeSelection.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            //check what mode was selected and show the corresponding options
            if(modeSelection.getValue().toString().equals("Parcels")){
                //remove other option
                topGrid.getChildren().removeAll(LPentominoLabel, PPentominoLabel, TPentominoLabel, LPentominoTextField, PPentominoTextField, TPentominoTextField);

                //add labels
                topGrid.add(Parcel1Label, 0, 2);
                topGrid.add(Parcel2Label, 0, 3);
                topGrid.add(Parcel3Label, 0, 4);

                //add text fields
                topGrid.add(Parcel1TextField, 1, 2);
                topGrid.add(Parcel2TextField, 1, 3);
                topGrid.add(Parcel3TextField, 1, 4);
            } else if (modeSelection.getValue().toString().equals("Pentominoes")){
                //remove other option
                topGrid.getChildren().removeAll(Parcel1Label, Parcel2Label, Parcel3Label, Parcel1TextField, Parcel2TextField, Parcel3TextField);

                topGrid.add(LPentominoLabel, 0, 2);
                topGrid.add(PPentominoLabel, 0, 3);
                topGrid.add(TPentominoLabel, 0, 4);

                topGrid.add(LPentominoTextField, 1, 2);
                topGrid.add(PPentominoTextField, 1, 3);
                topGrid.add(TPentominoTextField, 1, 4);
            }
        });

        //Set evenListener for start button
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event-> {
            //Show loading circle (that was created at the start)
            topGrid.add(pin, 0, 2);

            //TODO use values from the textFields as input

            //TODO start calculations
            //TODO pin.setProgress(1); when it's done

        });

        //Set eventListeners for zoom and rotation
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event ->{
            switch (event.getCode()){
                //go foreword
                //TODO fix UP and DOWN not being detected since adding 2D components
                case UP:
                    contentGroup.translateZProperty().set(contentGroup.getTranslateZ()-50);
                    break;
                //go backwards
                case DOWN:
                    contentGroup.translateZProperty().set(contentGroup.getTranslateZ()+50);
                    break;
                case Q:
                    contentGroup.rotateByZ(ROTATE_SPEED);
                    break;
                case E:
                    contentGroup.rotateByZ(-ROTATE_SPEED);
                    break;
                case A:
                    contentGroup.rotateByY(ROTATE_SPEED);
                    break;
                case D:
                    contentGroup.rotateByY(-ROTATE_SPEED);
                    break;
                case W:
                    contentGroup.rotateByX(-ROTATE_SPEED);
                    break;
                case S:
                    contentGroup.rotateByX(ROTATE_SPEED);
                    break;
            }
        });

        scene.setCamera(camera);
        stage.setTitle("Filling 3D objects");
        scene.setFill(BACKGROUND_COLOR);
        stage.setScene(scene);
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

        contentGroup.getChildren().add(line);
    }

    static class SmartGroup extends Group {
        Rotate rotateDelta;
        Transform rotation = new Rotate();

        void rotateByX(int deg){
            rotateDelta = new Rotate(deg, 0, 0, 0, Rotate.X_AXIS);
            rotation = rotation.createConcatenation(rotateDelta);
            this.getTransforms().clear();
            this.getTransforms().addAll(rotation);
        }

        void rotateByY(int deg){
            rotateDelta = new Rotate(deg, 0, 0, 0, Rotate.Y_AXIS);
            rotation = rotation.createConcatenation(rotateDelta);
            this.getTransforms().clear();
            this.getTransforms().addAll(rotation);
        }

        void rotateByZ(int deg){
            rotateDelta = new Rotate(deg, 0, 0, 0, Rotate.Z_AXIS);
            rotation = rotation.createConcatenation(rotateDelta);
            this.getTransforms().clear();
            this.getTransforms().addAll(rotation);
        }
    }
}
