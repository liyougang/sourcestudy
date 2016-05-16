package ex07.pyrmont.bootstrap;

import java.io.IOException;

import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Loader;
import org.apache.catalina.Mapper;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Valve;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.logger.FileLogger;

import ex06.pyrmont.core.HeaderLogerValve;
import ex06.pyrmont.core.SimpleContext;
import ex06.pyrmont.core.SimpleContextLifecycleListener;
import ex06.pyrmont.core.SimpleContextMapper;
import ex06.pyrmont.core.SimpleLoader;
import ex06.pyrmont.core.SimpleWrapper;
import ex07.pyrmont.core.ClientIPLoggerValve;

/** 
 * @author:gang.li
 * @date:2016年5月10日 上午10:35:55 
 * @version 1.0.0 
 */
public class BootStrap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HttpConnector httpConnector = new HttpConnector();
		Wrapper wrapper = new SimpleWrapper();
		wrapper.setServletClass("ModernServlet");
		wrapper.setName("Modern");
		
		Context context = new SimpleContext();
		context.addChild(wrapper);
		
		Valve valve1 = new HeaderLogerValve();
		Valve valve2 = new ClientIPLoggerValve();
		
		((Pipeline)wrapper).addValve(valve1);
		((Pipeline)wrapper).addValve(valve2);
		
		Mapper mapper = new SimpleContextMapper();
		mapper.setProtocol("http");
		LifecycleListener listener = new SimpleContextLifecycleListener();
		((Lifecycle) context).addLifecycleListener(listener);
		context.addMapper(mapper);
		Loader loader = new SimpleLoader();
		context.setLoader(loader);
		 // context.addServletMapping(pattern, name);
	    context.addServletMapping("/Modern", "Modern");
	    
	    // ------ add logger --------
	    System.setProperty("catalina.base", System.getProperty("user.dir"));
	    FileLogger logger = new FileLogger();
	    logger.setPrefix("FileLog_");
	    logger.setSuffix(".txt");
	    logger.setTimestamp(true);
	    logger.setDirectory("webroot");
	    context.setLogger(logger);

	    //---------------------------

	    
	    httpConnector.setContainer(context);
		try {
			httpConnector.initialize();
			((Lifecycle) httpConnector).start();
		    ((Lifecycle) context).start();

		    // make the application wait until we press a key.
		    System.in.read();
		    ((Lifecycle) context).stop();
		} catch (LifecycleException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
