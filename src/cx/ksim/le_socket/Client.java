package cx.ksim.le_socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) {

		try(
				var clientSocket = new Socket("localhost", 9000);
				var output = new PrintWriter(clientSocket.getOutputStream(),true);
				var socketInput = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));
			){
			var stdInput = new BufferedReader(
					new InputStreamReader(System.in));
			String fromUser = null, fromSocket;
			while(!"/quit".equals(fromUser)) {
				fromSocket = socketInput.readLine();
				System.out.println("Server> "+fromSocket);
				System.out.print("> ");
				fromUser = stdInput.readLine();
				output.println(fromUser);
			}

		} catch (UnknownHostException e) {
			System.err.println("The IP address ofthe host could not be determined: " + e.getMessage());;
		} catch (IOException e) {
			System.err.println("An I/O error occurs when creating the socket: " + e.getMessage());
		}
	}

}
