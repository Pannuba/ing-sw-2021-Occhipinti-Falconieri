/*	This is a standalone program that converts Java code to XML files.
	It is used to set the parameters of every DevCard, LeaderCard and ActionToken without hardcoding them.
*/

import model.LeaderCard;
import model.DevCardsMarket;
import model.Skill;

import java.beans.ExceptionListener;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class XML_Serialization
{
	public static void main(String[] args) throws IOException        /* Wouldn't run because of no "static" and "String[] args"!!! */
	{
		/*
		LeaderCard leaderCard1 = new LeaderCard();
		leaderCard1.setPoints(2);
		leaderCard1.setRequirements("1Y1G");
		//leaderCard1.setSkill(Skills.SKILL_ONE);					/* Need to develop each skill
		serialize(leaderCard1, "resources/leadercards/leadercard1.xml");

		/* And so on with all leadercards, devcards... */


		/* Interactive system to generate XML without having to hardcode every card like above */

		Scanner input = new Scanner(System.in);
		String choice, filename, path;
		int leaderCardPoints;			// ...
		String leaderCardRequirements;	// ...
		Skill leaderCardSkill;

		while (true)
		{
			System.out.printf("LeaderCard: 1    DevCard: 2    Quit: 3%nInput: ");
			choice = input.nextLine();

			switch (choice) {

				case "1":		/* LeaderCard to XML */

					System.out.printf("Points: ");
					leaderCardPoints = Integer.parseInt(input.nextLine());		/* Convert input string to int */
					System.out.printf("Requirements: ");
					leaderCardRequirements = input.nextLine();

					System.out.printf("Filename (don't add \".xml\"): ");
					filename = "resources/leadercards/" + input.nextLine() + ".xml";

					LeaderCard leaderCard1 = new LeaderCard();
					leaderCard1.setPoints(leaderCardPoints);
					leaderCard1.setRequirements(leaderCardRequirements);
					//leaderCard1.setSkill(Skills.SKILL_ONE);					/* Need to develop each skill
					serialize(leaderCard1, filename);
					break;

				case "2":		/* DevCard to XML */

					break;

				case "3":
					return;

				default:
					System.out.printf("Insert 1, 2 or 3%n");
					break;
			}
		}
	}

	public static void serialize(LeaderCard leadercard, String filename) throws IOException
	{
		File myfile = new File(filename);
		myfile.createNewFile();
		FileOutputStream fos = new FileOutputStream(myfile);
		XMLEncoder encoder = new XMLEncoder(fos);
		encoder.setExceptionListener(new ExceptionListener()
		{
			public void exceptionThrown(Exception e)
			{
				System.out.println("Exception: " + e.toString());
			}
		});
		encoder.writeObject(leadercard);
		encoder.close();
		fos.close();
	}
}