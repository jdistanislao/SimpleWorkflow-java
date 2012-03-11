package it.sky.workflow;

/**
 * @author Jacopo Di Stanislao
 * 
 * Base Condition interface
 *
 */
public interface Condition {
	
	/**
	 * Verifica che sia presente il wfObject e che la condizione sia corretta
	 * 
	 * @param wfObject
	 * @return true se la condizione è verificata, false altrimenti
	 */
	public boolean verify(WorkflowObject wfObject);

}
