package it.sky.workflow.impl;

import java.io.Serializable;

public class HistoricalObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public Object payload;
	public String stateName;
	public Long millisFrom;
	public Long millisTo;
	public Long elaspedTime;


}
