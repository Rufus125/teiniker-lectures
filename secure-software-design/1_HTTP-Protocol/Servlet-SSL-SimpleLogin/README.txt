How to use a HTTPS connection?
-------------------------------------------------------------------------------
*) Create a Keystore

$ pwd
/home/student/install/wildfly-8.2.0.Final/standalone/configuration

$ keytool -genkeypair -keystore wildfly.keystore -storepass student -keypass student -keyalg RSA -alias wildfly -dname "cn=ims,o=fhj,c=at" 
 
 
*) Configure Wildfly (standalone.xml):

    <management>
        <security-realms>
			<!-- SSL/TLS Configuration --> 
			<security-realm name="CertificateRealm">
				<server-identities>
					<ssl>
						<keystore path="wildfly.keystore" relative-to="jboss.server.config.dir" keystore-password="student" />
					</ssl>
				</server-identities>
			</security-realm>
		...
	</management>
	
		<!-- SSL/TLS Configuration -->        
        <subsystem xmlns="urn:jboss:domain:undertow:1.1">
            <buffer-cache name="default"/>
            <server name="default-server">
                <https-listener name="https" socket-binding="https" security-realm="CertificateRealm"/>
                <http-listener name="default" socket-binding="http"/>
                <host name="default-host" alias="localhost">
                    <location name="/" handler="welcome-content"/>
                    <filter-ref name="server-header"/>
                    <filter-ref name="x-powered-by-header"/>
                </host>
            </server>	
		...
		</subsystem>

*) Restart Wildfly
	=> JBAS017519: Undertow HTTPS listener https listening on localhost/127.0.0.1:8543


How to access the Web application from a Browser?
-------------------------------------------------------------------------------

	
After starting the server, we can use a Browser to access the application's page:
    
    URL: https://localhost:8443/Servlet-SSL-SimpleLogin/
    
Note that it's also possible to access the page via regular HTTP: 
    
	URL: http://localhost:8080/Servlet-SSL-SimpleLogin/
    	
    		
               
How to Configure your Web Application to Work with SSL ?
-------------------------------------------------------------------------------

Note that this step is already done in this project!!

Open the WEB-INF/web.xml of the application and add this XML fragment:  	
 	
 	<security-constraint>
		<web-resource-collection>
			<web-resource-name>securedapp</web-resource-name>
			<url-pattern>/*</url-pattern>
		</web-resource-collection>
		<user-data-constraint>
			<transport-guarantee>CONFIDENTIAL</transport-guarantee>
		</user-data-constraint>
	</security-constraint>
	
	
Now a Browser's request to 

	URL: http://localhost:8080/Servlet-SSL-SimpleLogin 

will be redirected to 

	URL: https://localhost:8443/Servlet-SSL-SimpleLogin
	


How to Configure Tomcat for Using the Keystore File ?
-------------------------------------------------------------------------------

First of all, make sure that Tomcat is not running when you change the configuration files.

    $CATALINA_BASE/bin/shutdown
    
We change the following Tomcat configuration: $CATALINA_BASE/conf/server.xml

    <!-- Define a SSL HTTP/1.1 Connector on port 8443
         This connector uses the JSSE configuration, when using APR, the
         connector should be using the OpenSSL style configuration
         described in the APR documentation -->
 
    <Connector port="8443" protocol="HTTP/1.1" SSLEnabled="true"
               maxThreads="150" scheme="https" secure="true"
               keystoreFile="${user.home}/SSLKeyStore" keystorePass="student" 
               clientAuth="false" sslProtocol="TLS" />
               