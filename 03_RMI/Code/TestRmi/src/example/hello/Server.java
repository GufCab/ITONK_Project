package example.hello;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
public class Server implements Hello {
    private String _id;
    private int _nodeNum;
    //private Server _server;
    private Hello _helloStub;
    private Registry _registry;
    private final String NODE_NAME = "NodeHello";
    private int _currentLeader;
    private ILeader _leaderModule;

    public Server(String id, int nodeNum, int _currentLeader)
    {
        _id = id;
        _nodeNum = nodeNum;

        try {
            //_helloStub = (Hello)UnicastRemoteObject.exportObject(this, 0);
            //_registry = LocateRegistry.getRegistry();
            //_registry.bind(NODE_NAME, _helloStub);



        } catch (Exception e)
        {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }

    }

    public String sayHello()
    {
        return "Hello from: " + _id;
    }

    public int getNodeNum()
    {
        return _nodeNum;
    }

    public void StopServer()
    {
        try{
            _registry.unbind(NODE_NAME);
        } catch (Exception e)
        {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }

    }

    public void OrganizationMessage(int newLeader)
    {
        _currentLeader = newLeader;
    }


    public int QuestFunction()
    {
        int responseID = -1;

        for(int i = _nodeNum; i < 10; i++)
        {
            try {
                Registry registry = LocateRegistry.getRegistry(null);

                String registryEntry = "QuestNode" + i;
                Hello serverStub = (Hello)registry.lookup(registryEntry);
                responseID = serverStub.QuestFunction();
                if(responseID > 0)
                {
                    return responseID;
                }

            } catch(Exception e)
            {
                System.err.println("QuestFunction on ID " + responseID + e.toString());
                e.printStackTrace();
            }
        }

        //We have not returned, THIS must be the highest ID
        _currentLeader = _nodeNum;

        //Create a LeaderModule or this object
        //_leaderModule = new LeaderClass(_nodeNum);
        //_leaderModule.SendOrganizationMessages();
        SetLeader();

        return _nodeNum;
    }

    public void SetLeader()
    {
        _leaderModule = new LeaderClass(_nodeNum);
        //_leaderModule.SendOrganizationMessages();
    }

}

/*
public static void main(String args[]) {
        try {
            Server obj = new Server();
            Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);
// Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello", stub);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
 */