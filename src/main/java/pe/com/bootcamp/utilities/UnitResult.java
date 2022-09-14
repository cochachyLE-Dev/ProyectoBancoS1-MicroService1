package pe.com.bootcamp.utilities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

public class UnitResult<T> extends ResultBase {	
	
	@Getter @Setter @JsonInclude(JsonInclude.Include.NON_NULL)
	private T value;
	@Getter @Setter @JsonInclude(JsonInclude.Include.NON_NULL)
	private List<T> list;
	
	public UnitResult() {}
	
	public UnitResult(boolean ibException, String message) {
		super(ibException, message);
	}
	public UnitResult(boolean ibException, String message, int code, String[] causes) {	
		super(ibException, message, code, causes);
	}
	public UnitResult(T value, ArrayList<T> list,boolean ibException, String message) {
		super(ibException, message);
		this.value = value;
		this.list = list;
	}
	public UnitResult(T value, boolean ibException, String message) {
		super(ibException, message);
		this.value = value;
		this.list = null;
	}
	public UnitResult(T value) {
		super(false, null);
		this.value = value;
		this.list = null;
	}
	public UnitResult(ArrayList<T> list, boolean ibException, String message) {
		super(ibException, message);
		this.value = null;
		this.list = list;
	}
	public UnitResult(ArrayList<T> list) {
		super(false, null);
		this.value = null;
		this.list = list;
	}
	public UnitResult(List<T> list) {
		super(false, null);
		this.value = null;
		this.list = list;
	}
	
	@JsonIgnore
	@SuppressWarnings("unchecked")
	public Class<UnitResult<T>> getClassOfT(){
		return (Class<UnitResult<T>>) super.getClass();
	}
}