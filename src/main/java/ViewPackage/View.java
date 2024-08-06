package ViewPackage;

import SipInterface.PjSwigInterface.PanelCore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class View extends Application {
    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     */
    public static HelloController controller;

    @Override
    public void start(Stage stage) throws Exception {
        Application.setUserAgentStylesheet(Objects.requireNonNull(getClass().getClassLoader().getResource("themes/nord-dark.css")).toExternalForm());
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(View.class.getClassLoader().getResource("fxml/hello-view.fxml")));
        Scene scene = new Scene(fxmlLoader.load());
        if(getClass().getClassLoader().getResource("style.css") != null)
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("style.css")).toExternalForm());
        stage.setTitle("ISOVCP");
        stage.setScene(scene);
        stage.show();

        Thread.sleep(1000);
        PanelCore.getInstance().panelRegisterThisThread();
        controller = fxmlLoader.getController();
    }


    @Override
    public void stop(){
        controller.stop();
    }

    public static void main(String[] argv){
        launch();
    }
}
