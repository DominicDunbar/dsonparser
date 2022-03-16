/*
 * Name: Craig Dominic Dunbar
 * Company: Cyber Legacy Systems LLC
 * Location: United States of America, Metuchen, New Jersey,08840
 * Date: 05/17/2020
 */

package objects;

import java.util.ArrayList;

public class DsonList 
{
	private ArrayList<DsonElement>ELEMENTS;
	
	// Default constructor
	public DsonList()
	{
		this.ELEMENTS=new ArrayList<DsonElement>();
	}

///////////////////////////////////
// Set Methods
//////////////////////////////////
	
	// Method set the field
	public void setField(int index, String field)
	{
		if(index<this.ELEMENTS.size())
		{
			this.ELEMENTS.get(index).setField(field);
		}
	}
	
	// Method set the value 
	public void setValue(int index, Object value)
	{
		if(index<this.ELEMENTS.size())
		{
			this.ELEMENTS.get(index).setValue(value);
		}
	}
	
	// Method set the element
	public void setElement(int index, DsonElement element)
	{
		if(index<this.ELEMENTS.size())
		{
			this.ELEMENTS.set(index, element);
		}
	}
	
/////////////////////////////////
// Get Method
////////////////////////////////
	
	// Method get the field
	public String getField(int index)
	{
		String field=null;
		if(index<this.ELEMENTS.size())
		{
			field=this.ELEMENTS.get(index).getField();
		}
		return field;
	}
	
	// Method get the value
	public Object getValue(int index)
	{
		Object value=null;
		if(index<this.ELEMENTS.size())
		{
			value=this.ELEMENTS.get(index).getValue();
		}
		return value;
	}
	
	// Method get the dson
	public DsonElement getElement(int index)
	{
		DsonElement dsonobj=null;
		if(index<this.ELEMENTS.size())
		{
			dsonobj=this.ELEMENTS.get(index);
		}
		return dsonobj;
	}
	
	// Method get the fields
	public ArrayList<String> getFields()
	{
		ArrayList<String> fields=new ArrayList<String>();
		for(int i=0; i<this.ELEMENTS.size(); i++)
		{
			fields.add(this.ELEMENTS.get(i).getField());
		}
		return fields;
	}
	
	// Method get the values
	public ArrayList<Object> getValues()
	{
		ArrayList<Object> values=new ArrayList<Object>();
		for(int i=0; i<this.ELEMENTS.size(); i++)
		{
			values.add(this.ELEMENTS.get(i).getValue());
		}
		return values;
	}
	
	public ArrayList<DsonElement> getElements()
	{
		return this.ELEMENTS;
	}
	
	// Method get the size of the element list
	public int getSize()
	{
		return this.ELEMENTS.size();
	}

	
///////////////////////////////
// Other Methods
///////////////////////////////
	
	// Method append a dson object
	public void append(DsonElement element)
	{
		this.ELEMENTS.add(element);
	}
	
	// Method return a string representation of a dson element
	public String toString()
	{
		String str="";
		for(int i=0; i<this.ELEMENTS.size(); i++)
		{
			if(i==this.ELEMENTS.size()-1)
			{
				str+=this.ELEMENTS.get(i).toString();
			}
			else 
			{
				str+=this.ELEMENTS.get(i).toString()+",";
			}
		}
		return str;
	}
}
