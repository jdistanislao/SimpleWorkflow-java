package it.sky.workflow;

import it.sky.workflow.adapter.spring.SpringAsyncAdapter;
import it.sky.workflow.adapter.spring.SpringAsyncLoopAdapter;
import it.sky.workflow.custom.BCondition;
import it.sky.workflow.custom.CustomPayload;
import it.sky.workflow.custom.EndCondition;
import it.sky.workflow.custom.ToBLogicAdapter;
import it.sky.workflow.custom.ToEndLogicAdapter;
import it.sky.workflow.impl.AsyncLoopWorkflow;
import it.sky.workflow.impl.GenericWorkflowObject;
import it.sky.workflow.impl.WorkflowHeaders;
import it.sky.workflow.impl.condition.AlwaysTrueCondition;
import it.sky.workflow.impl.state.AsyncState;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration("/workflow-test-context.xml")
public class AsyncLoopWorkflowTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired private AsyncDispatcher springAsyncLoopDispatcher;
	@Autowired private SpringAsyncAdapter springAsyncAdapter;
	@Autowired private SpringAsyncLoopAdapter springAsyncLoopAdapter;
	
	private Workflow workflow;
	
	@Before
	public void setUp() {
		
		State startState = new AsyncState("START", "WF-QUEUE");
		State aState = new AsyncState("A", "A-QUEUE", new ToBLogicAdapter());
		State bState = new AsyncState("B", "B-QUEUE", new ToEndLogicAdapter());
		State endState = new AsyncState("END", "END-QUEUE");
		
		workflow = new AsyncLoopWorkflow("async-loop", "WF-QUEUE", springAsyncLoopDispatcher);
		
		workflow.registerState(startState);
		workflow.registerState(aState);
		workflow.registerState(bState);
		workflow.registerState(endState);
		
		workflow.addTransition("START", "A", new AlwaysTrueCondition(), null);
		workflow.addTransition("A", "B", new BCondition(), null);
		workflow.addTransition("A", "END", new EndCondition(), null);
		workflow.addTransition("B", "END", new EndCondition(), null);
		
		springAsyncAdapter.workflow = workflow;
		springAsyncLoopAdapter.workflow = (AsyncLoopWorkflow)workflow;
	}

	@Test
	public void testAsyncLoopWorkflow() throws Exception {
		CustomPayload payload = new CustomPayload("A");
		WorkflowObject wfObject = new GenericWorkflowObject(payload);
		wfObject.getHeaders().put(WorkflowHeaders.NEXT_STATE, "START");
		
		State state = workflow.resolveNextState(wfObject);
		Assert.assertNotNull(state);
		Assert.assertEquals("START", state.getName());
		
		state.execute(wfObject);
		Assert.assertEquals("A", ((CustomPayload)wfObject.getPayload()).value);
		
		Dispatcher dispatcher = state.resolveTransition(wfObject);
		Assert.assertEquals("A", wfObject.getHeaders().get(WorkflowHeaders.NEXT_STATE));
		
		Assert.assertFalse(dispatcher.dispatch(wfObject));
		
		Thread.sleep(1000);
		
		Assert.assertEquals("END", ((CustomPayload)wfObject.getPayload()).value);
		
	}
	
}
