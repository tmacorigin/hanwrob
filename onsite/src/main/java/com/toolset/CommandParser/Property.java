package com.toolset.CommandParser;


public class Property 
{
	/**
	 * 
	 */
	String PropertyName;
	Object Context;
	
	public Property()
	{
		
	}
	public Property( String P_Name , Object P_Cotext )
	{
		PropertyName = P_Name;
		Context = P_Cotext;
	}
	public void SetProperytName(String Name )
	{
		PropertyName = Name;
	}
	public String GetPropertyName()
	{
		return PropertyName;		
	}
	
	public void SetPropertyContext(String Text )
	{
		Context = Text;
		
	}
	
	public Object GetPropertyContext()
	{
		return Context;
	}
	
}

