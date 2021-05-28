package it.polimi.ingsw.util;

import java.io.Serializable;

public class Pair<Object1, Object2> implements Serializable            /* Used by SkillProduction for requirements */
{
	private Object1 obj1;
	private Object2 obj2;

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

	public void setObj1(Object1 obj1)
	{
		this.obj1 = obj1;
	}

	public void setObj2(Object2 obj2)
	{
		this.obj2 = obj2;
	}
}