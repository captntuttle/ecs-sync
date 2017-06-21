General Performance Metrics
===========================

It is important to note that the performance metrics posted below are
real world examples but are meant to serve only as rough guidelines and
are not necessarily indicative of the users actual performance.

ECS-sync can be scaled up or down by changing the number of sync
servers. More sync servers allows for more jobs to be done
simultaneously.

General Performance ecs-sync 3.0 OVA With Validation
----------------------------------------------------

+--------+-------------+----------------+---------------+-----------+---------------+----+
| Filesi | No. Sync    | Jobs per Sync  | No. of Access | Thread    | 24-hour       | To |
| ze     | Servers     | Server         | Nodes         | Count     | Throughput/Se | ta |
|        |             |                |               |           | rver          | l/ |
|        |             |                |               |           |               | Da |
|        |             |                |               |           |               | y  |
+========+=============+================+===============+===========+===============+====+
| 1 -    | 2           | 1              | 2             | 30        | 7.6TB         | 15 |
| 3MB    |             |                |               |           |               | .2 |
|        |             |                |               |           |               | TB |
+--------+-------------+----------------+---------------+-----------+---------------+----+
| 30KB   | 4           | 1              | 2             | 30        | 2.3M clip     | 9. |
|        |             |                |               |           |               | 2M |
|        |             |                |               |           |               | Ob |
|        |             |                |               |           |               | je |
|        |             |                |               |           |               | ct |
|        |             |                |               |           |               | s  |
+--------+-------------+----------------+---------------+-----------+---------------+----+

Real World Examples
-------------------

+-------------+-----------+-------------+---------------+------------------+-----------+
| Data        | Avg. File | No. Sync    | No. of Access | 24-hour          | Total/Day |
| Application | Size      | Servers     | Nodes         | Throughput/Serve |           |
|             |           |             |               | r                |           |
+=============+===========+=============+===============+==================+===========+
| PACS        | 1-3 MB    | 4           | 2             | 8TB              | 48TB      |
+-------------+-----------+-------------+---------------+------------------+-----------+
| Enterprise  | 300KB     | 8           | 2             | 1.5M Clips       | 12M       |
| Vault       |           |             |               |                  | Objects   |
+-------------+-----------+-------------+---------------+------------------+-----------+
