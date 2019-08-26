.. highlight:: rst

.. _documentation-notes:


===================
Documentation Notes
===================


Install Sphinx Documentation
----------------------------

.. code-block:: bash

    git clone git@gitlab.com:geolinkis/wellrecordarchiver-softsuave.git

- Follow `this steps <https://www.sphinx-doc.org/en/1.6/install.html>`_ to install Sphyinx.

- Install sphinx dependencies.

.. code:: Bash

    pip install sphinx
    pip install sphinxcontrib-plantuml
    pip install sphinxcontrib-needs
    pip install sphinx_rtd_theme
    pip install sphinx-tabs


- Build the documentation

MAC
        .. code-block:: bash

            rm -rf documentation documentation/docs
            sphinx-build -b html documentation documentation/docs


PC
        .. code-block:: bash

            RMDIR /Q/S documentation documentation/docs
            sphinx-build -b html documentation documentation/docs


Ensure there are no errors or warnings.

View Documentation locally
--------------------------

Get the absolute path of MyPathToProject/wellrecordarchiver-softsuave/documentation/docs/index.html and view it in chrome.

Images
------

I want all images to be built with draw.io - and the source file to be shared.


Cheat Sheet
-----------

Here is a cheap sheet for the RST formatting: https://docs.typo3.org/m/typo3/docs-how-to-document/master/en-us/WritingReST/CheatSheet.html


