package cx.ksim.tchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

class Client {
	
	private void start() {
		String hostName = "localhost";
		int portNumber = 9000;
		
		try {
			Socket server = new Socket(hostName, portNumber);

			new Thread(this.new SendMessageHandler(server)).start();
			new Thread(this.new ReceiveMessageHandler(server)).start();
			
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " +
					hostName);
			System.exit(1);
		} 
	}
	
	private class SendMessageHandler implements Runnable {
		private Socket server;
		public SendMessageHandler(Socket server) {
			this.server = server;
		}

		@Override
		public void run() {
			try(BufferedReader stdIn = new BufferedReader(
					new InputStreamReader(System.in));
					PrintWriter serverOut = new PrintWriter(server.getOutputStream(), true);
				) {
				String userInput;
				while ((userInput = stdIn.readLine()) != null) {
					serverOut.println(userInput);
				}				
				server.getOutputStream().write("Client quit\r\n".getBytes());
			} catch (IOException e) {
				System.err.println("Client " + e.getMessage());
			}
		}
		
	}
	
	private class ReceiveMessageHandler implements Runnable {
		private Socket server;
		public ReceiveMessageHandler(Socket server) {
			this.server = server;
		}
		
		@Override
		public void run() {
			try {
				BufferedReader serverIn = new BufferedReader(
						new InputStreamReader(server.getInputStream()));
				String input;
				while((input = serverIn.readLine()) != null) {
					System.out.println(input);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Client().start();
	}
}
