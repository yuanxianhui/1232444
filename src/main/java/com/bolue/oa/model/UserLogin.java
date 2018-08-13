package com.bolue.oa.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserLogin implements Serializable {
	private static final long serialVersionUID = -1634019886574162260L;
	private String id;
	private String name;
	private String sex;
	private String password;
}
