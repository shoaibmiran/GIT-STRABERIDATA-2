package com.tap.connection;

import org.json.JSONException;
import org.junit.Test;

import com.tap.connection.RestClient;
public class JerseyClientLiveTest {
	  
    public static final int HTTP_CREATED = 201;
    private RestClient client = new RestClient();
 
    @Test
    public void givenCorrectObject_whenCorrectJsonRequest_thenResponseCodeCreated() throws JSONException {
    	/*RestClient rc=new RestClient();
    	//JSONObject itemdefault1= new JSONObject();
    	JSONArray j= new JSONArray();
        JSONObject itemdefault= new JSONObject();
        JSONObject itemdefault1= new JSONObject();
        JSONObject content= new JSONObject();

        itemdefault.put("company", "Strawberi");
        j.put(itemdefault);
    	HashMap a = new HashMap();
    	a.put("company", "Strawbery");
    	itemdefault1.put("item_default", j);
    	System.out.println("a---"+ itemdefault1);
    	ArrayList<Item_Defaults> itemd=new ArrayList<Item_Defaults>();
    	Item_Defaults id=new Item_Defaults();
    	id.setCompany("Strawberi");
    	itemd.add(id);
        Item item = new Item("New Item","New Item","Nos","Sikkim",itemd);
 
        Response response = client.createJsonItem(item);
 
        assertEquals(response.getStatus(), HTTP_CREATED);*/
    	System.out.println(client);
    }
}
