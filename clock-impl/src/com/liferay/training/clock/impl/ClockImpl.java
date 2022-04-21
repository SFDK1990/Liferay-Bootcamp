package com.liferay.training.clock.impl;

import com.liferay.training.clock.api.ClockApi;

import java.util.Date;

//@Component(
//	service = ClockApi.class	
//) //Podemos omitirlo, solo para saber que normalmente tenemos que importarlo de esta manera 

public class ClockImpl implements ClockApi{

	@Override 
	public String getTime() {
		return new Date().toString();
	}
}
