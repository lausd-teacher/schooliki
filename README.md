## Modules:

The project is divided into the following maven modules :

      - schooliki-client: client side 
      - schooliki-server: backend classes
      - schooloki-shared: shared code between client and server
      - picker: picker dependency
      - channel: Google app engine channel
      - gwtquery-ui-plugin: the gwtquery-ui-plugin


## How to run the project in Dev mode:

The project needs to be imported into Eclipse as Maven project ( File -> import -> existing maven projects)

Then, dependencies modules needs to be installed, by executing the following maven command from the root project (Right Click on project -> Run As -> Run configurations -> double click on Maven build  -> enter the following command in Goals field) : install

This needs to be executed only before executing the project for the first time. 

![alt tag](https://2.bp.blogspot.com/-YtHuujZruxI/V_IhseZW3lI/AAAAAAAAA3Q/8i1K-E_CJTQG7nq7wqMS1p47-le0pZWqACLcB/s1600/schooliki-dev-mode.png)

Finally, to run the dev mode, you need to go to the schooliki server, and run the project with the command: package -Pdevmode

![alt tag](https://2.bp.blogspot.com/-rna-7nJ3Sz0/V_IiKe1ee6I/AAAAAAAAA3U/P0z-grGKo_Mw5kBXV-FZ2FF-C4x_l33JACLcB/s1600/schooliki-dev.png)



