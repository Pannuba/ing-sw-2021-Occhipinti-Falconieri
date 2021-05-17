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

				case "1":															/* LeaderCard to XML */
					int leaderCardPoints;
					String leaderCardRequirements = null;

					System.out.print("LeaderCard number [1, 16]: ");
					filename = "src/main/resources/xml/leadercards/leadercard" + input.nextLine() + ".xml";

					System.out.print("Points: ");
					leaderCardPoints = Integer.parseInt(input.nextLine());		/* Convert input string to int */

					System.out.print("Requires resources: 1    devCards: 2 ");

					switch(input.nextLine())
					{
						case "1":
							leaderCardRequirements = "RES_";
							break;

						case "2":
							leaderCardRequirements = "DEV_";
							System.out.print("Level: ");			/* Could be hardcoded since we already know the cards, but it's better this way */
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

					System.out.print("Requirements (1Y1P, ...): ");
					leaderCardRequirements += input.nextLine();

					System.out.print("Skill (1 = discount, 2 = additional storage, 3 = white marble, 4 = additional production): ");

					switch (input.nextLine())
					{
						case "1":		/* Get discount */
							SkillDiscount leaderCardOne = new SkillDiscount();	/* Not too sure about this but we'll give it a try */
							leaderCardOne.setPoints(leaderCardPoints);
							leaderCardOne.setRequirements(leaderCardRequirements);
							ResourceType discountedResource = null;
							System.out.print("Resource (G/Y/B/P): ");
							discountedResource = ResourceType.convertStringToResType(input.nextLine());
							leaderCardOne.setDiscountedResource(discountedResource);	/* Discount amount is already set in SkillDiscount */
							serialize(leaderCardOne, filename);
							break;

						case "2":		/* Additional storage */
							SkillStorage leaderCardTwo = new SkillStorage();
							leaderCardTwo.setPoints(leaderCardPoints);
							leaderCardTwo.setRequirements(leaderCardRequirements);
							System.out.print("Additional storage resource (G/Y/B/P): ");
							leaderCardTwo.getAdditionalStorage().setShelfSize(2);
							leaderCardTwo.getAdditionalStorage().getShelfResource().setResourceType(ResourceType.convertStringToResType(input.nextLine()));
							serialize(leaderCardTwo, filename);
							break;

						case "3":		/* White marble */
							SkillMarble leaderCardThree = new SkillMarble();
							leaderCardThree.setPoints(leaderCardPoints);
							leaderCardThree.setRequirements(leaderCardRequirements);
							System.out.print("White marble resource (G/Y/B/P): ");				/* TODO: switch/case for ResourceType */
							leaderCardThree.setWhiteMarble(ResourceType.convertStringToResType(input.nextLine()));
							serialize(leaderCardThree, filename);
							break;

						case "4":		/* Additional production */
							SkillProduction leaderCardFour = new SkillProduction();
							leaderCardFour.setPoints(leaderCardPoints);
							leaderCardFour.setRequirements(leaderCardRequirements);
							System.out.print("Resource needed for production (G/Y/B/P): ");
							leaderCardFour.getCost().setResourceType(ResourceType.convertStringToResType(input.nextLine()));
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
					devCard.setColor(DevCardColor.convertStringToDevColorType(input.nextLine()));

					System.out.print("Requirements, top of the card. How many resources? ");
					int reqNum = Integer.parseInt(input.nextLine());

					for (i = 0; i < reqNum; i++)
					{
						requirements.add(new Resource());

						System.out.print("Quantity of requirement resource #" + (i + 1) + ": ");
						requirements.get(i).setQuantity(Integer.parseInt(input.nextLine()));

						System.out.print("Category of requirement resource #" + (i + 1) + " (G/Y/B/P): ");
						requirements.get(i).setResourceType(ResourceType.convertStringToResType(input.nextLine()));
					}

					System.out.print("Cost, left side of the card. How many resources? ");
					int costNum = Integer.parseInt(input.nextLine());

					for (i = 0; i < costNum; i++)
					{
						cost.add(new Resource());

						System.out.print("Quantity of cost resource #" + (i + 1) + ": ");
						cost.get(i).setQuantity(Integer.parseInt(input.nextLine()));

						System.out.print("Category of cost resource #" + (i + 1) + " (G/Y/B/P): ");
						cost.get(i).setResourceType(ResourceType.convertStringToResType(input.nextLine()));
					}

					System.out.print("Product, right side of the card. How many resources? ");
					int prodNum = Integer.parseInt(input.nextLine());

					for (i = 0; i < prodNum; i++)
					{
						String prodQuantity, prodType;

						System.out.print("Quantity of product resource #" + (i + 1) + ": ");
						prodQuantity = input.nextLine();

						System.out.print("Category of product resource #" + (i + 1) + " (G/Y/B/P/R): ");
						prodType = input.nextLine();

						if (prodType.equals("R"))		/* If the card produced faith points, set the faithpoints value */
						{
							devCard.setFaithPoints(Integer.parseInt(prodQuantity));
						}

						else
						{
							product.add(new Resource());
							product.get(i).setQuantity(Integer.parseInt(prodQuantity));
							product.get(i).setResourceType(ResourceType.convertStringToResType(prodType));
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