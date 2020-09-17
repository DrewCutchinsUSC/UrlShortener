package csci310.urlshortener;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class UrlServlet extends HttpServlet{
	private final Gson gson;
	private final UrlStore urlStore;
	
	public UrlServlet() {
		gson = new Gson();
		urlStore = new DatabaseUrlStore();
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		String requestBody;
		
		try {
			requestBody = req.getReader().lines().reduce("", (a, b) -> a + b);
		} catch (IOException e) {
			
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		UrlPost urlPost = gson.fromJson(requestBody, UrlPost.class);
		
		String code = urlStore.storeUrl(urlPost.getUrl());
		
		UrlPostResponse urlPostResponse = new UrlPostResponse(code);
		
		String responseBody = gson.toJson(urlPostResponse);
		
		try {
			PrintWriter pw = res.getWriter();
			pw.write(responseBody);
			pw.flush();
		} catch (IOException e) {
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
	}
}
