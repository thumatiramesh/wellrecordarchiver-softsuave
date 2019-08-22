.. Backup solution for docker deployment documentation master file, created by
   sphinx-quickstart on Sun Jun 30 00:13:06 2019.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.

Index configuration
===================

To query the index in Kibana, configure it under the 'Management' menu:

- Under the index patterns, Define a new pattern by typing ``wellrecord``.
	
.. image:: _static/kibana-create-index-pattern.jpg

- Configure the time filtering field of your index.

.. image:: _static/kibana-create-index-settings.jpg


Index querying
==============

Under 'Discover', select the fields to filter results to. Here we are using the 'content' and 'filer url' feilds.

.. image:: _static/add-content.jpg

.. image:: _static/add-url.jpg


Create a query in the search field. In the given example we are querying for files with containing keyword "1AA063409108W400" so the query is ``"content:"1AA063409108W400"``.

.. image:: _static/query.jpg