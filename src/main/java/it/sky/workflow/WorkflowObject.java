package it.sky.workflow;

import it.sky.workflow.impl.WorkflowHeaders;

import java.io.Serializable;

/**
 * @author Jacopo Di Stanislao
 * 
 * WorkflowObject interface
 *
 */
public interface WorkflowObject extends Serializable {
	
	/**
	 * Ritorna glie headers del messaggio nel workflow
	 * @return WorkflowHeaders
	 */
	public WorkflowHeaders getHeaders();
	
	/**
	 * Ritorna l'oggetto 'trasportato' dal workflow
	 * @return Object
	 */
	public Object getPayload();
	
	/**
	 * Metodo chiamato prima dell'esecuzione dello stato
	 * @param State
	 */
	public void beforeState(State state, Object payload);
	
	/**
	 * Metodo chiamato dopo l'esecuzione dello stato
	 * @param State
	 */
	public void afterState(State state, Object payload);

}
