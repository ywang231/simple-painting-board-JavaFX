import javafx.application.Application;
import javafx.stage.Stage;
 
public class FXMain extends Application {
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
  
    	Stage stage = new Stage();
    	
    	System.out.println("Load scene from fxml file");
		stage.setScene(Controller.loadScene(getClass(), "scene.fxml"));
		System.out.println("Finish Loading scene from fxml file");
		
		//Set the minimum size of the main window.
		stage.setMinWidth(480);
		stage.setMinHeight(320);
		
		//Set the title for the Windows.
		stage.setTitle("Painter");
		
		//Show the scene
		stage.show();
		
    }
}