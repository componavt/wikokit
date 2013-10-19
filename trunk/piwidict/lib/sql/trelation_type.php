<?php

global $LINK_DB;

class TRelationType {
    
/** Gets data from the database table 'relation_type'.
 * The table 'relation_type' contains a list of semantic relations: name and ID.
 */
static public function getAllRelations() {
    global $LINK_DB;
  
    // $result = mysqli_query($LINK_DB, $query) or die("Query failed in TText::getByID: " . mysqli_error().". Query: ".$query);
  
    /* todo
    int size = Statistics.Count(connect, "relation_type");
    if(0==size) {
        System.out.println("Error (wikt_parsed TRelationType.java getAllRelations()):: The table `relation_type` is empty!");
        return NULL_TRELATIONTYPE_ARRAY;
    }*/

    $rr = array(); // rrrelations

    // relation_type (id, name)
    $query = "SELECT id, name FROM relation_type";
    $result = mysqli_query($LINK_DB, $query) or die("Query failed (line 26) in TRelationType::getAllRelations: " . mysqli_error().". Query: ".$query);

    while($row = mysqli_fetch_array($result)){
        $id = $row['id'];
        $rr[ $id ] ['id'] = $id;
        $rr[ $id ] ['name'] = $row['name'];
    }

    return $rr;
}

/* Gets name of relation type by ID from the table 'relation_type'.
 * Returns NULL if ID is absent in the table.
 */
static public function getNameByID($relation_type_all, $_id) {

  foreach ($relation_type_all as $key => $value) {
      if($_id == $value['id'])
          return $value['name'];
  }
  
  return NULL;
}


/* Gets ID from the table 'relation_type' by the relation type name, e.g. "hyponyms", "hypernyms", "synonyms".
 * Returns NULL if it is unknown name.
 */
static public function getIDByName($relation_type_all, $_name) {

    foreach ($relation_type_all as $key => $value) {
      if($_name == $value['name'])
          return $key;
    }
    return NULL;
}

}
?>