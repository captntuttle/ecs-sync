Installing the Service
======================

The ecs-sync distribution comes with scripts to aid in configuring a
Linux VM to run ecs-sync (and optionally the UI) as a service. In 3.0,
this will be the standard configuration for all methods of running the
tool (CLI, XML or Web UI). In 2.1, it is not required, but it is how
`the
OVA <https://object.ecstestdrive.com/share/ECS-Sync%20OVA%20-%203.1.4.ova?AWSAccessKeyId=130820690509421904%40ecstestdrive.emc.com&Expires=1527085429&Signature=Zp8Y3JwLRB9VsMPkpRLaUw0%2BABs%3D>`__
is configured by default. Here is a rough diagram of the service
architecture.

.. figure:: https://share.object.ecstestdrive.com/ecs-sync-2.1-ui-arch.png?AWSAccessKeyId=130753149435015067@ecstestdrive.emc.com&Expires=1503704369&Signature=fqD0%2F37ZiJY6IMmQW5ibTExqj%2Bg%3D
   :alt: ecs-sync 2.1 UI architecture

   ecs-sync 2.1 UI architecture

As you can see, the 2.1 UI was designed to support a single use-case of
syncing Filesystem directories to ECS buckets. In 3.0, just about all
storage plugins and filters will be supported as well. The goal for 3.0
is to allow any type of configuration to be submitted via CLI, XML or
Web UI using essentially the same structure.

The installation scripts will install a number of dependencies,
including Sqlite, MariaDB (OSS mySQL), Java, some standard analysis
tools, etc. The procedure for preparing a VM and running these scripts
is outlined below.

1. Create a 64-bit Linux VM (CentOS or RHEL 6.4+ is supported).

   -  Ideally, the VM should have at least 8 vCores and 16GB memory, but
      the biggest requirement is to maximize the network pipe to both
      the source and the target.
   -  Our internal OVA uses CentOS 7 minimal.

2. Upload the ecs-sync distribution zip and UI jar file to the VM.

   -  Get these files from the latest release page
      `here <https://github.com/EMCECS/ecs-sync/releases>`__.
   -  Unzip the distribution zip (after which you can delete the zip
      file), then move the UI jar into the folder that was created (next
      to the ecs-sync jar).

3. Update installed packages to latest version: ``sudo yum update``
4. Run the OS configuration script.

   -  As root, change CWD to the distribution folder and run
      ``ova/configure-centos.sh``.
   -  At some point, you will be guided through the
      ``mysql_secure_installation`` script. There is no root password by
      default; you should set one.
   -  Remember your mySQL root password so that you can enter it when
      prompted later.

5. Run the ecs-sync install script.

   -  This will install the ecs-sync and ecs-sync-ui services. If you
      are running 2.1 in CLI-only, you can skip this step.
   -  As root, from within the distribution folder, run
      ``ova/install.sh``.

6. If you are performing any CAS migrations, install the `CAS
   SDK <https://developer-content.emc.com/downloads/centera/download.htm>`__.
7. If you are writing to a filesystem (NFS, SMB, etc.), install
   `iozone <http://www.iozone.org/>`__ to test write performance.

   -  You should build an RPM from the SRPM, since there is no published
      package available for RedHat.

8. If you are writing to ECS via S3, install
   `bucket-perf <https://share.object.ecstestdrive.com/bucket-perf-3.1.jar?AWSAccessKeyId=130753149435015067@ecstestdrive.emc.com&Expires=1503707596&Signature=77oqhE2C4m7Q4mtV5WE8%2BujD4q0%3D>`__
   to test S3 write performance.

   -  This is just a self-executing jar file, so place it in an
      accessible location on the VM.

Updates and vulnerabilities
---------------------------

Note that our pre-built OVA releases are based on CentOS minimal and,
prior to release, are updated with the latest OS patches. However, there
will always be some updates made available between release and
deployment time. With this in mind, you are encouraged to always
``sudo yum update`` after deployment to avoid any vulnerability exposure
in a production environment.
