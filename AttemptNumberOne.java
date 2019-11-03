import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.Iterator;

import java.util.ArrayList;

public class AttemptNumberOne
{
  static FileInputStream fis;
  static HSSFWorkbook myFirst_Workbook;
  static HSSFSheet sheet1;
  static Iterator<Row> rowIt;
  static Row rowNames;
  static ArrayList<String> numbers;
  static int wantedCallersCol;
  static int rateCol;
  static int totalCol;
  static int billsecCol;
  static boolean whichSheet;// true for origination    
  static int GlobalCount;
  
  public AttemptNumberOne(String fileLocation)
   {
          File f = new File(fileLocation); // origination -- seperate toll free.      
      
     try
      {                            
          fis = new FileInputStream(f);                  
          myFirst_Workbook = new HSSFWorkbook(fis);
          sheet1 = myFirst_Workbook.getSheetAt(0);
          
          if (sheet1.getSheetName().contains("Origination"))
            whichSheet = true;
          
          rowIt = sheet1.iterator();
          rowNames = rowIt.next();  // this contains all the titles of the colunms.                    
          
          setColIndexes(rowNames);          
          initiation(wantedCallersCol);    
          
         //  System.out.println("The number of Phone Numbers tabulated is: " + numbers.size() + ".\n\n\n");
//           for (int i = 0; i < numbers.size(); i++)            
//               calculate(numbers.get(i));                          

           myFirst_Workbook.close();
           fis.close();        
       }    
      catch(Exception e) 
       {
         System.out.println(e + " ");
       }          
   }  
    
  public static void setColIndexes(Row rw)
   {
      String temp = "";
      String wantedCallers = "";
      
      if (whichSheet)
         wantedCallers = "to_did"; // a different colunm name/title
      else
         wantedCallers = "from_ani";
         
      for (int i = 0; i < rw.getLastCellNum(); i++)
       {
            temp = rw.getCell(i).toString();          
        if (temp.equals(wantedCallers))
           wantedCallersCol = i;
        else if (temp.equals("rate"))
           rateCol = i;
        else if ((temp.equals("total")) || ((temp.equals("total_charge")))) // check??? in origination it shows up as totalcharge????
           totalCol = i;         
        else if (temp.equals("billsec"))
           billsecCol = i;
       }  
   }
   
  public static void initiation(int numbersCol)
   {
      rowIt = sheet1.iterator();
      String currentNumber = "";                
      numbers = new ArrayList<String>();
      Row row = rowIt.next();    

           for (int i = 0; i < sheet1.getLastRowNum(); i++)
               {           
                  row = rowIt.next();                 
                  currentNumber = row.getCell(numbersCol).toString();
                  //GlobalCount++;
                  if ((!numbers.contains(currentNumber)) && (currentNumber.charAt(0) >= 48) && (currentNumber.charAt(0) <= 57))
                  {
                     numbers.add(currentNumber);
                  }                      
               }              
   }      
   
  public static void calculate(ArrayList<String> number)
   {
         double [] CountRateTotalBillsec = new double [4];
         double temp []  = null;
         for (int i = 0; i < number.size(); i++)
         {
            temp = calculate(number.get(i));
            for (int j = 0; j < temp.length; j++)
               CountRateTotalBillsec[j]+= temp[j];// math check: here the averages are being added!!!
         } 
 
         if (number.size() == 1)
         {
            System.out.println(number.get(0) + " made " + (int)CountRateTotalBillsec[0] + " call(s). Average Rate: " + CountRateTotalBillsec[1]/CountRateTotalBillsec[0] + ". Total Charge: " + CountRateTotalBillsec[2] + ". Number of minutes billed: " + CountRateTotalBillsec[3]/60 + ". Hours(" + (CountRateTotalBillsec[3]/60)/60 + ")");
         }
         else
         {
           System.out.println("For the numbers selected " + (int)CountRateTotalBillsec[0] + " calls were made. Thier combined Average Rate is: " + CountRateTotalBillsec[1]/CountRateTotalBillsec[0] + ". Thier combined Total charge is: " + CountRateTotalBillsec[2] + ". Thier combined number of minutes billed is: " + CountRateTotalBillsec[3]/60 + ". Hours(" + (CountRateTotalBillsec[3]/60)/60 + ")" );
         }            
   }
  
  public static double [] calculate(String number)
   {
      String currentNumber = "";
      rowIt = sheet1.iterator();
      Row row = rowIt.next();// passes the names row. the first row that names each colunm
      double [] CountRateTotalBillsec = new double[4];
      
      while (rowIt.hasNext())
         {
            row = rowIt.next();     
            currentNumber = row.getCell(wantedCallersCol).toString();
            if (number.equals(currentNumber))
                {
                   CountRateTotalBillsec[0]++;  
                   CountRateTotalBillsec[1] += Double.parseDouble(row.getCell(rateCol).toString());
                   CountRateTotalBillsec[2] += Double.parseDouble(row.getCell(totalCol).toString());
                   CountRateTotalBillsec[3] += Double.parseDouble(row.getCell(billsecCol).toString());
                }                       
        }       
                  
      return(CountRateTotalBillsec);      
   } 
   
  public static void main (String [] args )
   {
      Scanner kbd = new Scanner(System.in);
      AttemptNumberOne ANO = new AttemptNumberOne("JulyTermination.xls");
      ArrayList<String> temp = new ArrayList<String>();

      calculate(temp);      
   }
}