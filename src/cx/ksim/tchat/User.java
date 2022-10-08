package cx.ksim.tchat;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

public record User(String username, Socket socket) {
	
	public InputStream getInputStream() throws IOException {
		return socket.getInputStream();
	}

	public void send(String message) throws IOException {
		PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
		socketOut.println(message);
	}
}