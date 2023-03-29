//*************************************************************
// CalculatorServant.java
// A Remote object class that implements Calculator.
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.math.BigInteger;

public class BumperServant extends UnicastRemoteObject implements Bumper {
    public static BigInteger input = new BigInteger("0");

    public BumperServant() throws RemoteException {
    }

    public boolean bump() throws RemoteException {
        input = input.add(BigInteger.ONE);
        return true;
    }
    public BigInteger get() throws RemoteException{
        return input;
    }

}
