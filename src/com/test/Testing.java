package com.test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class Testing {
	
	
	public static void main (String[] args) throws UnsupportedEncodingException {
		
		String s = "apps-stage.hds.com/hitachi-calculator/pdf.html?employees%3D54%26salary%3D12%26revenue%3D13%26email%3Dankit.rana%40nvish.com%26fname%3DAnkit%26lname%3DRana%26company%3DNvish%26job_title%3Dsoftware%20developer%26country%3DIndia";
		
		String url =	java.net.URLDecoder.decode(s, "UTF-8");
		System.out.println("! : "+url);
		if (url != null && url.contains("/") && url.contains("."))
			
			if(url.contains("="))
			{
			

				
				System.out.println("1 "+url.substring(url.lastIndexOf("=") + 1));
			
				
			}
			else{
			

				
				System.out.println("2 :"+url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".")));
			
				
			}
	}
		
	}

