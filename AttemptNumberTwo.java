import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.BufferedWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.Scanner;
import java.text.DecimalFormat;

/**Everything related to computation*/
public class AttemptNumberTwo
{

  private static DecimalFormat df = new DecimalFormat("0.000");// how many decimals to round to?
  String title;    
  
  static FileReader fr;
  static BufferedReader br;
  static String fileLocation;
  static int wantedCallersCol;
  static int rateCol;
  static int totalCol;
  static int billsecCol;
  static boolean whichSheet;// true for origination    
  static File groupFile = new File("groups.txt");

  //things used in multiple classes----
  static ArrayList<String> numbers;
  static ArrayList<String> tempNumbers;
  static ArrayList<String> groupNames = new ArrayList<String>();
  static Caller [] callers;
  static ObservableList<Caller> callerList;
  /**finds the needed columns, makes two arrays of the unique numbers in the excel sheet.*/
  public AttemptNumberTwo(String xfileLocation)
   {     
     try
      {     
          fileLocation = xfileLocation;  
          fr = new FileReader(fileLocation);
          br = new BufferedReader(fr);
               
          if (fileLocation.toLowerCase().contains("orig"))
            whichSheet = true;
          
          title = br.readLine();  // this contains all the titles of the colunms.    
          setColIndexes(title);                    
          initiation();  
                     
          tempNumbers = (ArrayList<String>) numbers.clone();                  
       }    
      catch(Exception e) 
       {
         System.out.println(e + " ");
       }          
   }
  /**Finds the columns that are important for computation. It Also distinguishes between the Origination and Termination tables.*/  
  public static void setColIndexes(String title)
   {
      String wantedCallers = "";
      String [] headers = title.split(",");
      
      if (whichSheet)
         wantedCallers = "to_did"; // a different colunm name/title
      else
         wantedCallers = "from_ani";             
         
      for (int i = 0; i < headers.length; i++)
       {
        if (headers[i].equals(wantedCallers))
           wantedCallersCol = i;
        else if (headers[i].equals("rate"))
           rateCol = i;
        else if ((headers[i].equals("total")) || ((headers[i].equals("total_charge")))) // check??? in origination it shows up as totalcharge????
           totalCol = i;         
        else if (headers[i].equals("billsec"))
           billsecCol = i;
       }    
   } 
 //  /**Finds all unique numbers in the excel sheet*/ 
  public static void initiation()
   {      
     numbers = new ArrayList<String>();
     String line = "";
    try
     { 
        while((line = br.readLine())!= null)
          {    
             String [] row = line.split(",");          
             String currentNumber = row[wantedCallersCol];
             if ((!numbers.contains(currentNumber)) && (currentNumber.charAt(0) >= 48) && (currentNumber.charAt(0) <= 57))
             {
                numbers.add(currentNumber);
             }                      
          }
     }
    catch(Exception e)
     {
         System.out.println(e);
     } 
      
   }       
   /**computes the rates count ... for each number*/
  public static void initializeCallers()
   {       
       callers = new Caller [numbers.size()];
       for (int i = 0; i < callers.length; i++)
        {
            callers[i] = calculate(numbers.get(i));
        }           
        getGroups();// this initializes the list of names of the groups      
            
   }
  /**Makes the calculations to find the count, rate, total and billSec*/
  public static Caller calculate(String number)
   {

     double [] CountRateTotalBillsec = new double[4];
     try
      { 
         fr = new FileReader(fileLocation);
         br = new BufferedReader(fr);
      
         String currentNumber = "";     
         String line = "";
         
         while ((line = br.readLine()) != null)
            {
               String xline [] = line.split(",");
               currentNumber = xline[wantedCallersCol];
               if (number.equals(currentNumber))
                   {
                      CountRateTotalBillsec[0]++;  
                      CountRateTotalBillsec[1] += Double.parseDouble(xline[rateCol]);
                      CountRateTotalBillsec[2] += Double.parseDouble(xline[totalCol]);
                      CountRateTotalBillsec[3] += Double.parseDouble(xline[billsecCol]);
                   }                       
           } 
       }
      catch(Exception e)
       {
         System.out.println(e);
       }           
      //Math Check!!!  
      CountRateTotalBillsec[1]= Double.parseDouble(df.format(CountRateTotalBillsec[1]/((int)CountRateTotalBillsec[0])));
      CountRateTotalBillsec[2] = Double.parseDouble(df.format(CountRateTotalBillsec[2]));
      CountRateTotalBillsec[3] = Double.parseDouble(df.format(CountRateTotalBillsec[3]/60));
      Caller call = new Caller(number, (int)CountRateTotalBillsec[0], CountRateTotalBillsec[1], CountRateTotalBillsec[2], CountRateTotalBillsec[3]);// this object contains the number without the E10                  
      return(call);            
   }  
   /**Takes an ArrayList of numbers (in String form) and adds all of them to an ObservableList to be added to the table in the GUI.*/
  public static ObservableList<Caller> calculate(ArrayList<String> number)
   {      
      callerList = FXCollections.observableArrayList();
      //callerList.clear();    
       
      for (int i = 0; i < number.size(); i++)
      {
       for (int j = 0; j < callers.length; j++)
        {
          if (number.get(i).equals(callers[j].getNumberUgly()))
           {
              callerList.add(callers[j]);         
              break;
           }   
        }     
      }      
      return(callerList);            
   }
  public static void getGroups()
   {
      try
       {
         Scanner reader = new Scanner(groupFile);
      
         while (reader.hasNext())
          {
            String temp = reader.nextLine();
           if (temp.charAt(temp.length()-1) == ':')
            {
             groupNames.add(temp.substring(0, temp.length()-1));
            } 
          }      
        }
       catch(Exception e)
        {
         System.out.println(e);
        }          
   }
   

  /**Writes the name for the group and all of its numbers*/
  public void pleaseSelect(ArrayList<String> groupOfNumbers, String grpName)
   {
      try
       {
          FileWriter fw = new FileWriter(groupFile, true);
          BufferedWriter kbdOut = new BufferedWriter(fw);
          
          kbdOut.write(grpName + ":");
          kbdOut.newLine();
                      
           for (int i = 0; i < groupOfNumbers.size(); i++)
            {
                 kbdOut.write(groupOfNumbers.get(i));                 
                 kbdOut.newLine();                 
                 kbdOut.flush();       
            }
      }     
     catch(Exception e)
      {
         System.out.println(e);
      }           
   }   
  
   /**Returns an ArrayList with all numbers associated with the group. The name of the group is passed in.*/
  public static ArrayList<String> prepareGroup(String client)
   {
      String temp = "";
      client+=":";
      ArrayList<String> groupList = new ArrayList<String>();
      try
       {
         Scanner reader = new Scanner(groupFile);  
         while(reader.hasNextLine())
          {
            if (reader.nextLine().equals(client))
             {
               while(reader.hasNext ())
                {   
                  temp = reader.next();               
                  if (temp.charAt(0) == '1')
                     groupList.add(temp);
                  else                             
                     break;
                }
             }
          }      
       } 
      catch(Exception e)
       {
          System.out.println(e);
       }
      
      return(groupList);      
  } 
  /**Makes an ArrayList of all the names that are a group.*/
  public static void numbersUsed()
   {
      String temp = "";
      try
       {
        Scanner reader = new Scanner(groupFile);     
        while(reader.hasNext())
         {
           temp = reader.nextLine();
           if (temp.charAt(0) >= 48 || temp.charAt(0) <= 57)
              tempNumbers.remove(temp);
         }         
       }
      catch(Exception e)
       {
         System.out.println(e);       
       }  
   }
  public static void main (String [] args)
   {
        AttemptNumberTwo ANT = new AttemptNumberTwo("C:\\Users\\Martin\\Desktop\\Zevys' Stuff!\\July Origination.csv"); 
   
   } 
}   