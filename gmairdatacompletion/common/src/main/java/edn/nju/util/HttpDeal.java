package edn.nju.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.HttpClient;

import java.net.URI;

/**
 * @author: Bright Chan
 * @date: 2020/3/3 20:19
 * @description: TODO
 */
public class HttpDeal {
    public static String getResponse(String url) {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet get = new HttpGet(new URI(url));
            HttpResponse response = httpClient.execute(get);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
