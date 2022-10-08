package cx.ksim.tchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HandleClient implements Runnable{
	
	private User user;
	private Server server;
	
	public HandleClient(Server server, User user) {
		this.server = server;
		this.user = user;
	}

	@Override
	public void run() {
		try(BufferedReader userIn = new BufferedReader(
				new InputStreamReader(user.getInputStream()))
			){
			String input;
			while((input = userIn.readLine()) != null) {
				if("/".equals(input)) {
//					server;
				}
				server.messageAll(user,String.format("[%s] %s",user.username(), input));
			}
		} catch (IOException e) {
			System.err.println("Handle " + e.getMessage());
			server.removeUser(user);
		}
	}

}
