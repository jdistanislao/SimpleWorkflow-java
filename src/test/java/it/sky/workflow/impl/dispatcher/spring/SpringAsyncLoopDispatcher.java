package it.sky.workflow.impl.dispatcher.spring;

import it.sky.workflow.WorkflowObject;
import it.sky.workflow.exception.WorkflowAsyncException;

import java.util.HashMap;
import java.util.Map;

import org.springframework.integration.MessageChannel;

public class SpringAsyncLoopDispatcher extends SpringAsyncDispatcher {
	
	private Map<String, MessageChannel> channelMap;

	public SpringAsyncLoopDispatcher(String destination) {
		super(destination);
		this.channelMap = new HashMap<String, MessageChannel>();
	}
	
	@Override
	protected void send(String destination, WorkflowObject wfObject) throws WorkflowAsyncException {
		super.send(destination, wfObject);
	}
	
	@Override
	protected MessageChannel resolveChannel(String destination) {
		return this.channelMap.get(destination);
	}

	public void setChannelA(MessageChannel channelA) {
		this.channelMap.put("A-QUEUE", channelA);
	}

	public void setChannelB(MessageChannel channelB) {
		this.channelMap.put("B-QUEUE", channelB);
	}

	public void setChannelEND(MessageChannel channelEND) {
		this.channelMap.put("END-QUEUE", channelEND);
	}
	
	public void setChannelWF(MessageChannel channelWF) {
		this.channelMap.put("WF-QUEUE", channelWF);
	}

}
