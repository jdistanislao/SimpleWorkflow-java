package it.sky.workflow.impl;

import it.sky.workflow.CloneablePayload;
import it.sky.workflow.State;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HistoricalWorkflowObject extends GenericWorkflowObject {

	private static final long serialVersionUID = 1L;
	
	private final List<HistoricalObject> history;
	
	private HistoricalObject historyObj;

	public HistoricalWorkflowObject(Object payload) {
		super(payload);
		this.history = new LinkedList<HistoricalObject>();
	}

	public HistoricalWorkflowObject(Object payload, Map<String, Object> headers) {
		super(payload, headers);
		this.history = new LinkedList<HistoricalObject>();
	}
	
	@Override
	public void beforeState(State state, Object payload) {
		historyObj = new HistoricalObject();
		historyObj.millisFrom = System.currentTimeMillis();
		historyObj.stateName = state.getName();
	}

	@Override
	public void afterState(State state, Object payload) {
		try {
			historyObj.millisTo = System.currentTimeMillis();
			historyObj.elaspedTime = historyObj.millisTo - historyObj.millisFrom;
			if(payload instanceof CloneablePayload) {	
				historyObj.payload = ((CloneablePayload)payload).clone();
			}
			history.add(historyObj);
		} catch (CloneNotSupportedException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	public List<HistoricalObject> getHistory() {
		return history;
	}

}
