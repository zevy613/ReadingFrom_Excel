import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.ChoiceBox;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OutPut extends Application{    

    String location = "";
    @FXML TextField loke = new TextField("");    
    @FXML Button next = new Button();
    @FXML ChoiceBox<String> listOfNumbers = new ChoiceBox<String>();
    AttemptNumberOne ANO;
           
    //trying somthing
    //ObservableList<String> somthing = FXCollections.observableArrayList("11111111","2222222","3333333");
    
    @Override
    public void start(Stage primaryStage)
    {    
      try
      {
       Parent root = FXMLLoader.load(getClass().getResource("fileLocation.fxml"));  
       Scene starterScene = new Scene(root);
       
       primaryStage.setTitle("The millionth try");
       primaryStage.setScene(starterScene);
       primaryStage.show();              
       
      }
      catch(Exception e)
      {
         System.out.println("error" + e);
      }           
    }   
    
   public void nextScene(ActionEvent event)
    {
       location = loke.getText();

       //ANO = new AttemptNumberOne(location);
       //initialize(ANO.numbers);
       try
       {
         Parent root1 = FXMLLoader.load(getClass().getResource("desicsion.fxml"));
         Scene choose = new Scene(root1);
         Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();       
         window.setScene(choose);
         window.show();
                  
          // ObservableList<String> somthing = FXCollections.observableArrayList();
//           somthing.addAll("11111","22222","55555");
//           listOfNumbers.getItems().addAll(somthing);
         //listOfNumbers.setItems(somthing);
        //  listOfNumbers.setRotate(-90);
//           
//             System.out.println(listOfNumbers.getItems());
//          listOfNumbers.show();
            //listOfNumbers.getItems().addAll("11111","22222","55555");
                              

       }
       catch(Exception e)
       {
            System.out.println(e);
       }             
    }
      
   public static void main(String[]args)
    {
        Application.launch(args);
    } 
}