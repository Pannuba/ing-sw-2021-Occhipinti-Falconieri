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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
			System.out.print("LeaderCard: 1    DevCard: 2    Quit: 3\nInput: ");

			switch (input.nextLine())
			{

				case "1":	/* LeaderCard to XML. TODO: put card number in xml, not during initialization in Model */

					int leaderCardPoints;

					System.out.print("LeaderCard number [1, 16]: ");
					filename = "src/main/resources/xml/leadercards/leadercard" + input.nextLine() + ".xml";

					System.out.print("Points: ");
					leaderCardPoints = Integer.parseInt(input.nextLine());		/* Convert input string to int */

					System.out.print("Skill (1 = discount, 2 = additional storage, 3 = white marble, 4 = additional production): ");

					switch (input.nextLine())
					{
						case "1":		/* Get discount */
							SkillDiscount leaderCardOne = new SkillDiscount();	/* Not too sure about this but we'll give it a try */
							DevCardColor[] discountCost = new DevCardColor[2];

							for (int i = 0; i < 2; i++)
							{
								System.out.print("(cost) devcard color " + (i + 1) + " (G/Y/B/P): ");
								discountCost[i] = convertStringToDevColorType(input.nextLine());
							}

							ResourceType discountedResource = null;
							System.out.print("Resource (G/Y/B/P): ");
							discountedResource = convertStringToResType(input.nextLine());

							leaderCardOne.setPoints(leaderCardPoints);
							leaderCardOne.setCost(discountCost);
							leaderCardOne.setDiscountedResource(discountedResource);	/* Discount amount is already set in SkillDiscount */
							serialize(leaderCardOne, filename);
							break;

						case "2":		/* Additional storage */
							SkillStorage leaderCardTwo = new SkillStorage();
							Resource storageCost = new Resource();
							storageCost.setQuantity(5);
							System.out.print("Resource cost (G/Y/B/P): ");
							storageCost.setResourceType(convertStringToResType(input.nextLine()));
							System.out.print("Additional storage resource (G/Y/B/P): ");
							ResourceType storageResource = convertStringToResType(input.nextLine());
							// leaderCardTwo.getAdditionalStorage().setShelfSize(2);	shelfSize 2 is already in SkillStorage's constructor

							leaderCardTwo.setPoints(leaderCardPoints);
							leaderCardTwo.setCost(storageCost);
							leaderCardTwo.getAdditionalStorage().getShelfResource().setResourceType(storageResource);
							serialize(leaderCardTwo, filename);
							break;

						case "3":		/* White marble */
							SkillMarble leaderCardThree = new SkillMarble();
							DevCardColor[] whiteMarbleCost = new DevCardColor[3];

							for (int i = 0; i < 3; i++)
							{
								System.out.print("(cost) Devcard color " + (i + 1) + " (G/Y/B/P): ");
								whiteMarbleCost[i] = convertStringToDevColorType(input.nextLine());
							}

							System.out.print("White marble resource (G/Y/B/P): ");
							ResourceType whiteResource = convertStringToResType(input.nextLine());

							leaderCardThree.setPoints(leaderCardPoints);
							leaderCardThree.setCost(whiteMarbleCost);
							leaderCardThree.setWhiteMarble(whiteResource);
							serialize(leaderCardThree, filename);
							break;

						case "4":		/* Additional production */
							SkillProduction leaderCardFour = new SkillProduction();
							System.out.print("(cost) Devcard color: ");
							DevCardColor productionCost = convertStringToDevColorType(input.nextLine());
							System.out.print("Resource needed for production (G/Y/B/P): ");
							ResourceType productionRequirement = convertStringToResType(input.nextLine());

							leaderCardFour.setPoints(leaderCardPoints);
							leaderCardFour.setCost(productionCost);
							leaderCardFour.getRequirement().setResourceType(productionRequirement);
							serialize(leaderCardFour, filename);
							break;

						default:
							break;
					}

					break;

				case "2":															/* DevCard to XML */
					DevCard devCard = new DevCard();
					List<Resource> cost = new ArrayList<Resource>();
					List<Resource> requirements = new ArrayList<Resource>();
					List<Resource> product = new ArrayList<Resource>();
					int i = 0;

					System.out.print("DevCard number [1, 48]: ");
					int cardNumber = Integer.parseInt(input.nextLine());
					filename = "src/main/resources/xml/devcards/devcard" + cardNumber + ".xml";
					devCard.setCardNumber(cardNumber);

					System.out.print("Points: ");
					devCard.setPoints(Integer.parseInt(input.nextLine()));

					System.out.print("Level (1/2/3): ");
					devCard.setLevel(Integer.parseInt(input.nextLine()));

					System.out.print("Color (G/Y/B/P): ");
					devCard.setColor(convertStringToDevColorType(input.nextLine()));

					System.out.print("Cost, top of the card. How many resources? ");
					int costNum = Integer.parseInt(input.nextLine());

					for (i = 0; i < costNum; i++)
					{
						cost.add(new Resource());
						System.out.print("Category of cost resource #" + (i + 1) + " (G/Y/B/P): ");
						cost.get(i).setResourceType(convertStringToResType(input.nextLine()));

						System.out.print("Quantity of cost resource #" + (i + 1) + ": ");
						cost.get(i).setQuantity(Integer.parseInt(input.nextLine()));
					}

					System.out.print("Requirements, left side of the card. How many resources? ");
					int reqNum = Integer.parseInt(input.nextLine());

					for (i = 0; i < reqNum; i++)
					{
						requirements.add(new Resource());
						System.out.print("Category of requirement resource #" + (i + 1) + " (G/Y/B/P): ");
						requirements.get(i).setResourceType(convertStringToResType(input.nextLine()));

						System.out.print("Quantity of requirement resource #" + (i + 1) + ": ");
						requirements.get(i).setQuantity(Integer.parseInt(input.nextLine()));
					}

					System.out.print("Product, right side of the card. How many resources? ");
					int prodNum = Integer.parseInt(input.nextLine());

					for (i = 0; i < prodNum; i++)
					{
						String prodQuantity, prodType;

						System.out.print("Category of product resource #" + (i + 1) + " (G/Y/B/P/R): ");
						prodType = input.nextLine();

						System.out.print("Quantity of product resource #" + (i + 1) + ": ");
						prodQuantity = input.nextLine();

						if (prodType.equals("R"))		/* If the card produced faith points, set the faithpoints value */
						{
							devCard.setFaithPoints(Integer.parseInt(prodQuantity));
						}

						else
						{
							product.add(new Resource());
							product.get(i).setQuantity(Integer.parseInt(prodQuantity));
							product.get(i).setResourceType(convertStringToResType(prodType));
						}
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

		switch(str.toUpperCase())		/* So inputs like "g" and "y" are still valid */
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
				System.out.print("Invalid resource type");
				return null;
		}
	}

	public static DevCardColor convertStringToDevColorType(String str)
	{
		switch(str.toUpperCase())
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
				System.out.print("Invalid color type");
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