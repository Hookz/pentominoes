import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

/*
To get JavaFX working follow the following steps:
1. Install JavaFX
2. Go to project structure -> libraries -> add -> JavaFX install location -> select lib -> apply
3. Go to configuration (of the file (next to the run button)) -> VM options -> paste --module-path "C:\Program Files\Java\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml
 */

public class FX3D extends Application {
    final Color BACKGROUND_COLOR = Color.rgb(220, 220, 220);
    final Color CONTAINER_COLOR = Color.rgb(0, 0, 0, 0.2);
    final Color ITEM_COLOR = Color.rgb(20, 20, 100, 0.8);
    final Color EDGE_COLOR = Color.rgb(0, 0, 0, 0.5);
    final int ROTATE_SPEED = 10;
    final int SCREEN_WIDTH = 1500;
    final int SCREEN_HEIGHT = 1000;

    final double EDGE_WIDTH = 2;

    private Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    private Rotate rotateZ = new Rotate(0, Rotate.Z_AXIS);

    //SmartGroup helps with rotations
    SmartGroup group = new SmartGroup();
    Camera camera = new PerspectiveCamera();

    //Create materials
    final PhongMaterial edge_material = new PhongMaterial();
    final PhongMaterial container_material = new PhongMaterial();
    final PhongMaterial item_material = new PhongMaterial();

    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage stage){
        //Set materials
        //TODO make item color random when adding the item, and add borders
        container_material.setDiffuseColor(CONTAINER_COLOR);
        item_material.setDiffuseColor(ITEM_COLOR);
        edge_material.setDiffuseColor(EDGE_COLOR);

        //set objects
        //TODO only create 'content_box' in a separate method
        Box container = new Box(200, 200, 200);
        Box box2 = new Box(100, 100, 100);

        //Set initial coordinates
        //TODO only create 'content_box' in a separate method
        container.setTranslateX(SCREEN_WIDTH/2);
        container.setTranslateY(SCREEN_HEIGHT/2);
        box2.setTranslateX(SCREEN_WIDTH/2);
        box2.setTranslateY(SCREEN_HEIGHT/2);

        //TODO only create 'content_box' in a separate method
        createBoxLines(100, 100, 100, 700, 450, -50);

        //Set color
        container.setMaterial(container_material);
        //TODO only create 'content_box' in a separate method
        box2.setMaterial(item_material);

        //Add items to group
        group.getChildren().add(container);
        group.getChildren().add(box2);

        //Setup scene
        Scene scene = new Scene(group, SCREEN_WIDTH, SCREEN_HEIGHT);

        //Set eventListeners for zoom and rotation
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event ->{
            switch (event.getCode()){
                //go foreword
                case UP:
                    group.translateZProperty().set(group.getTranslateZ()-50);
                    break;
                //go backwards
                case DOWN:
                    group.translateZProperty().set(group.getTranslateZ()+50);
                    break;
                case Q:
                    group.rotateByZ(ROTATE_SPEED);
                    break;
                case E:
                    group.rotateByZ(-ROTATE_SPEED);
                    break;
                case A:
                    group.rotateByY(ROTATE_SPEED);
                    break;
                case D:
                    group.rotateByY(-ROTATE_SPEED);
                    break;
                case W:
                    group.rotateByX(-ROTATE_SPEED);
                    break;
                case S:
                    group.rotateByX(ROTATE_SPEED);
                    break;
            }
        });

        scene.setCamera(camera);
        stage.setTitle("Filling 3D objects");
        scene.setFill(BACKGROUND_COLOR);
        stage.setScene(scene);
        stage.show();
    }

    //TODO implement this method directly into the constructor for 'content_box'
    public void createBoxLines(double contW, double contH, double contD, double x, double y, double z) {
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

    public void createLine(Point3D origin, Point3D target) {
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

        group.getChildren().add(line);
    }

    class SmartGroup extends Group {
        Rotate rotateDelta;
        Transform rotation = new Rotate();

        void rotateByX(int deg){
            rotateDelta = new Rotate(deg, SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 0, Rotate.X_AXIS);
            rotation = rotation.createConcatenation(rotateDelta);
            this.getTransforms().clear();
            this.getTransforms().addAll(rotation);
        }

        void rotateByY(int deg){
            rotateDelta = new Rotate(deg, SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 0, Rotate.Y_AXIS);
            rotation = rotation.createConcatenation(rotateDelta);
            this.getTransforms().clear();
            this.getTransforms().addAll(rotation);
        }

        void rotateByZ(int deg){
            rotateDelta = new Rotate(deg, SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 0, Rotate.Z_AXIS);
            rotation = rotation.createConcatenation(rotateDelta);
            this.getTransforms().clear();
            this.getTransforms().addAll(rotation);
        }
    }
}
