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

    private int _nextNodeId;

    public Server(String id, int nodeNum, int _currentLeader)
    {
        _id = id;
        _nodeNum = nodeNum;
        _isElection = false;

        try {
            _helloStub = (Hello)UnicastRemoteObject.exportObject(this, 0);
            _registry = LocateRegistry.getRegistry("192.168.204.130");
            _registry.bind("QuestNode" + _nodeNum, _helloStub);

        } catch (Exception e)
        {
            System.err.println("Server exception: " + e.toString());
            //e.printStackTrace();
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
            //e.printStackTrace();
        }

    }

    public void OrganizationMessage(int newLeader)
    {
        System.out.println("OrganizationMessage received. New leader: " + newLeader);
        _currentLeader = newLeader;
    }


    private void incrementNextNode()
    {
        _nextNodeId++;

        if(_nextNodeId > 10)
            _nextNodeId=0;
    }


    public void RingElectionFunction(int[] nodeIds)
    {
        boolean ringComplete = false;


        int futureLeader = -1;

        //Find out if ring is complete
        for(int s:nodeIds)
        {
            if(s == _nodeNum)
            {
                ringComplete = true;
                break;
            }
        }

        if(ringComplete==true)
        {
            for(int node:nodeIds) {
                if(node > futureLeader) {
                    futureLeader = node;
                }
            }

            try {
                Registry registry = LocateRegistry.getRegistry("192.168.204.130");

                String registryEntry = "QuestNode" + _nextNodeId;
                Hello serverStub = (Hello) registry.lookup(registryEntry);

                RingElectionSetNewLeader(futureLeader);


            } catch (Exception e) {

            }

        } else {
            //Ring is not complete
            try {
                Registry registry = LocateRegistry.getRegistry("192.168.204.130");

                String registryEntry = "QuestNode" + _nextNodeId;
                Hello serverStub = (Hello) registry.lookup(registryEntry);

                nodeIds[nodeIds.length+1] = _nodeNum;

                serverStub.RingElectionFunction(nodeIds);
            } catch (Exception e) {

            }
        }


    }

    public void RingElectionSetNewLeader(int leaderId) {
        if(leaderId==_nodeNum) {
            this.SetLeader();
        }

        if(leaderId > _currentLeader) {
            //if the received leaderId is larger then the known one
            try {
                Registry registry = LocateRegistry.getRegistry("192.168.204.130");

                String registryEntry = "QuestNode" + _nextNodeId;
                Hello serverStub = (Hello) registry.lookup(registryEntry);

                RingElectionSetNewLeader(leaderId);
            } catch (Exception e)
            {

            }
        }


    }

    public int QuestFunction()
    {
        System.out.println("Questing node: " + _nodeNum);
        int responseID = -1;

        for(int i = _nodeNum + 1; i <= 10; i++)
        {
            try {
                Registry registry = LocateRegistry.getRegistry("192.168.204.130");

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
                //e.printStackTrace();
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

        for(int i = _nodeNum + 1; i <= 10; i++)
        {
            try {
                Registry registry = LocateRegistry.getRegistry("192.168.204.130");

                String registryEntry = "QuestNode" + i;
                Hello serverStub = (Hello)registry.lookup(registryEntry);
                responseID = serverStub.BullyElection();
            } catch(Exception e) {
                System.err.println("QuestFunction on ID " + responseID + e.toString());                
            }
        }

        //I have the greatest id
        if(responseID == -1) {
            //declare winner
            System.out.println("I'm leader " + _nodeNum);
            SetLeader();
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