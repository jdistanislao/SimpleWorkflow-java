package it.sky.workflow;

import it.sky.workflow.exception.WorkflowAsyncException;

/**
 * @author Jacopo Di Stanislao
 * 
 * Dispatcher asincrono
 *
 */
public interface AsyncDispatcher extends Dispatcher {
	
	/**
	 * Esegue il dispatch di wfObject verso destination e torna True e False per far continuare o meno il workflow.
	 * 
	 * @param destination
	 * @param wfObject
	 * @return true se il workflow deve continuare, false altrimenti
	 */
	public boolean dispatch(String destination, WorkflowObject wfObject) throws WorkflowAsyncException;
	
	/**
	 * Imposta la destinazione
	 * @param destination
	 */
	public void setDestination(String destination);

}
