/*
 * Name: Craig Dominic Dunbar
 * Company: Cyber Legacy Systems LLC
 * Location: United States of America, Metuchen, New Jersey,08840
 * Date: 07/09/2018
 */

package format;

public class JsonFormat 
{
	// Method formate a json string
	public char[] formatJson(String json_string)
	{
		char ch[]=new char[0];
		try 
		{
			json_string=this.removeChar(json_string, '\r');
			json_string=this.removeChar(json_string, '\n');
			ch=this.toChar(json_string);
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
		return ch;
	}
	
	// Method removes the brackets
	public String removeChar(String data,char c)
	{
		char ch;
		String element="";
		for(int i=0;i<data.length();i++)
		{
			ch=data.charAt(i);
			if(c!=ch)
			{
				element+=ch;
			}
		}
		return element;			
	}
	
	// Method return the char array of a string
	public char[] toChar(String data)
	{
		char ch[]=new char[data.length()];
		try
		{
			Thread t1=new Thread(new Runnable() {
				public void run() 
				{
					for(int i=0;i<data.length()/2;i++)
					{
						ch[i]=data.charAt(i);
					}
				}
				
			});
			Thread t2=new Thread(new Runnable() {
				public void run() 
				{
					for(int i=data.length()/2;i<data.length();i++)
					{
						ch[i]=data.charAt(i);
					}
				}
				
			});
			t1.start();
			t2.start();
			t1.join();
			t2.join();
		}
		catch(Exception e)
		{
			
		}
		return ch;
	}

}
