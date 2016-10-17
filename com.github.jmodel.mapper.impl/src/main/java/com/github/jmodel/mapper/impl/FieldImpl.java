package com.github.jmodel.mapper.impl;

import com.github.jmodel.mapper.api.Entity;
import com.github.jmodel.mapper.api.Field;

public class FieldImpl implements Field {

	private String name;

	private String value;

	private boolean isUsed = false;

	private Entity parentEntity;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		setUsed(true);
	}

	public void setParentEntity(Entity parentEntity) {
		this.parentEntity = parentEntity;
	}

	public Entity getParentEntity() {
		return parentEntity;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public Field clone() {
		Field clonedField = new FieldImpl();
		clonedField.setName(this.getName() + "");
		return clonedField;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[name:" + name + ",value=" + value + "]";
	}

}
