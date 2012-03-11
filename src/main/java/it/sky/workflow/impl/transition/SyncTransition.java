package it.sky.workflow.impl.transition;

import it.sky.workflow.Condition;
import it.sky.workflow.Dispatcher;
import it.sky.workflow.impl.dispatcher.ContinueDispatcher;

/**
 * @author Jacopo Di Stanislao
 * 
 * Transition sincrona.
 *
 */
public class SyncTransition extends AbstractTransition {

	public SyncTransition(String targetStateName, Condition condition) {
		super(targetStateName, condition, new ContinueDispatcher());
	}
	
	public SyncTransition(String targetStateName, Condition condition, Dispatcher dispatcher) {
		super(targetStateName, condition, dispatcher);
	}

}
