
Tasks
=====

Communication
-------------

Slack or Google Hangouts - Please email me at maurice.tadros@gmail.com and I'll add you to a slack.

Documentation
-------------

Everything needs to be documented.  The original developer did a good job, took a full time position with someone else and is not available to do this work.

He is available for a couple of small questions (not too much) and a reference if you want.  The documentation is built by CI, and uses sphinx.  So any push to the repo will build it.

For more details see :ref:`documentation-notes`


Immutable Scope of Work
-----------------------

To ensure that this Scope of work is immutable, I suggest when we are finished defining this - we do a md5sum on the curl and put that in the upwork part.

.. code-block:: bash

    curl -u bowriverstudio:venividivici https://docs.bowriverstudio.com/geolinkis/tasks.html | md5sum | cut -d ' ' -f 1

Does that work for you?

IE: Word defined by document: 05723034fce846ee9bd58a452f9a5d7e (Will be different hash)


Repositories
------------

Files to Be Indexed are in: git@gitlab.com:geolinkis/wellrecordarchiver-files-to-index.git

The Docker and configuration logic is in git@gitlab.com:geolinkis/wellrecordarchiver.git


Project Task 1:  Ability to detect duplicate files/folders
----------------------------------------------------------

IN the Repo, there is a folder "Duplicate" And "Triplicate" with duplicate and triplicate test files.

Also in Duplicate there is a "Duplicated folder"  I'm not sure if the suggestion below is the best method to identify duplicate files/folders

Let us define the strategy to accomplish this.

Notes
.....

I asked about one method: https://discuss.elastic.co/t/fscrawler-duplicate-files/194322

I am interested in using FScrawler to index all my files, then Elasticsearch find duplicates.

I'm thinking of using the md5 function to hash all the files: https://fscrawler.readthedocs.io/en/fscrawler-2.5/admin/fs/local-fs.html#file-checksum

Then use something like https://www.elastic.co/guide/en/elasticsearch/reference/current/search-aggregations-bucket-terms-aggregation.html to search for duplicates.


Project Task 2: Benchmark OCR Libraries
---------------------------------------

Please Benchmark - the files: geolinkis/wellrecordarchiver-files-to-index/Benchmark with the below Libraries.

From the blog: https://www.amplenote.com/blog/2019_examples_amazon_textract_rekognition_microsoft_cognitive_services_google_vision

I believe some of the strongest OCR libraries are:
 - https://aws.amazon.com/rekognition/
 - https://aws.amazon.com/textract
 - https://cloud.google.com/vision/docs/ocr
 - https://docs.microsoft.com/en-us/azure/cognitive-services/computer-vision/concept-recognizing-text


Project Task 3: Integrate Library into Fscrawler
------------------------------------------------

Based on the Benchmark, we will pick one API to use.

I requested this feature: https://github.com/dadoonet/fscrawler/issues/794

- I will fork the repo and give you access (Public)
- You can modify the code accordingly.
- We will use the fork, until (if) the API is integrated.
