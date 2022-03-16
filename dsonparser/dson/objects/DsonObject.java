/*
 * Name: Craig Dominic Dunbar
 * Company: Cyber Legacy Systems LLC
 * Location: United States of America, Metuchen, New Jersey,08840
 * Date: 05/17/2020
 */

package objects;

import java.util.ArrayList;

public class DsonObject 
{
	private ArrayList<Dson> DSON=null;
	private DsonList DSON_LIST=null;
	
	// Default constructor
	public DsonObject()
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
	public void append(String field, DsonObject object)
	{
		if(field!=null && !field.isEmpty())
		{
			Dson dson=new Dson(field, object);
			this.append(dson);
		}
	}
	
	// Method append a dson array
	public void append(String field, DsonArray array)
	{
		if(field!=null && !field.isEmpty())
		{
			Dson dson=new Dson(field, array);
			this.append(dson);
		}
	}
	
	// Method append a dson element
	public void append(DsonElement element)
	{
		if(this.DSON_LIST!=null && element.getField()!=null && !element.getField().isEmpty())
		{
			this.DSON_LIST.append(element);
		}
	}
	
	// Mehod append objects to the dson array
	public void append(String field, Object object)
	{
		if(field!=null && !field.isEmpty())
		{
			DsonElement ele=new DsonElement(field, object);
			this.append(ele);
		}
	}
	
	// Mehod append objects to the dson array
	public void append(Object[][] object)
	{
		for(int i=0; i<object.length; i++)
		{
			if(object[i][0] instanceof String)
			{
				String field=(String)object[i][0];
				this.append(field, object[i][1]);
			}
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
	public Object find(String field)
	{
		Object obj=null;
		boolean bool=false;
		for(int i=0; i<this.DSON_LIST.getSize(); i++)
		{
			if(this.DSON_LIST.getField(i).equalsIgnoreCase(field))
			{
				obj=this.DSON_LIST.getElement(i);
				bool=true;
				break;
			}
		}
		if(!bool)
		{
			for(int i=0; i<this.DSON.size(); i++)
			{
				if(this.DSON.get(i).getField().equalsIgnoreCase(field))
				{
					String type=this.DSON.get(i).getType();
					if(type.equalsIgnoreCase("DSON_OBJECT"))
					{
						obj=this.DSON.get(i).getObject();
					}
					else if(type.equalsIgnoreCase("DSON_ARRAY"))
					{
						obj=this.DSON.get(i).getArray();
					}
					bool=true;
					break;
				}
			}
		}
		return obj;
	}
	
}
