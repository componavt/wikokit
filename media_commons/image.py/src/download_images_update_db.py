#!/usr/bin/env python
# -*- coding: utf-8 -*-

# Downloads thumbs of images from Wikimedia Commons, 
# write data to wikt_parsed database.
# MySQL:
#   use ruwikt20180601_parsed;
#   set names binary;
#   SELECT image_id,filename,image_caption,meaning_id FROM image, image_meaning WHERE image_meaning.image_id=image.id LIMIT 23;
# Input: 
#   ?, 
# Output: 
#   ?
# Calculate:
#   ?

import logging
import sys
import os
import MySQLdb

db = MySQLdb.connect(host="localhost",
                     user="javawiki",
                     passwd="",
                     db="ruwikt20180601_parsed")

logging.basicConfig(format='%(asctime)s : %(levelname)s : %(message)s', level=logging.INFO)

#sys.path.append(os.path.abspath('../')) # add parent folder, access to 'lib'
import lib.create_thumb_url

import configus
width = configus.THUMBWIDTH
print "width={0} ".format( configus.THUMBWIDTH )

# width = 220
# filename = "Sawhorse.png"
# filename = "De Stieglitz lat Carduelis carduelis.jpg"

cursor   = db.cursor(MySQLdb.cursors.DictCursor)
cursor2  = db.cursor(MySQLdb.cursors.DictCursor)

# gets image.filename, fills image.url, .width, .height
cursor.execute("SELECT id, filename FROM image WHERE filename IS NOT NULL;") # i.e. all filenames
result_set = cursor.fetchall()
i = 0;
for row in result_set:
    id       = row['id']
    filename = row['filename']
    # debug print " {0} : {1}".format( id, filename )

    imageinfo = lib.create_thumb_url.createThumbImageURL( filename, width )
    url    = imageinfo['thumburl']
    height = imageinfo['thumbheight']
    print "id={0}, height={1}, url={2}".format( id, height, url )

    cursor.execute("""
       UPDATE image
       SET url=%s, height=%s, width=%s
       WHERE id=%s
    """, (url, str(height), str(width), str(id)))

    db.commit()
    i = i+1
    #if(i > 3):
    #    break

cursor.close()
cursor2.close()
db.close()

sys.exit("\nLet's stop and think.")

