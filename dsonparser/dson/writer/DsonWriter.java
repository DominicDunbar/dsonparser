/*
 * Name: Craig Dominic Dunbar
 * Company: Cyber Legacy Systems LLC
 * Location: United States of America, Metuchen, New Jersey,08840
 * Date: 05/22/2020
 */

package writer;

import objects.Dson;
import objects.DsonArray;
import objects.DsonList;
import objects.DsonObject;

public class DsonWriter 
{
	// Method build a dson string from a dson object
	public String buildDsonString(DsonObject object)
	{
		String dson_string=this.buildObject(object);
		return dson_string;
	}
	
	// Method build a dson string from a dson array
	public String buildDsonString(DsonArray array)
	{
		String dson_string=this.buildArray(array);
		return dson_string;
	}
	
	// Method build the object
	private String buildObject(DsonObject object)
	{
		String object_string="{";
		DsonList list=object.getList();
		for(int i=0; i<list.getSize(); i++)
		{
			String field=list.getField(i);
			Object value=list.getValue(i);
			if(i<(list.getSize()-1))
			{
				if(value instanceof String)
				{
					object_string+='"'+field+'"'+':'+'"'+value+'"'+',';
				}
				else 
				{
					object_string+='"'+field+'"'+':'+value+',';
				}
			}
			else 
			{
				if(value instanceof String)
				{
					object_string+='"'+field+'"'+':'+'"'+value+'"';
				}
				else 
				{
					object_string+='"'+field+'"'+':'+value;
				}
			}
		}
		for(int i=0; i<object.getDsonSize(); i++)
		{
			Dson dson=object.getDson(i);
			String field=dson.getField();
			String type=dson.getType();
			if(object_string.length()>1)
			{
				object_string+=","+'"'+field+'"'+':';
			}
			else 
			{
				object_string+=""+'"'+field+'"'+':';
			}
			if(type.equalsIgnoreCase("DSON_OBJECT"))
			{
				object_string+=this.buildObject(dson.getObject());
			}
			else if(type.equalsIgnoreCase("DSON_ARRAY"))
			{
				object_string+=this.buildArray(dson.getArray());
			}
		}
		object_string+='}';
		return object_string;
	}
	
	// Method build the array
	private String buildArray(DsonArray array)
	{
		String array_string="[";
		DsonList list=array.getList();
		for(int i=0; i<list.getSize(); i++)
		{
			Object value=list.getValue(i);
			if(i<(list.getSize()-1))
			{
				if(value instanceof String)
				{
					array_string+=""+'"'+value+'"'+',';
				}
				else 
				{
					array_string+=""+value+',';
				}
			}
			else 
			{
				if(value instanceof String)
				{
					array_string+=""+'"'+value+'"';
				}
				else 
				{
					array_string+=value;
				}
			}
		}
		for(int i=0; i<array.getDsonSize(); i++)
		{
			Dson dson=array.getDson(i);
			String type=dson.getType();
			if(array_string.length()>1)
			{
				array_string+=',';
			}
			if(type.equalsIgnoreCase("DSON_OBJECT"))
			{
				array_string+=this.buildObject(dson.getObject());
			}
			else if(type.equalsIgnoreCase("DSON_ARRAY"))
			{
				array_string+=this.buildArray(dson.getArray());
			}
		}
		array_string+=']';
		return array_string;
	}
}
