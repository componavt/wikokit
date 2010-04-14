#!/usr/bin/perl -w

# DESCRIPTION: 
#
#   Adds transaction commands: BEGIN...COMMIT every 50 000 lines 
#
# AUTHOR:       Andrew Krizhanovsky (http://code.google.com/p/wikokit)
# START DATE:   2010
# FINISH DATE:  
# SEE: 
#   Let's speed up SQLite http://componavt.livejournal.com/3393.html

use strict;
no strict 'refs';

use Getopt::Std;

my( $headline, $str_today, $fn_in, $fn_out, $fn_log, $fn_err, $fsize);
my( $n_period, $n_line);

my( $log_text, $err_text);
my( $n_transaction, $add_text_start, $add_text_middle, $add_text_end);

$headline = "add_transactions.pl V0.01 (GNU-GPL) 2010 AKA MBG\n";
# --------------------------------------------------------------
# subroutine help_exit
# --------------------------------------------------------------
#wrap up in, s
sub help_exit
{
	if ($#ARGV != 1){
		print "\n".$headline."\n".
        "Usage:\n  add_transactions.exe in_dump.sql out\n".
        "  in_dump.sql - dump of the SQLite database\n".
        "Examples: add_transactions.exe in_dump.sql out.xml";
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

  # number of lines (in dump file) between each commit
  $n_period = 10000;

  # open, copy files to buffer, close
  # h - FILE HANDLE
  # --------------------------------------------------------------
  open (h_in,"<".$fn_in) or die ("Cannot open input file ".$fn_in);
  $fsize = (stat ($fn_in))[7];
#  read h_in, $text, $fsize;
#  close (h_in);
#  print "Read ${fn_in}.\n";

  open (h_out,">".$fn_out) or die ("Cannot open output file ".$fn_out);

  #my $b_table_mode = 0; # current lines belong to the CREATE TABLE SQL command

  $add_text_start = "\n\nBEGIN;\n\n";
  $add_text_middle = "\n\nCOMMIT;\nBEGIN;\n\n";
  $add_text_end = "\n\nCOMMIT;\n\n";
 
  print h_out $add_text_start;
 
  $n_line = 0;
  $n_transaction = 1;
  LINE: while(<h_in>)
  {
       $n_line ++;

      if (0 == $n_line % $n_period) {   # $n_period-th line
          $n_transaction ++;
          print h_out $add_text_middle;
          print ".";
      }
      
      #my($line) = $_;             # file line
      # chomp($line);

      # remove lines with text "<redirect />"
      # next LINE if $line =~ s/^\s*\<redirect\s\/\>//;
      # next LINE if -1 ne index $line, "<redirect />";

      # Print the line to the result file and add a newline
      print h_out $_;
  }

  print h_out $add_text_end;
  print "Number of transactions: ".$n_transaction;
  
  EXIT:
  # close output&log file
  close (h_out);
  
  print "\n";
  close(STDOUT); # baffle banners ;)
