package it.sky.workflow;

import it.sky.workflow.custom.BCondition;
import it.sky.workflow.custom.CustomPayload;
import it.sky.workflow.custom.EndCondition;
import it.sky.workflow.custom.FailLogicAdapter;
import it.sky.workflow.custom.ToBLogicAdapter;
import it.sky.workflow.custom.ToEndLogicAdapter;
import it.sky.workflow.exception.WorkflowException;
import it.sky.workflow.exception.WorkflowLogicException;
import it.sky.workflow.impl.GenericWorkflowObject;
import it.sky.workflow.impl.HistoricalWorkflowObject;
import it.sky.workflow.impl.SyncWorkflow;
import it.sky.workflow.impl.WorkflowHeaders;
import it.sky.workflow.impl.condition.AlwaysTrueCondition;
import it.sky.workflow.impl.state.SyncState;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


public class SyncWorkflowTest {
	
	private Workflow workflow;
	
	@Before
	public void setUp() {
		
		State startState = new SyncState("START");
		State aState = new SyncState("A", new ToBLogicAdapter());
		State bState = new SyncState("B", new ToEndLogicAdapter());
		State endState = new SyncState("END");
		
		workflow = new SyncWorkflow("sync");
		
		workflow.registerState(startState);
		workflow.registerState(aState);
		workflow.registerState(bState);
		workflow.registerState(endState);
		
		workflow.addTransition("START", "A", new AlwaysTrueCondition(), null);
		workflow.addTransition("A", "B", new BCondition(), null);
		workflow.addTransition("A", "END", new EndCondition(), null);
		workflow.addTransition("B", "END", new EndCondition(), null);
	}

	@Test
	public void testSyncWorkflow() throws WorkflowException {
		
		State state = null;
		Dispatcher dispatcher = null;
		
		CustomPayload payload = new CustomPayload("A");
		WorkflowObject wfObject = new GenericWorkflowObject(payload);
		wfObject.getHeaders().put(WorkflowHeaders.NEXT_STATE, "START");
		
		// START
		state = workflow.resolveNextState(wfObject);
		Assert.assertNotNull(state);
		Assert.assertEquals("START", state.getName());
		
		state.execute(wfObject);
		Assert.assertEquals("A", ((CustomPayload)wfObject.getPayload()).value);
		
		dispatcher = state.resolveTransition(wfObject);
		Assert.assertEquals("A", wfObject.getHeaders().get(WorkflowHeaders.NEXT_STATE));
		
		Assert.assertTrue(dispatcher.dispatch(wfObject));
		
		// A
		state = workflow.resolveNextState(wfObject);
		Assert.assertNotNull(state);
		Assert.assertEquals("A", state.getName());
		
		state.execute(wfObject);
		Assert.assertEquals("B", ((CustomPayload)wfObject.getPayload()).value);
		
		dispatcher = state.resolveTransition(wfObject);
		Assert.assertEquals("B", wfObject.getHeaders().get(WorkflowHeaders.NEXT_STATE));
		
		Assert.assertTrue(dispatcher.dispatch(wfObject));
		
		// B
		state = workflow.resolveNextState(wfObject);
		Assert.assertNotNull(state);
		Assert.assertEquals("B", state.getName());
		
		state.execute(wfObject);
		Assert.assertEquals("END", ((CustomPayload)wfObject.getPayload()).value);
		
		dispatcher = state.resolveTransition(wfObject);
		Assert.assertEquals("END", wfObject.getHeaders().get(WorkflowHeaders.NEXT_STATE));
		
		Assert.assertTrue(dispatcher.dispatch(wfObject));
		
		// END
		state = workflow.resolveNextState(wfObject);
		Assert.assertNotNull(state);
		Assert.assertEquals("END", state.getName());
		
		state.execute(wfObject);
		Assert.assertEquals("END", ((CustomPayload)wfObject.getPayload()).value);
		
		dispatcher = state.resolveTransition(wfObject);
		Assert.assertEquals("END", wfObject.getHeaders().get(WorkflowHeaders.NEXT_STATE));
		
		Assert.assertFalse(dispatcher.dispatch(wfObject));
	}
	
	@Test
	public void testSyncHistoricalWorkflow() throws WorkflowException {
		
		State state = null;
		Dispatcher dispatcher = null;
		
		CustomPayload payload = new CustomPayload("A");
		WorkflowObject wfObject = new HistoricalWorkflowObject(payload);
		wfObject.getHeaders().put(WorkflowHeaders.NEXT_STATE, "START");
		
		// START
		state = workflow.resolveNextState(wfObject);
		Assert.assertNotNull(state);
		Assert.assertEquals("START", state.getName());
		
		state.execute(wfObject);
		Assert.assertEquals("A", ((CustomPayload)wfObject.getPayload()).value);
		
		dispatcher = state.resolveTransition(wfObject);
		Assert.assertEquals("A", wfObject.getHeaders().get(WorkflowHeaders.NEXT_STATE));
		
		Assert.assertTrue(dispatcher.dispatch(wfObject));
		
		// A
		state = workflow.resolveNextState(wfObject);
		Assert.assertNotNull(state);
		Assert.assertEquals("A", state.getName());
		
		state.execute(wfObject);
		Assert.assertEquals("B", ((CustomPayload)wfObject.getPayload()).value);
		
		dispatcher = state.resolveTransition(wfObject);
		Assert.assertEquals("B", wfObject.getHeaders().get(WorkflowHeaders.NEXT_STATE));
		
		Assert.assertTrue(dispatcher.dispatch(wfObject));
		
		// B
		state = workflow.resolveNextState(wfObject);
		Assert.assertNotNull(state);
		Assert.assertEquals("B", state.getName());
		
		state.execute(wfObject);
		Assert.assertEquals("END", ((CustomPayload)wfObject.getPayload()).value);
		
		dispatcher = state.resolveTransition(wfObject);
		Assert.assertEquals("END", wfObject.getHeaders().get(WorkflowHeaders.NEXT_STATE));
		
		Assert.assertTrue(dispatcher.dispatch(wfObject));
		
		// END
		state = workflow.resolveNextState(wfObject);
		Assert.assertNotNull(state);
		Assert.assertEquals("END", state.getName());
		
		state.execute(wfObject);
		Assert.assertEquals("END", ((CustomPayload)wfObject.getPayload()).value);
		
		dispatcher = state.resolveTransition(wfObject);
		Assert.assertEquals("END", wfObject.getHeaders().get(WorkflowHeaders.NEXT_STATE));
		
		Assert.assertFalse(dispatcher.dispatch(wfObject));
		Assert.assertEquals(4, ((HistoricalWorkflowObject)wfObject).getHistory().size());
		Assert.assertEquals("START", ((HistoricalWorkflowObject)wfObject).getHistory().get(0).stateName);
		Assert.assertEquals("A", ((HistoricalWorkflowObject)wfObject).getHistory().get(1).stateName);
		Assert.assertEquals("B", ((HistoricalWorkflowObject)wfObject).getHistory().get(2).stateName);
		Assert.assertEquals("END", ((HistoricalWorkflowObject)wfObject).getHistory().get(3).stateName);
	}
	
	@Test
	public void testSyncWorkflowLogicException() {
		State failState = new SyncState("FAIL", new FailLogicAdapter());
		try {
			failState.execute(null);
			Assert.fail("WorkflowLogicException expected");
		} catch(WorkflowLogicException e) {
			Assert.assertEquals("Eccezione nella logica", e.getMessage());
		}
	}
	
}
