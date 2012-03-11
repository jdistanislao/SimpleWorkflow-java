package it.sky.workflow.impl.state;

import it.sky.workflow.Dispatcher;
import it.sky.workflow.LogicAdapter;
import it.sky.workflow.State;
import it.sky.workflow.Transition;
import it.sky.workflow.WorkflowObject;
import it.sky.workflow.exception.WorkflowLogicException;
import it.sky.workflow.impl.WorkflowHeaders;
import it.sky.workflow.impl.dispatcher.EndDispatcher;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author Jacopo Di Stanislao
 *
 */
public abstract class AbstractState implements State {
	
	private Logger log = Logger.getLogger(AbstractState.class);
	
	public String name;
	public LogicAdapter logicAdapter;
	private List<Transition> transitions;
	
	public AbstractState(String name, LogicAdapter logicAdapter) {
		this.name = name;
		this.logicAdapter = logicAdapter;
		this.transitions = new LinkedList<Transition>();
	}

	public String getName() {
		return this.name;
	}

	public void execute(WorkflowObject wfObject) throws WorkflowLogicException {
		this.logicAdapter.execute(wfObject);
	}

	public void addTransition(Transition transition) {
		this.transitions.add(transition);
	}

	public Dispatcher resolveTransition(WorkflowObject wfObject) {
		Dispatcher dispatcher = null;
		Iterator<Transition> it = this.transitions.iterator();
		while(it.hasNext() && dispatcher == null) {
			Transition transition = it.next();
			if(transition.verify(wfObject)) {
				wfObject.getHeaders().put(WorkflowHeaders.NEXT_STATE, transition.getTargetStateName());
				dispatcher = transition.getDispatcher();
			}
		}
		// Dispatcher di default EndDispatcher cosi' da non far proseguire il
		// workflow nel caso di una transizione non trovata
		if(dispatcher == null) {
			dispatcher = new EndDispatcher();
		}
		if(log.isDebugEnabled()) {
			log.debug("State ["+name+"]: transition to ["+wfObject.getHeaders().get(WorkflowHeaders.NEXT_STATE)+"] found " +
					"with dispatcher ["+dispatcher.getClass().getName()+"].");
		}
		wfObject.afterState(this, wfObject.getPayload());
		return dispatcher;
	}

}
