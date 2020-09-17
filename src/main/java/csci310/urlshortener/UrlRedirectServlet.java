package csci310.urlshortener;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UrlRedirectServlet extends HttpServlet {
	private UrlStore urlStore;
	
	public UrlRedirectServlet() {
		urlStore = new DatabaseUrlStore();
	}
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		String[] urlParts = req.getRequestURL().toString().split("/");
		
		String code = urlParts[urlParts.length - 1];
		
		String url = urlStore.getUrl(code);
		
		try {
			res.sendRedirect(url);
		} catch (IOException e) {
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
