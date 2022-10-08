package cx.ksim.tchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


class Server {

	private List<User> users;
	private ServerSocket server;
	
	public Server() {
		users = new ArrayList<>();
	}
	
	public void addUser(User user) {
		users.add(user);
		System.out.printf("[INFO] %s connected\n", user);
	}
	
	public void removeUser(User user) {
		var b = users.removeIf(u -> u.equals(user));
		if(b) System.out.printf("[INFO] %s disconnected\n", user);
		else System.out.printf("[WARN] Could not find %s", user );
	}

	private void start() throws IOException {
		int portNumber = 9000;
		server = new ServerSocket(portNumber) {
			protected void finalize() throws IOException {
				this.close();
			}
		};
		try {
			while(true) {
				Socket client = server.accept();
				client.getOutputStream().write("Enter your username: \r\n".getBytes());
				byte[] bytes = new byte[255];
				client.getInputStream().read(bytes);
				String username = "";
				for (byte _byte : bytes) {
					if(_byte == 0 || _byte == 10 || _byte == 13) continue;
					username += (char)_byte;
				}
				User user = new User(username, client);
				addUser(user);
				broadcast(username + " join the chat");
				new Thread(new HandleClient(this,user)).start();
			}
		} catch (IOException e) {
			System.err.println("Server "+e.getMessage());
			System.exit(1);
		} 		
	}

	public static void main(String[] args) throws IOException {
		new Server().start();
	}
	
	public void messageAll(User sender, String message) {
		users.stream()
		.filter(u -> !u.equals(sender))
		.forEach(user -> {
			try {
				user.send(message);
				System.out.println("Message sent to " + user);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		});
	}

	public void broadcast(String message) {
		users.forEach(user -> {
			try {
				user.send(message);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		});
	}
}