# dsonparser
///////////////////////////////////////
// 	What is Dsonparser?
////////////////////////////////////////

Dsonparser is a Json parser for java that converts Json strings into data types that Java programs can use.

////////////////////////////////////
//	Dsonparser Structure
///////////////////////////////////

DSON => DSON_OBJECT | DSON_ARRAY
DSON_OBJECT => DSON_LIST | DSON
DSON_ARRAY => DSON_LIST | DSON
DSON_LIST => DSON_ELEMENT(S)
DSON_ELEMENT => FIELD | VALUE
FIELD => STRING
VALUE => OBJECT

////////////////////////////////////////
	DsonWriter Structure
////////////////////////////////////////

DSON_OBJECT => DSON_ELEMENT(S) | DSON(S)
DSON_ARRAY => DSON_ELEMENT(S) | DSON(S)
DSON => DSON_OBECT | DSON_ARRAY
DSON_ELEMENT=> FIELD | VALUE
FIELD => STRING
VALUE => OBJECT

////////////////////////////////////
//	How To Use Dsonparser
///////////////////////////////////

String Json_String=”{ “name”: “John”, “age”: 32 , “address”: { “country”: “USA”, “city”: “New Jersey”, “street”: “Barkley”},  
              “contact”:{“cell”:[ {“personal”:“732-555-4567”,“work”:“732-123-8968”}], 
              “landline”:[{“home”:“732-567-2372”, “office”: “732-899-9010”}]}}”;
              
Dsonparser paser=new Dsonparser(Json_String);

To get entire Json object the best practice is

Dson dson=parser.getRoot();
String type=dson.getType();
if (type.isEqualsIgnoreCase(“DSON_OBJECT”))
{
	DsonObject dsonobj=dson.getObject();
}
else if (type.isEqualsIgnoreCase(“DSON_ARRAY”))
{
	DsonArray dsonarr=dson.getArray();
}

///////////////////////////////////////////////////////////////
// Searching for the Data in the Dsonpaser Structure
//////////////////////////////////////////////////////////////

To get a specific nested object like the work cell contact there are multiple ways.

1)	Using find("seach query string") method (Recommended)

	Note: the find method in the Dsonparser or the DsonObect or DsonArray classes it returns an object 
	that can be of type instance of DsonObject or DsonArray or DsonElement use instantceof and casting operator 
	to change to that specific type will give you access to that class public functions.
	
	Also: A seach Query String is the name of the fields separated by a dot ('.') using square backets with a number 
	provide the index of where to access information in a DsonArray [1].

    	Object obj=parser.find(“contact.cell.[0].work”);

	obj  will be a DsonElement that is returned
	to make sure that obj is of type DsonElement use the instanceof 

	if(obj instanceof DsonElement)
	{
		//Use casting to change the object to type DsonElement
		DsonElement ele=(DsonElement)obj; 

		//Accessing the value as well can be of primitive data type 
		// Use casting to change to such data type to work within program.
		Object value=ele.getValue();
		String field=ele.getField();
		if(value instanceof String)
		{
			String number=(String)value;
			System.out.println(field+” “+number);
		}
	}
  
2)	Achieving the above result it can be done as follows but not recommended.
	You have to follow the rules of the Dsonparser structure and also follow the
	correct nesting of the Json String.

	DsonElement ele=parser.getRoot().getObject().getDson(1).getObject().getDson(0).getArray().getDson(0).getObject().getElement(1);	

	if(ele != null)
	{
		String field=ele.getField();
		Object value=ele.getValue();
		if(value instanceof String)
		{
			String number=(String)value;
			System.out.println(field+” “+number);
		}
	}

///////////////////////////////////////////////////////
//	Other functionality of the DsonParser
///////////////////////////////////////////////////////

Example 1:
Let's say we have a Json String structure like this and you want to find the total salarys

String Json_String= "{ "employees":[{"name":"Hanna Jones", "salary": 42845.65}, {"name":"John Smith", "salary": 49845.65}, {"name":"Mary Jane", "salary": 42845.65}] }";

DsonParser parser=new DsonParser(Json_String);
Object obj=parser.get("employees");
if(obj instanceof DsonArray)
{
	DsonArray array=(DsonArray)obj;
	double total=0;
	for(int i=0; i<array.getDsonSize(); i++)
	{
		DsonObject employee=array.getDson(i).getObject();
		DsonElement element=employee.find("salary");
		total+=(double)element.getValue();
	}
	system.out.println("Total Salaries: "+total);
}

.........................................................................................

Example 2:
Let's say we have a Json String structure like this and you want to output all its elements

String Json_String= "{ "name": "Sam Adams", "age":32, "address": "231 Center ave": "state": "NJ" "city": "Edison"}";
DsonParser parser=new DsonParser(Json_String);
DsonObject object=parser.getRoot().getObject();
if(object != null)
{
	for(int i=0; i<object.getElementSize(); i++)
	{
		DsonElement ele=object.getElement(i);
		System.out.println(ele.getField()+":"+ele.getValue());
	}
}

//or you can get the list of elements
DsonList list=object.getList();
for(int i=0; i<list.getSize(); i++)
{
	DsonElement ele=object.getElement(i);
	// or
	String field=list.getField(i);
	Object value=list.getValue(i);
	
	System.out.println(ele.getField()+":"+ele.getValue());
	System.out.println(field+":"+value);
}

////////////////////////////////////////////////////
//	What is a DsonWriter? 
///////////////////////////////////////////////////

A DsonWriter allows the user to take Java primitive data and convert it into a Json String.
The DsonWriter takes either a DsonObject or DsonArray and return a Json_String that web browsers can understand.

Example:
DsonObject root=new DsonObject();
DsonObject object=new DsonObject();
DsonArray array=new DsonArray();
DsonElement element=new DsonElement(“id”, “123456789”);

Note: In adding an 2D Array of Object[rows][columns=max=2] into a DsonObject type the 
rows can be any size but the columns limit must be 2. For each row the first columns 
much contain a string type for the field name and the second column is the value assigned
to that associated field.

Object inputs[][]=new Object[4][2];
Inputs[0][0]=”name”;
Inputs[0][1]=”Craig Dunbar”;
Inputs[1][0]=”age”;
Inputs[1][1]=32;
Inputs[2][0]=”isStudent”;
Inputs[2][1]=true;
Inputs[3][0]=”tuition”;
Inputs[3][1]=136235.92;

DsonElement element2=new DsonElement(“JAVA”)

Note: In adding an 1D Array of Object[rows] into a DsonArray type the 
rows can be any size but just contain values that are of Java primitive data
type.

Object obj[]=new Object[7];
obj[0]=”JavaScript”;
obj[1]=”Python”;
obj[2]=”HTML”;
obj[3]=”VueJs”;
obj[4]=”SASS”;
obj[5]=”CSS”;
obj[6]=”MongoDB”;

array.appen(element2);
array.append(obj);
object.append(inputs);
object.append(“languages”, array);
root.append(element)
root.append(“info”, object);
DsonWriter writer=new DsonWriter();
String Json_String=writer.buildDsonString(root);
