package example.hello;

/**
 * Created by guf on 5/13/14.
 */
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote{
    String sayHello() throws RemoteException;
    int getNodeNum() throws RemoteException;
    int QuestFunction() throws RemoteException;
    int BullyElection() throws RemoteException;
    void OrganizationMessage(int newLeader) throws RemoteException;

}
