package it.sky.workflow.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class WorkflowHeaders extends HashMap<String, Object> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final String NEXT_STATE = "NEXT_STATE";

	public WorkflowHeaders() {
		super();
	}
	
	public WorkflowHeaders(int arg0, float arg1) {
		super(arg0, arg1);
	}

	public WorkflowHeaders(int arg0) {
		super(arg0);
	}
	
	public WorkflowHeaders(Map<? extends String, ? extends Object> arg0) {
		super(arg0);
	}

}
