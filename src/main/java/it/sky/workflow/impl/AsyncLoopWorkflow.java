package it.sky.workflow.impl;

import it.sky.workflow.AsyncDispatcher;
import it.sky.workflow.Condition;
import it.sky.workflow.Dispatcher;
import it.sky.workflow.State;
import it.sky.workflow.Transition;
import it.sky.workflow.WorkflowObject;
import it.sky.workflow.WorkflowType;
import it.sky.workflow.exception.WorkflowAsyncException;
import it.sky.workflow.impl.state.AsyncState;
import it.sky.workflow.impl.transition.AsyncTransition;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author Jacopo Di Stanislao
 * 
 * Workflow asincrono con coda di workflow.
 * In questo caso gli stati dopo aver eseguito al logica riaccodano il messaggio sulla code del workflow
 * che poi si occuperà di effettuare il dispatch verso la coda opportuna
 *
 */
public class AsyncLoopWorkflow extends AsyncWorkflow {
	
	private Logger log = Logger.getLogger(AsyncLoopWorkflow.class);
	
	private String loopDestination;
	private Map<String, String> destinationsMap;
	private AsyncDispatcher dispatcher;

	public AsyncLoopWorkflow(String name) {
		super(name);
		this.destinationsMap = new HashMap<String, String>();
		if(log.isDebugEnabled()) {
			log.debug("Workflow async-loop ["+name+"] created.");
		}
	}

	public AsyncLoopWorkflow(String name, String loopDestination, AsyncDispatcher dispatcher) {
		super(name);
		this.loopDestination = loopDestination;
		this.dispatcher = dispatcher;
		this.destinationsMap = new HashMap<String, String>();
	}
	
	@Override
	public WorkflowType getType() {
		return WorkflowType.ASYNCHRONOUS_LOOP;
	}

	@Override
	protected Transition createTransition(State targetState, Condition condition, Dispatcher dispatcher) {
		this.destinationsMap.put(targetState.getName(), ((AsyncState)targetState).getDestination());
		return new AsyncTransition(targetState.getName(), this.loopDestination, condition, this.dispatcher);
	}
	
	public void dispatch(String targetStateName, WorkflowObject wfObject) throws WorkflowAsyncException {
		this.dispatcher.dispatch(this.destinationsMap.get(targetStateName), wfObject);
	}
	
	public void setLoopDestination(String loopDestination) {
		this.loopDestination = loopDestination;
	}

	public void setDispatcher(AsyncDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	
}
