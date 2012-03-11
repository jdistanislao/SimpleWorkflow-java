package it.sky.workflow.impl.dispatcher;

import it.sky.workflow.Dispatcher;
import it.sky.workflow.WorkflowObject;

/**
 * @author Jacopo Di Stanislao
 * 
 * Dispatcher che fa sempre continuare l'esecuzione del workflow
 *
 */
public class ContinueDispatcher implements Dispatcher {

	public boolean dispatch(WorkflowObject wfObject) {
		return true;
	}

}
