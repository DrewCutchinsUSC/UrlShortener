package csci310.urlshortener;

public interface UrlStore {
	// Returning generated code that can be used to access the url later
	String storeUrl(String url);

	String getUrl(String code);
}
