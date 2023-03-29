import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.util.StringTokenizer;
import java.math.BigInteger;

public class BumperClient {
   public static void main(String args[]) throws Exception {
      Bumper b  = (Bumper) Naming.lookup("//localhost/CoolBumper");
      
      long start = System.currentTimeMillis();

      try {
         BigInteger ctr = new BigInteger("0");
         BigInteger n = new BigInteger("10000");

         while(ctr.compareTo(n) != 0){
            b.bump();
            ctr = ctr.add(BigInteger.ONE);
         }

         long stop = System.currentTimeMillis();

         System.out.println(b.get().toString());

         System.out.println(String.valueOf((stop-start)/1000));

      }

      catch(RemoteException e) {
         System.out.println("allComments: " + e.getMessage());
      }

      
   }
}