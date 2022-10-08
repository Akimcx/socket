package cx.ksim.tchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class WriteToSocket implements Runnable {

	private Socket socket;
	public WriteToSocket(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try(BufferedReader socketIn = new BufferedReader(
				new InputStreamReader(socket.getInputStream()))) {
			String socketInput = "";
			while((socketInput = socketIn.readLine()) != null) {
				System.out.println("Client: " + socketInput);
			}
		} catch (IOException e) {
			System.err.printf("[ERR] %s %s\n",socket,e.getMessage());;
		}
		
	}

}
