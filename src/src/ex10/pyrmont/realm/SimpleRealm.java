package ex10.pyrmont.realm;

import java.beans.PropertyChangeListener;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.catalina.Container;
import org.apache.catalina.Realm;
import org.apache.catalina.realm.GenericPrincipal;

/** 
 * @author:gang.li
 * @date:2016年5月17日 下午5:47:45 
 * @version 1.0.0 
 */
public class SimpleRealm implements Realm{
	
	private Container container;
	private ArrayList users;
	public SimpleRealm(){
		createUserDateBase();
	}
	
	
	@Override
	public Container getContainer() {
		return this.container;
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
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Principal authenticate(String username, String credentials) {
		System.out.println("SimpleRealm.authenticate()");
		if(username == null || credentials == null){
			return null;
		}
		
		User user = getUser(username,credentials);
		if(user == null){
			return null;
		}
		
		return new GenericPrincipal(this, user.userName, user.password, user.getRoles());
	}

	@Override
	public Principal authenticate(String username, byte[] credentials) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Principal authenticate(String username, String digest, String nonce,
			String nc, String cnonce, String qop, String realm, String md5a2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Principal authenticate(X509Certificate[] certs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasRole(Principal principal, String role) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}
	
	private  User getUser(String userName ,String password){
		Iterator iterator = users.iterator();
		while(iterator.hasNext()){
			User user = (User)iterator.next();
			if(user.userName.equals(userName) && user.password.equals(password)){
				return user;
			}
		}
		return null;
	}
	
	private void createUserDateBase(){
		User userl = new User("ken", "blackcomb");
		userl.addRole("manager");
		userl.addRole("programmer");
		User user2 = new User("cindy", "bamboo"); 
		user2.addRole("programmer"); 
		users.add(userl); 
		users.add(user2);
	}
	
	class User{
		String userName;
		String password;
		ArrayList roles = new ArrayList();
		
		public User(String userName,String password){
			this.userName = userName;
			this.password = password;
		}
		
		public void addRole(String role){
			roles.add(role);
		}
		
		public List getRoles(){
			return this.roles;
		}
		
	}

}
	
