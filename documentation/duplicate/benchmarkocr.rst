===============================
Task 2: Benchmark OCR Libraries
===============================

As per my detailed research about to find best and strongest OCR libraries in the current market, I found that Microsoft and Google did best by our sample data among Amazon Rekognition,

Amazon Textract, Microsoft Cognitive Services, Google Cloud Vision API. The selection process was happened respective of precision rate of the extracted text, advantages of the product and limitations as well.

Here I am going to provide brief info about all the OCR which includes their advantages, limitations to choose best OCR finally

OCR
~~~


Amazon Rekognition
==================

I think we no need to consider(more limitations) this OCR since it is not supporting pdf formats and also on image file it will fetch only 50 words.

Limits
------
- Image must be .jpeg or .png format and no larger than 5MB
- It won't support pdf/any other docs
- Only 50 words it can recognise

Test cases
----------

Target file with response (85%)
-------------------------------

.. image:: _static/aws_rekognition_handwriting.png


Amazon Textract
===============

Limits
------

- Your document must be in JPEG, PNG or PDF format
- The maximum document image (JPEG/PNG) size is 5 MB
- The maximum PDF file size is 500 MB
- The maximum number of pages in the PDF file should be 10

Test cases
----------

Target file with response - image (72%)
---------------------------------------

.. image:: _static/aws_textract_handwriting.png

Target file with response - pdf (98%)
---------------------------------------

.. image:: _static/aws_textract_pdf_extraction.png


Microsoft Cognitive Services
============================

Here we have two different api's,

1. Microsoft Computer Vision API - v2.0
2. Microsoft Form Recognizer API

In both the cases We can directly hit those api's with file url or we can simply pass Binary image data of the file to get the response. no other steps include. We are getting expected responses On both image text extraction and pdf text extraction while using this OCR with less integration steps.


Test cases
----------

Target file with response (99.5%)
---------------------------------

.. image:: _static/microsoft_written_text.png


Google Cloud Vision API
=======================

Here we have to user two different api's

1. To detect handwriting/printed text in images
2. Detect text in files (PDF/TIFF)

In both the cases definitely we should need to upload the file(target file) to the Google could storage to get the ``cloud-storage-image-uri``. And that uri we should use while hitting that api to get the extracted text response from the api.

https://cloud.google.com/vision/docs/handwriting

In case of PDF/TIFF file processing, we need to include few more steps to get the response like, Once we hit the api with ``cloud-storage-image-uri`` we will get an ``operation-id``. with that ``operation-id`` again we need to hit another api by providing ``operation-id``, ``gcsDestination`` directory (the folder which is present in the google cloud storage bucket to store response file). So this process may took more steps to integrate. this is the main limitation of Google Cloud Vision API we can say.

https://cloud.google.com/vision/docs/pdf

Limits
------

- The Vision API accepts PDF/TIFF files up to 2000 pages. Larger files will return an error
- Max image file size should be 20 MB

Test cases
----------

Target file (image)
-----------

.. image:: _static/handwritten-text-image.jpg

Extracted text response(99.5%)
------------------------------

.. code-block:: bash

	"description": "HI REDDID,\nI CAME ACROSS THIS SUBREDDIT TODAY\nAND SINCE I LOVE TO HAND-WRITE. I
        THOUGHT\nI WOULD SHARE MY OWN PATNERSHIP. THIS IS\nMY NORMAL. EVERYDAY HANDWRITING. I HAVE\nWRITTEN IN
        THIS STYLE FOR SEVERAL YEARS\nNow. I LIKE THAT IT'S QUICK TO WRITE\nNEAT, AND EASY TO READ.\n",


Target file (PDF)
-----------------

   ``wellrecordarchiver-files-to-index\Benchmark\150902-VB2 EMBER ENTICE 9-15-26-26.pdf``


Extracted text response
-----------------------
Since it was very big file so that's why I have uploaded in the google drive and provided the link here(with permission)

https://drive.google.com/open?id=1ze2kgnG17iZNFIuP2gmPlAVKmVcUcVi-


Finally, the responses from Google api's or Microsoft api's are almost same but in case of Google api integration we should need to make many steps to hit the api and get response. But in case of Microsoft, we can achieve best results in single hit. So, I believe, It is good use Microsoft Computer Vision API - v2.0 to extract text from images and Microsoft Form Recognizer API to extract data from pdfs.