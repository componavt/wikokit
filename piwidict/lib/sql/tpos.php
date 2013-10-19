<?php

global $LINK_DB;

class TPOS {
    
/** An operations with the table 'part_of_speech' in Wiktionary parsed database.
 * The table 'part_of_speech' contains a list of POS: name and ID.
 */
static public function getAllPOS() {
    global $LINK_DB;
  
    $posss = array(); // all partS of Speech

    // part_of_speech (id, name)
    $query = "SELECT id, name FROM part_of_speech";
    $result = mysqli_query($LINK_DB, $query) or die("Query failed (line 17) in TPOS::getAllPOS: " . mysqli_error().". Query: ".$query);

    while($row = mysqli_fetch_array($result)){
        $id = $row['id'];
        
        $posss[ $id ] ['id']   = $id;
        $posss[ $id ] ['name'] = $row['name'];
    }
    return $posss;
}


/* Gets TPOS object (id and name) by ID.
 * Returns NULL if it is unknown ID.
 */
static public function getByID($id, $pos_all) {
    
    // [39] => Array ( [id] => 39 [name] => noun )
    
    foreach ($pos_all as $key => $value) {
        if($id == $value['id']) {
            return (object) array('id' => $id, 'name' => $value['name']);
        }
    }
    return NULL;
}

/* Gets ID from the table 'part_of_speech' by the part of speech name, e.g. "noun", "verb", "phrase".
 * Returns NULL if it is unknown name.
 */
static public function getIDByName($pos_all, $_name) {

    foreach ($pos_all as $key => $value) {
      if($_name == $value['name'])
          return $key;
    }
    return NULL;
}

}
?>