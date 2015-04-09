# gae-gcs-gcsql
Develop a Google App Engine web app to use Google Cloud Storage and Google Cloud SQL instances and upload different size of files to measure the performance

###Steps for Google Cloud Storage:
1. Created a Google App Engine account and registered for its Free Tier. Created a Google App Engine Project with name `cse6331gap`.
2. Downloaded `Virginia.csv` weather file from `NOAA`. Download Data. This file has approx 150K rows.
3. Divided this `Virginia.csv` file into 5 different files containing 10k, 25k, 50k, 100k, and 150k rows.
4. Created a Google Cloud Storage bucket with name `cse6331gap.appspot.com`.
5. Created a Google App Engine Web Project in Eclipse with default configuration.
6. When deployed this app on GAE, uploaded all .csv files one by one to the Google Cloud Storage.
7. Since web app has no control over uploading these files to Google Cloud Storage as it is completely handled by Google App Engine API, I have measured all these upload and download times using Chrome Developer Tools’ Network tab.

###Steps for Google Cloud SQL:
1. Created a Google Cloud SQL instance with default configuration (`cse6331gap:cloud-db`).
2. Configured this MySQL instance by generating its own IP and allowing my machine to connect to it.
3. Connected to Google Cloud SQL instance using local MySQL Workbench.
4. Created a table in `cse6331_db` schema to load data.
5. Using `LOAD DATA` query in `CloudSQLUploader` class, loaded all the csv files into Virginia table, queried data from this table. Time was measured between all these operations.
