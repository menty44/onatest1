/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test1;

/**
 *
 * @author fred
 */
import java.io.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
 
/**
 * This example demonstrates an alternative way to call a URL
 * using the Apache HttpClient HttpGet and HttpResponse
 * classes.
 * 
 * I copied the guts of this example from the Apache HttpClient
 * ClientConnectionRelease class, and decided to leave all the
 * try/catch/finally handling in the class. You don't have to catch
 * all the exceptions individually like this, I just left the code
 * as-is to demonstrate all the possible exceptions.
 * 
 * Apache HttpClient: <a href="http://hc.apache.org/httpclient-3.x/" title="http://hc.apache.org/httpclient-3.x/">http://hc.apache.org/httpclient-3.x/</a>
 *
*/
public class ApacheHttpRestClient2 {
 
  public final static void main(String[] args) {
     
    HttpClient httpClient = new DefaultHttpClient();
    try {
      // this ona api call returns results in a JSON format
      HttpGet httpGetRequest = new HttpGet("https://api.ona.io/api/v1/users");
 
      // Execute HTTP request
      HttpResponse httpResponse = httpClient.execute(httpGetRequest);
 
      System.out.println("------------------HTTP RESPONSE----------------------");
      System.out.println(httpResponse.getStatusLine());      
      System.out.println("------------------HTTP RESPONSE----------------------");
 
      // Get hold of the response entity
      HttpEntity entity = httpResponse.getEntity();
 
      // If the response does not enclose an entity, there is no need
      // to bother about connection release
      byte[] buffer = new byte[1024];
      if (entity != null) {
        InputStream inputStream = entity.getContent();
        try {
          int bytesRead = 0;
          BufferedInputStream bis = new BufferedInputStream(inputStream);
          while ((bytesRead = bis.read(buffer)) != -1) {
            String chunk = new String(buffer, 0, bytesRead);
            FileWriter file = new FileWriter("C:\\Users\\fred\\Desktop\\webicons\\output.txt");
            //file.write(chunk.toJSONString());
            file.write(chunk.toCharArray());
                file.flush();
                file.close();
                
                System.out.print(chunk);
            System.out.println(chunk);           
          }
        } catch (IOException ioException) {
          // In case of an IOException the connection will be released
          // back to the connection manager automatically
          ioException.printStackTrace();
        } catch (RuntimeException runtimeException) {
          // In case of an unexpected exception you may want to abort
          // the HTTP request in order to shut down the underlying
          // connection immediately.
          httpGetRequest.abort();
          runtimeException.printStackTrace();
        }
//          try {
//              FileWriter file = new FileWriter("C:\\Users\\fred\\Desktop\\webicons\\output.json");
//                file.write(bis.toJSONString());
//                file.flush();
//                file.close();
//
//                System.out.print(bis);
//          } catch (Exception e) {
//          }
        
        
        finally {
          // Closing the input stream will trigger connection release
          try {
            inputStream.close();
          } catch (Exception ignore) {
          }
        }
      }
    } catch (ClientProtocolException e) {
      // thrown by httpClient.execute(httpGetRequest)
      e.printStackTrace();
    } catch (IOException e) {
      // thrown by entity.getContent();
      e.printStackTrace();
    } finally {
      // When HttpClient instance is no longer needed,
      // shut down the connection manager to ensure
      // immediate deallocation of all system resources
      httpClient.getConnectionManager().shutdown();
    }
  }
}