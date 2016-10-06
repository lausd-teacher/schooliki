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

Then, dependencies modules needs to be packaged, by executing the following maven command from the root project (Right Click on project -> Run As -> Run configurations -> double click on Maven build  -> enter the following command in Goals field) : `package`

This needs to be executed only before executing the project for the first time or if a clean is executed. 

To run the dev mode, you need run the project form the root with the command: gwt:devmode -Plocal

![alt tag](https://1.bp.blogspot.com/-8pz-GZ1ITic/V_a5OT1-3GI/AAAAAAAAA3w/bkHPg2tpIZQnkehIdaFFYi3NtjXmLpyGgCLcB/s1600/devmodelocal.png)

and then when the GWT dev mode window, you can choose which url to start from and copy it in the browser

![alt tag](https://2.bp.blogspot.com/-JQCUX1O7enE/V_a5L8MgZ6I/AAAAAAAAA3s/mUIncMehZGkCv2N5CPSPHa8tcimJ2OSPgCLcB/s1600/gwt-window.png)


Important: the local DB in schooliki-server/src/main/webapp/WEB-INF/appengine-generated/local_db.bin may need to be copied manually in the /target/schooliki-server-0.1/WEB-INF/appengine-generated/ beause sometimes the app does not recognize it when copied by Maven (This is a strange behavior)



