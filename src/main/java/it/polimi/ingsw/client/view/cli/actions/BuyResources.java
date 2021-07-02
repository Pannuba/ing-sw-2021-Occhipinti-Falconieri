package it.polimi.ingsw.client.view.cli.actions;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.PrintMethods;
import it.polimi.ingsw.model.cards.SkillMarble;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BuyResources
{
	private Scanner input;
	private CLI cli;

	public BuyResources(Scanner input, CLI cli)
	{
		this.input = input;
		this.cli = cli;
	}

	public List<String> run()
	{
		List<String> command = new ArrayList<>();
		String rowOrCol, rowOrColNum, whiteMarbleRes = "", choice;

		System.out.print("This is the current marbles market:\n\n");
		PrintMethods.printMarblesMarket(cli.getGameState().getCurrMarblesMarket());

		System.out.print("\nSelect a row (1) or column(2)? (Q = choose another action) ");
		choice = input.nextLine();

		switch (choice)
		{
			case "1":
				rowOrCol = "ROW";
				System.out.print("Insert row #: ");
				rowOrColNum = input.nextLine();
				break;

			case "2":
				rowOrCol = "COLUMN";
				System.out.print("Insert column #: ");
				rowOrColNum = input.nextLine();
				break;

			default:
				cli.chooseAction();
				return null;		/* Without return it still sends a command after the recursion(?) which leads to chaos */
		}

		List<SkillMarble> marbleLeaders = cli.getGameState().getPlayerByName(cli.getUsername()).getMarbleLeaders();

		if (marbleLeaders.size() == 2)
		{
			System.out.print(	"You have " + marbleLeaders.size() + " leaders with a white marble skill! "				+
								PrintMethods.convertResTypeToString(marbleLeaders.get(0).getWhiteMarble()) + " and "	+
								PrintMethods.convertResTypeToString(marbleLeaders.get(1).getWhiteMarble())				+
								".\nInsert the resource type you want to convert (G/Y/B/P): "							);
			whiteMarbleRes = input.nextLine();
		}

		command.add("BUY_RESOURCES");
		command.add(rowOrCol);
		command.add(rowOrColNum);
		command.add(whiteMarbleRes);
		return command;
	}
}
