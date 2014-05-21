package example.hello;

import java.rmi.registry.LocateRegistry;

/**
 * Created by chr on 5/20/14.
 */
public class SystemInitializerMain {
    public static void main(String args[]) {
        try {
            System.setProperty("java.rmi.server.hostname", "192.168.204.130");
            LocateRegistry.createRegistry(1099);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while(true) {}
    }
}
