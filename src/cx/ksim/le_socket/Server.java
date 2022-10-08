package cx.ksim.le_socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

public class Server{

	public static void main(String[] args) {
		try(
				var serverSocket = new ServerSocket(6069);
				var clientSocket = serverSocket.accept();
				var output = new PrintWriter(clientSocket.getOutputStream(), true);
				var input = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));
				var stdIn = new BufferedReader(
						new InputStreamReader(System.in));
			) {
			var r = new Read(input);
			String inLine, outLine;
			System.out.println("New Client: " + clientSocket);
			output.println("Welcome, what can i do 4 you ?");
			while(serverSocket.isBound()){
				inLine = input.readLine();
				System.out.println("Client>" + inLine);
				System.out.print(">");
				outLine = stdIn.readLine();
				output.println(outLine);
			}
			
		} catch (IOException e) {
			System.err.println(e.getMessage() + ": An I/O error occurs when opening the socket.");
		}

	}

//	private static String process(String input) {
//		System.out.println(input);
//		return switch(input) {
//			case "/rooms" -> "No rooms";
//			case "/join" -> "Please specify room id";
//			case "/users" -> "Only you 😜";
//			case "?" -> "Usage\n\t/rooms\n\t/joins\n\t/users";
//			default -> "Type '?' to see options";
//		};
//	}

}


