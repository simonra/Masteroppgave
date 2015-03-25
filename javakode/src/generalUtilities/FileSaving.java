package generalUtilities;

import graph.FloydWarshall;
import graph.Graph;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FileSaving {
	
	static Gson gson;
	static GsonBuilder gsonBuilder = new GsonBuilder();
	static TimeZone timeZone = TimeZone.getTimeZone("UTC");
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
	public static void writeFloydWarshall(){
		String contentToBeWritten = getGson().toJson(new FloydWarshall());
		try {
			PrintWriter writer = new PrintWriter(buildFileName("FloydWarshall"), "UTF-8");
			writer.println(contentToBeWritten);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeGraph(){
		String contentToBeWritten = getGson().toJson(new Graph());
		try {
			PrintWriter writer = new PrintWriter(buildFileName("Graph"), "UTF-8");
			writer.println(contentToBeWritten);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeEntireRun(){
		dateFormat.setTimeZone(timeZone);
		String path = "./results/runs/"
				+ Graph.problemName + "/"
				+ dateFormat.format(new Date()) + "/";
		
		new File(path).mkdirs();
		
		try {
			PrintWriter writer = new PrintWriter(path + "Graph", "UTF-8");
			writer.println(getGson().toJson(new Graph()));
			writer.close();
			
			writer = new PrintWriter(path + "FloydWarshall", "UTF-8");
			writer.println(getGson().toJson(new FloydWarshall()));
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String buildFileName(String customPartOfName){
		dateFormat.setTimeZone(timeZone);
		
		String path = "./results/runs/" + Graph.problemName + "/";
		new File(path).mkdirs();
		String filename = "";
		filename += path +
				dateFormat.format(new Date())
				+ "_" + customPartOfName;
		
		
		return filename;
	}
	
	public static Gson getGson(){
		if(gson != null){
			return gson;
		}else{
			gsonBuilder.excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT);
			gson = gsonBuilder.create();
			return gson;
		}
	}
}
