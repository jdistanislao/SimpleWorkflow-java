package it.sky.workflow.impl.state;

import it.sky.workflow.LogicAdapter;
import it.sky.workflow.impl.logicadapter.NullLogicAdapter;

import org.apache.log4j.Logger;

/**
 * @author Jacopo Di Stanislao
 * 
 * State sincrono
 *
 */
public class SyncState extends AbstractState {
	
	private Logger log = Logger.getLogger(SyncState.class);

	public SyncState(String name) {
		this(name, new NullLogicAdapter());
	}

	public SyncState(String name, LogicAdapter logicAdapter) {
		super(name, logicAdapter);
		if(log.isDebugEnabled()) {
			log.debug("State sync ["+name+"] created with logic adapter ["+logicAdapter.getClass().getName()+"].");
		}
	}

}
