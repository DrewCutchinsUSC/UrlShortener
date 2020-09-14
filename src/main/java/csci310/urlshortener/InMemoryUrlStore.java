package csci310.urlshortener;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUrlStore implements UrlStore {
	private final static InMemoryUrlStore instance = new InMemoryUrlStore();
	
	public static InMemoryUrlStore getInstance() {
		return instance;
	}
	
	private static final int CODE_RADIX = 36;
	
	private List<String> urls;
	
	private InMemoryUrlStore() {
		urls = new ArrayList<String>();
	}

	@Override
	public String storeUrl(String url) {
		int urlNumber = urls.size();
		String urlCode = Integer.toString(urlNumber, CODE_RADIX);
		
		urls.add(url);
		
		return urlCode;
	}

	@Override
	public String getUrl(String code) {
		try {
			int urlNumber = Integer.parseInt(code, CODE_RADIX);
			
			return urls.get(urlNumber);
		}
		catch(NumberFormatException nfe){
			return null;
		}
		catch(IndexOutOfBoundsException iobe) {
			return null;
		}
	}

}
