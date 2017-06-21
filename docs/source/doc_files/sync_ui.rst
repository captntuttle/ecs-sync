Getting Started
===============

Ecs-sync requires an XML configuration file in order to run a sync.
Previously these had to be written by hand, or by using the XML
generator. While these are still legitimate options for running
ecs-sync, there is now an simpler, faster, and easier way.

The new ecs-sync UI has been released, making running migrations simpler
than ever before. The following guide will lay out instructions for its
use. With the addition of the new web UI it is no longer necessary to
run migrations through the command line or manually create, or edit, the
required XML config files

Installing the UI
-----------------

The new UI is installed easily and instructions can be found
`here <https://github.com/EMCECS/ecs-sync/wiki/Service-Installation>`__
with the general ecs-sync instructions.

Logging In
----------

The default credentials for the sync UI are admin/ecs-sync. It is, of
course, recommended that this password be changed immediately. The
default password is changed by running
``sudo htpasswd /etc/httpd/.htpasswd admin`` on the ecs-sync VM.

After a fresh installation the UI must be initialized before it can be
used. Upon first login the user will see: |image0|

This means that the UI must be initialized before being used. As noted
in the troubleshooting page, the grails error can be ignored and will
resolve once a user email is successfully submitted. Instructions can be
found
`here <https://github.com/EMCECS/ecs-sync/wiki/Initializing-the-UI>`__.

Note the option to store configuration files on a remote ECS server.
This is a helpful way of preserving configuration files independently of
ecs-sync servers, in the case of teardown, rebuild, etc.

Starting a sync
---------------

After the UI is initialized it is ready to be used. A single, one-time
sync can be run by going to the 'Status' tab on the top left. This will
show the user any currently active jobs, a "New Sync" button, and basic
user statistics. Clicking the "New Sync" shows source, target, and sync
options fields. Source and target are used to select the appropriate
plugins for the sync. Selecting the desired plugins yields: |image1| As
you can see the UI prompts the user for the information required for
each plugin to function. It may be necessary to change default settings
such as port number, VDCs, etc. This is done by clicking on "show
advanced options." Be sure to take a look at these options before
starting your sync, as some may be necessary.

Filters
~~~~~~~

Note that filters will be applied in the order specified, so make sure
the order is appropriate. For example, a source-extraction filter should
come before a target-ingest filter.

Sync Options
~~~~~~~~~~~~

While these fields are optional, they can prove to be very important.
Object list, Verify, and Thread Count, are all important options that
should be considered before running your sync

Database Table
^^^^^^^^^^^^^^

Particular attention must be paid to the field "Db Table." Ecs-sync
records every sync in a database table that is available for later
review. However, if this field is left blank, that is the database
remains unnamed, ecs-sync will consider the table **temporary** and
***the table will be wiped on completion***. If the table is named
before the sync, it will be retained until manually wiped. This is
important to note as the table may be necessary to the user at a later
time. Please keep this in mind for every sync.

.. |image0| image:: https://github.com/captntuttle/pics/blob/master/Screen%20Shot%202017-01-17%20at%209.15.33%20AM.png
.. |image1| image:: https://github.com/captntuttle/pics/blob/master/Screen%20Shot%202017-01-26%20at%209.33.31%20AM.png
