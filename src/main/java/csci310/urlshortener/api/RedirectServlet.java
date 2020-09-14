package csci310.urlshortener.api;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import csci310.urlshortener.DbUrlStore;
import csci310.urlshortener.InMemoryUrlStore;
import csci310.urlshortener.UrlStore;

public class RedirectServlet extends HttpServlet{
	private final UrlStore urlStore;
	
	public RedirectServlet() {
		urlStore = new DbUrlStore();
	}
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		// get the last element of the url
		String[] urlParts = req.getRequestURL().toString().split("/");
		String code = urlParts[urlParts.length - 1];
		
		String redirect = urlStore.getUrl(code);
		
		try {
			res.sendRedirect(redirect);
		} catch (IOException e) {
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
