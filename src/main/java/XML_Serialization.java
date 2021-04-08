/*	This is a standalone program that converts Java code to XML files.
	It is used to set the parameters of every DevCard, LeaderCard and ActionToken without hardcoding them.
*/

import model.LeaderCard;
import model.Resource;

import java.beans.ExceptionListener;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
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

		while (true)
		{
			System.out.printf("LeaderCard: 1    DevCard: 2    Quit: 3%nInput: ");
			choice = input.nextLine();

			switch (choice) {

				case "1":		/* LeaderCard to XML */

					System.out.printf("Filename (don't add \".xml\"): ");
					filename = "resources/leadercards/" + input.nextLine() + ".xml";

					System.out.printf("Points: ");
					leaderCardPoints = Integer.parseInt(input.nextLine());		/* Convert input string to int */

					System.out.printf("Requirements: ");
					leaderCardRequirements = input.nextLine();

					System.out.printf("Skill (1 = discount, 2 = additional storage, 3 = white marble, 4 = additional production): ");
					choice = input.nextLine();
					switch (choice)
					{
						case "1":		/* Get discount */
							LeaderCard.SkillOne leaderCardOne = new LeaderCard.SkillOne();	/* Not too sure about this but we'll give it a try */
							leaderCardOne.setPoints(leaderCardPoints);
							leaderCardOne.setRequirements(leaderCardRequirements);
							Resource discountedResource = new Resource();
							System.out.printf("Resource: ");
							discountedResource.setCategory(input.nextLine());
							discountedResource.setQuantity(0);							/* We just need the resource type, we don't *own* any more resources */
							leaderCardOne.setDiscountedResource(discountedResource);
							leaderCardOne.setDiscount(-1);								/* All cards have a discount of 1 resource less */
							serialize(leaderCardOne, filename);
							break;

						case "2":
							LeaderCard.SkillTwo leaderCardTwo = new LeaderCard.SkillTwo();
							leaderCardTwo.setPoints(leaderCardPoints);
							leaderCardTwo.setRequirements(leaderCardRequirements);
							Resource additionalStorage = new Resource();
							System.out.printf("Additional storage category: ");
							additionalStorage.setCategory(input.nextLine());
							additionalStorage.setQuantity(2);								/* Can only hold one type of resource, amount is always 2 */
							leaderCardTwo.setAdditionalStorage(additionalStorage);
							serialize(leaderCardTwo, filename);
							break;

						case "3":

							break;

						case "4":

							break;

						default:

					}

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

	public static void serialize(Object toSerialize, String filename) throws IOException
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

		encoder.writeObject(toSerialize);		/* Throws exceptions */
		encoder.close();
		fos.close();
	}

	public static Object deserialize(String filename) throws IOException
	{
		FileInputStream fis = new FileInputStream(filename);
		XMLDecoder decoder = new XMLDecoder(fis);
		Object decodedObject = (Object) decoder.readObject();
		decoder.close();
		fis.close();
		return decodedObject;
	}

}