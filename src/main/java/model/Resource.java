package model;

/*	Stone --> grey ---> "G"
	Coin --> yellow --> "Y"
	Shield --> blue --> "B"
	Slave --> purple -> "P"
	Cross --> red ----> "R"  (faith points)
 */

public class Resource
{
	private String category;				/* enum!! */
	private int quantity;

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
}
