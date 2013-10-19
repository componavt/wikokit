<?php

global $LINK_DB;

class TLang {
    
/** Gets data from the database table 'lang' with English and Russian names of POS,
 * from code to ID, name_en and name_ru
 */
//static public function get_lang_by_code($db_connect, $db_name) {
static public function getAllLang() {
  global $LINK_DB;
  
    // $result = mysqli_query($LINK_DB, $query) or die("Query failed in TText::getByID: " . mysqli_error().". Query: ".$query);

  $lang = array();

  // lang (id, code, name, n_foreign_POS, n_translation)
  $query = "SELECT id, code, name FROM lang";
  $result = mysqli_query($LINK_DB, $query) or die("Query failed (line 20) in TLang::getAllLang: " . mysqli_error().". Query: ".$query);

  while($row = mysqli_fetch_array($result)){
      $code = $row['code'];

      $lang[ $code ] ['id'] = $row['id'];
      $lang[ $code ] ['name'] = $row['name'];
  }

  return $lang;
}
/*
static public function debug_print_lang_by_code($lang_all) {

  foreach ($lang_all as $key => $value) {
    echo "code=$key; id=${value['id']}";
    echo " name_en=${value['name_en']}";
    echo " name_ru=${value['name_ru']}";
    echo "\n<BR>";
  }
}
*/

/* Gets TLang object (code, ID and name) by ID.
 * Returns NULL if it is unknown ID.
 */
static public function getByID($id, $lang_all) {

    //  [ru] => Array ( [id] => 803 [name] => Russian )
    
    foreach ($lang_all as $key => $value) {
        if($id == $value['id']) {
            return (object) array('code' => $key, 'id' => $id, 'name' => $value['name']);
        }
    }
    return NULL;
}

/* Gets language name by ID. 
 * The language of the result (e.g. Russian) depends on the '$result_language_code' e.g. ru en. 
 * Returns NULL if it is unknown code.
 */
static public function getNameByID($lang_all, $id) {

  foreach ($lang_all as $key => $value) {
      if($id == $value['id'])
          return $value['name'];
  }
  
  return NULL;
}


/* Gets ID from the table lang by the language code, e.g. ru en. 
 * Returns NULL if it is unknown code.
 */
static public function getIDByLangCode($lang_all, $_code) {

  if (array_key_exists($_code, $lang_all))
    return $lang_all[ $_code ] ['id'];
  return NULL;
}


// ===============================
// Visual forms
// ===============================


/** Gets a drop-down languages list.
 * 
 * @param int $selected_language_id - language selected for this object in this drop-down menu
 * @param string $select_name - name of HTML "select" element
 * @return string
 * 
 * Example:
 * 
 * Язык <select name="lang_id">
            <option></option>   // empty field for empty translation of text
            <option value="1"  selected>вепсский</option>
            <option value="2" >русский</option>
            <option value="3" >английский</option>
        </select>
 */
static public function getDropDownLanguagesList($lang_all, $selected_language_id, $select_name) {
    global $INTERFACE_LANGUAGE;
    
    $s = "<SELECT name=\"$select_name\">\n";
    
    if(empty($selected_language_id)) {  // empty language for translation
        $s .= "<OPTION></OPTION>\n";
    }
    
    foreach ($lang_all as $key => $value) {
        
        $language_name = "";
        if("en" == $INTERFACE_LANGUAGE) {
            $language_name = $value['name_en'];
        } else if("ru" == $INTERFACE_LANGUAGE) {
            $language_name = $value['name_ru'];
        }
        
        $selected = "";
        if($selected_language_id == $value['id']) {
            $selected = " selected"; // selected option
        }
        
        $s .= "<OPTION value=\"${value['id']}\"$selected>$language_name</OPTION>\n";
    }
    $s .= "</SELECT>";
    return $s;
}

}
?>