Prerequisites:
- Ensure your WAMP/MAMP server is running to allow access to the database.
- If not done so, import the deploy.sql script. This can be done in phpmyadmin, MySQL Workbench or otherwise.

Below are the information on the tables for deploy.sql:
+----------------+-----------------------------------------------------+-------------+--------------------------+
| Table Name     | Columns Name                                        | Primary Key | Foreign Key              |
+----------------+-----------------------------------------------------+-------------+--------------------------+
| user           | id: int(11) AUTO_INCREMENT,                         | id          | -                        |
|                | name: varchar(32) NOT NULL,                         |             |                          |
|                | email: varchar(32) NOT NULL,                        |             |                          |
|                | password: varchar(60) NOT NULL,                     |             |                          |
|                | token: varchar(60)                                  |             |                          |
+----------------+-----------------------------------------------------+-------------+--------------------------+
| vessel         | uniqueId: varchar(60) NOT NULL,                     | uniqueId    | vessel_history: uniqueId |
|                | imoN: varchar(12) DEFAULT NULL,                     |             |                          |
|                | fullVslM: varchar(48) NOT NULL,                     |             |                          |
|                | abbrVslM: varchar(32) NOT NULL                      |             |                          |
|                | fullInVoyN: varchar(500) DEFAULT NULL,              |             |                          |
|                | inVoyN: varchar(8) NOT NULL,                        |             |                          |
|                | fullOutVoyN: varchar(500) DEFAULT NULL              |             |                          |
|                | outVoyN: varchar(8) NOT NULL,                       |             |                          |
|                | shiftSeqN: varchar(5) NOT NULL,                     |             |                          |
|                | bthgDt: varchar(21) NOT NULL,                       |             |                          |
|                | unbthgDt: varchar(21) NOT NULL,                     |             |                          |
|                | berthN: varchar(10) DEFAULT NULL,                   |             |                          |
|                | status: varchar(12) NOT NULL,                       |             |                          |
|                | abbrTerminalM: varchar(8) DEFAULT NULL              |             |                          |
+----------------+-----------------------------------------------------+-------------+--------------------------+
| vessel_history | uniqueId: varchar(60) NOT NULL,                     | uniqueId    | -                        |
|                | last_bthgDt: varchar(21) NOT NULL,                  |             |                          |
|                | last_unbthgDt: varchar(21) NOT NULL,                |             |                          |
|                | bthgDt_change_count: int(2) NOT NULL DEFAULT '0',   |             |                          |
|                | unbthgDt_change_count: int(2) NOT NULL DEFAULT '0', |             |                          |
|                | first_arrival: varchar(21) NOT NULL                 |             |                          |
+----------------+-----------------------------------------------------+-------------+--------------------------+
| subscription   | id: int(11) AUTO_INCREMENT,                         | id          | user: user_id,           |
|                | user_id: int(11) NOT NULL,                          |             | vessel: voyage_id        |
|                | voyage_id: varchar(60) NOT NULL                     |             |                          |
+----------------+-----------------------------------------------------+-------------+--------------------------+
| favourite      | id: int(11) AUTO_INCREMENT,                         | id          | user: user_id,           |
|                | user_id: int(11) NOT NULL,                          |             | vessel: voyage_id        |
|                | voyage_id: varchar(60) NOT NULL                     |             |                          |
+----------------+-----------------------------------------------------+-------------+--------------------------+

Below are the information on the 2 triggers created for the vessel table:
+---------------------------------+--------------------------+--------------------------------------------------------+
| Trigger Name                    | Trigger Point?           | Description                                            |
+---------------------------------+--------------------------+--------------------------------------------------------+
| before_insert_set_history       | Before insertion of data | For each matching row:                                 |
|                                 | into vessel table        |                                                        |
|                                 |                          |   set check foreign key constraints to do not          |
|                                 |                          |   check and insert data that are passed in into        |
|                                 |                          |   vessel_history table                                 |
+---------------------------------+--------------------------+--------------------------------------------------------+
| if_berth_changed_update_history | Before update of data    | For each matching row:                                 |
|                                 | into vessel table        |                                                        |
|                                 |                          |   if new unbthgDt is different from old unbthgDt,      |
|                                 |                          |   and bthgDt is different from old bthgDt:             |
|                                 |                          |      update vessel_history table by setting:           |
|                                 |                          |          - the last_bthgDt as old bthgDt,              |
|                                 |                          |          - bthgDt_change_count to +1,                  |
|                                 |                          |          - last_unbthgDt as the old unbthgDt           |
|                                 |                          |          - unbthgDt_change_count to +1                 |
|                                 |                          |                                                        |
|                                 |                          |   else if new bthgDt is different from old bthgDt:     |
|                                 |                          |      update vessel_history table by setting:           |
|                                 |                          |          - last_bthgDt to old bthgDt                   |
|                                 |                          |          - bthgDt_change_count to +1                   |
|                                 |                          |                                                        |
|                                 |                          |   else if new unbthgDt is different from old unbthgDt: |
|                                 |                          |      update vessel_history table by setting:           |
|                                 |                          |          - last_unbthgDt as old unbthgDt               |
|                                 |                          |          - unbthgDt_change_count to +1                 |
+---------------------------------+--------------------------+--------------------------------------------------------+