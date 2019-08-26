
=============
Google Vision
=============

https://cloud.google.com/vision/docs/quickstart#setting_up_an_api_key


@TODO ramesh.t@softsuave.com

Please write the "REST & CMD LINE" as described in https://cloud.google.com/vision/docs/ocr


.. code-block:: bash

    #ie: something like this but correct (Over write this with the correct syntax)
    curl -X POST \
    -H "Authorization: Bearer "$(gcloud auth application-default print-access-token) \
    -H "Content-Type: application/json; charset=utf-8" \
    -d @request.json \
    https://vision.googleapis.com/v1/images:annotate



- Generate all the results in: wellrecordarchiver-files-to-index/Benchmark/results/google-vision









Trial using API Explorer
------------------------



https://cloud.google.com/vision/docs/ocr








Setup API
---------

https://cloud.google.com/sdk/docs/authorizing#authorizing_with_a_service_account

Project Name: wellrecordachiever
Organization: None




Notes
-----

Installed gcloud
https://cloud.google.com/sdk/docs/quickstart-macos


Ran

.. code-block:: bash

    curl -X POST \
    -H "Authorization: Bearer "$(gcloud auth application-default print-access-token) \
    -H "Content-Type: application/json; charset=utf-8" \
    -d @request.json \
    https://vision.googleapis.com/v1/images:annotate

Got the message

.. code-block:: json

    {
      "error": {
        "code": 403,
        "message": "The request is missing a valid API key.",
        "status": "PERMISSION_DENIED"
      }
    }

