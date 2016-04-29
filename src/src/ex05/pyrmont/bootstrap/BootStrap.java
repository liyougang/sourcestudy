package ex05.pyrmont.bootstrap;

import java.io.IOException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.Loader;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Valve;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.http.HttpConnector;

import ex05.pyrmont.core.ClientIPLoggerValve;
import ex05.pyrmont.core.HeaderLogerValve;
import ex05.pyrmont.core.SimpleLoader;
import ex05.pyrmont.core.SimpleWrapper;

/** 
 * @author:gang.li
 * @date:2016年4月29日 上午10:50:55 
 * @version 1.0.0 
 */
public class BootStrap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HttpConnector httpConnector = new HttpConnector();
		Wrapper wrapper = new SimpleWrapper();
		wrapper.setServletClass("ModernServlet");
		Loader loader = new SimpleLoader();
		
		Valve valve1 = new HeaderLogerValve();
		Valve valve2 = new ClientIPLoggerValve();
		
		wrapper.setLoader(loader);
		((Pipeline)wrapper).addValve(valve1);
		((Pipeline)wrapper).addValve(valve2);
		
		httpConnector.setContainer(wrapper);
		try {
			httpConnector.initialize();
			httpConnector.start();
			System.in.read();
		} catch (LifecycleException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
