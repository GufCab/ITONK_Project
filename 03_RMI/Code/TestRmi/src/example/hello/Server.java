package example.hello;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
    private Boolean _isElection;

    public Server(String id, int nodeNum, int _currentLeader)
    {
        _id = id;
        _nodeNum = nodeNum;
        _isElection = false;

        try {
            _helloStub = (Hello)UnicastRemoteObject.exportObject(this, 0);
            _registry = LocateRegistry.getRegistry();
            _registry.bind("QuestNode" + _nodeNum, _helloStub);

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
        System.out.println("OrganizationMessage received. New leader: " + newLeader);
        _currentLeader = newLeader;
    }


    public int QuestFunction()
    {
        System.out.println("Questing node: " + _nodeNum);
        int responseID = -1;

        for(int i = _nodeNum + 1; i <= 10; i++)
        {
            try {
                Registry registry = LocateRegistry.getRegistry(null);

                String registryEntry = "QuestNode" + i;
                Hello serverStub = (Hello)registry.lookup(registryEntry);
                responseID = serverStub.QuestFunction();
                if(responseID > 0)
                {
                    return _nodeNum;
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

    public int BullyElection() {
        System.out.println("Questing node: " + _nodeNum);
        int responseID = -1;

        if(!_isElection) {
            _isElection = true;

            for(int i = _nodeNum + 1; i <= 10; i++)
            {
                try {
                    Registry registry = LocateRegistry.getRegistry(null);

                    String registryEntry = "QuestNode" + i;
                    Hello serverStub = (Hello)registry.lookup(registryEntry);
                    responseID = serverStub.BullyElection();
                } catch(Exception e) {
                    System.err.println("QuestFunction on ID " + responseID + e.toString());
                    e.printStackTrace();
                }
            }

            //I have the greatest id
            if(responseID == -1) {
                //declare winner
                System.out.println("I'm leader" + _nodeNum);
                SetLeader();
            }
        }


        //someone is taking over
        return 1;
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