package it.sky.workflow.impl;

import it.sky.workflow.Condition;
import it.sky.workflow.Dispatcher;
import it.sky.workflow.State;
import it.sky.workflow.Transition;
import it.sky.workflow.WorkflowType;
import it.sky.workflow.impl.dispatcher.ContinueDispatcher;
import it.sky.workflow.impl.transition.SyncTransition;

import org.apache.log4j.Logger;

/**
 * @author Jacopo Di Stanislao
 * 
 * Workflow sincrono
 *
 */
public class SyncWorkflow extends AbstractWorkflow {
	
	private Logger log = Logger.getLogger(SyncWorkflow.class);
	
	public SyncWorkflow(String name) {
		super(name);
		if(log.isDebugEnabled()) {
			log.debug("Workflow sync ["+name+"] created.");
		}
	}
	
	public WorkflowType getType() {
		return WorkflowType.SYNCHRONOUS;
	}

	@Override
	protected Transition createTransition(State targetState, Condition condition, Dispatcher dispatcher) {
		if(dispatcher == null) {
			dispatcher = new ContinueDispatcher();
		}
		return new SyncTransition(targetState.getName(), condition, dispatcher);
	}

}
