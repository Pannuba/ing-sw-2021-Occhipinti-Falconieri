package it.polimi.ingsw.util;

import java.io.Serializable;

public class Pair<Object1, Object2> implements Serializable            /* Used by SkillProduction for requirements */
{
	public Object1 obj1;
	public Object2 obj2;

	public Pair()			/* InstantiationException without empty constructor */
	{
		this.obj1 = null;
		this.obj2 = null;
	}

	public Pair(Object1 obj1, Object2 obj2)
	{
		this.obj1 = obj1;
		this.obj2 = obj2;
	}
}