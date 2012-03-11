package it.sky.workflow;

import it.sky.workflow.adapter.spring.SpringAsyncAdapter;
import it.sky.workflow.custom.BCondition;
import it.sky.workflow.custom.CustomPayload;
import it.sky.workflow.custom.EndCondition;
import it.sky.workflow.custom.ToBLogicAdapter;
import it.sky.workflow.custom.ToEndLogicAdapter;
import it.sky.workflow.impl.AsyncWorkflow;
import it.sky.workflow.impl.GenericWorkflowObject;
import it.sky.workflow.impl.WorkflowHeaders;
import it.sky.workflow.impl.condition.AlwaysTrueCondition;
import it.sky.workflow.impl.state.AsyncState;
import it.sky.workflow.impl.state.SyncState;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration("/workflow-test-context.xml")
public class AsyncWorkflowTest extends AbstractJUnit4SpringContextTests  {
	
	@Autowired private AsyncDispatcher springAsyncDispatcherA;
	@Autowired private AsyncDispatcher springAsyncDispatcherB;
	@Autowired private AsyncDispatcher springAsyncDispatcherEND;
	@Autowired private SpringAsyncAdapter springAsyncAdapter;
	
	private Workflow workflow;
	
	@Before
	public void setUp() {
		
		State startState = new SyncState("START");
		State aState = new AsyncState("A", "A-QUEUE", new ToBLogicAdapter());
		State bState = new AsyncState("B", "B-QUEUE", new ToEndLogicAdapter());
		State endState = new AsyncState("END", "END-QUEUE");
		
		workflow = new AsyncWorkflow("async");
		
		workflow.registerState(startState);
		workflow.registerState(aState);
		workflow.registerState(bState);
		workflow.registerState(endState);
		
		workflow.addTransition("START", "A", new AlwaysTrueCondition(), springAsyncDispatcherA);
		workflow.addTransition("A", "B", new BCondition(), springAsyncDispatcherB);
		workflow.addTransition("A", "END", new EndCondition(), springAsyncDispatcherEND);
		workflow.addTransition("B", "END", new EndCondition(), springAsyncDispatcherEND);
		
		springAsyncAdapter.workflow = workflow;
	}

	
	@Test
	public void test() throws Exception {
		CustomPayload payload = new CustomPayload("A");
		WorkflowObject wfObject = new GenericWorkflowObject(payload);
		wfObject.getHeaders().put(WorkflowHeaders.NEXT_STATE, "START");
		
		State state = workflow.resolveNextState(wfObject);
		Assert.assertNotNull(state);
		Assert.assertEquals("START", state.getName());
		
		System.out.println("Eseguo lo stato: "+state.getName());
		
		state.execute(wfObject);
		Assert.assertEquals("A", ((CustomPayload)wfObject.getPayload()).value);
		
		Dispatcher dispatcher = state.resolveTransition(wfObject);
		Assert.assertEquals("A", wfObject.getHeaders().get(WorkflowHeaders.NEXT_STATE));
		
		Assert.assertFalse(dispatcher.dispatch(wfObject));
		
		Thread.sleep(1000);
		
		Assert.assertEquals("END", ((CustomPayload)wfObject.getPayload()).value);
	}
	
	
	
}
