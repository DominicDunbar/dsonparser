/*
 * Name: Craig Dominic Dunbar
 * Company: Cyber Legacy Systems LLC
 * Location: United States of America, Metuchen, New Jersey,08840
 * Date: 05/17/2020
 */

package objects;

import java.util.ArrayList;

public class DsonArray 
{
	private ArrayList<Dson> DSON=null;
	private DsonList DSON_LIST=null;
	
	// Default constructor
	public DsonArray()
	{
		this.DSON=new ArrayList<Dson>();
		this.DSON_LIST=new DsonList();
	}
	
////////////////////////////////////
// Set Methods
///////////////////////////////////
	
	// Method set the list
	public void setList(DsonList list)
	{
		this.DSON_LIST=list;
	}
	
	// Method set the element
	public void setElement(int index, DsonElement element)
	{
		this.DSON_LIST.setElement(index, element);
	}
	
	// Method set a dson
	public void setDson(int index, Dson dson)
	{
		if(this.DSON!=null && index<this.DSON.size())
		{
			this.DSON.set(index, dson);
		}
	}
	
/////////////////////////////
// Get Methods
////////////////////////////
	
	// Method get the list
	public DsonList getList()
	{
		return this.DSON_LIST;
	}
	
	// Method get a element
	public DsonElement getElement(int index)
	{
		DsonElement element=null;
		if(this.DSON_LIST!=null && index<this.DSON_LIST.getSize())
		{
			element=this.DSON_LIST.getElement(index);
		}
		return element;
	}
	
	// Method get a dson
	public Dson getDson(int index)
	{
		Dson dson=null;
		if(this.DSON!=null && index<this.DSON.size())
		{
			dson=this.DSON.get(index);
		}
		return dson;
	}
	
	// Method get the element size
	public int getElementSize()
	{
		int size=-1;
		if(this.DSON_LIST!=null)
		{
			size=this.DSON_LIST.getSize();
		}
		return size;
	}
	
	// Method get the dson size
	public int getDsonSize()
	{
		int size=-1;
		if(this.DSON!=null)
		{
			size=this.DSON.size();
		}
		return size;
	}
	
//////////////////////////
// Other Methods
//////////////////////////
	
	// Method append a dson
	public void append(Dson dson)
	{
		if(this.DSON!=null)
		{
			this.DSON.add(dson);
		}
	}
	
	// Method append a dson object
	public void append(DsonObject object)
	{
		Dson dson=new Dson("", object);
		this.append(dson);
	}
	
	// Method append a dson array
	public void append(DsonArray array)
	{
		Dson dson=new Dson("", array);
		this.append(dson);
	}
	
	// Method append a dson element
	public void append(DsonElement element)
	{
		if(this.DSON_LIST!=null)
		{
			this.DSON_LIST.append(element);
		}
	}
	
	// Mehod append objects to the dson array
	public void append(Object object)
	{
		DsonElement ele=new DsonElement(object);
		this.append(ele);
	}
	
	// Mehod append objects to the dson array
	public void append(Object[] object)
	{
		for(int i=0; i<object.length; i++)
		{
			this.append(object[i]);
		}
	}
	
	// Method check to see if the object is empty
	public boolean isEmpty()
	{
		if(this.DSON_LIST.getSize()==0 && this.DSON.size()==0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	// Method find the object of interest
	public Object find(int index)
	{
		Object obj=null;
		boolean bool=false;
		if(index>-1 && index<this.DSON.size())
		{
			String type=this.DSON.get(index).getType();
			if(type.equalsIgnoreCase("DSON_OBJECT"))
			{
				obj=this.DSON.get(index).getObject();
			}
			else if(type.equalsIgnoreCase("DSON_ARRAY"))
			{
				obj=this.DSON.get(index).getArray();
			}
			bool=true;
		}
		if(!bool)
		{
			if(index>-1 && index<this.DSON_LIST.getSize())
			{
				obj=this.DSON_LIST.getElement(index);
			}
		}
		return obj;
	}
	
}
