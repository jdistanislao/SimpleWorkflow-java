package it.sky.workflow.impl.dispatcher.spring;

import it.sky.workflow.WorkflowObject;
import it.sky.workflow.exception.WorkflowAsyncException;
import it.sky.workflow.impl.dispatcher.AbstractAsyncDispatcher;

import org.apache.log4j.Logger;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.message.GenericMessage;

public class SpringAsyncDispatcher extends AbstractAsyncDispatcher  {
	
	private Logger log = Logger.getLogger(SpringAsyncDispatcher.class);
	
	private MessageChannel channel;

	public SpringAsyncDispatcher(String destination) {
		super(destination);
	}

	@Override
	protected void send(String destination, WorkflowObject wfObject) throws WorkflowAsyncException {
		this.channel = resolveChannel(destination);
		log.info("Sending to: "+this.channel.toString());
		this.channel.send(new GenericMessage<WorkflowObject>(wfObject));
	}

	public void setChannel(MessageChannel channel) {
		this.channel = channel;
	}
	
	protected MessageChannel resolveChannel(String destination) {
		return this.channel;
	}

}
