import java.io.File;
import java.util.Scanner;
import java.io.FileOutputStream;
import javafx.scene.control.TableView;
import java.io.PrintWriter;

public class WriteToCSV
{
   static FileOutputStream fos;
   static File temp;
    
   public static boolean write(TableView callerList, String almost)
    {
    try
       {
            Scanner kbd = new Scanner(System.in);
            String location = almost + ".csv";   
           
            FileOutputStream fos = new FileOutputStream(location, true);                            
            PrintWriter pw = new PrintWriter(fos);
            File f = new File(location);
            
            // this if block only executes one time. if the user tries to export to the same file in one run pw.println("Number, Count, Rate, Total, Billsec"); doesnt work.   
            if (f.length() != 0)
               {
                  pw.println();
                  pw.println(", ,New Collection");
                  pw.println("Number, Count, Rate, Total, Billsec");   
                  pw.flush();                               
               }
            else
               {
                  pw.println(", ,New Collection");                
                  pw.println("Number, Count, Rate, Total, Billsec");   
                  pw.flush();
               }   
               
            String temp = "";
            for (int i = 0; i < callerList.getItems().size(); i++)
             {                
               temp = callerList.getItems().get(i) + "";
               temp = temp.replace(' ',',');
               temp = temp.replace(".", "");

               pw.println(temp);
               pw.flush(); 
             }  
             
          fos.close();
          pw.close();                              
        }
       catch(Exception e)
        {
           System.out.println(e);
        }   
       
       return(true);
    }
 }