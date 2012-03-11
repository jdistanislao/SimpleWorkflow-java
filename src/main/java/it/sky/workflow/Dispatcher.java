package it.sky.workflow;

import it.sky.workflow.exception.WorkflowException;

/**
 * @author Jacopo Di Stanislao
 * 
 * Base Dispatcher interface
 *
 */
public interface Dispatcher {
	
	/**
	 * Esegue il dispatch di wfObject e torna True e False per far continuare o meno il workflow.
	 * 
	 * @param wfObject
	 * @return true se il workflow deve continuare, false altrimenti
	 */
	public boolean dispatch(WorkflowObject wfObject) throws WorkflowException;

}
