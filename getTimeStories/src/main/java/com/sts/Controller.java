package com.sts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Controller {
	
	@GetMapping("/getTimeStories")
	public List<Map<String, String>> timeStories() {
		List<Map<String, String>> resList=new ArrayList<Map<String, String>>();
		try {
			Elements document=Jsoup.connect("https://time.com/").get().select("body > main > section.homepage-section-v2.you-should-know > div > div > ul > li");
			for(int i=0; i<document.size(); i++) {
				Map<String, String> temp=new HashMap<String, String>();
				temp.put("title", document.select("a > div > div > h3").get(i).html());
				temp.put("link", "https://time.com"+document.select("a").get(i).attr("href"));
				resList.add(temp);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resList;
	}
}