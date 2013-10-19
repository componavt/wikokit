<?php

if (isset($_SERVER) && isset($_SERVER["SERVER_NAME"]) && $_SERVER["SERVER_NAME"]=='vepsian.krc.karelia.ru') {
	$root=$_SERVER["DOCUMENT_ROOT"];
	$site_url="/";
} else {
	$root="D:/all/projects/corpus/vepsian/trunk/vepsian/temp/piwidict";
	$site_url="/~dadada/";
}
$PHP_SELF=$_SERVER["PHP_SELF"];

if (substr($root,-1,1) != "/") $root.="/";
define("SITE_ROOT",$root);
define("LIB_DIR",SITE_ROOT."lib/");

// misc classes
/*include_once(LIB_DIR."sessione.php");
include_once(LIB_DIR."array_util.php");
include_once(LIB_DIR."string_util.php");
include_once(LIB_DIR."mysql_util.php");
*/
// dictionary classes
include_once(LIB_DIR."sql/tlang.php");
include_once(LIB_DIR."sql/trelation_type.php");
include_once(LIB_DIR."sql/tlang_pos.php");
include_once(LIB_DIR."sql/tpos.php");
include_once(LIB_DIR."sql/tpage.php");

foreach ($_REQUEST as $var=>$value)
   $$var = $value;

//if (!isset($query_to_db)) $query_to_db = '';

$NAME_DB = 'ruwikt20130815_parsed';
global $NAME_DB;

$config['hostname']   = 'localhost';
$config['login']      = 'javawiki';
$config['password']   = '';
$config['dbname']     = $NAME_DB;
    
//$link_db = mysql_connect($config['db']['hostname'], $config['db']['login'], $config['db']['password']) or die("[DB]: Could not connect : ". mysql_error());

//$sock = mysqli_connect($config['hostname'], $config['login'], $config['password']);//, $config[$db]);

//if (!$sock) { 
//    echo 'Problem connecting!' . mysqli_connect_error();
//}

//exit(0);
global $LINK_DB;
//$LINK_DB = connectMySQL($config);

global $INTERFACE_LANGUAGE;
$INTERFACE_LANGUAGE = "ru"; 
// $INTERFACE_LANGUAGE = "en"; 

##------------------------------------------------------------------------------------------------------
## DB connection 
## mysql>GRANT SELECT, INSERT, UPDATE ON vepsian.* TO vepsian@'%' identified by 'W4qOfWf';
## mysql>FLUSH PRIVILEGES;
##
//function connectMySQL($config) {
function connectMySQL() {
    global $config;
    // print_r ($config);
    
    $sock = mysqli_connect($config['hostname'], $config['login'], $config['password']) //, $config['db'][$db]) 
            or die ("Could not connect to MySQL server! " +
                    "or could not select database ".$config['dbname']."!");
    
    mysqli_select_db($sock, $config['dbname'])
            or die("Could not select database ".$config['dbname']."!");
    mysqli_set_charset($sock, "binary"); // utf8
    return $sock;
}

?>