package ex06.pyrmont.core;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.HttpResponse;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;

/** 
 * @author:gang.li
 * @date:2016年4月22日 上午10:47:30 
 * @version 1.0.0 
 */
public class SimpleWrapperValve implements Valve ,Contained{
	private Container container = null;
	@Override
	public Container getContainer() {
		// TODO Auto-generated method stub
		return this.container;
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
		
		SimpleWrapper wrapper = (SimpleWrapper) getContainer();
		Servlet servlet = null;
		
		ServletRequest req = request.getRequest();
		ServletResponse resp = response.getResponse();
		
		HttpServletRequest hreq = null;
		HttpServletResponse hresp = null;
		
		if(req instanceof HttpServletRequest && resp instanceof HttpServletResponse){
			hreq = (HttpServletRequest) req;
			hresp = (HttpServletResponse) resp;
			
		}
		try{
			servlet = wrapper.allocate();
			if(hreq !=null && hresp !=null){
				servlet.service(hreq, hresp);
			}
			else{
				servlet.service(req, resp);
			}
		}
		catch(ServletException e ){
			System.out.print(e.getMessage());
		}
		
	}


	

}
