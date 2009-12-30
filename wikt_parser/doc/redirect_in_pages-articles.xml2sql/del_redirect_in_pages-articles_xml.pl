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


$headline = "del_redirect_in_pages-articles_xml V0.01 (GNU-GPL) 2009 AKA MBG \n";
# --------------------------------------------------------------
# subroutine help_exit
# --------------------------------------------------------------
#wrap up in, s
sub help_exit
{
	if ($#ARGV != 1){
		print "\n".$headline."\n".
        "Usage:\n  del_redirect_in_pages-articles_xml.exe in_dump.sql > out\n".
        "  wiki-pages-articles.xml - dump of Wiki database\n".
        "Examples: del_redirect_in_pages-articles_xml.exe wiki-pages-articles.xml out.xml";
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
 
  LINE: while(<h_in>)
  {
      my($line) = $_;             # file line
      chomp($line);

      # remove lines with text "<redirect />"
      # next LINE if $line =~ s/^\s*\<redirect\s\/\>//;
      next LINE if -1 ne index $line, "<redirect />";

      # Print the line to the result file and add a newline
      print h_out $line."\n";
  }
  
  EXIT:
  # close output&log file
  close (h_out);
  
  print "\n";
  close(STDOUT); # baffle banners ;)
