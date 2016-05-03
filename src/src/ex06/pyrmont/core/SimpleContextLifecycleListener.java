package ex06.pyrmont.core;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

/** 
 * @author:gang.li
 * @date:2016年5月4日 下午3:33:39 
 * @version 1.0.0 
 */
public class SimpleContextLifecycleListener implements LifecycleListener {

	@Override
	public void lifecycleEvent(LifecycleEvent event) {
		
		Lifecycle lifecycle = event.getLifecycle();
		
		System.out.println("SimpleContextLifecycleListener's event"+event.getType().toString());
		
		if(Lifecycle.START_EVENT.equals(event.getType())){
			System.out.println("starting Context");
		}else if(Lifecycle.START_EVENT.equals(event.getType())){
			System.out.println("stoping Context");
		}
		
	}

}
