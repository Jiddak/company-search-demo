package com.risk.companysearchdemo.client.trueproxy.model.exception;

public class TrueProxyClientException extends RuntimeException {

	private static final long serialVersionUID = 4359882500749611008L;

	public TrueProxyClientException() {
        super();
    }

    public TrueProxyClientException(String message) {
        super(message);
    }

   public TrueProxyClientException(Throwable cause) {
      super(cause);
   }

    public TrueProxyClientException(String message, Throwable cause) {
        super(message, cause);
    }

}
