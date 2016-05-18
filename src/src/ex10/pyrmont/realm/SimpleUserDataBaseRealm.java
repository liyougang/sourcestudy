package ex10.pyrmont.realm;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.catalina.Group;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.apache.catalina.UserDatabase;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.realm.RealmBase;
import org.apache.catalina.users.MemoryUserDatabase;

/** 
 * @author:gang.li
 * @date:2016年5月18日 上午10:24:45 
 * @version 1.0.0 
 */
public class SimpleUserDataBaseRealm extends RealmBase{
	
	protected UserDatabase database = null;
	protected static final String name = "SimpleUserDatabaseRealm";
	protected String resourceName = "UserDatabase";
	
	public Principal authenticate(String userName ,String credentials){
		// Does a user with this username exist?
		User user = database.findUser(userName);
		if(user == null){
			return null;
		}
		
		// Do the credentials specified by the user match?
		boolean validated = false;
		if(hasMessageDigest()){
			// Hex hashes should be compared case-insensitive
			validated = (digest(credentials).equalsIgnoreCase(user.getPassword()));
		}
		else{
			validated = (digest(credentials).equals(user.getPassword()));
		}
		
		if(!validated){
			return null;
		}
		
		ArrayList combined = new ArrayList();
		Iterator roles = user.getRoles();
		while(roles.hasNext()){
			Role role = (Role)roles.next();
			String roleName = role.getName();
			if(!combined.contains(roleName)){
				combined.add(roleName);
			}
		}
		
		Iterator groups = user.getGroups();
		while(groups.hasNext()){
			Group group = (Group)groups.next();
			roles = group.getRoles();
			while(roles.hasNext()){
				Role role = (Role) roles.next();
				String roleName = role.getRolename();
				if(!combined.contains(roleName)){
					combined.add(roleName);
				}
						
			}
		}
		
		return new GenericPrincipal(this, userName, credentials, combined);
		
	}
	@Override
	protected String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	protected String getPassword(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Principal getPrincipal(String username) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void createDatebase(String path){
		database = new MemoryUserDatabase(name);
		((MemoryUserDatabase)database).setPathname(path);
		try {
			database.open();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
