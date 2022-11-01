/*
 * SPDX-FileCopyrightText: 2006-2009 Dirk Riehle <dirk@riehle.org> https://dirkriehle.com
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package org.wahlzeit.model;

import java.sql.*;

import org.wahlzeit.services.*;

/**
 * An Abstract Factory for creating photos and related objects.
 */
public abstract class PhotoFactory {

	/**
	 * 
	 */
	protected PhotoFactory() {
		// do nothing
	}

	/**
	 * @methodtype factory
	 */
	public abstract Photo createPhoto();
	
	/**
	 * 
	 */
	public abstract Photo createPhoto(PhotoId id);
	
	/**
	 * 
	 */
	public abstract Photo createPhoto(ResultSet rs) throws SQLException;
	
	/**
	 * 
	 */
	public PhotoFilter createPhotoFilter() {
		return new PhotoFilter();
	}
	
	/**
	 * 
	 */
	public PhotoTagCollector createPhotoTagCollector() {
		return new PhotoTagCollector();
	}

}
