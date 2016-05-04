package ex06.pyrmont.core;

import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.naming.directory.DirContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;

import org.apache.catalina.Cluster;
import org.apache.catalina.Container;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.InstanceListener;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Loader;
import org.apache.catalina.Logger;
import org.apache.catalina.Manager;
import org.apache.catalina.Mapper;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Realm;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.Wrapper;
import org.apache.catalina.util.LifecycleSupport;

/**
 * 
 * @author gang.li
 * @date 2016-04-19 14:54,
 * @version v1.0.0
 */
public class SimpleWrapper implements Wrapper ,Pipeline ,Lifecycle{
	// the servlet instance
	private String name;
	private Servlet instance = null;
	private String servletClass =null;
	private Loader loader = null;
	protected Container parent = null;
	private SimplePipeline pipeline = new SimplePipeline(this);
	private LifecycleSupport lifecycle = new LifecycleSupport(this);
	private boolean started =false;
	public SimpleWrapper(){
		pipeline.setBasic(new SimpleWrapperValve());
	}
	
	  public Servlet allocate() throws ServletException {
		    // Load and initialize our instance if necessary
		    if (instance==null) {
		      try {
		        instance = loadServlet();
		      }
		      catch (ServletException e) {
		        throw e;
		      }
		      catch (Throwable e) {
		        throw new ServletException("Cannot allocate a servlet instance", e);
		      }
		    }
		    return instance;
		  }

		  private Servlet loadServlet() throws ServletException {
		    if (instance!=null)
		      return instance;

		    Servlet servlet = null;
		    String actualClass = servletClass;
		    if (actualClass == null) {
		      throw new ServletException("servlet class has not been specified");
		    }

		    Loader loader = getLoader();
		    // Acquire an instance of the class loader to be used
		    if (loader==null) {
		      throw new ServletException("No loader.");
		    }
		    ClassLoader classLoader = loader.getClassLoader();

		    // Load the specified servlet class from the appropriate class loader
		    Class classClass = null;
		    try {
		      if (classLoader!=null) {
		        classClass = classLoader.loadClass(actualClass);
		      }
		    }
		    catch (ClassNotFoundException e) {
		      throw new ServletException("Servlet class not found");
		    }
		    // Instantiate and initialize an instance of the servlet class itself
		    try {
		      servlet = (Servlet) classClass.newInstance();
		    }
		    catch (Throwable e) {
		      throw new ServletException("Failed to instantiate servlet");
		    }

		    // Call the initialization method of this servlet
		    try {
		      servlet.init(null);
		    }
		    catch (Throwable f) {
		      throw new ServletException("Failed initialize servlet.");
		    }
		    return servlet;
		  }
	
	public Loader getLoader() {
		if(loader !=null ){
			return this.loader;
		}
		else if(parent !=null){
			return parent.getLoader();
		}
		return null;
	}

	@Override
	public void setLoader(Loader loader) {
		this.loader = loader;
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Logger getLogger() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogger(Logger logger) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Manager getManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setManager(Manager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cluster getCluster() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCluster(Cluster cluster) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name ;
	}

	@Override
	public Container getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParent(Container container) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ClassLoader getParentClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParentClassLoader(ClassLoader parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Realm getRealm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRealm(Realm realm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DirContext getResources() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setResources(DirContext resources) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addChild(Container child) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addContainerListener(ContainerListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMapper(Mapper mapper) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Container findChild(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Container[] findChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContainerListener[] findContainerListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mapper findMapper(String protocol) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mapper[] findMappers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void invoke(Request request, Response response) throws IOException,
			ServletException {
		// TODO Auto-generated method stub
		pipeline.invoke(request, response);
	}

	@Override
	public Container map(Request request, boolean update) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeChild(Container child) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeContainerListener(ContainerListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeMapper(Mapper mapper) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getAvailable() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAvailable(long available) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getJspFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setJspFile(String jspFile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLoadOnStartup() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLoadOnStartup(int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRunAs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRunAs(String runAs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getServletClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setServletClass(String servletClass) {
		// TODO Auto-generated method stub
		this.servletClass = servletClass;
	}

	@Override
	public boolean isUnavailable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addInitParameter(String name, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addInstanceListener(InstanceListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSecurityReference(String name, String link) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deallocate(Servlet servlet) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String findInitParameter(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] findInitParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findSecurityReference(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] findSecurityReferences() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void load() throws ServletException {
		// TODO Auto-generated method stub
		instance = loadServlet();
	}

	@Override
	public void removeInitParameter(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeInstanceListener(InstanceListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeSecurityReference(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unavailable(UnavailableException unavailable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unload() throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Valve getBasic() {
		return pipeline.getBasic();
	}

	@Override
	public void setBasic(Valve valve) {
		pipeline.setBasic(valve);
	}

	@Override
	public synchronized void addValve(Valve valve) {
		// TODO Auto-generated method stub
		pipeline.addValve(valve);
	}

	@Override
	public Valve[] getValves() {
		// TODO Auto-generated method stub
		return pipeline.getValves();
	}

	@Override
	public void removeValve(Valve valve) {
		pipeline.removeValve(valve);
	}

	@Override
	public void addLifecycleListener(LifecycleListener listener) {
		lifecycle.addLifecycleListener(listener);
	}

	@Override
	public LifecycleListener[] findLifecycleListeners() {
		return lifecycle.findLifecycleListeners();
	}

	@Override
	public void removeLifecycleListener(LifecycleListener listener) {
		lifecycle.removeLifecycleListener(listener);
	}

	@Override
	public void start() throws LifecycleException {
		System.out.println("starting the wrapper :"+name);
		if(started){
			throw new LifecycleException("SimpleWrapper has started");
		}
		
		lifecycle.fireLifecycleEvent(Lifecycle.BEFORE_START_EVENT, null);
		started = true;
		
		try{
			//Start our subordinate components, if any
			if(loader != null && loader instanceof Lifecycle){
				((Lifecycle) loader).start();
			}
			
			// Start the Valves in our pipeline (including the basic),
		    // if any
			if(pipeline != null && pipeline instanceof Lifecycle){
				((Lifecycle) pipeline).start();
				
			}
			
			lifecycle.fireLifecycleEvent(Lifecycle.START_EVENT, null);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		lifecycle.fireLifecycleEvent(Lifecycle.AFTER_START_EVENT, null);
	}

	@Override
	public void stop() throws LifecycleException {
		System.out.println("stop the wrapper :"+name);
		if(!started){
			throw new LifecycleException("SimpleWrapper has been stoped");
		}
		try{
			instance.destroy();
		}
		catch(Throwable T){
			
		}
		
		lifecycle.fireLifecycleEvent(Lifecycle.BEFORE_START_EVENT, null);
		lifecycle.fireLifecycleEvent(Lifecycle.STOP_EVENT, null);
		started = false;
		try{
			// stop the Valves in our pipeline (including the basic),
		    // if any
			if(pipeline != null && pipeline instanceof Lifecycle){
				((Lifecycle) pipeline).stop();
				
			}
			
			
			//stop our subordinate components, if any
			if(loader != null && loader instanceof Lifecycle){
				((Lifecycle) loader).stop();
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		lifecycle.fireLifecycleEvent(Lifecycle.AFTER_STOP_EVENT, null);
	}
	
}
