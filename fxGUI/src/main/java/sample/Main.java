package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private double initX = 0,
                   initY = 0;
    @Override
    public void start(Stage stage) throws Exception{
        stage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("fxml/sample.fxml"));
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight(),Color.TRANSPARENT);
        stage.setScene(scene);
        //scene.getStylesheets().add("css/style.css");
        setUp(stage, scene);
        scene.setFill(Color.TRANSPARENT);
        stage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }

    public void setUp(final Stage stage, Scene scene){
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                initX = me.getScreenX() - stage.getX();// - me.getSceneX();
                initY = me.getScreenY() - stage.getY();
            }
        });

        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent me) {
                stage.setX(me.getScreenX() - initX);
                stage.setY(me.getScreenY() - initY);
            }
        });
    }
}
