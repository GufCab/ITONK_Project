package example.hello;

/**
 * Created by guf on 5/13/14.
 */
import java.lang.reflect.Array;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Hello extends Remote{
    String sayHello() throws RemoteException;
    int getNodeNum() throws RemoteException;
    int QuestFunction() throws RemoteException;
    void RingElectionFunction(int[] nodeIds) throws RemoteException;
    void RingElectionSetNewLeader(int leaderId) throws RemoteException;
    int BullyElection() throws RemoteException;
    void OrganizationMessage(int newLeader) throws RemoteException;
}
