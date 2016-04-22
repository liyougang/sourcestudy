package ex05.pyrmont.core;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;

/** 
 * @author:gang.li
 * @date:2016年4月22日 下午2:11:03 
 * @version 1.0.0 
 */
public class HeaderLogerValve implements Valve ,Contained{
	
	protected Container container;
	@Override
	public Container getContainer() {
		return container;
	}

	@Override
	public void setContainer(Container container) {
		this.container = container;
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void invoke(Request request, Response response, ValveContext context)
			throws IOException, ServletException {
		
		context.invokeNext(request, response);
		System.out.println("header logger valve");
		ServletRequest sreq = request.getRequest();
		if(sreq instanceof HttpServletRequest){
			HttpServletRequest hreq = (HttpServletRequest) sreq;
			Enumeration  headerNames = hreq.getHeaderNames();
			while(headerNames.hasMoreElements()){
				String headerName = headerNames.nextElement().toString();
				String headerValue = hreq.getHeader(headerName);
				System.out.println("name : value----"+headerName+":"+headerValue);
			}
		}
		else{
			System.out.println("Not an http req");
		}
	}

}
