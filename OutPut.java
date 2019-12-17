import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import javafx.scene.control.RadioButton;

import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;//<S,T>
import javafx.scene.control.cell.PropertyValueFactory;//<S,T>

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tab;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import java.text.DecimalFormat;


import javafx.stage.FileChooser;
import java.io.File;
import javafx.stage.FileChooser.ExtensionFilter;


/**All things GUI related */
public class OutPut extends Application{    

    Stage window = new Stage();
    Scene starterScene, choose, groupLists, FL;          
        
    AttemptNumberTwo ANT; 
    TextField Explocation; 
    DecimalFormat df = new DecimalFormat("0.000");// how many decimals to round to?
    FileChooser fc = new FileChooser();
    // open to a specific location fc.setInitialDirectory(new File("C:\\Users\\Martin\\Desktop\\Zevys' Stuff!\\Side_Project"));         
              
    @Override
    /** Displays the page where a file location is expected. Also an option to make a group*/
    public void start(Stage primaryStage)
    {   
       VBox holder = new VBox();       
       
       HBox top = new HBox();
       top.setSpacing(15);
       top.setAlignment(Pos.CENTER);
       top.setPrefHeight(100);
       
       TextField location = new TextField();
       location.setText("JulyTermination.csv");
       Label locationLb = new Label("Please type the location of your file: ", location);
       locationLb.setContentDisplay(ContentDisplay.RIGHT);
       
       VBox actions = new VBox();
       actions.setPadding(new Insets(20,0,0,0));
       actions.setSpacing(10);
         
       Button groupMaking = new Button("Create a group");
       groupMaking.setOnAction(e ->
         {
            makeGroup(location.getText());
         }
       );
       Button next = new Button("Next");  
       next.setOnAction(e ->
         {
             nextScene(location.getText());                       
         }
       );     
       next.setPrefWidth(95);
       next.setPrefHeight(25);
       
       Button fileChoose = new Button("Choose a File");
       fileChoose.setOnAction( e ->
         {           
            /* only allow files with the specified extension*///Good video https://www.youtube.com/watch?v=hNz8Xf4tMI4
             //fc.getExtensionFilters().add( new ExtensionFilter("xls Files", "*.xls"));               
             File selectedFile = fc.showOpenDialog(null);
             
             if (selectedFile != null)
              { 
               location.setText(selectedFile.getAbsolutePath());
              } 
         }
       );
       fileChoose.setPrefWidth(95);
       fileChoose.setPrefHeight(25);
       
       actions.getChildren().addAll(fileChoose, next, groupMaking);
       
       top.getChildren().addAll(locationLb, actions);  
       
       VBox warn = new VBox();

       warn.setPadding(new Insets(0,0,0,15));  
       Text warning1 = new Text("Warning: Please make sure that the Following conditions are met: ");
       Text warning2 = new Text("1. There should exist only one file by that name in the same folder.");
       Text warning3 = new Text("2. The file containing Origination info should either be named or have a sheet that has the name \"Origination\". ");
       Text warning4 = new Text("3. Any file should not contain any empty rows at the end.");
       Text warning5 = new Text("4. Forgot what the last warnign should be.");
       
       //warning1.setStyle("-fx-fill: #ffff00");
       
       warn.getChildren().addAll(warning1,warning2,warning3,warning4,warning5);  
       
       holder.getChildren().addAll(top, warn);
                           
       starterScene = new Scene(holder, 620, 200);
       //starterScene.getStylesheets().add("Vipes.css");
       
       window.setTitle("The best software ever!");
       window.setScene(starterScene);
       window.show();                                     
    }
   /** Makes the scenes for a user to choose to compute by either number or group. */ 
   public void nextScene(String tempLocation)
    {        
      // TODO change this to take in a string instead of makeing the location variable global
       ANT = new AttemptNumberTwo(tempLocation);
       ANT.initializeCallers();
       
       TabPane choices = new TabPane();
       Tab byNum = makeTabNum();
       Tab byGrp = makeTabGrp();       
       choices.getTabs().addAll(byNum, byGrp);
       byNum.setClosable(false);
       byGrp.setClosable(false);                    
       
       choose = new Scene(choices, 715,500);       
       window.setScene(choose);
       window.show();  
            
    }   
   /**This tab is used to compute by number*/
   public Tab makeTabNum()
    {
       Tab byNum = new Tab("Compute by Number");
       FlowPane everything = new FlowPane();
       everything.setPrefWidth(715);
       everything.setPrefHeight(500);    
       
        ScrollPane nums = new ScrollPane();
        nums.setPrefSize(155,430);
        nums.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                       
        ANT.numbersUsed();// excludes numbers in the groups
        VBox nums_box = new VBox();
        nums_box.setSpacing(5);
        nums.setContent(nums_box);
        String temp = "";
        CheckBox [] potential = new CheckBox[ANT.tempNumbers.size()];
        int j = 0;
        while(j < ANT.tempNumbers.size())
         {
             temp = Caller.makePretty(ANT.tempNumbers.get(j));
             potential[j] = new CheckBox(temp);          
             nums_box.getChildren().add(potential[j]);
             j++;          
         }
       
        VBox act_data = new VBox();
        act_data.setPrefWidth(560);
        act_data.setPrefHeight(470);
        
        HBox actions = new HBox();
        actions.setPrefWidth(560);
        actions.setPrefHeight(40);
        actions.setPadding(new Insets(7,0,0,25));
        actions.setSpacing(25);
         
       Button selectAll = new Button("Select All");
        selectAll.setOnAction(e ->
           {
             for (int i = 0; i < potential.length; i++)
               {                 
                   potential[i].setSelected(true);            
               }                
          }
        );
        
        Button deSelectAll = new Button("deSelectAll");
        deSelectAll.setOnAction(e ->
          {
             for (int i = 0; i < potential.length; i++)
               {                 
                   potential[i].setSelected(false);            
               }                
          }
        );    
       
       TableView<Caller> data = new TableView();       
       data.setPrefWidth(560);
       data.setPrefHeight(435);
       
       TableColumn<Caller, String> numberCol = new TableColumn<>(" Number ");
       numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
             
       TableColumn<Caller, Integer> countCol = new TableColumn<>(" Count ");
       countCol.setCellValueFactory(new PropertyValueFactory<>("count"));
              
       TableColumn<Caller, Double> rateCol = new TableColumn<>(" Rate ");
       rateCol.setCellValueFactory(new PropertyValueFactory<>("rate"));
       rateCol.setPrefWidth(75);
       
       TableColumn<Caller, Double> totalCol = new TableColumn<>(" Total ");
       totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));  
       totalCol.setPrefWidth(75);   
           
       TableColumn<Caller, Double> billSecCol = new TableColumn<>(" BillSec ");
       billSecCol.setCellValueFactory(new PropertyValueFactory<>("billSec"));  
       data.getColumns().addAll(numberCol, countCol, rateCol, totalCol, billSecCol); 
  
       Button compute = new Button("Apply");
       ArrayList<String> temporary = new ArrayList<String>();
       compute.setOnAction( e ->
         {
             temporary.clear();
             for (int i = 0; i < potential.length; i++)
              {                 
                  if (potential[i].isSelected())
                   {                     
                     temporary.add(ANT.tempNumbers.get(i));                              
                   }  
              }
              data.setItems(ANT.calculate(temporary)); 
         }         
       );
       
       Button sum = new Button("Sum");
       sum.setOnAction(e ->
         {
           letsSum(data);             
         }
       );
       
       Button exp = new Button("Export");
       exp.setOnAction(e ->
         {            
            whereToWrite(data);
         }
       );
       
       Button back = new Button("Back");
       back.setOnAction(e ->
         {
            window.setScene(starterScene);
         }
       );
       
       actions.getChildren().addAll(selectAll, deSelectAll, compute, sum, exp, back);                               
             
       act_data.getChildren().addAll(actions, data);       
       
       everything.getChildren().addAll(nums, act_data);

       byNum.setContent(everything);            
     
     return(byNum);
    } 
   /** Adds a row that contians the sum of all the other rows in the table */
   public void letsSum(TableView data)
    {
      // put this in a method in the ANT class and then call it from here. and also dont forget about rounding
            double [] sums = new double[4];
            for (int i = 0; i < data.getItems().size(); i++)
             {  
               String firstSelectedNum = data.getItems().get(i) + "";
               String [] firstSelectedNumArray = firstSelectedNum.split(" ");
             // we have to ignore the first entry in firstSelectedNum (or firstSelectedNumArray) because it contains the number which we dont need to find the sum of the selected numbers.    
               sums[0]+= Double.parseDouble(firstSelectedNumArray[1]); 
               sums[1]+= Double.parseDouble(firstSelectedNumArray[2]);
               sums[2]+= Double.parseDouble(firstSelectedNumArray[3]);  
               sums[3]+= Double.parseDouble(firstSelectedNumArray[4]);
             }  
             
             Caller totals = new Caller("GrandTotal", (int)sums[0], Double.parseDouble(df.format(sums[1]/data.getItems().size())), Double.parseDouble(df.format(sums[2])), Double.parseDouble(df.format(sums[3])));
             ANT.callerList.add(totals);    
    }   
   /**Gives the option to compute by group*/
   public Tab makeTabGrp()
    {
       Tab byGrp = new Tab("Compute by Group");       
       FlowPane everything = new FlowPane();
       everything.setPrefWidth(715);
       everything.setPrefHeight(500);  
       
        ScrollPane nums = new ScrollPane();
        nums.setPrefSize(155,430);
        nums.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
       
        VBox nums_box = new VBox();
        nums_box.setSpacing(5);
        nums.setContent(nums_box);
        String temp = "";
        
        //toggle group. allows only one selection
        ToggleGroup toggle = new ToggleGroup();    
        RadioButton [] potential = new RadioButton[ANT.groupNames.size()];
        for (int i = 0; i < potential.length; i++)
         {                                  
            potential[i] = new RadioButton(ANT.groupNames.get(i));
            potential[i].setToggleGroup(toggle);
            nums_box.getChildren().add(potential[i]);
         }
                  
        VBox act_data = new VBox();
        act_data.setPrefWidth(560);
        act_data.setPrefHeight(470);
        
        HBox actions = new HBox();
        actions.setPrefWidth(560);
        actions.setPrefHeight(40);
        actions.setPadding(new Insets(7,0,0,25));
        actions.setSpacing(25);         
       
       TableView<Caller> data = new TableView();       
       data.setPrefWidth(560);
       data.setPrefHeight(435);
       
       TableColumn<Caller, String> numberCol = new TableColumn<>(" Number ");
       numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
             
       TableColumn<Caller, Integer> countCol = new TableColumn<>(" Count ");
       countCol.setCellValueFactory(new PropertyValueFactory<>("count"));
              
       TableColumn<Caller, Double> rateCol = new TableColumn<>(" Rate ");
       rateCol.setCellValueFactory(new PropertyValueFactory<>("rate"));
       rateCol.setPrefWidth(75);
       
       TableColumn<Caller, Double> totalCol = new TableColumn<>(" Total ");
       totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));  
       totalCol.setPrefWidth(75);   
           
       TableColumn<Caller, Double> billSecCol = new TableColumn<>(" BillSec ");
       billSecCol.setCellValueFactory(new PropertyValueFactory<>("billSec"));  
       data.getColumns().addAll(numberCol, countCol, rateCol, totalCol, billSecCol); 
  
       Button compute = new Button("Apply");
       ArrayList<String> temporary = new ArrayList<String>();
       compute.setOnAction( e ->
         {
             temporary.clear();
             for (int i = 0; i < potential.length; i++)
              {                 
                  if (potential[i].isSelected())
                   {                                           
                     data.setItems(ANT.calculate(ANT.prepareGroup(potential[i].getText())));
                   }  
              }
         }         
       );
       
       Button sum = new Button("Sum");
       sum.setOnAction(e ->
         {
           letsSum(data);             
         }
       );
              
       Button exp = new Button("Export");
       exp.setOnAction(e ->
         {                    
            whereToWrite(data);
         }
       );
       
       Button back = new Button("Back");
       back.setOnAction(e ->
         {
            window.setScene(starterScene);
         }
       );

       actions.getChildren().addAll(compute, sum, exp, back);                               
             
       act_data.getChildren().addAll(actions, data);       
       
       everything.getChildren().addAll(nums, act_data);

       byGrp.setContent(everything);            
    
      return(byGrp);
    } 
   
   public void whereToWrite(TableView data)
    {
      HBox container = new HBox();
      container.setSpacing(10);
      container.setPrefWidth(600);
      container.setPrefHeight(150);
      container.setPadding(new Insets(10));
      container.setAlignment(Pos.CENTER);
      
      Button moveOn = new Button(" Continue ");
      
      TextField location = new TextField();
      Label flLB = new Label("Please enter the name of a file. Do not type the extension.", location);
      flLB.setContentDisplay(ContentDisplay.RIGHT);
      
      
      Image complete = new Image("complete.png");
      ImageView completeIV = new ImageView(complete);
      completeIV.setFitWidth(50);
      completeIV.setFitHeight(50);
      
      container.getChildren().addAll(flLB, moveOn);
      
      Scene temp = new Scene(container, 650, 100);
      
      moveOn.setOnAction( e->
         {
            boolean returnVal = WriteToCSV.write(data, location.getText());
            if (returnVal)
               container.getChildren().add(completeIV);           
         } 
      );
      
      Stage whatever = new Stage();
      whatever.setTitle("Collecting File Location"); 
      whatever.setScene(temp);
      whatever.show();
    } 
             
   /**Makes the scene for the user to make a group*/              
   public void makeGroup(String tempLocation)
    {               
         ANT = new AttemptNumberTwo(tempLocation);

         FlowPane container = new FlowPane();
         container.setPrefWidth(540);
         container.setPrefHeight(410);
         container.setPadding(new Insets(20,0,0,30));
         container.setHgap(25);
         
         ScrollPane nums = new ScrollPane();
         nums.setPrefSize(255,360);
         nums.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
         VBox nums_box = new VBox();
         nums_box.setSpacing(5);
         nums.setContent(nums_box);

         String temp = "";
         CheckBox [] potential = new CheckBox[ANT.numbers.size()];
         for (int i = 0; i < potential.length; i++)
          {         
             temp = Caller.makePretty(ANT.numbers.get(i));
             
              potential[i] = new CheckBox(temp);
              nums_box.getChildren().add(potential[i]);
          }            
         container.getChildren().add(nums); 
         
         FlowPane name = new FlowPane();
         name.setPrefSize(240,360);
         name.setHgap(10);
         name.setVgap(10);
         TextField nm = new TextField();
         
         Image poeple = new Image("groupPic.png");
         ImageView hands = new ImageView(poeple);
         hands.setFitWidth(325);
         hands.setFitHeight(350);
         
         Image check_mark = new Image("check_mark.png");
         ImageView check = new ImageView(check_mark);
         check.setFitWidth(50);
         check.setFitHeight(50);
         check.setVisible(false);
         
         ArrayList<String> selectedNumbers = new ArrayList<String>();
         Button update = new Button("Update");         
            update.setOnAction( e ->
               {
                  for (int i = 0; i < potential.length; i++)
                    {
                       if (potential[i].isSelected())
                            selectedNumbers.add(ANT.numbers.get(i));            
                    }             
                  ANT.pleaseSelect(selectedNumbers, nm.getText());
                  check.setVisible(true);
   
               }
            );
            
         Button reset = new Button("Add Another Group");
         
            reset.setOnAction( e ->
               {
                 selectedNumbers.clear();
                 for (int i = 0; i < potential.length; i++)
                    {
                       if (potential[i].isSelected())
                            potential[i].setSelected(false);            
                    }                  
                  check.setVisible(false);
                  nm.setText(null);
               }
            ); 
          
          Button back = new Button("Back");
          back.setOnAction(e ->
            {
               window.setScene(starterScene);
            }
          );          
         
         name.getChildren().addAll(new Label("Group Name: "), nm, new Label("Update Group Content"), update, check, reset, back, hands);
         
         container.getChildren().add(name);
         
         groupLists = new Scene(container,665, 505);

         window.setTitle("Group Making");       
         window.setScene(groupLists);
         window.show();         
    }       
              
   public static void main(String [] args)
    {
        Application.launch(args);
    } 
}