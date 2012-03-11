package it.sky.workflow.exception;

public class WorkflowException extends Exception {

	private static final long serialVersionUID = 1L;

	public WorkflowException() {
	}

	public WorkflowException(String arg0) {
		super(arg0);
	}

	public WorkflowException(Throwable arg0) {
		super(arg0);
	}

	public WorkflowException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
