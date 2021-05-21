/*	This is a standalone program that converts Java objects to XML files.
	It is used to set the parameters of every DevCard and LeaderCard without hardcoding them.
	In order to find a good balance between freedom of customization and practicality, the tool automatically sets some fields
	based on the base game's cards. For example, it automatically sets "points = 5" for leadercards with the marble skill or
	the produced faithpoints in SkillProduction cards to 1 instead of asking for user input.
	These values are still included in the xmls so they can be edited manually by the user or with the parameters editor.
	There are still some limitations as to what can be customized, because too much freedom leads to chaos and excessive complexity.
	For example, leadercard requirements depend on the card's skill.
	For SkillProduction cards, only the card's level and color can be changed and not the amount of cards, or the type
	(resources instead of devcards), because adding a list or changing the type would be a mess.
*/

package it.polimi.ingsw.util;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.*;

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

					System.out.print("LeaderCard number [1, 16]: ");
					int leaderCardNumber = Integer.parseInt(input.nextLine());
					filename = "src/main/resources/xml/leadercards/leadercard" + leaderCardNumber + ".xml";

					System.out.print("Skill (1 = discount, 2 = additional storage, 3 = white marble, 4 = additional production): ");

					switch (input.nextLine())
					{
						case "1":		/* Get discount */
							SkillDiscount leaderCardOne = new SkillDiscount();	/* Not too sure about this but we'll give it a try */
							leaderCardOne.setCardNumber(leaderCardNumber);
							leaderCardOne.setPoints(2);

							List<DevCardColor> discountReq = new ArrayList<>();
							System.out.print("Required devcard color #1 (G/Y/B/P): ");
							discountReq.add(DevCardColor.convertStringToDevColorType(input.nextLine()));
							System.out.print("Required devcard color #2: ");
							discountReq.add(DevCardColor.convertStringToDevColorType(input.nextLine()));
							leaderCardOne.setRequirements(discountReq);

							System.out.print("Discounted resource (G/Y/B/P): ");
							leaderCardOne.setDiscountedResource(ResourceType.convertStringToResType(input.nextLine()));
							leaderCardOne.setDiscount(-1);		/* In xml so it can be changed by parameters editor */

							serialize(leaderCardOne, filename);
							break;

						case "2":		/* Additional storage */
							SkillStorage leaderCardTwo = new SkillStorage();
							leaderCardTwo.setCardNumber(leaderCardNumber);
							leaderCardTwo.setPoints(3);

							System.out.print("Requirement resource (G/Y/B/P): ");
							leaderCardTwo.getRequirements().setQuantity(5);
							leaderCardTwo.getRequirements().setResourceType(ResourceType.convertStringToResType(input.nextLine()));

							System.out.print("Additional storage resource (G/Y/B/P): ");
							leaderCardTwo.getAdditionalStorage().setShelfSize(2);			/* shelfSize in xml so it can be changed by the parameters editor */
							leaderCardTwo.getAdditionalStorage().getShelfResource().setResourceType(ResourceType.convertStringToResType(input.nextLine()));

							serialize(leaderCardTwo, filename);
							break;

						case "3":		/* White marble */
							SkillMarble leaderCardThree = new SkillMarble();
							leaderCardThree.setCardNumber(leaderCardNumber);
							leaderCardThree.setPoints(5);

							List<DevCardColor> marbleReq = new ArrayList<>();
							System.out.print("Required devcard color #1 (G/Y/B/P): ");
							marbleReq.add(DevCardColor.convertStringToDevColorType(input.nextLine()));
							System.out.print("Required devcard color #2 (same as before): ");
							marbleReq.add(DevCardColor.convertStringToDevColorType(input.nextLine()));
							System.out.print("Required devcard color #3: ");
							marbleReq.add(DevCardColor.convertStringToDevColorType(input.nextLine()));
							leaderCardThree.setRequirements(marbleReq);

							System.out.print("White marble resource (G/Y/B/P): ");
							leaderCardThree.setWhiteMarble(ResourceType.convertStringToResType(input.nextLine()));

							serialize(leaderCardThree, filename);
							break;

						case "4":		/* Additional production */
							SkillProduction leaderCardFour = new SkillProduction();
							leaderCardFour.setCardNumber(leaderCardNumber);
							leaderCardFour.setPoints(4);

							System.out.print("Required devcard color (G/Y/B/P): ");
							Pair<DevCardColor, Integer> productionReq = new Pair<>(DevCardColor.convertStringToDevColorType(input.nextLine()), 2);
							leaderCardFour.setRequirements(productionReq);

							System.out.print("Resource needed for production (G/Y/B/P): ");
							leaderCardFour.getCost().setResourceType(ResourceType.convertStringToResType(input.nextLine()));
							leaderCardFour.getCost().setQuantity(1);
							leaderCardFour.getProduct().setQuantity(1);			/* In xml so it can be changed by parameters editor */
							leaderCardFour.setFaithPoints(1);

							serialize(leaderCardFour, filename);
							break;
					}

					break;

				case "2":															/* DevCard to XML */
					DevCard devCard = new DevCard();
					List<Resource> cost = new ArrayList<>();
					List<Resource> requirements = new ArrayList<>();
					List<Resource> product = new ArrayList<>();
					int i = 0;

					System.out.print("DevCard number [1, 48]: ");
					int devCardNumber = Integer.parseInt(input.nextLine());
					filename = "src/main/resources/xml/devcards/devcard" + devCardNumber + ".xml";
					devCard.setCardNumber(devCardNumber);

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
					System.out.println("Insert 1, 2 or 3");
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
		encoder.writeObject(toSerialize);		/* Doesn't serialize "final" fields for some reason */
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