package client.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class CLI		/* Is this the view? Controller? Both? */
{
	public static void main(String[] args) throws IOException
	{
		Scanner input = new Scanner(System.in);
		String choice;
		Socket socket = new Socket("127.0.0.1", 2000);		/* Add port to config file? */

		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		System.out.println("Masters of the Renaissance!\n\nNew game: 1\tJoin Game: 2");		/* TODO: add error detection (if input is not 1 nor 2) */

		/* Client directly asks for code, numPlayers... No reason to make the server ask because it's a standard process */
		/* ALWAYS use writeUTF(), never writeInt() or anything else */

		switch(input.nextLine())
		{
			case "1":
				dos.writeUTF("NEW_GAME");
				System.out.println("Game code: " + dis.readUTF());
				System.out.println("Insert number of players:" );
				dos.writeUTF(input.nextLine());
				break;

			case "2":
				dos.writeUTF("JOIN_GAME");
				System.out.println("Insert game code: ");
				dos.writeUTF(input.nextLine());


				break;

			default:
				break;
		}

		System.out.println("incoming from server: " + (String)dis.readUTF());
		dos.flush();
		dos.close();
		socket.close();
	}
}
