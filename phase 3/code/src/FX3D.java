import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

/*
To get JavaFX working follow the following steps:
1. Install JavaFX
2. Go to project structure -> libraries -> add -> JavaFX install location -> select lib -> apply
3. Go to configuration (of the file (next to the run button)) -> VM options -> paste --module-path "C:\Program Files\Java\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml
 */

public class FX3D extends Application {
    final Color BACKGROUND_COLOR = Color.rgb(220, 220, 220);
    final int ROTATE_SPEED = 10;
    final int SCREEN_WIDTH = 1500;
    final int SCREEN_HEIGHT = 1000;

    private Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    private Rotate rotateZ = new Rotate(0, Rotate.Z_AXIS);

    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage stage){
        Camera camera = new PerspectiveCamera();
        //SmartGroup helps with rotations
        SmartGroup group = new SmartGroup();

        //set objects
        Box box = new Box(200, 200, 200);

        //Set initial coordinates
        box.setTranslateX(SCREEN_WIDTH/2);
        box.setTranslateY(SCREEN_HEIGHT/2);

        //Set color
        PhongMaterial blue = new PhongMaterial();
        blue.setDiffuseColor(Color.BLUE);
        box.setMaterial(blue);

        //Add items to group
        group.getChildren().add(box);

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
        stage.setTitle("3D");
        scene.setFill(BACKGROUND_COLOR);
        stage.setScene(scene);
        stage.show();
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
