package example.hello;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by guf on 5/18/14.
 */
public interface ILeader extends Remote{
    String GloriousLeaderFunction(String data) throws RemoteException;
}
