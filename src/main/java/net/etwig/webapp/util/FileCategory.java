/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: File categories.
	*/

package net.etwig.webapp.util;

/**
 * Represents different categories of file types. This enumeration is used to classify
 * files based on their general type, facilitating file management and operations
 * related to processing different kinds of files.
 *
 * <p>Each enum constant represents a distinct category of files:</p>
 * <ul>
 *   <li>{@code APPLICATION} - Represents application files, typically executables or software-related files.</li>
 *   <li>{@code IMAGE} - Represents image files, such as photos and graphics.</li>
 *   <li>{@code TEXT} - Represents text files, including documents and other readable formats.</li>
 * </ul>
 */

public enum FileCategory {

	/**
	 * Represents application files, typically executables or software-related files.
	 */

	APPLICATION,

	/**
	 * Represents image files, such as photos and graphics.
	 */

	IMAGE,

	/**
	 * Represents text files, including documents and other readable formats.
	 */

	TEXT,
}
