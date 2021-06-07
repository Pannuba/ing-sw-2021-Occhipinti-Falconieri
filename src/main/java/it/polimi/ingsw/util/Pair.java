package it.polimi.ingsw.util;

import java.io.Serializable;

/**
 * Pair class used by SkillProduction leader cards for their requirements
 * @param <Object1> the first element of the pair
 * @param <Object2> the second element
 */

public class Pair<Object1, Object2> implements Serializable
{
	private final Object1 obj1;
	private final Object2 obj2;

	public Pair()			/* InstantiationException during serialization without empty constructor */
	{
		this.obj1 = null;
		this.obj2 = null;
	}

	public Pair(Object1 obj1, Object2 obj2)
	{
		this.obj1 = obj1;
		this.obj2 = obj2;
	}

	public Object1 getObj1()
	{
		return obj1;
	}

	public Object2 getObj2()
	{
		return obj2;
	}
}