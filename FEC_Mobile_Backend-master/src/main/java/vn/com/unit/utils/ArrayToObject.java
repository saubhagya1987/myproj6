package vn.com.unit.utils;

import java.util.Arrays;

public class ArrayToObject {
	private Object[] objects;

	public Object[] getObjects() {
		return objects;
	}

	public void setObjects(Object[] objects) {
		this.objects = objects;
	}

	public ArrayToObject(Object[] objects) {
		super();
		this.objects = objects;
	}

	@Override
	public String toString() {
		return "'" + Arrays.toString(objects) + "'";
	}
	
}	
