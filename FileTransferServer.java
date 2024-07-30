// Step 1: Specify the Protocol
// We'll define a simple protocol with the following steps:

// 1.Client sends a request to the server to transfer a file.
// 2.Server acknowledges the request.
// 3.Client sends the file data to the server.
// 4.Server confirms the file reception.

// Step 2: Develop the Client Program
// First, let's define the remote interface that both client and server will use.

// Remote Interface (FileTransfer.java)
// import java.rmi.Remote;
// import java.rmi.RemoteException;

// public interface FileTransfer extends Remote {
//     String sendFile(String fileName, byte[] fileData) throws RemoteException;
// }
//Client Program (FileTransferClient.java)
// import java.io.File;
// import java.io.FileInputStream;
// import java.rmi.registry.LocateRegistry;
// import java.rmi.registry.Registry;

// public class FileTransferClient {
//     public static void main(String[] args) {
//         try {
//             // Locate the registry where the server is registered
//             Registry registry = LocateRegistry.getRegistry("localhost", 1099);

//             // Lookup the remote object
//             FileTransfer stub = (FileTransfer) registry.lookup("FileTransfer");

//             // Read file and convert it to byte array
//             File file = new File("path/to/your/file.txt");
//             FileInputStream fis = new FileInputStream(file);
//             byte[] fileData = new byte[(int) file.length()];
//             fis.read(fileData);
//             fis.close();

//             // Call the remote method
//             String response = stub.sendFile(file.getName(), fileData);
//             System.out.println("Server response: " + response);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }
// Step 3: Develop the Server Program
// Server Implementation (FileTransferImpl.java)
// import java.io.FileOutputStream;
// import java.rmi.RemoteException;
// import java.rmi.server.UnicastRemoteObject;

// public class FileTransferImpl extends UnicastRemoteObject implements FileTransfer {
//     protected FileTransferImpl() throws RemoteException {
//         super();
//     }

//     @Override
//     public String sendFile(String fileName, byte[] fileData) throws RemoteException {
//         try {
//             // Save the file data to a new file
//             FileOutputStream fos = new FileOutputStream("received_" + fileName);
//             fos.write(fileData);
//             fos.close();
//             return "File received successfully.";
//         } catch (Exception e) {
//             e.printStackTrace();
//             return "File transfer failed.";
//         }
//     }
// }
//Server Program (FileTransferServer.java)
// import java.rmi.registry.LocateRegistry;
// import java.rmi.registry.Registry;
// import java.rmi.server.UnicastRemoteObject;

// public class FileTransferServer {
//     public static void main(String[] args) {
//         try {
//             // Create the remote object
//             FileTransferImpl obj = new FileTransferImpl();

//             // Export the remote object to the RMI runtime
//             FileTransfer stub = (FileTransfer) UnicastRemoteObject.exportObject(obj, 0);

//             // Bind the remote object's stub in the registry
//             Registry registry = LocateRegistry.createRegistry(1099);
//             registry.bind("FileTransfer", stub);

//             System.out.println("FileTransfer server ready");
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }
//Compile all the Java files:
//javac FileTransfer.java FileTransferImpl.java FileTransferClient.java FileTransferServer.java
//Start the RMI Registry:
//rmiregistry 1099
//Start the Server
//java FileTransferServer
//Run the Client
//java FileTransferClient

