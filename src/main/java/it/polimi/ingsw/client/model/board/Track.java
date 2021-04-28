package it.polimi.ingsw.client.model.board;

import it.polimi.ingsw.client.model.cards.PopeToken;

public class Track
{
	private Box[] faithTrack = new Box[25];
	private PopeToken[] popeTokens = new PopeToken[3];
	private int[] redPawns;
	private int blackPawn;

	public Box[] getFaithTrack()
	{
		return faithTrack;
	}

	public void setFaithTrack(Box[] faithTrack)
	{
		this.faithTrack = faithTrack;
	}

	public int[] getRedPawns()
	{
		return redPawns;
	}

	public void setRedPawns(int[] redPawns)
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

