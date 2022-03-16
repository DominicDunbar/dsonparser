package dsonparser;

import objects.DsonArray;
import objects.DsonElement;
import objects.DsonObject;
import parser.DsonParser;
import writer.DsonWriter;

public class DsonTest 
{
	public static void main(String[]args)
	{
		
		//Dson Parser Example
		String json_string="{"
							+"\"name\": \"John\", \"age\": 32 ,"
							+"\"address\": { \"country\": \"USA\", \"city\": \"New Jersey\", \"street\": \"Barkley\"},"
							+"\"contact\":{\"cell\":[ {\"personal\":\"732-555-4567\","
							+"\"work\":\"732-123-8968\"}]," 
							+"\"landline\":[{\"home\":\"732-567-2372\", \"office\":\"732-899-9010\"}]}}";
		
		DsonParser dson=new DsonParser(json_string);
		
		Object obj=dson.find("contact.cell.[0].work");

		if(obj instanceof DsonElement)
		{
			DsonElement ele=(DsonElement)obj;
			System.out.println("Using the Find Method: "+ ele.getField()+":"+ele.getValue());
		}
		//Alternative
		DsonElement directpath=dson.getRoot().getObject().getDson(1).getObject().getDson(0).getArray().getDson(0).getObject().getElement(1);
		System.out.println("Using the Direct Path approach: "+directpath.getField()+":"+directpath.getValue());
		// Dson Writer Example
		DsonObject root=new DsonObject();
		DsonObject duplicate=new DsonObject();
		DsonArray duplicate_value=new DsonArray();
		DsonElement element=new DsonElement("field1", "first input");
		/*
		 * Note in using the 2D array of objects the first column has to have the field name that is
		 * of type string the second column can be your data of any primitive data type
		 */
		Object input[][]=new Object[4][2];
		input[0][0]="field2";
		input[0][1]=3;
		input[1][0]="field3";
		input[1][1]=false;
		input[2][0]="field4";
		input[2][1]=3.25;
		input[3][0]="field5";
		input[3][1]="second input";
		
		root.append(element);
		root.append(input);
		duplicate.append(element);
		duplicate.append(input);
		duplicate_value.append(element.getValue());
		for(int i=0; i<input.length; i++)
		{
			duplicate_value.append(input[i][1]);
		}
		root.append("duplicate", duplicate);
		root.append("duplicate_value", duplicate_value);
		
		DsonWriter writer=new DsonWriter();
		json_string=writer.buildDsonString(root);
		System.out.println(json_string);
	}
}
