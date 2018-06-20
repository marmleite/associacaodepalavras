package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class FormatHttp {
	
	static Gson 			_gson;
	static String 			line;
	static StringBuilder 	buffer;
	static BufferedReader 	reader;
	static String 			pathInfo;
	static Integer			id;
	static String[]			url;
	
	public static void asJson(HttpServletResponse response, Object obj) throws IOException{
		_gson = new Gson();
		response.setContentType("application/json");
		
		String 		res = _gson.toJson(obj);
		PrintWriter out = response.getWriter();
		  
		out.print(res);
		out.flush();

	}
	
	public static String asString(HttpServletRequest request) throws IOException{
		pathInfo = request.getPathInfo();

		if(pathInfo == null || pathInfo.equals("/")){

			buffer = new StringBuilder();
		    reader = request.getReader();
		    
		    while ((line = reader.readLine()) != null) {
		        buffer.append(line);
		    }

		}
		return  buffer.toString();
	}
	
	public static Integer getId(HttpServletRequest request){
		pathInfo 	= request.getPathInfo();
		id 			= null;
		url			= pathInfo.split("/");
		if(pathInfo != null && pathInfo.equals("/") || pathInfo.split("/").length != 2){
			id = Integer.parseInt(url[1]);
		}
		return id;
	}
}
