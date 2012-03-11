package it.sky.workflow.impl;

import it.sky.workflow.State;
import it.sky.workflow.WorkflowObject;

import java.util.Map;

/**
 * @author Jacopo Di Stanislao
 *
 * Implementazione base di WorkflowObject
 */
public class GenericWorkflowObject implements WorkflowObject {

	private static final long serialVersionUID = 1L;
	
	private final Object payload;
	private final WorkflowHeaders headers;
	
	public GenericWorkflowObject(Object payload) {
		this(payload, null);
	}
	
	public GenericWorkflowObject(Object payload, Map<String, Object> headers) {
		this.payload = payload;
		if(headers == null) {
			this.headers = new WorkflowHeaders();			
		} else {			
			this.headers = new WorkflowHeaders(headers);
		}
	}

	public Object getPayload() {
		return this.payload;
	}

	public WorkflowHeaders getHeaders() {
		return this.headers;
	}

	public void beforeState(State state, Object payload) {
		// Non fa nulla
	}

	public void afterState(State state, Object payload) {
		// Non fa nulla
	}

}
