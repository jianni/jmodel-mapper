package com.github.jmodel.mapper.sample;

import java.io.InputStream;

import com.github.jmodel.mapper.api.Engine;
import com.github.jmodel.mapper.api.IllegalException;
import com.github.jmodel.mapper.impl.BaseConvertEngine;

public class Sample {

	public static void main(String[] args) {

		Engine convertEngine = new BaseConvertEngine();
		InputStream f = new Sample().getClass().getResourceAsStream("Sample.json");

		try {
			String output = convertEngine.convert(f, "com.github.jmodel.mapper.sample.SampleMapping");
			System.out.println(output);
		} catch (IllegalException e) {
			e.printStackTrace();
		}

	}

}
