package Com.SeleniumAutomation;
import java.net.HttpURLConnection;
import java.net.URL;



public class RestAPI extends DriverScript {
	
	
	  public static Boolean callApi(String Parameers) throws Exception{
		  Boolean status = true;
		  
		  try {
		
		  String []arguments = splitfunction(Parameers, "->");
		  String [] args = splitfunction(arguments[2], ",");
		  String url = arguments[1];
		  String method = args[0];
		  String value = args[1];
		  URL obj = new URL(url);
		  HttpURLConnection con = (HttpURLConnection)obj.openConnection();
		  con.setRequestMethod(method);
		  int responseCode = con.getResponseCode();
		  if(responseCode==Integer.parseInt(value)) {
			  System.out.println("ResponseCode matched with the given value: "+responseCode);
		  } else {
			  System.out.println("ResponseCode matched did not match with the given value: "+responseCode);
			  status = false;
		  }
		  } catch(Exception e) {
			  System.out.println(e.getMessage());
			  status = false;
			  
		  }
		  
		  return status;
		}
	 
		

}


