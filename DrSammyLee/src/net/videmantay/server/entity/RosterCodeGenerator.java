package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Serialize;

@Entity
public class RosterCodeGenerator implements Serializable {

	public static String KEYGEN = "rosterCodeGenerator";
	
	@Id
	public String id = "rosterCodeGenerator";
	
	@Serialize
	Map<String, Key<RosterDetail>> availableCode = new HashMap<String, Key<RosterDetail>>();
	
	public String assignCode(){
		String key = null;
		if(availableCode.size() < 1 || !availableCode.containsValue(null)){
			generateCodes();
		}
		//pick any null value and  assign the key as code
		while(availableCode.entrySet().iterator().hasNext()){
			if(availableCode.entrySet().iterator().next().getValue() == null){
				 key = availableCode.entrySet().iterator().next().getKey();
				 break;
			}
		}
		
		return key;
	}
	
	public void relinquishCode(String key){
		availableCode.put(key, null);
		}
	
	private void generateCodes(){
		for(int i = 0 ; i < 100; i++){
			String key = RandomStringUtils.randomAlphanumeric(7);
			if(availableCode.containsKey(key)){
				continue;
			}
			availableCode.put(key, null);
		}
	}
	
}
