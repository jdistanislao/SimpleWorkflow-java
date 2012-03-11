package it.sky.workflow.impl;

import it.sky.workflow.Condition;
import it.sky.workflow.Dispatcher;
import it.sky.workflow.State;
import it.sky.workflow.Transition;
import it.sky.workflow.Workflow;
import it.sky.workflow.WorkflowObject;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author Jacopo Di Stanislao
 * 
 * Base AbstractWorkflow
 *
 */
public abstract class AbstractWorkflow implements Workflow {
	
	private Logger log = Logger.getLogger(AbstractWorkflow.class);

	private String name;
	private Map<String, State> stateMap;
	
	public AbstractWorkflow(String name) {
		this.name = name;
		this.stateMap = new HashMap<String, State>();
	}

	public String getName() {
		return this.name;
	}

	public void registerState(State state) {
		if(state != null) {
			this.stateMap.put(state.getName(), state);
			if(log.isDebugEnabled()) {
				log.debug("Workflow ["+this.name+"]: state ["+state.getName()+"] added.");
			}
		}
	}

	public void addTransition(String sourceState, String targetState, Condition condition, Dispatcher dispatcher) {
		if(this.stateMap.containsKey(sourceState) && this.stateMap.containsKey(targetState) &&  condition != null) {
			State source = this.stateMap.get(sourceState);
			State target = this.stateMap.get(targetState);
			Transition transition = createTransition(target, condition, dispatcher);
			source.addTransition(transition);
			if(log.isDebugEnabled()) {
				log.debug("Workflow ["+this.name+"]: transition ["+transition.getClass().getName()+"] added from ["+sourceState+"] to ["+targetState+"] " +
						"with condition ["+condition.getClass().getName()+"] " +
						"and dispacher ["+transition.getDispatcher().getClass().getName()+"].");
			}
		}

	}

	public State resolveNextState(WorkflowObject wfObject) {
		if(wfObject != null && this.stateMap.containsKey(wfObject.getHeaders().get(WorkflowHeaders.NEXT_STATE))) {
			State state = this.stateMap.get(wfObject.getHeaders().get(WorkflowHeaders.NEXT_STATE));
			if(log.isDebugEnabled()) {
				log.debug("Workflow ["+this.name+"]: returning next state ["+state.getName()+"].");
			}
			wfObject.beforeState(state, wfObject.getPayload());
			return state;
		} else {
			return null;
		}
	}

	protected abstract Transition createTransition(State targetState, Condition condition, Dispatcher dispatcher);
}
