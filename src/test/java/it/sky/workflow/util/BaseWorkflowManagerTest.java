package it.sky.workflow.util;

import it.sky.workflow.Dispatcher;
import it.sky.workflow.State;
import it.sky.workflow.Workflow;
import it.sky.workflow.WorkflowObject;
import it.sky.workflow.WorkflowType;
import it.sky.workflow.custom.CustomPayload;
import it.sky.workflow.impl.AsyncLoopWorkflow;
import it.sky.workflow.impl.GenericWorkflowObject;
import it.sky.workflow.impl.WorkflowHeaders;
import it.sky.workflow.util.impl.BaseWorkflowManager;
import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class BaseWorkflowManagerTest {
	
	private WorkflowManager workflowManager;
	private WorkflowObject wfObject;
	
	@Before
	public void setUp() {
		workflowManager = new BaseWorkflowManager();
		
		CustomPayload payload = new CustomPayload("A");
		wfObject = new GenericWorkflowObject(payload);
		wfObject.getHeaders().put(WorkflowHeaders.NEXT_STATE, "START");
	}
	
	@Test
	public void testAddWorkflow() throws Exception {
		Workflow workflow = EasyMock.createMock(Workflow.class);
		
		EasyMock.expect(workflow.getName()).andReturn("SYNC");
		EasyMock.replay(workflow);
		
		workflowManager.addWorkflow(workflow);
		
		EasyMock.verify(workflow);
	}
	
	@Test
	public void testSyncWorkflow() throws Exception {
		String name = "SYNCHRONOUS";
		
		Workflow workflow = EasyMock.createMock(Workflow.class);
		State state = EasyMock.createMock(State.class);
		Dispatcher dispatcher = EasyMock.createMock(Dispatcher.class);
		
		EasyMock.expect(workflow.getName()).andReturn(name);
		EasyMock.replay(workflow);
		workflowManager.addWorkflow(workflow);
		EasyMock.verify(workflow);
		EasyMock.reset(workflow);
		
		EasyMock.expect(workflow.getType()).andReturn(WorkflowType.SYNCHRONOUS);
		EasyMock.expect(workflow.resolveNextState(wfObject)).andReturn(state).times(2);
		state.execute(wfObject);
		EasyMock.expect(state.resolveTransition(wfObject)).andReturn(dispatcher);
		EasyMock.expect(dispatcher.dispatch(wfObject)).andReturn(true);
		EasyMock.expect(workflow.getName()).andReturn(name);
		EasyMock.expect(state.getName()).andReturn("A");
		
		EasyMock.expect(workflow.resolveNextState(wfObject)).andReturn(state).times(2);
		state.execute(wfObject);
		EasyMock.expect(state.resolveTransition(wfObject)).andReturn(dispatcher);
		EasyMock.expect(dispatcher.dispatch(wfObject)).andReturn(false);
		EasyMock.expect(workflow.getName()).andReturn(name);
		EasyMock.expect(state.getName()).andReturn("B");
		
		EasyMock.replay(workflow, state, dispatcher);
		
		workflowManager.runWorkflow(name, wfObject);
		
		// i valori non vengono modificati
		Assert.assertEquals("START", wfObject.getHeaders().get(WorkflowHeaders.NEXT_STATE));
		Assert.assertEquals("A", ((CustomPayload)wfObject.getPayload()).value);
		
		EasyMock.verify(workflow, state, dispatcher);
	}
	
	@Test
	public void testAsyncWorkflow() throws Exception {
		String name = "ASYNCHRONOUS";
		
		Workflow workflow = EasyMock.createMock(Workflow.class);
		State state = EasyMock.createMock(State.class);
		Dispatcher dispatcher = EasyMock.createMock(Dispatcher.class);
		
		EasyMock.expect(workflow.getName()).andReturn(name);
		EasyMock.replay(workflow);
		workflowManager.addWorkflow(workflow);
		EasyMock.verify(workflow);
		EasyMock.reset(workflow);
		
		EasyMock.expect(workflow.getType()).andReturn(WorkflowType.ASYNCHRONOUS);
		EasyMock.expect(workflow.resolveNextState(wfObject)).andReturn(state).times(2);
		state.execute(wfObject);
		EasyMock.expect(state.resolveTransition(wfObject)).andReturn(dispatcher);
		EasyMock.expect(dispatcher.dispatch(wfObject)).andReturn(false);
		EasyMock.expect(workflow.getName()).andReturn(name);
		EasyMock.expect(state.getName()).andReturn("A");
		
		EasyMock.replay(workflow, state, dispatcher);
		
		workflowManager.runWorkflow(name, wfObject);
		
		// i valori non vengono modificati
		Assert.assertEquals("START", wfObject.getHeaders().get(WorkflowHeaders.NEXT_STATE));
		Assert.assertEquals("A", ((CustomPayload)wfObject.getPayload()).value);
		
		EasyMock.verify(workflow, state, dispatcher);
	}
	
	@Test
	public void testAsyncLoopWorkflow() throws Exception {
		String name = "ASYNCHRONOUS_LOOP";
		String stateName = "STATE";
		
		AsyncLoopWorkflow workflow = EasyMock.createMock(AsyncLoopWorkflow.class);
		State state = EasyMock.createMock(State.class);
		Dispatcher dispatcher = EasyMock.createMock(Dispatcher.class);
		
		EasyMock.expect(workflow.getName()).andReturn(name);
		EasyMock.replay(workflow);
		workflowManager.addWorkflow(workflow);
		EasyMock.verify(workflow);
		EasyMock.reset(workflow);
		
		EasyMock.expect(workflow.getType()).andReturn(WorkflowType.ASYNCHRONOUS_LOOP);
		EasyMock.expect(workflow.resolveNextState(wfObject)).andReturn(state).times(2);
		EasyMock.expect(state.getName()).andReturn(stateName);
		workflow.dispatch(stateName, wfObject);
		EasyMock.expect(workflow.getName()).andReturn(name);
		EasyMock.expect(state.getName()).andReturn(stateName);
		
		EasyMock.replay(workflow, state, dispatcher);
		
		workflowManager.runWorkflow(name, wfObject);
		
		// i valori non vengono modificati
		Assert.assertEquals("START", wfObject.getHeaders().get(WorkflowHeaders.NEXT_STATE));
		Assert.assertEquals("A", ((CustomPayload)wfObject.getPayload()).value);
		
		EasyMock.verify(workflow, state, dispatcher);
	}

}
