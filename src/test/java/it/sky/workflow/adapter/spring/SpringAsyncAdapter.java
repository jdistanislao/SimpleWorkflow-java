package it.sky.workflow.adapter.spring;

import it.sky.workflow.Dispatcher;
import it.sky.workflow.State;
import it.sky.workflow.Workflow;
import it.sky.workflow.WorkflowObject;

import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;
import org.springframework.integration.core.MessageHandler;

public class SpringAsyncAdapter implements MessageHandler {
	
	public Workflow workflow;

	public void handleMessage(Message<?> message) throws MessagingException {
		try {
			WorkflowObject wfObject = (WorkflowObject)message.getPayload();
			State state = workflow.resolveNextState(wfObject);
			state.execute(wfObject);
			Dispatcher dispatcher = state.resolveTransition(wfObject);
			dispatcher.dispatch(wfObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
