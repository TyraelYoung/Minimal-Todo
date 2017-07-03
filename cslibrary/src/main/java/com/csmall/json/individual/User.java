package com.csmall.json.individual;

import com.csmall.library.statistics.individual.Logable;

public class User implements Logable{
	public String csmallToken;
	public String mobile;

	@Override
	public String toString() {
		String sb = "user:" +
				csmallToken +
				"," +
				mobile;
		return sb;
	}
}
