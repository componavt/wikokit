<?php

global $LINK_DB;

/** An operations with the table 'page' in MySQL wiktionary_parsed database. */
class TPage {
    

/* Gets TPage object (id, page_title, word_count, wiki_link_count, is_in_wiktionary, is_redirect, redirect_target, lang_pos) by ID.
 * Returns NULL if somethinig is unknown.
 */
static public function getByID($page_id) {
    global $LINK_DB;
        
    $page = NULL;
    
    $query = "SELECT page_title, word_count, wiki_link_count, is_in_wiktionary, is_redirect, redirect_target FROM page WHERE id=$page_id";
    $result = mysqli_query($LINK_DB, $query) or die("Query failed (line 18) in TPage::getByID: " . mysqli_error().". Query: ".$query);

    if($row = mysqli_fetch_array($result)){
        $page ['page_title'] = $row['page_title'];
        $page ['word_count'] = $row['word_count'];
        $page ['wiki_link_count'] = $row['wiki_link_count'];
        $page ['is_in_wiktionary'] = $row['is_in_wiktionary'];
        $page ['is_redirect'] = $row['is_redirect'];
        $page ['redirect_target'] = $row['redirect_target'];
    }
    // print_r($page);
    
    return (object)$page;
}

}
?>