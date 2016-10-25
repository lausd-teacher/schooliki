## Modules:

The new project is more modular and divided into the following maven modules :

      - schooliki-client-roster: roster module
      - schooliki-client-admin: admin module
      - schooliki-server: backend classes
      - schooloki-shared: shared code between client and server
      - picker: picker dependency
      - channel: Google app engine channel
      - gwtquery-ui-plugin: the gwtquery-ui-plugin



## Maven plugin

to get rid of the plumbing that was used in the initial version, I introduced the new generation maven plugin which designed to work with GWT projects with different modules: https://tbroyer.github.io/gwt-maven-plugin/. 

The plugin is fairly simple to use: there are two types of packaging either gwt-app ( for web applications wars) or `gwt-lib` for librairies. I am using gwt-lib on picker, channel, and gwtquery-ui-plugin. For the client modules (schooliki-client-roster, schooliki-client-admin) they are packaged as `gwt-app`. For client side modules, all the .gwt.xml config is managed into src/main/module.gwt.xml and at run the time the plugin automatically creates the module descriptor named in the config, e.g :

```
<plugin>
				<groupId>net.ltgt.gwt.maven</groupId>
				<artifactId>gwt-maven-plugin</artifactId>
				<version>1.0-rc-6</version>
				<extensions>true</extensions>
				<configuration>
					<moduleName>net.videmantay.Admin</moduleName>
					<localWorkers>4</localWorkers>
				</configuration>
			</plugin>
```

The net/videmanta/Admin.gwt.xml module is automatically created from module.gwt.xml. 

In any case everything is set up in the project.


## How to run the project in Dev mode:

The project needs to be imported into Eclipse as Maven project ( File -> import -> existing maven projects)

Then, the app engine dev server needs to be run, from the schooliki-server submodule using the command `appengine:devserver`

To run the dev mode, you need run the module that you would like to debug using: `gwt:devmode` from the directory of the module

For simplicity, it's better to run only one module. But if you would like to run several modules in dev mode, you need to use different ports

![alt tag](https://2.bp.blogspot.com/-O9yGA7ccPTM/WA_EuIksyCI/AAAAAAAAA4Y/_ed37mBk7MMdGwTKsoVkDCxmUbsXlkK5QCLcB/s1600/devmodenew.png)

Important: The module needs to be run from the address of the server not the address of GWT code server, for example if the app engine dev server is on port 9997, if the teacher module is run then the access address would be: http://localhost:9997/teacher

Important: the local DB in schooliki-server/src/main/webapp/WEB-INF/appengine-generated/local_db.bin may need to be copied manually in the /target/schooliki-server-0.1/WEB-INF/appengine-generated/ because sometimes the app does not recognize it when copied by Maven (This is a strange behavior)


## Deploying to Google App Engine: 

1) you need to clean the schooliki-server module and package it. From the schooliki-server module: `clean package` 

2) you need to package the modules one by one. From the module directory project: `clean package`, the files are copied automatically in the schooliki-server war directory.

3) finally, you need to repackage the server to make sure the GWT modules are included in the war and execute the update goal of the google app engine plugin. From the schooliki-server module: `package appengine:update`

The current app id is schooliki-dev which is a project I created for testing. Do not forget to change the app id and version in appengine-web.xml to your app id and version.





