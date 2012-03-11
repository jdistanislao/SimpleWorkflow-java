package it.sky.workflow.custom;

import it.sky.workflow.CloneablePayload;

public class CustomPayload implements CloneablePayload {

	public String value;
	
	public CustomPayload(String value) {
		this.value = value;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}


}
