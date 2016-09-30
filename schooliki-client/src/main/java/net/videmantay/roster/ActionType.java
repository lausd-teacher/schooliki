package net.videmantay.roster;

public enum ActionType{SINGLE("Single"), MULTI("Multi"), WHOLE("Whole");
	final String type;           
	ActionType(String type){
		this.type = type;
	};
	@Override
	public String toString(){
		return type;
	}
	           }
