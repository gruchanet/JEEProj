package com.jee.web.filter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class SiteHitCounter implements Filter {

	private int hitCounter;

	public void init(FilterConfig config) throws ServletException {
		hitCounter = 0;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		readFromFile();
		hitCounter++;
		writeToFile();

		chain.doFilter(request, response);
	}

	public void destroy() {
		// could write hitCounter value to database...
	}
	
	private void writeToFile() {
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter("counter.txt", "UTF-8");
			
			writer.println(hitCounter);
		} catch (Exception e) {}
		
		writer.close();
	}
	
	private void readFromFile() {
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader("counter.txt"));
			
			hitCounter = Integer.parseInt(reader.readLine().toString());
			
			reader.close();
		} catch (Exception e) {}
	}
}
