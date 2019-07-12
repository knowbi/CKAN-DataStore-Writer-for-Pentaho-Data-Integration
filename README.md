# Kettle CKAN Writer
Kettle CKAN DataStore Writer (forked from https://github.com/OpenGov-OpenData/CKAN-DataStore-Writer-for-Pentaho-Data-Integration)

## Installation
Remove old versions of the plugin from your <KETTLE_DIR>/plugins.
run 'mvn clean install'
copy target/knowbi-kettle-ckan-step to the plugins folder in your Kettle install dir
OR
run 'mvn clean install -Dkettle.dir=<KETTLE_DIR>/plugins' to install directly.


## Options
To create an new Datastore resource provide a valid Package ID and omit the Resource ID

| Option         | Description                                                                                             |
| -------------- | ------------------------------------------------------------------------------------------------------- |
| Step name	     |Name of the step. This name should be unique in a single transformation.                                 |
| Domain         |The domain of the CKAN data portal to which data will be uploaded. (eg: http://demo.ckan.org)            |
| API Key        |Users can find their API key in their user profile on the CKAN data portal.                              |
| Package ID     |The ID of an existing package where data will be uploaded in a DataStore resource. (eg: test-dataset)    |
| Resource Title |The title of the resource.                                                                               |
| Description    |A short description about the resource. (optional)                                                       |
| Resource ID    |The ID of an existing DataStore resource to update. If left empty a new DataStore resource will be made. |
| Batch Size     |The writer will upload rows of data in batches of this amount. (default: 5000)                           |
| Primary Key    |If a Primary Key is specified then data will be upserted instead of inserted. Multiple fields can be specified as the Primary Key, use double semicolons to delineate the fields (eg: field1;;field2) |

### PDI Sample Transformation
Caio Moreno's blog post http://blog.professorcoruja.com/2017/06/unlock-your-data-using-pdi-and-ckan-pdi.html
example transformation: src/test/resources/tr_ckan_kettle_plugin_example.ktr