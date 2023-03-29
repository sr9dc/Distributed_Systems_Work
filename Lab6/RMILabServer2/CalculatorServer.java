//**************************************************************
// CalculatorServer.java             Serve remote Calculator
// Creates a calculator object and gives
// it a name in the registry.
import java.rmi.*;
public class CalculatorServer {
	public static void main(String args[]){
          System.out.println("Calculator Server Running");
          try{
            // create the servant
            Calculator c = new CalculatorServant();
            System.out.println("Created Calculator object");
            System.out.println("Placing in registry");
            // publish to registry
	          Naming.rebind("CoolCalculator", c);
            System.out.println("CalculatorServant object ready");
           }catch(Exception e) {
            System.out.println("CalculatorServer error main " + e.getMessage());
        }
    }
}
