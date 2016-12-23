package com.client.utils;

public enum DisEnum {

	HTTP("HTTP"),DUBBO("DUBBO"),REDIS("REDIS");
	
	private String _type;
	
	DisEnum(String type){
		this._type = type;
	}
	
	public String TYPE(){
		return _type;
	}
}
