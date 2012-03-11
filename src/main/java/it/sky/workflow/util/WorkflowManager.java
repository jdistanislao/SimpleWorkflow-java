package it.sky.workflow.util;

import it.sky.workflow.Workflow;
import it.sky.workflow.WorkflowObject;
import it.sky.workflow.exception.WorkflowException;
import it.sky.workflow.exception.WorkflowLogicException;

/**
 * @author Jacopo Di Stanislao
 * 
 * Base WorkflowManager interface
 *
 */
public interface WorkflowManager {
	
	/**
	 * Aggiunge un workflow al manager
	 * 
	 * @param workflow
	 */
	public void addWorkflow(Workflow workflow);
	
	/**
	 * Esegue un workflow sincrono e asincrono
	 * 
	 * @param workflowName
	 * @param wfObject
	 * @throws WorkflowLogicException
	 */
	public void runWorkflow(String workflowName, WorkflowObject wfObject) throws WorkflowException;

}
