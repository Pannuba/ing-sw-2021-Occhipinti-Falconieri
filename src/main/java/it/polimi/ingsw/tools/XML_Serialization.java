/*	This is a standalone program that converts Java code to XML files.
	It is used to set the parameters of every DevCard, LeaderCard and ActionToken without hardcoding them.
*/

/* TODO: fix new enum types input */

package it.polimi.ingsw.tools;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.cards.*;

import java.beans.ExceptionListener;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.*;
import java.util.Scanner;

public class XML_Serialization
{
	public static void main(String[] args) throws IOException        /* Wouldn't run because of no "static" and "String[] args"!!! */
	{
		/* Interactive system to generate XML without having to hardcode every card */

		Scanner input = new Scanner(System.in);
		String filename;

		while (true)
		{
			System.out.printf("LeaderCard: 1    DevCard: 2    Quit: 3\nInput: ");

			switch (input.nextLine())
			{

				case "1":															/* LeaderCard to XML */
					int leaderCardPoints;
					String leaderCardRequirements = null;

					System.out.printf("LeaderCard number [1, 16]: ");
					filename = "src/main/resources/xml/leadercards/leadercard" + input.nextLine() + ".xml";

					System.out.printf("Points: ");
					leaderCardPoints = Integer.parseInt(input.nextLine());		/* Convert input string to int */

					System.out.printf("Requires resources: 1    devCards: 2 ");

					switch(input.nextLine())
					{
						case "1":
							leaderCardRequirements = "RES_";
							break;

						case "2":
							leaderCardRequirements = "DEV_";
							System.out.printf("Level: ");			/* Could be hardcoded since we already know the cards, but it's better this way */
																	/* For simplicity's sake all required cards have to be of the same level */
							switch(input.nextLine())
							{
								case "1":
									leaderCardRequirements += "LV1_";
									break;

								case "2":
									leaderCardRequirements += "LV2_";
									break;

								case "3":
									leaderCardRequirements += "LV3_";
									break;

								default:
									break;
							}

							break;

						default:
							break;
					}

					System.out.printf("Requirements (1Y1P, ...): ");
					leaderCardRequirements += input.nextLine();

					System.out.printf("Skill (1 = discount, 2 = additional storage, 3 = white marble, 4 = additional production): ");

					switch (input.nextLine())
					{
						case "1":		/* Get discount */
							SkillDiscount leaderCardOne = new SkillDiscount();	/* Not too sure about this but we'll give it a try */
							leaderCardOne.setPoints(leaderCardPoints);
							leaderCardOne.setRequirements(leaderCardRequirements);
							ResourceType discountedResource = null;
							System.out.printf("Resource (G/Y/B/P): ");
							discountedResource = convertStringToResType(input.nextLine());
							leaderCardOne.setDiscountedResource(discountedResource);	/* Discount amount is already set in SkillDiscount */
							serialize(leaderCardOne, filename);
							break;

						case "2":		/* Additional storage */
							SkillStorage leaderCardTwo = new SkillStorage();
							leaderCardTwo.setPoints(leaderCardPoints);
							leaderCardTwo.setRequirements(leaderCardRequirements);
							System.out.printf("Additional storage resource (G/Y/B/P): ");
							leaderCardTwo.getAdditionalStorage().setShelfSize(2);
							leaderCardTwo.getAdditionalStorage().getShelfResource().setResourceType(convertStringToResType(input.nextLine()));
							serialize(leaderCardTwo, filename);
							break;

						case "3":		/* White marble */
							SkillMarble leaderCardThree = new SkillMarble();
							leaderCardThree.setPoints(leaderCardPoints);
							leaderCardThree.setRequirements(leaderCardRequirements);
							System.out.printf("White marble resource (G/Y/B/P): ");				/* TODO: switch/case for ResourceType */
							leaderCardThree.setWhiteMarble(convertStringToResType(input.nextLine()));
							serialize(leaderCardThree, filename);
							break;

						case "4":		/* Additional production */
							SkillProduction leaderCardFour = new SkillProduction();
							leaderCardFour.setPoints(leaderCardPoints);
							leaderCardFour.setRequirements(leaderCardRequirements);
							System.out.printf("Resource needed for production (G/Y/B/P): ");
							leaderCardFour.getRequirement().setResourceType(convertStringToResType(input.nextLine()));
							System.out.printf("Resource produced (G/Y/B/P): ");
							leaderCardFour.getProduct().setResourceType(convertStringToResType(input.nextLine()));
							serialize(leaderCardFour, filename);
							break;

						default:
							break;
					}

					break;

				case "2":															/* DevCard to XML */
					DevCard devCard = new DevCard();
					Resource cost[] = new Resource[3];
					Resource requirements[] = new Resource[2];
					Resource product[] = new Resource[3];
					int i = 0;

					System.out.printf("DevCard number [1, 48]: ");
					filename = "resources/xml/devcards/devcard" + input.nextLine() + ".xml";

					System.out.printf("Points: ");
					devCard.setPoints(Integer.parseInt(input.nextLine()));

					System.out.printf("Level (1/2/3): ");
					devCard.setLevel(Integer.parseInt(input.nextLine()));

					System.out.printf("Color (G/Y/B/P): ");
					devCard.setColor(convertStringToColorType(input.nextLine()));		/* TODO: if/else to pass enum to setColor instead of input.nextLine() */

					for (i = 0; i < 3; i++)
					{
						cost[i] = new Resource();
						System.out.printf("Quantity of cost resource #" + (i + 1) + ": ");
						cost[i].setQuantity(Integer.parseInt(input.nextLine()));

						System.out.printf("Category of cost resource #" + (i + 1) + " (G/Y/B/P/null): ");
						cost[i].setResourceType(convertStringToResType(input.nextLine()));
					}

					for (i = 0; i < 2; i++)
					{
						requirements[i] = new Resource();
						System.out.printf("Quantity of requirement resource #" + (i + 1) + " (0 if doesn't exist): ");
						requirements[i].setQuantity(Integer.parseInt(input.nextLine()));

						System.out.printf("Category of requirement resource #" + (i + 1) + " (G/Y/B/P/null): ");
						requirements[i].setResourceType(convertStringToResType(input.nextLine()));
					}

					for (i = 0; i < 3; i++)
					{
						product[i] = new Resource();
						System.out.printf("Quantity of product resource #" + (i + 1) + ": ");
						product[i].setQuantity(Integer.parseInt(input.nextLine()));

						System.out.printf("Category of product resource #" + (i + 1) + " (G/Y/B/P/R/null): ");
						product[i].setResourceType(convertStringToResType(input.nextLine()));
					}

					devCard.setRequirements(requirements);
					devCard.setCost(cost);
					devCard.setProduct(product);

					serialize(devCard, filename);
					break;

				case "3":
					return;

				default:
					System.out.printf("Insert 1, 2 or 3%n");
					break;
			}
		}
	}

	public static ResourceType convertStringToResType(String str)
	{
		if (str == null)
		{
			return null;
		}

		switch(str)
		{
			case "G":
				return ResourceType.GREY;

			case "Y":
				return ResourceType.YELLOW;

			case "B":
				return ResourceType.BLUE;

			case "P":
				return ResourceType.PURPLE;

			default:
				System.out.print("Error");
				return null;
		}
	}

	public static DevCardColor convertStringToColorType(String str)
	{
		switch(str)
		{
			case "G":
				return DevCardColor.GREEN;

			case "Y":
				return DevCardColor.YELLOW;

			case "B":
				return DevCardColor.BLUE;

			case "P":
				return DevCardColor.PURPLE;

			default:
				System.out.print("Error");
				return null;
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

	public static Object deserialize(String filename) throws IOException    /* Works! println output is the same before and after serialization */
	{
		FileInputStream fis = new FileInputStream(filename);
		XMLDecoder decoder = new XMLDecoder(fis);
		Object decodedObject = (Object) decoder.readObject();
		decoder.close();
		fis.close();
		return decodedObject;
	}
}