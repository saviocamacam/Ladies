package client;

import java.util.Scanner;

public class MainClient {
	public static Scanner scanner = new Scanner(System.in);
	public static String command = "";
	
	public static void main(String[] args) {
		
		System.out.println("Informe seu apelido: ");
		String apelide = scanner.nextLine();
		
		ChatManager chatManager = new ChatManager(apelide, 6799, 6789);
		chatManager.initialize();
		
		do {
			scanner = new Scanner(System.in);
			command = scanner.nextLine();
			chatManager.executeCommand(command);
			
		} while(!command.matches("sair"));
	}

}
