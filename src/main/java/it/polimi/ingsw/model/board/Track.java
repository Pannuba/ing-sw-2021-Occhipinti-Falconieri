package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.PopeToken;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * One track shared among all players. The players' pawns are stored in the redPawns hashmap, the faith track is an array of integers,
 * and the popeTokens array is used to keep track of whether the first, second or third vatican report has already been called
 * @author Giulio Occhipinti
 * @author Chiara Falconieri
 */

public class Track implements Serializable
{
	private final int[] faithTrack = new int[25];
	private final PopeToken[] popeTokens = new PopeToken[3];
	private HashMap<Integer, Integer> redPawns;
	private int numPlayers;
	private int blackPawn;

	/**
	 * Empty constructor required by XMLEncoder for the "persistence" advanced feature
	 */

	public Track()
	{

	}

	/**
	 * Creates the track by setting all parameters
	 * @param players used to create a redPawn for each player
	 */

	public Track(List<Player> players)
	{
		System.out.println("Creating track...");

		numPlayers = players.size();
		redPawns = new HashMap<>();

		for (int i = 0; i < players.size(); i++)		/* Initialize player pawns, player ID 0 has redPawns[0], ID 1 has redPawns[1] and so on */
			redPawns.put(players.get(i).getId(), 0);

		blackPawn = 0;

		popeTokens[0] = new PopeToken(2);
		popeTokens[1] = new PopeToken(3);
		popeTokens[2] = new PopeToken(4);

		faithTrack[0]	= 0;		faithTrack[1]	= 0;		faithTrack[2]	= 0;		faithTrack[3]	= 1;
		faithTrack[4]	= 0;		faithTrack[5]	= 0;		faithTrack[6]	= 2;		faithTrack[7]	= 0;
		faithTrack[8]	= 0;		faithTrack[9]	= 4;		faithTrack[10]	= 0;		faithTrack[11]	= 0;
		faithTrack[12]	= 6;		faithTrack[13]	= 0;		faithTrack[14]	= 0;		faithTrack[15]	= 9;
		faithTrack[16]	= 0;		faithTrack[17]	= 0;		faithTrack[18]	= 12;		faithTrack[19]	= 0;
		faithTrack[20]	= 0;		faithTrack[21]	= 16;		faithTrack[22]	= 0;		faithTrack[23]	= 0;
		faithTrack[24]	= 20;
	}

	/**
	 * Checks whether a pope box has been reached or surpassed by a player. Called by the controller after every round
	 * @return the pope box number that triggered the vatican report. 0 if no vatican report
	 */

	public int checkVaticanReport()
	{
		for (int i = 0; i < numPlayers; i++)		/* redPawns.get(i) returns the position of player ID i */
		{
			if ((redPawns.get(i) >= 24 || blackPawn >= 24) && !popeTokens[2].isDiscarded())	return 24;

			if ((redPawns.get(i) >= 16 || blackPawn >= 16) && !popeTokens[1].isDiscarded())	return 16;

			if ((redPawns.get(i) >= 8  || blackPawn >= 8)  && !popeTokens[0].isDiscarded())	return 8;
		}

		return 0;
	}

	public int[] getFaithTrack()
	{
		return faithTrack;
	}

	public PopeToken[] getPopeTokens()
	{
		return popeTokens;
	}

	public HashMap<Integer, Integer> getRedPawns()
	{
		return redPawns;
	}

	public void setRedPawns(HashMap<Integer, Integer> redPawns)
	{
		this.redPawns = redPawns;
	}

	public int getBlackPawn()
	{
		return blackPawn;
	}

	public void setBlackPawn(int blackPawn)
	{
		this.blackPawn = blackPawn;
	}
}

