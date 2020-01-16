import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/*
 * @author: Chhai Chivon on Jan 13, 2020
 * Senior Application Developer
 */

public class Client {
	
	private static OutputStreamWriter outputStreamWriter;
	private static Scanner scanner;
	
	public static void main(String[] agrs) {
		
		
		try {
			
			/**
			 * Certificate Server
			 */
			/**
			 
			// Specifying Trust Store Info
			System.setProperty("javax.net.ssl.trustStore", "C:\\Users\\DELL\\Desktop\\Certificate\\MiteTrustStore.key");
			System.setProperty("javax.net.ssl.trustStorePassword", "123456");

			// Creating Socket and Connecting to the Server
			SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			SSLSocket socket = (SSLSocket) socketFactory.createSocket("localhost", 1234);
			
			**/
			
			
			/**
			 * trust all certificate
			 */
	
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] {
					new X509TrustManager() {
						
						@Override
						public X509Certificate[] getAcceptedIssuers() {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
							// TODO Auto-generated method stub
							
						}
					}
			};
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
			
		    // Connect to Server
		    Socket socket = new Socket("",1234);
		 					
			outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
			outputStreamWriter.write("Hello\n");
			outputStreamWriter.flush();
			
			scanner  = new Scanner(socket.getInputStream());
			while (scanner.hasNext()) {
				String request = scanner.nextLine();
				System.out.println(" Request => " + request);
			}
			socket.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
