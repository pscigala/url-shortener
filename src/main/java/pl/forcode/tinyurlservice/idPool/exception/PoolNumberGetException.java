package pl.forcode.tinyurlservice.idPool.exception;

public class PoolNumberGetException extends RuntimeException {

	private static final String MSG = "Exception during getting distributed pool number counter";

	public PoolNumberGetException() {
		super(MSG);
	}

	public PoolNumberGetException(Exception e) {
		super(MSG, e);
	}
}
