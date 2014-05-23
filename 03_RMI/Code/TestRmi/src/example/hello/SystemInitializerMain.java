package example.hello;

import java.rmi.registry.LocateRegistry;

/**
 * Created by chr on 5/20/14.
 */
public class SystemInitializerMain {
    public static void main(String args[]) {
        try {
            System.setProperty("java.rmi.server.hostname", GlobalHost.HostName);
            LocateRegistry.createRegistry(GlobalHost.Port);
            System.out.println("RMI Registry created.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        while(true) {}
    }
}
