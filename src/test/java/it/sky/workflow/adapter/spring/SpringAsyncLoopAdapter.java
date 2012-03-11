package it.sky.workflow.adapter.spring;

import it.sky.workflow.State;
import it.sky.workflow.WorkflowObject;
import it.sky.workflow.impl.AsyncLoopWorkflow;

import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;
import org.springframework.integration.core.MessageHandler;

public class SpringAsyncLoopAdapter implements MessageHandler {
	
	public AsyncLoopWorkflow workflow;

	public void handleMessage(Message<?> message) throws MessagingException {
		try {
			WorkflowObject wfObject = (WorkflowObject)message.getPayload();
			State state = workflow.resolveNextState(wfObject);
			workflow.dispatch(state.getName(), wfObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
