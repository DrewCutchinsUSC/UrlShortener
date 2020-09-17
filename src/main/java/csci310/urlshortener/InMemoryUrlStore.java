package csci310.urlshortener;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUrlStore implements UrlStore{
	private final List<String> urls;
	private static int RADIX = 36;
	
	private final static InMemoryUrlStore instance = new InMemoryUrlStore();
	
	public static InMemoryUrlStore getInstance() {
		return instance;
	}
	
	public InMemoryUrlStore() {
		urls = new ArrayList<String>();
	}
	
	@Override
	public String storeUrl(String url) {
		urls.add(url);
		
		// "11" -> "a", 36 -> "z"
		String urlCode = Integer.toString(urls.size() - 1, RADIX);
		
		return urlCode;
	}

	@Override
	public String getUrl(String code) {
		int index = Integer.parseInt(code, RADIX);
		
		return urls.get(index);
	}
}
