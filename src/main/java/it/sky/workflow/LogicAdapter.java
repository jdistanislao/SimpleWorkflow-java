package it.sky.workflow;

import it.sky.workflow.exception.WorkflowLogicException;

/**
 * @author Jacopo Di Stanislao
 * 
 * Base LogicAdapter interface
 *
 */
public interface LogicAdapter {

	/**
	 * Esegue la logica
	 * 
	 * @param wfObject
	 * @throws WorkflowLogicException
	 */
	public void execute(WorkflowObject wfObject) throws WorkflowLogicException;
}
