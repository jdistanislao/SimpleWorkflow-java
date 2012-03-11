package it.sky.workflow.impl.dispatcher;

import it.sky.workflow.AsyncDispatcher;
import it.sky.workflow.WorkflowObject;
import it.sky.workflow.exception.WorkflowAsyncException;
import it.sky.workflow.exception.WorkflowException;

import org.apache.log4j.Logger;

/**
 * @author Jacopo Di Stanislao
 * 
 * Base AsyncDispatcher
 *
 */
public abstract class AbstractAsyncDispatcher implements AsyncDispatcher {
	
	private Logger log = Logger.getLogger(AbstractAsyncDispatcher.class);
	
	protected String destination;
	
	public AbstractAsyncDispatcher(String destination) {
		this.destination = destination;
	}

	public boolean dispatch(WorkflowObject wfObject) throws WorkflowException {
		return dispatch(this.destination, wfObject);
	}
	
	public boolean dispatch(String destination, WorkflowObject wfObject) throws WorkflowAsyncException {
		if(log.isDebugEnabled()) {
			log.debug("Dispatching to destination ["+destination+"].");
		}
		send(destination, wfObject);
		return false;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	protected abstract void send(String destination, WorkflowObject wfObject) throws WorkflowAsyncException;

}
