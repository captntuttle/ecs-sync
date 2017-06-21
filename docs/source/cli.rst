CLI Syntax
==========

The following is the complete syntax of the CLI arguments for 3.0. Note
that you can also generate this text simply by running:
``java -jar ecs-sync-3.0.jar --help``

Full 3.0 CLI syntax:

::

    EcsSync v3.0
    usage: java -jar ecs-sync.jar -source <source-uri> [-filters <filter1>[,<filter2>,...]]
                -target <target-uri> [options]
    Common options:
        --buffer-size <buffer-size>               Sets the buffer size (in bytes) to use when
                                                  streaming data from the source to the target
                                                  (supported plugins only). Defaults to 512K
        --db-connect-string <db-connect-string>   Enables the MySQL database engine and
                                                  specified the JDBC connect string to connect
                                                  to the database (i.e.
                                                  "jdbc:mysql://localhost:3306/ecs_sync?user=f
                                                  oo&password=bar")
        --db-file <db-file>                       Enables the Sqlite database engine and
                                                  specifies the file to hold the status
                                                  database. A database will make repeat runs
                                                  and incrementals more efficient. You can
                                                  also use the sqlite3 client to interrogate
                                                  the details of all objects in the sync
        --db-table <db-table>                     Specifies the DB table name to use. Use this
                                                  with --db-connect-string to provide a unique
                                                  table name or risk corrupting a previously
                                                  used table. Default table is "objects"
        --delete-source                           Supported source plugins will delete each
                                                  source object once it is successfully synced
                                                  (does not include directories). Use this
                                                  option with care! Be sure log levels are
                                                  appropriate to capture transferred (source
                                                  deleted) objects
        --filters <filter-names>                  The comma-delimited list of filters to apply
                                                  to objects as they are synced. Specify the
                                                  activation names of the filters [returned
                                                  from Filter.getActivationName()]. Examples:
                                                  id-logging
                                                  gladinet-mapping,strip-acls
                                                  Each filter may have additional custom
                                                  parameters you may specify separately
        --force-sync                              Force the write of each object, regardless
                                                  of its state in the target storage
        --help                                    Displays this help content
        --ignore-invalid-acls                     If syncing ACL information when syncing
                                                  objects, ignore any invalid entries (i.e.
                                                  permissions or identities that don't exist
                                                  in the target system)
        --log-level <log-level>                   Sets the verbosity of logging
                                                  (silent|quiet|verbose|debug). Default is
                                                  quiet
        --no-monitor-performance                  Enables performance monitoring for reads and
                                                  writes on any plugin that supports it. This
                                                  information is available via the REST
                                                  service during a sync
        --no-rest-server                          Disables the REST server
        --no-sync-data                            Object data is synced by default
        --no-sync-metadata                        Metadata is synced by default
        --non-recursive                           Hierarchical storage will sync recursively
                                                  by default
        --perf-report-seconds <seconds>           Report upload and download rates for the
                                                  source and target plugins every <x> seconds
                                                  to INFO logging.  Default is off (0)
        --remember-failed                         Tracks all failed objects and displays a
                                                  summary of failures when finished
        --rest-endpoint <rest-endpoint>           Specified the host and port to use for the
                                                  REST endpoint. Optional; defaults to
                                                  localhost:9200
        --rest-only                               Enables REST-only control. This will start
                                                  the REST server and remain alive until
                                                  manually terminated. Excludes all other
                                                  options except --rest-endpoint
        --retry-attempts <retry-attempts>         Specifies how many times each object should
                                                  be retried after an error. Default is 2
                                                  retries (total of 3 attempts)
        --source <source-uri>                     The URI for the source storage. Examples:
                                                  atmos:http://uid:secret@host:port
                                                  '- Uses Atmos as the source; could also be
                                                  https.
                                                  file:///tmp/atmos/
                                                  '- Reads from a directory
                                                  archive:///tmp/atmos/backup.tar.gz
                                                  '- Reads from an archive file
                                                  s3:http://key:secret@host:port
                                                  '- Reads from an S3 bucket
                                                  Other plugins may be available. See their
                                                  documentation for URI formats
        --source-list-file <source-list-file>     Path to a file that supplies the list of
                                                  source objects to sync. This file must be in
                                                  CSV format, with one object per line and the
                                                  identifier is the first value in each line.
                                                  This entire line is available to each plugin
                                                  as a raw string
        --sync-acl                                Sync ACL information when syncing objects
                                                  (in supported plugins)
        --sync-retention-expiration               Sync retention/expiration information when
                                                  syncing objects (in supported plugins). The
                                                  target plugin will *attempt* to replicate
                                                  retention/expiration for each object. Works
                                                  only on plugins that support
                                                  retention/expiration. If the target is an
                                                  Atmos cloud, the target policy must enable
                                                  retention/expiration immediately for this to
                                                  work
        --target <target-uri>                     The URI for the target storage. Examples:
                                                  atmos:http://uid:secret@host:port
                                                  '- Uses Atmos as the target; could also be
                                                  https.
                                                  file:///tmp/atmos/
                                                  '- Writes to a directory
                                                  archive:///tmp/atmos/backup.tar.gz
                                                  '- Writes to an archive file
                                                  s3:http://key:secret@host:port
                                                  '- Writes to an S3 bucket
                                                  Other plugins may be available. See their
                                                  documentation for URI formats
        --thread-count <thread-count>             Specifies the number of objects to sync
                                                  simultaneously. Default is 16
        --timing-window <timing-window>           Sets the window for timing statistics. Every
                                                  {timingWindow} objects that are synced,
                                                  timing statistics are logged and reset.
                                                  Default is 10,000 objects
        --timings-enabled                         Enables operation timings on all plug-ins
                                                  that support it
        --verify                                  After a successful object transfer, the
                                                  object will be read back from the target
                                                  system and its MD5 checksum will be compared
                                                  with that of the source object (generated
                                                  during transfer). This only compares object
                                                  data (metadata is not compared) and does not
                                                  include directories
        --verify-only                             Similar to --verify except that the object
                                                  transfer is skipped and only read operations
                                                  are performed (no data is written)
        --version                                 Displays package version
        --xml-config <xml-config>                 Specifies an XML configuration file. In this
                                                  mode, the XML file contains all of the
                                                  configuration for the sync job. In this
                                                  mode, most other CLI arguments are ignored.

    Available plugins are listed below along with any custom options they may have

    Archive File (archive:)
        The archive plugin reads/writes data from/to an archive file (tar, zip, etc.) It is
        triggered by an archive URL:
    archive:[<scheme>://]<path>, e.g. archive:file:///home/user/myfiles.tar
    or archive:http://company.com/bundles/project.tar.gz or archive:cwd_file.zip
    The contents of the archive are the objects. To preserve object metadata on the target
        filesystem, or to read back preserved metadata, use --store-metadata.
        NOTE: Storage options must be prefixed by source- or target-, depending on which role
        they assume
        --delete-check-script <delete-check-script>   when --delete-source is used, add this
                                                      option to execute an external script to
                                                      check whether a file should be deleted.
                                                      If the process exits with return code
                                                      zero, the file is safe to delete.
        --delete-older-than <delete-age>              when --delete-source is used, add this
                                                      option to only delete files that have
                                                      been modified more than <delete-age>
                                                      milliseconds ago
        --excluded-paths <pattern,pattern,...>        A list of regular expressions to search
                                                      against the full file path.  If the path
                                                      matches, the file will be skipped.
                                                      Since this is a regular expression, take
                                                      care to escape special characters.  For
                                                      example, to exclude all files and
                                                      directories that begin with a period,
                                                      the pattern would be .*/\..*
        --follow-links                                instead of preserving symbolic links,
                                                      follow them and sync the actual files
        --modified-since <yyyy-MM-ddThh:mm:ssZ>       only look at files that have been
                                                      modified since the specifiec date/time.
                                                      Date/time should be provided in ISO-8601
                                                      UTC format (i.e. 2015-01-01T04:30:00Z)
        --store-metadata                              when used as a target, stores source
                                                      metadata in a json file, since
                                                      filesystems have no concept of user
                                                      metadata
        --use-absolute-path                           Uses the absolute path to the file when
                                                      storing it instead of the relative path
                                                      from the source dir

    Atmos (atmos:)
        The Atmos plugin is triggered by the URI pattern:
    atmos:http[s]://uid:secret@host[,host..][:port][/namespace-path]
    Note that the uid should be the 'full token ID' including the subtenant ID and the uid
        concatenated by a slash
    If you want to software load balance across multiple hosts, you can provide a
        comma-delimited list of hostnames or IPs in the host part of the URI.
        NOTE: Storage options must be prefixed by source- or target-, depending on which role
        they assume
        --access-type <access-type>             The access method to locate objects
                                                (objectspace or namespace)
        --preserve-object-id                    Supported in ECS 3.0+ when used as a target
                                                where another AtmosStorage is the source (both
                                                must use objectspace). When enabled, a new ECS
                                                feature will be used to preserve the legacy
                                                object ID, keeping all object IDs the same
                                                between the source and target
        --remove-tags-on-delete                 When deleting from a source subtenant,
                                                specifies whether to delete listable-tags
                                                prior to deleting the object. This is done to
                                                reduce the tag index size and improve write
                                                performance under the same tags
        --replace-metadata                      Atmos does not have a call to replace
                                                metadata; only to set or remove it. By
                                                default, set is used, which means removed
                                                metadata will not be reflected when updating
                                                objects. Use this flag if your sync operation
                                                might remove metadata from an existing object
        --ws-checksum-type <ws-checksum-type>   If specified, the atmos wschecksum feature
                                                will be applied to writes. Valid algorithms
                                                are sha1, or md5. Disabled by default

    S3 (s3:)
        Represents storage in an Amazon S3 bucket. This plugin is triggered by the pattern:
    s3:[http[s]://]access_key:secret_key@[host[:port]]/bucket[/root-prefix]
    Scheme, host and port are all optional. If omitted, https://s3.amazonaws.com:443 is
        assumed. keyPrefix (optional) is the prefix under which to start enumerating or
        writing keys within the bucket, e.g. dir1/. If omitted, the root of the bucket is
        assumed.
        NOTE: Storage options must be prefixed by source- or target-, depending on which role
        they assume
        --create-bucket                         By default, the target bucket must exist. This
                                                option will create it if it does not
        --decode-keys                           Specifies if keys will be URL-decoded after
                                                listing them. This can fix problems if you see
                                                file or directory names with characters like
                                                %2f in them
        --disable-v-hosts                       Specifies whether virtual hosted buckets will
                                                be disabled (and path-style buckets will be
                                                used)
        --include-versions                      Transfer all versions of every object. NOTE:
                                                this will overwrite all versions of each
                                                source key in the target system if any exist!
        --legacy-signatures                     Specifies whether the client will use v2 auth.
                                                Necessary for ECS < 3.0
        --mpu-part-size-mb <size-in-MB>         Sets the part size to use when multipart
                                                upload is required (objects over 5GB). Default
                                                is 128MB, minimum is 5MB
        --mpu-thread-count <mpu-thread-count>   The number of threads to use for multipart
                                                upload (only applicable for file sources)
        --mpu-threshold-mb <size-in-MB>         Sets the size threshold (in MB) when an upload
                                                shall become a multipart upload
        --preserve-directories                  If enabled, directories are stored in S3 as
                                                empty objects to preserve empty dirs and
                                                metadata from the source
        --socket-timeout-ms <timeout-ms>        Sets the socket timeout in milliseconds
                                                (default is 50000ms)

    CAS (cas:)
        The CAS plugin is triggered by the URI pattern:
    cas:[hpp:]//host[:port][,host[:port]...]?name=<name>,secret=<secret>
    or cas:[hpp:]//host[:port][,host[:port]...]?<pea_file>
    Note that <name> should be of the format <subtenant_id>:<uid> when connecting to an Atmos
        system. This is passed to the CAS SDK as the connection string (you can use primary=,
        secondary=, etc. in the server hints). To facilitate CAS migrations, sync from a
        CasStorage source to a CasStorage target. Note that by default, verification of a
        CasStorage object will also verify all blobs.
        NOTE: Storage options must be prefixed by source- or target-, depending on which role
        they assume
        --application-name <application-name>         This is the application name given to
                                                      the pool during initial connection.
        --application-version <application-version>   This is the application version given to
                                                      the pool during initial connection.
        --delete-reason <audit-string>                When deleting source clips, this is the
                                                      audit string.

    ECS S3 (ecs-s3:)
        Reads and writes content from/to an ECS S3 bucket. This plugin is triggered by the
        pattern:
    ecs-s3:http[s]://access_key:secret_key@hosts/bucket[/key-prefix] where hosts =
        host[,host][,..] or vdc-name(host,..)[,vdc-name(host,..)][,..] or load-balancer[:port]
    Scheme, host and port are all required. key-prefix (optional) is the prefix under which to
        start enumerating or writing within the bucket, e.g. dir1/. If omitted the root of the
        bucket will be enumerated or written to.
        NOTE: Storage options must be prefixed by source- or target-, depending on which role
        they assume
        --apache-client-enabled                    Enable this if you have disabled MPU and
                                                   have objects larger than 2GB (the limit for
                                                   the native Java HTTP client)
        --create-bucket                            By default, the target bucket must exist.
                                                   This option will create it if it does not
        --decode-keys                              Specifies if keys will be URL-decoded after
                                                   listing them. This can fix problems if you
                                                   see file or directory names with characters
                                                   like %2f in them
        --enable-v-hosts                           Specifies whether virtual hosted buckets
                                                   will be used (default is path-style
                                                   buckets)
        --geo-pinning-enabled                      Enables geo-pinning. This will use a
                                                   standard algorithm to select a consistent
                                                   VDC for each object key or bucket name
        --include-versions                         Enable to transfer all versions of every
                                                   object. NOTE: this will overwrite all
                                                   versions of each source key in the target
                                                   system if any exist!
        --mpu-disabled                             Disables multi-part upload (MPU). Large
                                                   files will be sent in a single stream
        --mpu-part-size-mb <size-in-MB>            Sets the part size to use when multipart
                                                   upload is required (objects over 5GB).
                                                   Default is 128MB, minimum is 4MB
        --mpu-thread-count <mpu-thread-count>      The number of threads to use for multipart
                                                   upload (only applicable for file sources)
        --mpu-threshold-mb <size-in-MB>            Sets the size threshold (in MB) when an
                                                   upload shall become a multipart upload
        --no-smart-client                          The smart-client is enabled by default. Use
                                                   this option to turn it off when using a
                                                   load balancer or fixed set of nodes
        --preserve-directories                     If enabled, directories are stored in S3 as
                                                   empty objects to preserve empty dirs and
                                                   metadata from the source
        --socket-connect-timeout-ms <timeout-ms>   Sets the connection timeout in milliseconds
                                                   (default is 15000ms)
        --socket-read-timeout-ms <timeout-ms>      Sets the read timeout in milliseconds
                                                   (default is 60000ms)

    Filesystem (file:)
        The filesystem plugin reads/writes data from/to a file or directory. It is triggered
        by the URI:
    file://<path>, e.g. file:///home/user/myfiles
    If the URL refers to a file, only that file will be synced. If a directory is specified,
        the contents of the directory will be synced.  Unless the --non-recursive flag is set,
        the subdirectories will also be recursively synced. To preserve object metadata on the
        target filesystem, or to read back preserved metadata, use --store-metadata.
        NOTE: Storage options must be prefixed by source- or target-, depending on which role
        they assume
        --delete-check-script <delete-check-script>   when --delete-source is used, add this
                                                      option to execute an external script to
                                                      check whether a file should be deleted.
                                                      If the process exits with return code
                                                      zero, the file is safe to delete.
        --delete-older-than <delete-age>              when --delete-source is used, add this
                                                      option to only delete files that have
                                                      been modified more than <delete-age>
                                                      milliseconds ago
        --excluded-paths <pattern,pattern,...>        A list of regular expressions to search
                                                      against the full file path.  If the path
                                                      matches, the file will be skipped.
                                                      Since this is a regular expression, take
                                                      care to escape special characters.  For
                                                      example, to exclude all files and
                                                      directories that begin with a period,
                                                      the pattern would be .*/\..*
        --follow-links                                instead of preserving symbolic links,
                                                      follow them and sync the actual files
        --modified-since <yyyy-MM-ddThh:mm:ssZ>       only look at files that have been
                                                      modified since the specifiec date/time.
                                                      Date/time should be provided in ISO-8601
                                                      UTC format (i.e. 2015-01-01T04:30:00Z)
        --store-metadata                              when used as a target, stores source
                                                      metadata in a json file, since
                                                      filesystems have no concept of user
                                                      metadata
        --use-absolute-path                           Uses the absolute path to the file when
                                                      storing it instead of the relative path
                                                      from the source dir

    Simulated Storage for Testing (test:)
        This plugin will generate random data when used as a source, or act as /dev/null when
        used as a target
        NOTE: Storage options must be prefixed by source- or target-, depending on which role
        they assume
        --chance-of-children <chance-of-children>   When used as a source, the percent chance
                                                    that an object is a directory vs a data
                                                    object. Default is 30
        --max-child-count <max-child-count>         When used as a source, the maximum child
                                                    count for a directory (actual child count
                                                    is random). Default is 8
        --max-depth <max-depth>                     When used as a source, the maximum
                                                    directory depth for children. Default is 5
        --max-metadata <max-metadata>               When used as a source, the maximum number
                                                    of metadata tags to generate (actual
                                                    number is random). Default is 5
        --max-size <max-size>                       When used as a source, the maximum size of
                                                    objects (actual size is random). Default
                                                    is 1048576
        --no-discard-data                           By default, all data generated or read
                                                    will be discarded. Turn this off to store
                                                    the object data and index in memory
        --object-count <object-count>               When used as a source, the exact number of
                                                    root objects to generate. Default is 100
        --object-owner <object-owner>               When used as a source, specifies the owner
                                                    of every object (in the ACL)
        --read-data                                 When used as a target, actually read the
                                                    data from the source (data is not read by
                                                    default)
        --valid-groups <valid-groups>               When used as a source, specifies valid
                                                    groups for which to generate random grants
                                                    in the ACL
        --valid-permissions <valid-permissions>     When used as a source, specifies valid
                                                    permissions to use when generating random
                                                    grants
        --valid-users <valid-users>                 When used as a source, specifies valid
                                                    users for which to generate random grants
                                                    in the ACL

    ACL Mapper (acl-mapping)
        The ACL Mapper will map ACLs from the source system to the target using a provided
        mapping file. The mapping file should be ordered by priority and will short-circuit
        (the first mapping found for the source key will be chosen for the target). Note that
        if a mapping is not specified for a user/group/permission, that value will remain
        unchanged in the ACL of the object. You can optionally remove grants by leaving the
        target value empty and you can add grants to all objects using the --acl-add-grants
        option.
    If you wish to migrate ACLs with your data, you will always need this plugin unless the
        users, groups and permissions in both systems match exactly. Note: If you simply want
        to take the default ACL of the target system, there is no need for this filter; just
        don't sync ACLs (this is the default behavior)
        --acl-add-grants <acl-add-grants>         Adds a comma-separated list of grants to all
                                                  objects synced to the target system. Syntax
                                                  is like so (repeats are allowed):
                                                  group.<target_group>=<target_perm>,user.<tar
                                                  get_user>=<target_perm>
        --acl-append-domain <acl-append-domain>   Appends a directory realm/domain to each
                                                  user that is mapped. Useful when mapping
                                                  POSIX users to LDAP identities
        --acl-map-file <acl-map-file>             Path to a file that contains the mapping of
                                                  identities and permissions from source to
                                                  target. Each entry is on a separate  line
                                                  and specifies a group/user/permission source
                                                  and target name[s] like so:
                                                  group.<source_group>=<target_group>
                                                  user.<source_user>=<target_user>
                                                  permission.<source_perm>=<target_perm>[,<tar
                                                  get_perm>..]
                                                  You can also pare down permissions that are
                                                  redundant in the target system by using
                                                  permission groups. I.e.:
                                                  permission1.WRITE=READ_WRITE
                                                  permission1.READ=READ
                                                  will pare down separate READ and WRITE
                                                  permissions into one READ_WRITE/READ (note
                                                  the ordering by priority). Groups are
                                                  processed before straight mappings. Leave
                                                  the target value blank to flag an
                                                  identity/permission that should be removed
                                                  (perhaps it does not exist in the target
                                                  system)
        --acl-strip-domain                        Strips the directory realm/domain from each
                                                  user that is mapped. Useful when mapping
                                                  LDAP identities to POSIX users
        --acl-strip-groups                        Drops all groups from each object's ACL. Use
                                                  with --acl-add-grants to add specific group
                                                  grants instead
        --acl-strip-users                         Drops all users from each object's ACL. Use
                                                  with --acl-add-grants to add specific user
                                                  grants instead

    Decryption Filter (decrypt)
        Decrypts object data using the Atmos Java SDK encryption standard
        (https://community.emc.com/docs/DOC-34465). This method uses envelope encryption where
        each object has its own symmetric key that is itself encrypted using the master
        asymmetric key. As such, there are additional metadata fields added to the object that
        are required for decrypting
        --decrypt-keystore <keystore-file>            required. the .jks keystore file that
                                                      holds the decryption keys. which key to
                                                      use is actually stored in the object
                                                      metadata
        --decrypt-keystore-pass <keystore-password>   the keystore password
        --decrypt-update-mtime                        by default, the modification time
                                                      (mtime) of an object does not change
                                                      when decrypted. set this flag to update
                                                      the mtime. useful for in-place
                                                      decryption when objects would not
                                                      otherwise be overwritten due to matching
                                                      timestamps
        --fail-if-not-encrypted                       by default, if an object is not
                                                      encrypted, it will be passed through the
                                                      filter chain untouched. set this flag to
                                                      fail the object if it is not encrypted

    Encryption Filter (encrypt)
        Encrypts object data using the Atmos Java SDK encryption standard
        (https://community.emc.com/docs/DOC-34465). This method uses envelope encryption where
        each object has its own symmetric key that is itself encrypted using the master
        asymmetric key. As such, there are additional metadata fields added to the object that
        are required for decrypting. Note that currently, metadata is not encrypted
        --encrypt-force-strong                        256-bit cipher strength is always used
                                                      if available. this option will stop
                                                      operations if strong ciphers are not
                                                      available
        --encrypt-key-alias <encrypt-key-alias>       the alias of the master encryption key
                                                      within the keystore
        --encrypt-keystore <keystore-file>            the .jks keystore file that holds the
                                                      master encryption key
        --encrypt-keystore-pass <keystore-password>   the keystore password
        --encrypt-update-mtime                        by default, the modification time
                                                      (mtime) of an object does not change
                                                      when encrypted. set this flag to update
                                                      the mtime. useful for in-place
                                                      encryption when objects would not
                                                      otherwise be overwritten due to matching
                                                      timestamps
        --fail-if-encrypted                           by default, if an object is already
                                                      encrypted using this method, it will be
                                                      passed through the filter chain
                                                      untouched. set this flag to fail the
                                                      object if it is already encrypted

    Gladinet Mapper (gladinet-mapping)
        This plugin creates the appropriate metadata in Atmos to upload data in a fashion
        compatible with Gladinet's Cloud Desktop software when it's hosted by EMC Atmos
        --gladinet-dir <base-directory>   Sets the base directory in Gladinet to load content
                                          into. This directory must already exist

    ID Logging Filter (id-logging)
        Logs the input and output Object IDs to a file. These IDs are specific to the source
        and target plugins
        --id-log-file <path-to-file>   The path to the file to log IDs to

    Local Cache (local-cache)
        Writes each object to a local cache directory before writing to the target. Useful for
        applying external transformations or for transforming objects in-place (source/target
        are the same)
    NOTE: this filter will remove any extended properties from storage plugins (i.e. versions,
        CAS tags, etc.) Do not use this plugin if you are using those features
        --local-cache-root <cache-directory>   specifies the root directory in which to cache
                                               files

    Metadata Filter (metadata)
        Allows adding regular and listable (Atmos only) metadata to each object
        --add-listable-metadata <name=value,name=value,...>   Adds listable metadata to every
                                                              object
        --add-metadata <name=value,name=value,...>            Adds regular metadata to every
                                                              object

    Override Mimetype (override-mimetype)
        This plugin allows you to override the default mimetype of objects getting
        transferred. It is useful for instances where the mimetype of an object cannot be
        inferred from its extension or is nonstandard (not in Java's mime.types file). You can
        also use the force option to override the mimetype of all objects
        --force-mimetype                 If specified, the mimetype will be overwritten
                                         regardless of its prior value
        --override-mimetype <mimetype>   Specifies the mimetype to use when an object has no
                                         default mimetype

    Preserve ACLs (preserve-acl)
        This plugin will preserve source ACL information as user metadata on each object


    Preserve File Attributes (preserve-file-attributes)
        This plugin will read and preserve POSIX file attributes as metadata on the object


    Restore Preserved ACLs (restore-acl)
        This plugin will read preserved ACLs from user metadata and restore them to each
        object


    Restore File Attributes (restore-file-attributes)
        This plugin will restore POSIX file attributes that were previously preserved in
        metadata on the object


    Shell Command Filter (shell-command)
        Executes a shell command after each successful transfer. The command will be given two
        arguments: the source identifier and the target identifier
        --shell-command <path-to-command>   The shell command to execute
