package it.sky.workflow.custom;

import it.sky.workflow.Condition;
import it.sky.workflow.WorkflowObject;

public class EndCondition implements Condition {

	public boolean verify(WorkflowObject wfObject) {
		return "END".equalsIgnoreCase(((CustomPayload)wfObject.getPayload()).value);
	}

}
