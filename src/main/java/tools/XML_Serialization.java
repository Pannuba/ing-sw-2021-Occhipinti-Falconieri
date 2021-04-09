/*	This is a standalone program that converts Java code to XML files.
	It is used to set the parameters of every DevCard, LeaderCard and ActionToken without hardcoding them.
*/

package tools;

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
		/* Interactive system to generate XML without having to hardcode every card */

		Scanner input = new Scanner(System.in);
		String choice, filename, path;
		int leaderCardPoints;			// ...
		String leaderCardRequirements = null;	// ...

		while (true)
		{
			System.out.printf("LeaderCard: 1    DevCard: 2    Quit: 3%nInput: ");

			switch (input.nextLine()) {

				case "1":		/* LeaderCard to XML */

					System.out.printf("Filename (don't add \".xml\"): ");
					filename = "resources/leadercards/" + input.nextLine() + ".xml";

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

						case "2":		/* Additional storage */
							LeaderCard.SkillTwo leaderCardTwo = new LeaderCard.SkillTwo();
							leaderCardTwo.setPoints(leaderCardPoints);
							leaderCardTwo.setRequirements(leaderCardRequirements);
							Resource additionalStorage = new Resource();
							System.out.printf("Additional storage resource: ");
							additionalStorage.setCategory(input.nextLine());
							additionalStorage.setQuantity(2);								/* Can only hold one type of resource, amount is always 2 */
							leaderCardTwo.setAdditionalStorage(additionalStorage);
							serialize(leaderCardTwo, filename);
							break;

						case "3":		/* White marble */
							LeaderCard.SkillThree leaderCardThree = new LeaderCard.SkillThree();
							leaderCardThree.setPoints(leaderCardPoints);
							leaderCardThree.setRequirements(leaderCardRequirements);
							System.out.printf("White marble resource: ");
							leaderCardThree.setWhiteMarble(input.nextLine());
							serialize(leaderCardThree, filename);
							break;

						case "4":		/* Additional production */
							LeaderCard.SkillFour leaderCardFour = new LeaderCard.SkillFour();
							leaderCardFour.setPoints(leaderCardPoints);
							leaderCardFour.setRequirements(leaderCardRequirements);
							Resource productionCost = new Resource();
							System.out.printf("Resource needed for production: ");
							productionCost.setCategory(input.nextLine());
							productionCost.setQuantity(1);								/* Always 1 */
							leaderCardFour.setProductionCost(productionCost);
							serialize(leaderCardFour, filename);
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