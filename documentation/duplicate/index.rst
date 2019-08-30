======================================
Task 1: Detect duplicate files/folders
======================================

Generally, Duplicate files in the sense the files have absolutely identical content, size and extensions but might have different file names. Having so many duplicate files creates a mess and disarray in the system.


Steps to detect duplicates
~~~~~~~~~~~~~~~~~~~~~~~~~~

As per investigation, The best approach to find duplicate files over a large no.of files is based on checksum/hash value (MD5/SHA1) of a the files.

So that, by comparing the hash we can easily find the duplicate files since the hash values are unique for identical contents.


Indexing files
--------------

We are going to use FScrawler to index all the files, then Elasticsearch find duplicates.


File checksum
-------------

We want FSCrawler to generate a SHA1 checksum for each file. We can achieve that by setting ``fs.checksum`` property as ``SHA1`` in  ``~/.fscrawler/test/_settings.json`` file.

.. code:: json

  {
     "name": "job name",
     "fs": {
       "checksum": "SHA1"
     }
   }


Use Bucket Aggregation to search for duplicates
-----------------------------------------------

Quering in Kibana to find duplicates files based on the checksum value.


.. code:: json

  {
     "aggs": {
    "duplicate": {
      "terms": {
        "field": "file.checksum",
        "min_doc_count": 2
      },
      "aggs": {
        "duplicate list": {
          "top_hits": {
          }
        }
      }
    }
  }


A bucket mainly consists of a key i.e, ``checksum`` and a document. When the aggregation is executed, the documents are placed in the respective bucket. So finally we should have a list of buckets, each with a list of files.
