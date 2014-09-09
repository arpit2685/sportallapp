package com.sportall.app.model;

/**
 * This is the Model to hold the name and icon for the Menu.
 * @author asus i5
 *
 */
public class MenuItem {

	public String tag;
	public int iconRes;
	public MenuItem(String tag, int iconRes) {
		this.tag = tag; 
		this.iconRes = iconRes;
	}
}
