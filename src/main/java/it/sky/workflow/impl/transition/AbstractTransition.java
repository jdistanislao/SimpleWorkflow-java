package it.sky.workflow.impl.transition;

import it.sky.workflow.Condition;
import it.sky.workflow.Dispatcher;
import it.sky.workflow.Transition;
import it.sky.workflow.WorkflowObject;
import it.sky.workflow.impl.WorkflowHeaders;

import org.apache.log4j.Logger;

/**
 * @author Jacopo Di Stanislao
 * 
 * Base Transition
 *
 */
public abstract class AbstractTransition implements Transition {
	
	private Logger log = Logger.getLogger(AbstractTransition.class);
	
	private Dispatcher dispatcher;
	private Condition condition;
	private String targetStateName;
	
	public AbstractTransition(String targetStateName, Condition condition, Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
		this.condition = condition;
		this.targetStateName = targetStateName;
	}

	public boolean verify(WorkflowObject wfObject) {
		boolean result = false;
		if(this.condition.verify(wfObject)) {
			wfObject.getHeaders().put(WorkflowHeaders.NEXT_STATE, this.targetStateName);
			result =  true;
		}
		
		if(log.isDebugEnabled()) {
			log.debug("Transition to ["+targetStateName+"] result ["+result+"]. " +
					"Next state ["+wfObject.getHeaders().get(WorkflowHeaders.NEXT_STATE)+"]");
		}
		
		return result;
	}

	public String getTargetStateName() {
		return this.targetStateName;
	}

	public Dispatcher getDispatcher() {
		return this.dispatcher;
	}

}
