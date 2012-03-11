package it.sky.workflow.impl.logicadapter;

import it.sky.workflow.LogicAdapter;
import it.sky.workflow.WorkflowObject;
import it.sky.workflow.exception.WorkflowLogicException;

/**
 * @author Jacopo Di Stanislao
 *
 * LogicAdapter che non esegue nessuna logica
 */
public class NullLogicAdapter implements LogicAdapter {

	public void execute(WorkflowObject wfObject) throws WorkflowLogicException {
		// Non fa nulla :)
	}

}
