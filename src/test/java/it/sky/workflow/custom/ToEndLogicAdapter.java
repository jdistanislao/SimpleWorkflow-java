package it.sky.workflow.custom;

import it.sky.workflow.LogicAdapter;
import it.sky.workflow.WorkflowObject;
import it.sky.workflow.exception.WorkflowLogicException;

public class ToEndLogicAdapter implements LogicAdapter {

	public void execute(WorkflowObject wfObject) throws WorkflowLogicException {
		((CustomPayload)wfObject.getPayload()).value = "END";
	}

}
