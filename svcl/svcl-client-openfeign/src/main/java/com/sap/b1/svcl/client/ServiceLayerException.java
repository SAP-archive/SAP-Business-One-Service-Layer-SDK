package com.sap.b1.svcl.client;

public class ServiceLayerException extends Exception
{
	private static final long serialVersionUID = 241400767498256411L;
	private ExceptionDetail detail;
	public ServiceLayerException(ExceptionDetail detail)
	{
		super(detail.getError().getMessage().getValue());
		this.detail = detail;
	}
	
	public ExceptionDetail getDetails()
	{
		return detail;
	}

}

