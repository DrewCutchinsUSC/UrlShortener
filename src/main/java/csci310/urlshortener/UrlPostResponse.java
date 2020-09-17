package csci310.urlshortener;

public class UrlPostResponse {
	private String code;
	
	public UrlPostResponse(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}
