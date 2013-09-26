package com.robotbreakfast.atlas;


import java.lang.reflect.Array;
import java.util.Arrays;


public class Helpers {
	
	public static class print{
		
		public static String printArray(Object aObject) {
		    
			if (aObject.getClass().isArray()) {
		        if (aObject instanceof Object[]) // can we cast to Object[]
		            return Arrays.toString((Object[]) aObject);
		        else {  // we can't cast to Object[] - case of primitive arrays
		            int length = Array.getLength(aObject);
		            Object[] objArr = new Object[length];
		            for (int i=0; i<length; i++)
		                objArr[i] =  Array.get(aObject, i);
		            return Arrays.toString(objArr);
		        }
		      
		    }
			return "not array?";
		} 
	}
}
