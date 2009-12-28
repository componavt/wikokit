#!/usr/bin/perl -w

# DESCRIPTION: 
#
#   Parse text or russian synonym dictionary, build @synsets, @synwords
#
# AUTHOR:       Andrew Krizhanovsky (http://code.google.com/p/wikokit)
# START DATE:   17.12.2009 17:44:28
# FINISH DATE:  
# SEE: http://www.perlmonks.org/index.pl?node_id=150476

use strict;
no strict 'refs';

use DBI;
use Getopt::Std;

my( $headline, $str_today, $fn_in, $fn_out, $fn_log, $fn_err, $fsize);
my( $text);

my( $log_text, $err_text);
my( $synsets, $synwords, %unique_synwords);
my( $i, $set, $w);


$headline = "mysql2sqlite V0.01 wiwordik (GNU-GPL) 2009 AKA MBG \n";
# --------------------------------------------------------------
# subroutine help_exit
# --------------------------------------------------------------
#wrap up in, s
sub help_exit
{
	if ($#ARGV != 1){
		print "\n".$headline."\n".
        "Usage:\n  mysql2sqlite.exe MySQL_dump.sql > out\n".
        "  MYSQL_DUMP.SQL  - dump of parsed Wiktionary database\n".
        "Examples: mysql2sqlite.exe MySQL_dump.sql SQLite_dump.sql";
	}
	
	if (1 != $#ARGV){
  	  close(STDOUT); # baffle banners ;)
      exit(0);
    }
}
  help_exit;
  print "\n".$headline."\n";
  print "Processing files ...\n";
  
  # READ COMMAND LINE
  # --------------------------------------------------------------
  # must be two arguments
  $fn_in = $ARGV[0];
  $fn_log = $fn_in."_log";
  $fn_err = $fn_in."_error";
  $fn_out = $ARGV[1];

  # open, copy files to buffer, close
  # h - FILE HANDLE
  # --------------------------------------------------------------
  open (h_in,"<".$fn_in) or die ("Cannot open input file ".$fn_in);
  $fsize = (stat ($fn_in))[7];
#  read h_in, $text, $fsize;
#  close (h_in);
#  print "Read ${fn_in}.\n";

  open (h_out,">".$fn_out) or die ("Cannot open output file ".$fn_out);

  my @index;
  my @tablename;
  my $b_table_mode = 0; # current lines belong to the CREATE TABLE SQL command
 
  my $prev_line = "-- ".$headline; # previous SQL line, cause - I hate comma before bracket ",);"
  my $newline = "";

  LINE: while(<h_in>)
  {
      my($line) = $_;             # SQL line
      $newline = "\n";
      chomp($line);

      if(!$b_table_mode) {

              if($line =~ m/create \s+ table \s+ \"?([._-a-zA-Z]+)\"? /ix) {
                push @tablename, $1;
                $b_table_mode = 1;
                # print "\nb_table_mode=${b_table_mode}; line=".$line;
                # print "\n".join(" ", @tablename) if $#tablename > -1;
              }

      } else { 

              # $line =~ s/^#.*$//mg;           # chokes on comments # ...
              # $line =~ s/^\/\*.*\*\/;$//mg;   # chokes on comments /* ... */
              
              $line =~ s/\s*COMMENT.*/,/mg;     # remove COMMENT
              $line =~ s/unsigned\s*//ig;       # remove "unsigned"
              $line =~ s/CHARACTER SET latin1 COLLATE latin1_bin\s*//ig; # remove "CHAR... "

              # remove lines with text "PRIMARY KEY"
              next LINE if $line =~ s/^\s*PRIMARY KEY.*//i;

              $line =~ s/AUTO_INCREMENT/PRIMARY KEY/ig; # replace AUTO_INCREMENT by PRIMARY KEY

              # remove lines with text "CONSTRAINT ... FOREIGN KEY (...) REFERENCES"
              next LINE if $line =~ s/^\s*CONSTRAINT.*//i;
              
              if(");" eq substr($line,0,2)) {
                $b_table_mode = 0;
                $prev_line =~ s/,$//;     # remove comma in last line in CREATE TABLE
                # print "\nThe trying to remove comma: b_table_mode=${b_table_mode}; line=".$line."; prev_line=".$prev_line.".";
              }

              if("  KEY" eq substr($line,0,5) ||
                 "  UNIQUE KEY" eq substr($line,0,12)
                )
              {      
                      # UNIQUE KEY "unique_page_lang_pos" ("page_id","lang_id","pos_id")      
                      # 
                      # KEY "idx_page_title" ("page_title"(7))
                      # CREATE INDEX "idx_page_title" ON node("page_title"(7));
                      #
                      # \(\s*KEY\s\+"\?[a-zA-Z_]\+"\?\)\s*
                      $line =~ s{\s*KEY\s+"?([a-zA-Z_]+)"?\s*(.*)}{
                                    my($s1, $s2) = ($1, $2);
                                    $s2 =~ s/\(\d+\)//g; # ("page_title"(7)), -> ("page_title"),
                                    $s2 =~ s/,$//;      # ("page_title"), -> ("page_title")
                                    
                                    my $s = "CREATE INDEX \"$tablename[-1]_$s1\" ON \"$tablename[-1]\" $s2;";
                                    push @index, $s;
                                    "";
                                }giemx;
                                    # print "\n s=".$s;
                                    # push @{$index{$tablename[-1]}},$s;
                                    #
                      # print "keys: ".join(" ", keys %index) if length(keys %index) > -1;
                      # print "\n len index=".length(keys %index);
                      next LINE;
              }
      }
      # Print the line to the result file and add a newline
      print h_out $prev_line.$newline;
      $prev_line = $line;
  }
  print h_out $prev_line.$newline;
  
  print h_out "\n\n-- Index list\n";
  
  foreach my $index_line ( @index ) {
    print h_out "\n".$index_line;
    # print "\n index_line=".$index_line;                                
  }

  EXIT:
  # close output&log file
  close (h_out);
  
  print "\n";
  close(STDOUT); # baffle banners ;)
