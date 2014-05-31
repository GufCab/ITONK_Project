package example.hello;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by guf on 5/18/14.
 */
public class LeaderClass implements ILeader{
    private int _nodeID;
    private Registry _registry;
    private ILeader _leaderStub;


    public LeaderClass(int nodeID) {
        _nodeID = nodeID;
        boolean isGloriousLeaderBound = false;

        try {
            _leaderStub = (ILeader) UnicastRemoteObject.exportObject(this, 0);
            _registry = LocateRegistry.getRegistry(GlobalHost.HostName);

            String[] boundNames = _registry.list();

            for(String name : boundNames)
            {
                System.out.println("BoundName: " + name);
                if(name.equals("GloriousLeader"))
                {
                    //System.out.println("BoundName: " + name);
                    _registry.unbind("GloriousLeader");
                }
            }

            _registry.bind("GloriousLeader", _leaderStub);
            SendOrganizationMessages();
        } catch (Exception e) {
            System.err.println("Error in Leader..");
            //e.printStackTrace();
        }
    }

    public String GloriousLeaderFunction(String data) {
        return "Leader on node: " + _nodeID + " With data: " + data;
    }

    private void SendOrganizationMessages()
    {
        for(int i = 0; i <= 10; i++)
        {
            try {
                Registry _registry = LocateRegistry.getRegistry(GlobalHost.HostName);
                Hello stub = (Hello)_registry.lookup("QuestNode" + i);
                stub.OrganizationMessage(_nodeID);

            } catch(Exception e)
            {
                //System.out.println("Organization Exception");
            }

        }
    }

}
