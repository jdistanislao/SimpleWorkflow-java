package it.sky.workflow.impl.state;

import it.sky.workflow.LogicAdapter;
import it.sky.workflow.impl.logicadapter.NullLogicAdapter;

import org.apache.log4j.Logger;

/**
 * @author Jacopo Di Stanislao
 * 
 * State asincrono.
 *
 */
public class AsyncState extends AbstractState {
	
	private Logger log = Logger.getLogger(AsyncState.class);
	
	private static final String DESTINATION_SUFFIX = "-QUEUE";
	
	private String destination;

	public AsyncState(String name) {
		this(name, null, new NullLogicAdapter());
	}
	
	public AsyncState(String name, String destination) {
		this(name, destination, new NullLogicAdapter());
	}

	public AsyncState(String name, LogicAdapter logicAdapter) {
		this(name, null, logicAdapter);
	}
	
	public AsyncState(String name, String destination, LogicAdapter logicAdapter) {
		super(name, logicAdapter);
		this.destination = normalizeDestination(destination);
		if(log.isDebugEnabled()) {
			log.debug("State async ["+name+"] created with destination ["+this.destination+"] " +
					"and logic adapter ["+logicAdapter.getClass().getName()+"].");
		}
	}
	
	/**
	 * Se destination non e' impostato, di default ne crea uno utilizzando
	 * il nome della stato aggiungendo ROUTE_SUFFIX
	 * @param destination
	 * @return String
	 */
	private String normalizeDestination(String destination) {
		if(destination != null) {
			return destination;
		} else {
			String defaultDestination = getName()+DESTINATION_SUFFIX;
			if(log.isDebugEnabled()) {
				log.debug("Destination NULL set ti default ["+defaultDestination+"].");
			}
			return defaultDestination;
		}
	}
	
	public String getDestination() {
		return this.destination;
	}

}
