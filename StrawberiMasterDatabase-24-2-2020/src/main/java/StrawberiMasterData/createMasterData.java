package StrawberiMasterData;

//import com.sun.org.apache.bcel.internal.generic.AALOAD;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
public class createMasterData {

	//private static final String methodName = "POST";
    private static DefaultHttpClient httpClient = null;
   // private static String api_url = "http://localhost:8000/api/resource/Sales%20Order"; 
    //private static String api_url ="http://192.168.0.109:8000/api/resource/Customer"; 
    //private static String api_url ="http://192.168.0.109:8000/api/resource/Address"; 
    private static final String login_api_url = "http://localhost:8000/api/method/login?usr=administrator&pwd=password";

    public boolean login_authentication() {

        boolean returnstatus = false;

        String line = "";

        try {

            httpClient = new DefaultHttpClient();

            HttpPost postRequest = new HttpPost(login_api_url);

            HttpResponse response = httpClient.execute(postRequest);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();

            while ((line = rd.readLine()) != null) {

                result.append(line);

                returnstatus = true;
            }

            System.out.println(result);

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return returnstatus;
        
    }
    public static void main( String[] args )
    {
    	createMasterData cm=new createMasterData();
    	cm.login_authentication();
        System.out.println( "Hello World!" );
    }
	
}
    
