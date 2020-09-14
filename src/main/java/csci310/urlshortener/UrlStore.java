package csci310.urlshortener;

public interface UrlStore {
	String storeUrl(String url);
	
	String getUrl(String code);
}
