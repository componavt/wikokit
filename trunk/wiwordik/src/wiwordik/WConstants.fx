/* WConstants.fx - Constants and global variables used in Wiwordik.
 *
 * Copyright (c) 2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wiwordik;

import wikipedia.language.LanguageType;


/** Width of word card. */
public def wrapping_width = 380;

//var native_lang : LanguageType;
//public def native_lang = LanguageType.ru;
public def native_lang = LanguageType.en;

public var DEBUGUI : Boolean = false;

//public class WConstants {
//   public var wrapping_width : Integer = 380;
//}


// ===========
// Wiktionary parsed database
// ===========

public def wiwordik_version : String = "0.08";

//////////////////////////////
// Parameters

/** If true, then SQLite database extracted from the .jar and stored
 * to the directory user.dir (Add .jar with SQLite database to the project).
 * If false, then SQLite database from the project local folder ./sqlite/
 */
public def IS_RELEASE : Boolean = true;

/** true (SQLite), false (MySQL) */
public def IS_SQLITE : Boolean = false;

//            eo Parameters //
//////////////////////////////
