package pe.com.bootcamp.utilities;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class ResultBase {	
		
	private boolean ibException;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private int code = 1000;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String[] causes;
	
	public ResultBase() {}
	public ResultBase(boolean ibException, String message) {
		super();
		this.ibException = ibException;
		this.message = message;
	}
	public ResultBase(boolean ibException, String message, int code, String[] causes) {
		super();
		this.ibException = ibException;
		this.message = message;
		this.code = code;
		this.causes = causes;
	}
}

