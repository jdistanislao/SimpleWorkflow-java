package it.sky.workflow;

public interface CloneablePayload extends Cloneable {
	
	public Object clone() throws CloneNotSupportedException;

}
