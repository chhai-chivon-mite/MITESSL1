import java.io.FileInputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.util.Scanner;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/*
 * @author: Chhai Chivon on Jan 13, 2020
 * Senior Application Developer
 */

public class Server {

	private static OutputStreamWriter outputStreamWriter;
	private static Scanner scanner;
	
	public static void main(String[] args) {
		try {
			
			//Creating Context
			SSLContext context = SSLContext.getInstance("SSL");
			
			// Creating KeyManagerFactory
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
			
			// Create Key Store
			KeyStore keyStore  = KeyStore.getInstance("JDK");
			
			// Filling the keyStore
			FileInputStream fileInputStream = new FileInputStream("C:\\Users\\DELL\\Desktop\\Certificate\\MITEServer.key");
			char[] password = "123456".toCharArray();
			keyStore.load(fileInputStream,password);
			
			// Initializing the KeyManagerFactory 
			keyManagerFactory.init(keyStore,password);
			
			// Initializing the Context
			context.init(keyManagerFactory.getKeyManagers(), null, null);
			
			// Create Server Socket
			SSLServerSocketFactory sslServerSocketFactory = context.getServerSocketFactory();
			SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(1234);
			
			//Listen when client request
			while(true) {
				System.out.println("Waiting client request...");
				// Accept Client Request
				SSLSocket socket = (SSLSocket) sslServerSocket.accept();
				
				// write data Output Stream Write to client by Socket Client Request 
				outputStreamWriter  = new OutputStreamWriter(socket.getOutputStream());
				// Scan data socket client input stream
				scanner  = new Scanner(socket.getInputStream());	 
				// If have line data
				while(scanner.hasNext()) {
					String reuqest = scanner.nextLine();
					System.out.println("Request => "+  reuqest);
					requestFromClient(reuqest);
				}
				socket.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void responseToClient(String res) {
		try {
			outputStreamWriter.write(res+"\n");
			outputStreamWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void requestFromClient(String req) {
		responseToClient("Hello Client");
	}
}
