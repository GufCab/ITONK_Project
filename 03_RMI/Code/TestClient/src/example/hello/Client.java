package example.hello;

/**
 * Created by guf on 5/19/14.
 */
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private Client() {}

    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];

        try {
            System.setProperty("java.rmi.server.hostname", "192.168.204.130");
            Registry registry = LocateRegistry.getRegistry("192.168.204.130");
            Hello stub = (Hello) registry.lookup("Hello");
            String response = stub.sayHello();
            System.out.println("response: " + response);

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();

        }
    }
}
