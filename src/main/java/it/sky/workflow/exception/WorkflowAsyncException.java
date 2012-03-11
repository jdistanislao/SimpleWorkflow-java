package it.sky.workflow.exception;

public class WorkflowAsyncException extends WorkflowException {

	private static final long serialVersionUID = 1L;

	public WorkflowAsyncException() {
	}

	public WorkflowAsyncException(String arg0) {
		super(arg0);
	}

	public WorkflowAsyncException(Throwable arg0) {
		super(arg0);
	}

	public WorkflowAsyncException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
