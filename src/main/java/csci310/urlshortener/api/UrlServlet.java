package csci310.urlshortener.api;

import javax.servlet.http.*;

import com.google.gson.Gson;

import csci310.urlshortener.DbUrlStore;
import csci310.urlshortener.InMemoryUrlStore;
import csci310.urlshortener.UrlStore;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;


public class UrlServlet extends HttpServlet{
	private final Gson gson;
	private final UrlStore urlStore;
	
	public UrlServlet() {
		gson = new Gson();
		urlStore = new DbUrlStore();
	}
	
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		String body;
		try {
			body = req.getReader().lines().reduce("", (a, b) -> a + b);
		} catch (IOException e) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		UrlPost urlPost = gson.fromJson(body, UrlPost.class);
		
		String endpoint = urlStore.storeUrl(urlPost.url);
		
		UrlResponse response = new UrlResponse(endpoint);
		
		PrintWriter responseWriter;
		try {
			responseWriter = res.getWriter();
			responseWriter.write(gson.toJson(response));
			responseWriter.flush();
		} catch (IOException e) {
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
			return;
		}
	}
	
	private class UrlPost{
		private String url;
		
		public UrlPost(String url) {
			this.url = url;
		}
	}
	
	private class UrlResponse{
		private String endpoint;
		
		public UrlResponse(String endpoint) {
			this.endpoint = endpoint;
		}
	}
}
