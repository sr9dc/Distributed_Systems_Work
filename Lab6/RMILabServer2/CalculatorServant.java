//*************************************************************
// CalculatorServant.java
// A Remote object class that implements Calculator.
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
public class CalculatorServant extends UnicastRemoteObject implements Calculator {
       public CalculatorServant() throws RemoteException {
       }
       public int add(int x, int y) throws RemoteException {
            System.out.println("Got request to add " + x + " and " + y);

            return x + y;
    }
}
