/*
 * Name: Craig Dominic Dunbar
 * Company: Cyber Legacy Systems LLC
 * Location: United States of America, Metuchen, New Jersey,08840
 * Date: 05/17/2020
 */

package parser;

import format.JsonFormat;
import objects.Dson;
import objects.DsonArray;
import objects.DsonElement;
import objects.DsonObject;
import objects.DsonSearchObject;

public class DsonParser 
{
	private char CH[]; // holds the characts of the json string
	private int INDEX=0; // holds the index of where the parser currently parsing
	private Dson ROOT;
	
	// Method create a dson object
	public DsonParser(String json_string)
	{
		JsonFormat format=new JsonFormat();
		this.CH=format.formatJson(json_string);
		while (this.INDEX<this.CH.length) 
		{
			if(this.CH[this.INDEX]=='{')
			{
				this.INDEX++;
				DsonObject object=this.dsonObjectParse();
				this.ROOT=new Dson("root", object);
			}
			else if(this.CH[this.INDEX]=='[')
			{
				this.INDEX++;
				DsonArray array=this.dsonArrayParse();
				this.ROOT=new Dson("root", array);
			}
			else
			{
				this.INDEX++;
			}
		}
	}
	
	
	// Method parses a json object
	private DsonObject dsonObjectParse()
	{
		DsonObject object=new DsonObject();
		String field="";
		Object value=null;
		boolean get_field=true;
		boolean get_value=false;
		while (this.INDEX<this.CH.length) 
		{
			if(this.CH[this.INDEX]=='}')
			{
				this.INDEX++;
				return object;
			}
			else if(get_field && this.CH[this.INDEX]=='"')
			{
				this.INDEX++;
				field=this.fieldParse();
				get_field=false;
				get_value=true;
			}
			else if(get_value && !Character.isSpaceChar(this.CH[this.INDEX]) &&
					this.CH[this.INDEX]!='{' && this.CH[this.INDEX]!='[' && 
					this.CH[this.INDEX]!=',')
			{
				value=this.valueParse();
				DsonElement ele=new DsonElement(field, value);
				object.append(ele);
				field="";
				value=null;
				get_value=false;
				get_field=true;
			}
			else if(get_value && this.CH[this.INDEX]=='{') 
			{
				this.INDEX++;
				DsonObject inner_object=this.dsonObjectParse();
				Dson dson=new Dson(field, inner_object);
				object.append(dson);
				field="";
				get_value=false;
				get_field=true;
			}
			else if(get_value && this.CH[this.INDEX]=='[')
			{
				this.INDEX++;
				DsonArray inner_array=this.dsonArrayParse();
				Dson dson=new Dson(field, inner_array);
				object.append(dson);
				field="";
				get_value=false;
				get_field=true;
			}
			else if(Character.isSpaceChar(this.CH[this.INDEX]))
			{
				this.INDEX++;
			}
			else if(this.CH[this.INDEX]==',')
			{
				this.INDEX++;
				get_value=false;
				get_field=true;
			}
		}
		return object;
	}
	
	// Method parses a json array
	private DsonArray dsonArrayParse()
	{
		DsonArray array=new DsonArray();
		Object value=null;
		while (this.INDEX<this.CH.length) 
		{
			if(this.CH[this.INDEX]==']')
			{
				this.INDEX++;
				return array;
			}
			else if(this.CH[this.INDEX]=='{') 
			{
				this.INDEX++;
				DsonObject inner_obect=this.dsonObjectParse();
				Dson dson=new Dson("", inner_obect);
				array.append(dson);
			}
			else if(this.CH[this.INDEX]=='[')
			{
				this.INDEX++;
				DsonArray inner_array=this.dsonArrayParse();
				Dson dson=new Dson("", inner_array);
				array.append(dson);
			}
			else if(Character.isSpaceChar(this.CH[this.INDEX]) || this.CH[this.INDEX]==',')
			{
				this.INDEX++;
			}
			else if(!Character.isSpaceChar(this.CH[this.INDEX]) &&
				this.CH[this.INDEX]!='{' && this.CH[this.INDEX]!='[' 
				&& this.CH[this.INDEX]!=',')
			{
				value=this.valueParse();
				DsonElement ele=new DsonElement(value);
				array.append(ele);
				value=null;
			}
		}
		return array;
	}
	
	// Method parses a field
	private String fieldParse()
	{
		String field="";
		while (this.INDEX<this.CH.length) 
		{
			if(this.CH[this.INDEX]==':')
			{
				this.INDEX++;
				return field;
			}
			else if(this.CH[this.INDEX]=='"' || Character.isSpaceChar(this.CH[this.INDEX]))
			{
				this.INDEX++;
			}
			else if(Character.isLetterOrDigit(this.CH[this.INDEX]) || this.CH[this.INDEX]=='_' 
					|| this.CH[this.INDEX]=='-')
			{
				field+=this.CH[this.INDEX];
				this.INDEX++;
			}
		}
		return field;
	}
	
	// Method parses a value
	private Object valueParse()
	{
		Object value=null;
		while (this.INDEX<this.CH.length) 
		{
			if(this.CH[this.INDEX]=='{' || this.CH[this.INDEX]=='}' ||
					this.CH[this.INDEX]=='[' || this.CH[this.INDEX]==']' || 
					this.CH[this.INDEX]==',')
			{
				return value;
			}
			else if(this.CH[this.INDEX]=='t' || this.CH[this.INDEX]=='f')
			{
				String result=this.booleanParse();
				if(result.equalsIgnoreCase("true"))
				{
					value=true;
				}
				else
				{
					value=false;
				}
			}
			else if(this.CH[this.INDEX]=='n') 
			{
				String result=this.nullParse();
				if(result.equalsIgnoreCase("null"))
				{
					value=null;
				}
			}
			else if(Character.isDigit(this.CH[this.INDEX]) || this.CH[this.INDEX]=='-')
			{
				String result=this.numberParse();
				if(result.contains(".") || result.contains("e") || result.contains("E"))
				{
					value=Double.parseDouble(result);
				}
				else if(result.length()>9)
				{
					value=Long.parseLong(result);
				}
				else 
				{
					value=Integer.parseInt(result);
				}
			}
			else if(this.CH[this.INDEX]=='"')
			{
				this.INDEX++;
				value=this.stringParse();
			}
			else if(Character.isSpaceChar(this.CH[this.INDEX]))
			{
				this.INDEX++;
			}
		}
		return value;
	}
	
	// Method parses a boolean
	private String booleanParse()
	{
		String result="";
		while(this.INDEX<this.CH.length)
		{
			if(this.CH[this.INDEX]==',' || this.CH[this.INDEX]=='}' || this.CH[this.INDEX]==']')
			{
				return result;
			}
			else if(Character.isSpaceChar(this.CH[this.INDEX]))
			{
				this.INDEX++;
			}
			else 
			{
				result+=this.CH[this.INDEX];
				this.INDEX++;
			}
		}
		return result;
	}
	
	// Method parses a null
	private String nullParse()
	{
		String result="";
		while (this.INDEX<this.CH.length) 
		{
			if(this.CH[this.INDEX]==',' || this.CH[this.INDEX]=='}' || this.CH[this.INDEX]==']')
			{
				return result;
			}
			else if(Character.isSpaceChar(this.CH[this.INDEX]))
			{
				this.INDEX++;
			}
			else
			{
				result+=this.CH[this.INDEX];
				this.INDEX++;
			}
		}
		return result;
	}
	
	// Method parses a number
	private String numberParse()
	{
		String result="";
		while (this.INDEX<this.CH.length) 
		{
			if(this.CH[this.INDEX]==',' || this.CH[this.INDEX]=='}' || this.CH[this.INDEX]==']')
			{
				return result;
			}
			else if(Character.isSpaceChar(this.CH[this.INDEX]))
			{
				this.INDEX++;
			}
			else if(Character.isDigit(this.CH[this.INDEX]) || this.CH[this.INDEX]=='-' ||
					this.CH[this.INDEX]=='+' || this.CH[this.INDEX]=='.' || 
					this.CH[this.INDEX]=='e' || this.CH[this.INDEX]=='E')
			{
				result+=this.CH[this.INDEX];
				this.INDEX++;
			}
		}
		return result;
	}
	
	// Method parses a String
	private String stringParse()
	{
		String result="";
		char lastch=' ';
		int quotes=1;
		String sub_quote="";
		while (this.INDEX<this.CH.length) 
		{
			if(quotes%2==0 && this.CH[this.INDEX]=='}')
			{
				return result;
			}
			else if(quotes%2==0 && this.CH[this.INDEX]==']')
			{
				return result;
			}
			else if(quotes%2==0 && lastch=='"' && this.CH[this.INDEX]==',')
			{
				return result;
			}
			if(this.CH[this.INDEX]=='"')
			{
				lastch=this.CH[this.INDEX];
				quotes++;
				if(quotes%2==0)
				{
					sub_quote+=this.CH[this.INDEX];
				}
				else if(quotes>2 && quotes%2==1)
				{
					sub_quote+=this.CH[this.INDEX];
					result+=sub_quote;
					sub_quote="";
				}
				this.INDEX++;
			}
			else if(quotes%2==1 && this.CH[this.INDEX]!='"')
			{
				if(!Character.isSpaceChar(this.CH[this.INDEX]))
				{
					lastch=this.CH[this.INDEX];
				}
				result+=this.CH[this.INDEX];
				this.INDEX++;
			}
			else if(quotes%2==0 && this.CH[this.INDEX]!='"')
			{
				if(!Character.isSpaceChar(this.CH[this.INDEX]))
				{
					lastch=this.CH[this.INDEX];
				}
				sub_quote+=this.CH[this.INDEX];
				this.INDEX++;
			}
		}
		return result;
	}

	
	
//////////////////////////
// Get Method
//////////////////////////
	
	// Method get the root element
	public Dson getRoot()
	{
		return this.ROOT;
	}
	
	// Method get the value of the object
	public Object find(String search_path)
	{
		
		Object value=null;
		DsonSearchObject search_object=new DsonSearchObject(search_path);
		String type=this.ROOT.getType();
		if(type!=null && type.equalsIgnoreCase("DSON_OBJECT"))
		{
			DsonObject object=this.ROOT.getObject();
			String field=(String)search_object.getCurrentPath();
			value=object.find(field);
		}
		else if(type!=null && type.equalsIgnoreCase("DSON_ARRAY"))
		{
			DsonArray array=this.ROOT.getArray();
			int field=(int)search_object.getCurrentPath();
			value=array.find(field);
		}
		while(search_object.hasNext())
		{
			search_object.getNextPath();
			if(value instanceof DsonObject)
			{
				DsonObject object=(DsonObject)value;
				String field=(String)search_object.getCurrentPath();
				value=object.find(field);
			}
			else if(value instanceof DsonArray)
			{
				DsonArray array=(DsonArray)value;
				int field=(int)search_object.getCurrentPath();
				value=array.find(field);
			}
		}
		return value;
	}
	
}
