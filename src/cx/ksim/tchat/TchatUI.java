package cx.ksim.tchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TchatUI extends Application{
	
	private Socket socket;
	private BufferedReader sockIn;
	private PrintWriter sockOut;

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void stop() throws Exception {
		if(socket != null) socket.close();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		socket = new Socket("localhost", 9000);
		sockIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		sockOut = new PrintWriter(socket.getOutputStream(), true);

		Label label = new Label(sockIn.readLine());
		TextField textField = new TextField();
		
		VBox root = new VBox(10, label, textField);
		
		textField.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			if(e.getCode() == KeyCode.ENTER) {
				sockOut.println(textField.getText());
				primaryStage.setTitle(textField.getText());
				primaryStage.setScene(tchatScreen());
				ss();
//				textField.clear();
//				root.getChildren().clear();
//				root.getChildren().addAll(messageView, inputRow);
//				root.setPadding(new Insets(5));
//				VBox.setVgrow(messageView, Priority.ALWAYS);
			}
		});

		Scene scene = new Scene(root, 400, 600);

		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.centerOnScreen();
	}
	
	private Scene tchatScreen() {
		TextField inputField = new TextField();
		inputField.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			if(e.getCode() == KeyCode.ENTER) {
				sendMessage(inputField.getText());
				inputField.clear();
				inputField.requestFocus();
			}
		});
		
		HBox inputRow = new HBox(inputField);
		inputRow.setPadding(new Insets(0, 3, 0, 3));
		inputField.prefWidthProperty().bind(inputRow.widthProperty());
		
		VBox messageView = new VBox(10);
		
		VBox root = new VBox(10,messageView, inputRow);
		root.setPadding(new Insets(5));
		VBox.setVgrow(messageView, Priority.ALWAYS);

		return new Scene(root, 400, 600);

	}
	
	private void ss() {
		Platform.runLater( () -> {
			String input;
			System.out.println(Thread.currentThread());
			System.out.println(sockIn);
			try {
				while((input = sockIn.readLine()) != null) {
					TextField field = new TextField(input);
					field.setEditable(false);
//					messageView.getChildren().add(0,field);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}
	
	private void sendMessage(String msg) {
		new Thread( () -> {
			sockOut.println(msg);
		}).start();
	}

}
