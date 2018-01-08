package custom_http;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CustomHttpServletResponseWrapper extends HttpServletResponseWrapper 
{
	private final CharArrayWriter charArrayWriter = new CharArrayWriter();
	
	public CustomHttpServletResponseWrapper(HttpServletResponse response) 
	{
		super(response);
	}

	@Override
	public PrintWriter getWriter() throws IOException 
	{
		return new PrintWriter(charArrayWriter);
	}
	
	public String toString()
	{
		return charArrayWriter.toString();
	}
}
