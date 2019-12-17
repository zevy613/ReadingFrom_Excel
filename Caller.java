public class Caller
{
   private String number;
   private int count;
   private double rate;
   private double total;
   private double billSec;
   
   
   public Caller(String nm, int cnt, double rate, double total, double billSec)
    {
      this.number = nm;
      this.count = cnt;
      this.rate = rate;
      this.total = total;
      this.billSec = billSec;        
    }
   
   public String getNumber()
   {
      return(makePretty(this.number));
   }

   public String getNumberUgly()
   {
      return(this.number);
   }          
   public int getCount()
   {
      return(this.count);
   }    
   public double getRate()
   {
      return(this.rate);
   }    
   public double getTotal()
   {
      return(this.total);
   }    
   public double getBillSec()
   {
      return(this.billSec);
   }  
   
   public static String makePretty(String x)
    {
      if (x.length() > 12) // do this in ANO class
        {
            x = x.substring(0, x.indexOf('E'));
            while (x.length() < 12)
               x+= "0";                  
        }      
      return(x);    
    } 
   
   @Override
   public String toString() //for testing
    {
     return(this.getNumber() + " " + this.getCount() + " " + this.getRate() + " " + this.getTotal() + " " + this.getBillSec());    
    }
}