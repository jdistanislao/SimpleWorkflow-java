package it.sky.workflow.util.impl;

import it.sky.workflow.Dispatcher;
import it.sky.workflow.State;
import it.sky.workflow.Workflow;
import it.sky.workflow.WorkflowObject;
import it.sky.workflow.WorkflowType;
import it.sky.workflow.exception.WorkflowException;
import it.sky.workflow.impl.AsyncLoopWorkflow;
import it.sky.workflow.impl.WorkflowHeaders;
import it.sky.workflow.util.WorkflowManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class BaseWorkflowManager implements WorkflowManager {
	
	private Logger log = Logger.getLogger(BaseWorkflowManager.class);
	
	private Map<String, Workflow> workflowsMap;

	public BaseWorkflowManager() {
		this.workflowsMap = new HashMap<String, Workflow>();
	}
	
	public BaseWorkflowManager(List<Workflow> workflows) {
		this.workflowsMap = new HashMap<String, Workflow>();
		if(workflows != null) {
			for(Workflow workflow : workflows) {
				this.workflowsMap.put(workflow.getName(), workflow);
			}
		}
	}

	public void addWorkflow(Workflow workflow) {
		if(workflow != null) {
			this.workflowsMap.put(workflow.getName(), workflow);
		}
	}

	public void runWorkflow(String workflowName, WorkflowObject wfObject) throws WorkflowException {
		if(workflowName != null && this.workflowsMap.containsKey(workflowName) && wfObject != null) {
			Workflow workflow = this.workflowsMap.get(workflowName);
			if(WorkflowType.ASYNCHRONOUS_LOOP == workflow.getType()) {
				runAsyncLoopWorkflow((AsyncLoopWorkflow)workflow, wfObject);
			} else {
				runSyncAsyncWorkflow(workflow, wfObject);
			}
		}
	}
	
	protected void runSyncAsyncWorkflow(Workflow workflow, WorkflowObject wfObject) throws WorkflowException {
		boolean iterate = true;
		while(iterate && workflow.resolveNextState(wfObject) != null) {
			State state = workflow.resolveNextState(wfObject);
			state.execute(wfObject);
			Dispatcher dispatcher = state.resolveTransition(wfObject);
			iterate = dispatcher.dispatch(wfObject);
			log.info("Workflow ["+workflow.getName()+"] state ["+state.getName()+"]: " +
					"next state ["+wfObject.getHeaders().get(WorkflowHeaders.NEXT_STATE)+"] iterate ["+iterate+"]");
		}
	}

	protected void runAsyncLoopWorkflow(AsyncLoopWorkflow workflow, WorkflowObject wfObject) throws WorkflowException {
		if(workflow.resolveNextState(wfObject) != null) {
			State state = workflow.resolveNextState(wfObject);
			workflow.dispatch(state.getName(), wfObject);
			log.info("Workflow ["+workflow.getName()+"] state ["+state.getName()+"]: " +
					"next state ["+wfObject.getHeaders().get(WorkflowHeaders.NEXT_STATE)+"]");
		}
	}

}
