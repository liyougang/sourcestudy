package ex06.pyrmont.core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;

/** 
 * @author:gang.li
 * @date:2016年4月22日 下午2:01:46 
 * @version 1.0.0 
 */
public class ClientIPLoggerValve implements Valve,Contained {
	
	protected Container container = null;
	@Override
	public Container getContainer() {
		// TODO Auto-generated method stub
		return container;
	}

	@Override
	public void setContainer(Container container) {
		// TODO Auto-generated method stub
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
		System.out.println("Client Ip Logger Valve");
		
		ServletRequest sreq = request.getRequest();
		System.out.println("remote addr :"+sreq.getRemoteAddr());
		System.out.println("--------------------------");
	}

}
