package it.sky.workflow.impl.transition;

import it.sky.workflow.AsyncDispatcher;
import it.sky.workflow.Condition;
import it.sky.workflow.Dispatcher;

/**
 * @author Jacopo Di Stanislao
 * 
 * Transition aincrona.
 * Introduce il parametro destiantion per identificare dove verra' spedito il messaggio.
 *
 */
public class AsyncTransition extends SyncTransition {
	
	private String destiantion;

	public AsyncTransition(String targetStateName, Condition condition) {
		super(targetStateName, condition);
	}
	
	public AsyncTransition(String targetStateName, String destiantion, Condition condition, Dispatcher dispatcher) {
		super(targetStateName, condition, dispatcher);
		this.destiantion = destiantion;
		((AsyncDispatcher)dispatcher).setDestination(this.destiantion);
	}

	public String getDestiantion() {
		return this.destiantion;
	}
}
