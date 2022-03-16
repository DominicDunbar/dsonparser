/*
 * Name: Craig Dominic Dunbar
 * Company: Cyber Legacy Systems LLC
 * Location: United States of America, Metuchen, New Jersey,08840
 * Date: 05/17/2020
 */

package objects;

public class DsonElement 
{
	private String FIELD=null;
	private Object VALUE=null;
	
	// Default constructor
	public DsonElement()
	{
		this.FIELD=null;
		this.VALUE=null;
	}
	
	// Constructor that takes a value
	public DsonElement(Object value)
	{
		this.FIELD=null;
		this.VALUE=value;
	}
	
	// Constructor that takes a field and value
	public DsonElement(String field, Object value)
	{
		this.FIELD=field;
		this.VALUE=value;
	}
	
///////////////////////////////
// Set Methods
/////////////////////////////
	
	
	// Method set the field
	public void setField(String field)
	{
		this.FIELD=field;
	}
	
	// Method set the value
	public void setValue(Object value)
	{
		this.VALUE=value;
	}
	
////////////////////////////////
// Get Methods
////////////////////////////////
	
	// Method get the field
	public String getField()
	{
		return this.FIELD;
	}
	
	// Method get the value
	public Object getValue()
	{
		return this.VALUE;
	}
	
////////////////////////////
// Other Methods
///////////////////////////
	
	// to string method
	public String toString()
	{
		String str="";
		if(this.FIELD!=null)
		{
			str=this.FIELD+":";
		}
		if(this.VALUE!=null)
		{
			str+=this.VALUE;
		}
		return str;
	}
}
