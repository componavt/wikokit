#!/usr/bin/perl -w

# DESCRIPTION:   .
#
#   Deletes lines in the text file with text:
#   <redirect />
#   <ns>
#   <sha1 />
#   <model>
#   <format>
#   <parentid>
#
#   Also deletes:
#   <DiscussionThreading>\n...\n</DiscussionThreading>
#
# AUTHOR:       Andrew Krizhanovsky (http://code.google.com/p/wikokit)
# START DATE:   2009
# FINISH DATE:  2013
# SEE: 
#   Talk:Xml2sql   http://meta.wikimedia.org/wiki/Talk:Xml2sql
#   MySQL 2 SQLite http://www.perlmonks.org/index.pl?node_id=150476
# My project wiwordik:
#   http://code.google.com/p/wikokit/wiki/MySQL_import

use strict;
no strict 'refs';

use DBI;
use Getopt::Std;

my( $headline, $str_today, $fn_in, $fn_out, $fn_log, $fn_err, $fsize);
my( $text);

my( $log_text, $err_text);
my( $synsets, $synwords, %unique_synwords);
my( $i, $set, $w);


$headline = "xml2sql_helper V0.04 (GNU-GPL) 2009-2013 AKA MBG \n";
# --------------------------------------------------------------
# subroutine help_exit
# --------------------------------------------------------------
#wrap up in, s
sub help_exit
{
	if ($#ARGV != 1){
		print "\n".$headline."\n".
        "Usage:\n  xml2sql_helper in_dump.sql out_dump.sql\n".
        "  in_dump.sql - dump of Wiki database (wiki-pages-articles.xml)\n".
        "Examples: xml2sql_helper wiki-pages-articles.xml out.xml";
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
  my $b_skip_mode = 0; # 1 == current lines belong to the <DiscussionThreading> body

  #<DiscussionThreading>

  LINE: while(<h_in>)
  {
      my($line) = $_;             # file line
      chomp($line);

      if ($b_skip_mode) {
            $b_skip_mode = 0 if $line eq "</DiscussionThreading>";
            next LINE;
      } else {
            $b_skip_mode = 1 if $line eq "<DiscussionThreading>";
      }
      next LINE if $b_skip_mode;

      # remove lines with text "<redirect />"
      # next LINE if $line =~ s/^\s*\<redirect\s\/\>//;
      next LINE if -1 ne index $line, "<redirect />";
      next LINE if -1 ne index $line, "<redirect"; # e.g.: <redirect title="алтын" />

      # remove lines with text "<ns>", e.g. "<ns>8</ns>"
      next LINE if -1 ne index $line, "<ns>";

      # remove lines with text "<model>", e.g. "<model>wikitext</model>"
      next LINE if -1 ne index $line, "<model>";

      # remove lines with text "<format>", e.g. "<format>text/x-wiki</format>"
      next LINE if -1 ne index $line, "<format>";

      # remove lines with text "<parentid>", e.g. "<parentid>5322</parentid>"
      next LINE if -1 ne index $line, "<parentid>";

      # remove lines with text "<sha1 />"
      next LINE if -1 ne index $line, "<sha1 />";
      next LINE if -1 ne index $line, "<sha1>"; # e.g.: <sha1>0u1</sha1>      

      # Print the line to the result file and add a newline
      print h_out $line."\n";
  }
  
  EXIT:
  # close output&log file
  close (h_out);
  
  print "\n";
  close(STDOUT); # baffle banners ;)
