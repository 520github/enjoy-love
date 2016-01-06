/**
 * 
 */
package com.enjoy.love.common.encrypt.ucf;

/**
 * @author lenovo
 *
 */
public class CoderException extends Exception {
	private static final long serialVersionUID = 1L;

	  public CoderException()
	  {
	  }

	  public CoderException(String message)
	  {
	    super(message);
	  }

	  public CoderException(String message, Throwable cause)
	  {
	    super(message, cause);
	  }

	  public CoderException(Throwable cause)
	  {
	    super(cause);
	  }
}
