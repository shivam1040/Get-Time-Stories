package com.sts;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Controller {
	
	
	@GetMapping("/getTimeStories")
	public List<Map<String, String>> timeStories() {
			List<Map<String, String>> resList=new ArrayList<Map<String, String>>();
				try {
			HttpClient client=HttpClient.newHttpClient();
			HttpRequest request=HttpRequest.newBuilder().uri(URI.create("https://time.com")).GET().build();
			HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());
			String temp=response.body().substring(response.body().indexOf("<ul class=\"most-popular-feed__item-container\">"), response.body().indexOf("<div class=\"most-popular-feed-ad\">"));
			Pattern p = Pattern.compile(Pattern.quote("<a href=\"") + "(.*?)" + Pattern.quote("\">"));
			Matcher m = p.matcher(temp);
			Pattern n = Pattern.compile(Pattern.quote("<h3 class=\"most-popular-feed__item-headline\">") + "(.*?)" + Pattern.quote("</h3>"));
			Matcher o = n.matcher(temp);
			while (m.find() && o.find()) {
			  Map<String, String> res=new LinkedHashMap<String, String>();
			  res.put("title", o.group(1));
			  res.put("link", "http://time.com"+m.group(1));
			  resList.add(res);
			}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resList;
	}
}