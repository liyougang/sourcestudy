package ex10.pyrmont.core;

import org.apache.catalina.Authenticator;
import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Valve;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.deploy.SecurityConstraint;
/**
 * 
 * @author gang.li01
 * @date 2016-05-17 14:37
 * @version 1.0.0
 */
public class SimpleContextConfig implements LifecycleListener {
	private Context context;
    public void lifecycleEvent(LifecycleEvent event) {
        if (Lifecycle.START_EVENT.equals(event.getType())) {
        	context = (Context) event.getLifecycle();
        	authenticatorConfig();
        	context.setConfigured(true);
        }
    }
    
    private synchronized void authenticatorConfig(){
    //dose the context need a authenticator
	    SecurityConstraint constraints[] = context.findConstraints();
	    if(constraints == null || constraints.length <=0){
	    	return ;
	    }
	    
	    LoginConfig loginConfig = context.getLoginConfig();
	    if(loginConfig == null){
	    	loginConfig = new LoginConfig("NONE", null, null, null);
	    	context.setLoginConfig(loginConfig);
	    }
	    
	    //has authenticator been configured already
	    Pipeline pipeline = ((StandardContext)context).getPipeline();
	    if(pipeline != null){
	    	Valve basic = pipeline.getBasic();
	    	if(basic != null && basic instanceof Authenticator){
	    		return ;
	    	}
	    	Valve valves[] = pipeline.getValves();
	    	for(int i = 0 ;i<valves.length;i++){
	    		if(valves[i] instanceof Authenticator){
	    			return ;
	    		}
	    	}
	    }else{
	    	//no pipeline can not install authenticator
	    	return ;
	    }
	    
	    //has a realm bean configured for us to authenticate against
	    if(context.getRealm() == null){
	    	return ;
	    }
	    
	    //Identify the class name of Valve we should configure
	    String authenticatorName = "org.apache.catalina.authenticator.BasicAuthenticator";
	    // Instantiate and install an Authenticator of the requested class
	    Valve authenticator = null;
	    try {
			Class authenticatorClass = Class.forName(authenticatorName);
			authenticator = (Valve) authenticatorClass.newInstance();
			((StandardContext) context).addValve(authenticator);
			System.out.println("Added authenticator valve of Context");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	    
	    return ;
    }
  
  
  
}