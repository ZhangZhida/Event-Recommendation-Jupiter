package rpcHandler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;


public class RpcHelper {
	public static void writeJSONArray(HttpServletResponse response, JSONArray array) throws IOException{
		response.setContentType("application/json"); // content type
		response.setHeader("Access-Control-Allow-Origin", "*"); // set access permission origin list
		PrintWriter writer = response.getWriter();
		
		writer.print(array);
		writer.close();
	}
	
	public static void writeJSONObject(HttpServletResponse response, JSONObject obj) throws IOException{
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Origin", "*"); // set access permission origin list, "*" means no limit
		PrintWriter writer = response.getWriter();
		
		writer.print(obj);
		writer.close();
	}
}
