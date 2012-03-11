package it.sky.workflow;

import it.sky.workflow.exception.WorkflowLogicException;

/**
 * @author Jacopo Di Stanislao
 * 
 * Base State interface
 *
 */
public interface State {
	
	/**
	 * Nome logico dello state
	 * 
	 * @return Nome dello state
	 */
	public String getName();
	
	/**
	 * Esegue la logica associata allo State
	 * 
	 * @param wfObject
	 * @throws WorkflowLogicException
	 */
	public void execute(WorkflowObject wfObject) throws WorkflowLogicException;
	
	/**
	 * Aggiunge una transizione allo State
	 * 
	 * @param transition
	 */
	public void addTransition(Transition transition);
	
	/**
	 * Risolve una transizione a seguito dell'invocazione al metodo execute() associato ad essa.
	 *  
	 * @param wfObject
	 * @return Dispatcher dispatcher associato alla transizione trovata
	 */
	public Dispatcher resolveTransition(WorkflowObject wfObject);

}
