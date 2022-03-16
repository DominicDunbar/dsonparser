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
		DsonObject object=new DsonObject();
		DsonArray array=new DsonArray();
		DsonElement element=new DsonElement("id", "123456789");

		/*
		 * Note in appending a 2D array of objects the first column has to have the field name that is
		 * of type string and the second column is your data of any Java primitive data type. When it is 
		 * appended the fields will be associated with the value of the corresponding column.
		 */
		Object inputs[][]=new Object[4][2];
		Inputs[0][0]="name";
		Inputs[0][1]="Craig Dunbar";
		Inputs[1][0]="age";
		Inputs[1][1]=32;
		Inputs[2][0]="isStudent";
		Inputs[2][1]=true;
		Inputs[3][0]="tuition";
		Inputs[3][1]=136235.92;
		
		DsonElement element2=new DsonElement("JAVA");
		Object obj[]=new Object[7];
		obj[0]="JavaScript";
		obj[1]="Python";
		obj[2]="HTML";
		obj[3]="VueJs";
		obj[4]="SASS";
		obj[5]="CSS";
		obj[6]="MongoDB";

		array.appen(element2);
		array.append(obj);
		object.append(inputs);
		object.append("languages", array);
		root.append(element)
		root.append("info", object);
		DsonWriter writer=new DsonWriter();
		String Json_String=writer.buildDsonString(root);
		System.out.println(Json_String);
	}
}
