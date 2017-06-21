Creating Config File
====================

We recommend running ecs-sync by submitting an XML configuration file as
a job. To this end we’ve built and included a new XML generator tool.
This tool will generate a template configuration file to include the
necessary plugins for your migration. This is done according to the
options passed to it. Running the generator constructs an XML file that
has all of the available options set to their defaults (if available).

Please note:
------------

-  This tool does not create a complete configuration, only a template
   that must be expanded and/or modified before it is ready to be run.

-  The XML generator tool does not currently include error checking.
   This means that incorrect values, if passed into the generator, will
   also be passed into the newly created XML configuration file.
   **Please review the generated XML file before submitting it as a job
   to ecs-sync in order to ensure a successful migration.**

-  Several sample configuration files are included in the repo under
   ecs-sync/sample. Please refer to these samples when building your
   configuration file.

-  Some options will appear in the template with a value, but that value
   may not be valid. When generating, you can also optionally include
   comments describing each option and it's possible values and default
   value.

The new generator is part of the ecs-sync-ctl tool and invoked like so:
-----------------------------------------------------------------------

::

    usage: java -jar ecs-sync-ctl-{version}.jar --xml-gen <output-file>
           [--xml-comments] [--xml-source <source-prefix>] [--xml-filters
           <filter-list>]  [--xml-target <target-prefix>]
        --xml-comments                  Adds descriptive comments to the
                                        generated config file
        --xml-filters <filter-list>     A comma-delimited list of names of
                                        filters to use as the source in the
                                        generated config file (optional)
        --xml-gen <output-file>         Generates a verbose XML config file
                                        for the specified plugins
        --xml-source <source-prefix>    The prefix for the storage plugin to
                                        use as the source in the generated
                                        config file
        --xml-target <target-prefix>    The prefix for the storage plugin to
                                        use as the target in the generated
                                        config file

Notice that XML Generator requires three arguments to run successfully.
They are:

-  Desired name of the xml file being created
-  Necessary storage plugin for source
-  Necessary storage plugin for target

Available storage plugins and their appropriate uses can be found
`here <https://github.com/EMCECS/ecs-sync/wiki/Storage-Plugins>`__.

For example
``ecs-sync-ctl --xml-gen example.xml --xml-source s3 --xml-target ecs-s3``
outputs the file ``example.xml`` for a sync **coming from** s3 type
storage and **going to** ecs s3 type storage.

Example.xml sets the following options for the transfer:

::

    <syncConfig xmlns="http://www.emc.com/ecs/sync/model">
        <options>
            <bufferSize>524288</bufferSize>
            <dbConnectString>dbConnectString</dbConnectString>
            <dbFile>dbFile</dbFile>
            <dbTable>dbTable</dbTable>
            <deleteSource>false</deleteSource>
            <forceSync>false</forceSync>
            <ignoreInvalidAcls>false</ignoreInvalidAcls>
            <logLevel>quiet</logLevel>
            <monitorPerformance>true</monitorPerformance>
            <recursive>true</recursive>
            <rememberFailed>false</rememberFailed>
            <retryAttempts>2</retryAttempts>
            <sourceListFile>sourceListFile</sourceListFile>
            <syncAcl>false</syncAcl>
            <syncData>true</syncData>
            <syncMetadata>true</syncMetadata>
            <syncRetentionExpiration>false</syncRetentionExpiration>
            <threadCount>16</threadCount>
            <timingWindow>1000</timingWindow>
            <timingsEnabled>false</timingsEnabled>
            <verify>false</verify>
            <verifyOnly>false</verifyOnly>
        </options>

for the source:

::

            <awsS3Config>
                <accessKey>accessKey</accessKey>
                <bucketName>bucketName</bucketName>
                <createBucket>false</createBucket>
                <decodeKeys>false</decodeKeys>
                <disableVHosts>false</disableVHosts>
                <host>host</host>
                <includeVersions>false</includeVersions>
                <keyPrefix>keyPrefix</keyPrefix>
                <legacySignatures>false</legacySignatures>
                <mpuPartSizeMb>128</mpuPartSizeMb>
                <mpuThreadCount>4</mpuThreadCount>
                <mpuThresholdMb>512</mpuThresholdMb>
                <port>-1</port>
                <preserveDirectories>false</preserveDirectories>
                <protocol>protocol</protocol>
                <secretKey>secretKey</secretKey>
                <socketTimeoutMs>50000</socketTimeoutMs>
            </awsS3Config>
        </source>

and for the target:

::

        <target>
            <ecsS3Config>
                <accessKey>accessKey</accessKey>
                <apacheClientEnabled>false</apacheClientEnabled>
                <bucketName>bucketName</bucketName>
                <createBucket>false</createBucket>
                <decodeKeys>false</decodeKeys>
                <enableVHosts>false</enableVHosts>
                <geoPinningEnabled>false</geoPinningEnabled>
                <host>host</host>
                <includeVersions>false</includeVersions>
                <keyPrefix>keyPrefix</keyPrefix>
                <mpuDisabled>false</mpuDisabled>
                <mpuPartSizeMb>128</mpuPartSizeMb>
                <mpuThreadCount>4</mpuThreadCount>
                <mpuThresholdMb>512</mpuThresholdMb>
                <port>0</port>
                <preserveDirectories>false</preserveDirectories>
                <protocol>protocol</protocol>
                <secretKey>secretKey</secretKey>
                <smartClientEnabled>true</smartClientEnabled>
                <socketConnectTimeoutMs>15000</socketConnectTimeoutMs>
                <socketReadTimeoutMs>60000</socketReadTimeoutMs>
                <vdcs>vdcs</vdcs>
            </ecsS3Config>
         </target>

As noted previously, many fields, such as accessKey, bucketName,
protocol, port, secretKey, etc. are set to placeholder values and must
be changed accordingly depending on each specific case. ***Without
changing these placeholder values the configuration file cannot be run
successfully***. All values not filled with a placeholder are set to
default values that may or may not apply to any particular case. ***Be
sure to review these values before submitting as a job*** as they may
need to be changed in order to fit your situation.
