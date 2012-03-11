package it.sky.workflow.impl.condition;

import it.sky.workflow.Condition;
import it.sky.workflow.WorkflowObject;

/**
 * @author Jacopo Di Stanislao
 * 
 * Condition sempre verificata
 *
 */
public class AlwaysTrueCondition implements Condition {

	public boolean verify(WorkflowObject wfObject) {
		return true;
	}

}
