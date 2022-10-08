package cx.ksim.le_socket;

import java.io.BufferedReader;
import java.io.IOException;

public class Read extends Thread {

	private BufferedReader input;
	
	Read(BufferedReader input) {
		this.input = input;
	}
	@Override
	public void run() {
		read();
	}

	public void read() {
		try {
			input.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
