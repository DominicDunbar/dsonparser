/*
 * Name: Craig Dominic Dunbar
 * Company: Cyber Legacy Systems LLC
 * Location: United States of America, Metuchen, New Jersey,08840
 * Date: 05/17/2020
 */

package objects;

public class Dson 
{
	private String FIELD=null;
	private String TYPE=null;
	private DsonObject DSON_OBJECT=null;
	private DsonArray DSON_ARRAY=null;
	
	// Dson contructor that takes in a dson object
	public Dson(String field, DsonObject object)
	{
		this.TYPE="DSON_OBJECT";
		this.FIELD=field;
		this.DSON_OBJECT=object;
	}
	
	// Dson contructor that takes in a dson object
	public Dson(String field, DsonArray array)
	{
		this.TYPE="DSON_ARRAY";
		this.FIELD=field;
		this.DSON_ARRAY=array;
	}
	
//////////////////////////////
// Set Methods
//////////////////////////////
	
	// Method set the field name
	public void setField(String field)
	{
		this.FIELD=field;
	}
	
	// Method set the dson object
	public void setObject(DsonObject object)
	{
		this.TYPE="DSON_OBJECT";
		this.DSON_OBJECT=object;
		this.DSON_ARRAY=null;
	}
	
	// Method set the dson array
	public void setArray(DsonArray array)
	{
		this.TYPE="DSON_ARRAY";
		this.DSON_ARRAY=array;
		this.DSON_OBJECT=null;
	}
	
///////////////////////////////
// Get Methods
///////////////////////////////
	
	// Method get the field 
	public String getField()
	{
		return this.FIELD;
	}
	
	// Method get the type
	public String getType()
	{
		return this.TYPE;
	}
	
	// Method get the dson Object
	public DsonObject getObject()
	{
		return this.DSON_OBJECT;
	}
	
	// Method get the dson array
	public DsonArray getArray()
	{
		return this.DSON_ARRAY;
	}
}
