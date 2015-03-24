package main;

import org.irods.jargon.core.connection.AuthScheme;
import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.AuthenticationException;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.IRODSFileSystem;

public class TestConnection {
	private IRODSAccount gridInfo = null;
	private IRODSFileSystem irodsFileSystem;
	String homeDirectory = null;
	int port = 0;
	String host = null;
	String password = null;
	String userName = null;
	String zone = null;
	String defaultStorageResource = null;
	String initialPath=null ;
	TestConnection(String host,int port,String userName,String password,String initial,String zone,String defaultStorageResource){
	this.host=host;
	this.port=port;
	this.userName=userName;
	this.password=password;
	this.zone=zone;
	  initialPath=initial;
     if (initial == null){
         StringBuilder homeBuilder = new StringBuilder();
         homeBuilder.append("/");
         homeBuilder.append(zone);
         homeBuilder.append("/home/");
         homeBuilder.append(userName);
         initialPath = homeBuilder.toString();
     }
	this.homeDirectory=initialPath;
	this.defaultStorageResource=defaultStorageResource;
	}
	
	public String connectIRODS(Object cbAuthScheme){
		
		try {
			gridInfo=IRODSAccount.instance(host, port, userName, password, homeDirectory, zone, defaultStorageResource);
		} catch (JargonException e) {
			return "<html><body>Exception Occured. Refill and submit</body><html>";
		}
		AuthScheme scheme = (AuthScheme)cbAuthScheme;
        if ((scheme != null) && (!(scheme.getTextValue().isEmpty()))) {
            gridInfo.setAuthenticationScheme(scheme);
        }

        if (!validateGridAccount(gridInfo)) {
        	gridInfo = null;
        	return "<html><body>Unable to process login, the server <br> or accountappears to be invalid<body><html>";
            
        }
		return "<html><body>Success.<br>Connected to IRODS</body><html>";	
	}
	 private boolean validateGridAccount(final IRODSAccount gridInfo) {
	        try {
	        	irodsFileSystem=IRODSFileSystem.instance();
	        	System.out.println(irodsFileSystem.toString());
	        	irodsFileSystem.getIRODSAccessObjectFactory().authenticateIRODSAccount(gridInfo);
	            return true;
	        } catch (AuthenticationException ex) {
	            return false;
	        } catch (JargonException je) {
	        	return false;
	        }
	 }
	 public IRODSAccessObjectFactory getIRODSAccessObjectFactory() {
	        if (irodsFileSystem == null) {
	          //  throw new IdropRuntimeException(
	            //        "No IRODSFileSystem set, cannot obtain the IRODSAccessObjectFactory");
	        }
	        
	        //try {
	            try {
					return irodsFileSystem.getIRODSAccessObjectFactory();
				} catch (JargonException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
	 }
}
	 

