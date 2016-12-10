package model.bean;

public class Output {
	private int resultCode;
	private Object returnObject;
	
	public Output(int resultCode) {
		super();
		this.resultCode = resultCode;
		this.returnObject = null;
	}
	public Output(int resultCode, Object returnObject) {
		super();
		this.resultCode = resultCode;
		this.returnObject = returnObject;
	}
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public Object getReturnObject() {
		return returnObject;
	}
	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}
	
}
