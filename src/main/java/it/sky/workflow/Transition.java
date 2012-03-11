package it.sky.workflow;

/**
 * @author Jacopo Di Stanislao
 * 
 * Base Transition interface
 *
 */
public interface Transition {
	
	/**
	 * Verifica se la condizione della transizione e' verificata o meno
	 * 
	 * @param wfObject
	 * @return true se la condizione associata alla transizione è verificata, false altrimenti
	 */
	public boolean verify(WorkflowObject wfObject);
	
	/**
	 * Nome dello stato verso cui termina la transizione
	 * @return Strinf
	 */
	public String getTargetStateName();
	
	/**
	 * Dispatcher associato alla transizione
	 * @return Dispatcher
	 */
	public Dispatcher getDispatcher();

}
