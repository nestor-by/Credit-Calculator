package sample;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class HelpDialog {

    public HelpDialog(){
        Stage stage = new Stage();
        stage.setTitle("Справка");
        StackPane root = new StackPane();
        WebView view = new WebView();
        WebEngine engine = view.getEngine();
        engine.load(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("html/help.html")));
        root.getChildren().add(view);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
