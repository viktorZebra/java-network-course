import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.stream.Stream;

public class NetworkInfo {

    Stream<NetworkInterface> networkInterfaces;

    public NetworkInfo() {

        try {
            this.networkInterfaces = NetworkInterface.networkInterfaces();
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        }

    }

    public void printAllNetworkInterface() {

        networkInterfaces.forEach(parentNetIf -> {
                printNetworkInterface(parentNetIf);
                parentNetIf.subInterfaces().forEach(this::printNetworkInterface);
        });

    }

    private void printNetworkInterface(NetworkInterface networkInterface) {

        try {
            System.out.printf("Display name: %s\n", networkInterface.getDisplayName());
            System.out.printf("Name: %s\n", networkInterface.getName());
            System.out.printf("Active: %b\n", networkInterface.isUp());
            System.out.printf("Virtual: %b\n", networkInterface.isVirtual());
            System.out.printf("Loopback: %b\n\n", networkInterface.isLoopback());
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        }
    }

}
