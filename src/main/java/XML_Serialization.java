/*	This is a standalone program that converts Java code to XML files.
	It is used to set the parameters of every DevCard, LeaderCard and ActionToken without hardcoding them.
*/

import model.LeaderCard;
import model.DevCardsMarket;
import java.beans.ExceptionListener;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class XML_Serialization
{
	public static void main(String[] args) throws IOException        /* Wouldn't run because of no "static" and "String[] args"!!! */
	{
		LeaderCard leaderCard1 = new LeaderCard();
		leaderCard1.setPoints(2);
		leaderCard1.setRequirements("1Y1G");
		//leaderCard1.setSkill(Skills.SKILLONE);					/* Need to develop each skill */
		serialize(leaderCard1, "resources/leadercards/leadercard1.xml");

		/* And so on with all leadercards, devcards... */
	}

	public static void serialize(LeaderCard leadercard, String filename) throws IOException
	{
		File myfile = new File(filename);
		myfile.mkdirs();
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