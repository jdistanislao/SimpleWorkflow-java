package it.sky.workflow;

/**
 * @author Jacopo Di Stanislao
 * 
 * Base Workflow interface
 *
 */
public interface Workflow {
	
	/**
	 * Nome logico del workflow
	 * 
	 * @return Nome del workflow
	 */
	public String getName();
	
	/**
	 * Tipologia del workflow
	 * 
	 * @return Nome del workflow
	 */
	public WorkflowType getType();
	
	/**
	 * Aggiunge uno State al workflow
	 * 
	 * @param state
	 */
	public void registerState(State state);
	
	/**
	 * Aggiunge una transizione tra due stati
	 * 
	 * @param sourceState
	 * @param targetState
	 * @param condition
	 * @param dispatcher
	 */
	public void addTransition(String sourceState, String targetState, Condition condition, Dispatcher dispatcher);
	
	/**
	 * Ritorna il prossimo stato da eseguire
	 * 
	 * @param wfObject
	 * @return State da eseguire
	 */
	public State resolveNextState(WorkflowObject wfObject);

}
