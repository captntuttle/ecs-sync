(this page applies to ecs-sync 3.0+) # Starting the ecs-sync service

The `ecs-sync
OVA <https://object.ecstestdrive.com/share/ECS-Sync%20OVA%20-%203.1.4.ova?AWSAccessKeyId=130820690509421904%40ecstestdrive.emc.com&Expires=1527085429&Signature=Zp8Y3JwLRB9VsMPkpRLaUw0%2BABs%3D>`__
comes with ecs-sync installed and running as a service. However, if
you're **not** using the OVA, you need to start ecs-sync in REST mode so
you can submit jobs via XML configuration file. The best way is to
`install it as a service <Service-Installation>`__ (the same way the OVA
is configured). If that's not an option, you can also manually start the
service to run in the background. To do this, simply run the following:

``nohup java -jar ecs-sync-3.0.jar --rest-only > /var/log/ecs-sync.log &``

This will start ecs-sync in the background in REST mode detached from
the current console (it will still run after you exit the shell). The
logs will go to ``/var/log/ecs-sync.log`` (it's also a good idea to
rotate this via logrotated).

**Note:** only one instance of ecs-sync should be running at a time. To
be sure you're not running more than one, check for existing instances
with ps:

``ps -ef | grep java | grep ecs-sync``

Preparing the Configuration File
================================

Ecs-sync is designed to be run from a submitted xml file that contains
all necessary options, addresses, and credentials. This file can be
created by hand (there are examples in
``ecssync/ecs-sync-[version]/sample``) or easily via the newly included
XML Generator. A guide to the XML Generator can be found
`here <https://github.com/EMCECS/ecs-sync/wiki/XML-Generator-(3.0.1)>`__.
Once a proper xml configuration file has been created and modified with
the correct information, you're ready to begin your sync.

**Note:** filters will be applied in the order they are specified in the
XML (this is true for legacy-cli and UI as well)

Starting a Sync
===============

To start a sync, you should run the following:

``ecs-sync-ctl --submit <config-file>.xml``

Where <config-file>.xml is the path to your configuration XML file. Note
that the XML format has drastically changed in 3.0 given the new
universal configuration model. There are several sample XML files `here
on github <https://github.com/EMCECS/ecs-sync/tree/master/sample>`__ or
on the OVA in ~ecssync/ecs-sync-3.1/sample/. Use these as a guide.

The above command will return a job ID. It's important to keep track of
this ID so you don't confuse this job with another.

Note that all of the commands on this page assume you are using the OVA,
which has a pre-configured path to make these commands easier to run.
However, if you are not using the OVA, be aware that you will not have
the scripts in your path. The scripts are located in the ova/bin/
directory of the distribution.

Alternate (legacy) CLI execution
--------------------------------

You can also execute a sync in a separate process by passing CLI
arguments directly to the ecs-sync jar. Prior to 3.0, this was the
standard method of executing most syncs. Note that when running in a
separate process, when the sync completes, the REST server dies, so you
lose the ability to query status info (you will have to check the log
file to see the results of the sync).

To run a sync with an XML configuration via the CLI:

``nohup java -jar ecs-sync-3.0.jar --xml-config <config-file>.xml > <log-file>.log &``

You can also pass the entire configuration as CLI parameters instead of
using an XML file. Please refer to the `full CLI
syntax <CLI-Syntax-(3.0)>`__ for all available options.

Checking status
===============

To check status of all syncs, use the --list-jobs command like so:

``ecs-sync-ctl --list-jobs``

This will list all the jobs the service is aware of and what their
status is. It's important to keep track of your job IDs so you can tell
them apart.

To list detailed status of a specific job, use the --status command:

``ecs-sync-ctl --status <job-id>``

Where <job-id> is the job ID of the job.

Changing Thread Count
=====================

To change thread count use the --set-threads command:

``ecs-sync-ctl --set-threads <job-id> --threads 32``

The above will set the thread count to 32 for the <job-id> job. Note
that changing thread counts happens gracefully, so if you reduce the
thread count, running threads are allowed to finish their transfers
before being shut down.

Pausing/Resuming/Stopping
=========================

To pause a job:

``ecs-sync-ctl --pause <job-id>``

This operation gradually pauses the job by stopping new objects from
entering the transfer pool. Existing objects are allowed to finish.

To resume a job:

``ecs-sync-ctl --resume <job-id>``

This will resume the processing of new objects in the transfer pool
exactly where it left off when a pause operation was executed.

To completely stop and abandon a job:

``ecs-sync-ctl --stop <job-id>``

This is behaviorally the same as pause, except that you cannot resume
the job.

Deleting a job
==============

You may notice over time that there are many jobs listed by the service
and it may become confusing to sort them all. For this reason, it is
recommended that after you are satisfied with the completion of a job
and have collected any useful information from it (note the database
also contains detailed info as well), you should delete the job from the
list. You will no longer be able to see the job summary after this is
done.

``ecs-sync-ctl --delete <job-id>``

A job must be stopped or completed before deleting it.
