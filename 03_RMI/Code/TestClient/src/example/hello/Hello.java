package example.hello;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by guf on 5/19/14.
 */
public interface Hello extends Remote {
    String sayHello() throws RemoteException;
}
