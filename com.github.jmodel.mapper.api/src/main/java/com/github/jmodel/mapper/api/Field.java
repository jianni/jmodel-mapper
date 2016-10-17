package com.github.jmodel.mapper.api;

//should consider type of value and format
public interface Field {

	public String getName();

	public void setName(String name);

	public String getValue();

	public void setValue(String value);

	public Entity getParentEntity();

	public void setParentEntity(Entity entity);

	public boolean isUsed();

	public void setUsed(boolean used);

	public Field clone();

}
