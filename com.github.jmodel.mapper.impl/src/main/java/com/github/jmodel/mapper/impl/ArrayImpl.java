package com.github.jmodel.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import com.github.jmodel.mapper.api.Array;
import com.github.jmodel.mapper.api.Model;

public class ArrayImpl extends ModelImpl implements Array {

	@Override
	public Model clone() {
		Array clonedArray = new ArrayImpl();
		clonedArray.setName(this.getName() + "");

		List<Model> subModels = this.getSubModels();
		if (subModels != null) {
			clonedArray.setSubModels(new ArrayList<Model>());

			for (Model subModel : subModels) {
				Model clonedSubModel = subModel.clone();
				clonedArray.getSubModels().add(clonedSubModel);
				clonedSubModel.setParentModel(clonedArray);
			}
		}

		return clonedArray;
	}

}
