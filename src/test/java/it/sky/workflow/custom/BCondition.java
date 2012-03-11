package it.sky.workflow.custom;

import it.sky.workflow.Condition;
import it.sky.workflow.WorkflowObject;

public class BCondition implements Condition {

	public boolean verify(WorkflowObject wfObject) {
		return "B".equalsIgnoreCase(((CustomPayload)wfObject.getPayload()).value);
	}

}
