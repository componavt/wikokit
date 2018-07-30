#!/usr/bin/env python
# -*- coding: utf-8 -*-

import json
import urllib
import requests


# Creates thumb image URL by filename and required width.
# filename - name of file at Commons
def createThumbImageURL( filename, width ):
    "Creates thumb image URL by filename and required width"

    # 1. create Wikimedia Commons JSON URL with desired search parameters, see https://www.mediawiki.org/wiki/API:Imageinfo
    # e.g. https://commons.wikimedia.org/w/api.php?action=query&titles=File:Sawhorse.png&prop=imageinfo&iiprop=url&iiurlwidth=220

    url = "https://commons.wikimedia.org/w/api.php?"
    url = url + "action=query"
    url = url + "&titles=File:" + filename
    url = url + "&prop=imageinfo&iiprop=url"
    url = url + "&iiurlwidth=" + str(width)
    url = url + "&format=json"
    # print url # debug
    

    # 2. parse result JSON
    #    return .query.pages.pages[0].imageinfo.thumburl

    #    how to get two parameters at once?:
    #           .query.pages.pages[0].imageinfo.thumbheight

    req = requests.get(url)
    data =json.loads(req.content)
    # print data
    # print data['query']['pages']
    # print data['query']['pages']['750085']

    thumburl = ""
    thumbheight = -1 
    for page in data["query"]["pages"].values():
        thumburl = page["imageinfo"][0]['thumburl']
        thumbheight = page["imageinfo"][0]['thumbheight']
        break

    # print "createThumbImageURL: thumbheight={0}, thumburl={1}".format( thumbheight, thumburl )

    return {'thumburl':thumburl, 'thumbheight':thumbheight}

