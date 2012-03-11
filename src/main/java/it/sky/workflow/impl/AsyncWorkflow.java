package it.sky.workflow.impl;

import it.sky.workflow.Condition;
import it.sky.workflow.Dispatcher;
import it.sky.workflow.State;
import it.sky.workflow.Transition;
import it.sky.workflow.WorkflowType;
import it.sky.workflow.impl.state.AsyncState;
import it.sky.workflow.impl.transition.AsyncTransition;

import org.apache.log4j.Logger;

/**
 * @author Jacopo Di Stanislao
 * 
 * Workflow asincrono
 *
 */
public class AsyncWorkflow extends AbstractWorkflow {
	
	private Logger log = Logger.getLogger(AsyncWorkflow.class);

	public AsyncWorkflow(String name) {
		super(name);
		if(log.isDebugEnabled()) {
			log.debug("Workflow async ["+name+"] created.");
		}
	}
	
	public WorkflowType getType() {
		return WorkflowType.ASYNCHRONOUS;
	}
	
	@Override
	protected Transition createTransition(State targetState, Condition condition, Dispatcher dispatcher) {
		return new AsyncTransition(targetState.getName(), ((AsyncState)targetState).getDestination(), condition, dispatcher);
	}

}
