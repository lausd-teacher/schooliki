package net.videmantay.server;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateException;

public class TemplateGen{
	
	private Configuration cfg = null;
	
	
	private static TemplateGen instance = null;
	
	private final Logger log = Logger.getLogger("logger");
	
	private TemplateGen(){
		
		
		cfg = new Configuration(Configuration.VERSION_2_3_23);
		log.log(Level.INFO, this.getClass().getClassLoader().getResource("").getPath());
		

		try {
			cfg.setDirectoryForTemplateLoading(new File("html"));
			
			
		} catch (IOException e) {
		
			e.printStackTrace();
		}

		
		DefaultObjectWrapperBuilder owb = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_23);
		owb.setForceLegacyNonListCollections(false);
		owb.setDefaultDateType(TemplateDateModel.DATETIME);
		cfg.setObjectWrapper(owb.build());
		
		cfg.setDefaultEncoding("UTF-8");

		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

		cfg.setIncompatibleImprovements(Configuration.VERSION_2_3_21);  // FreeMarker 2.3.20
		
	}
	
	
	public String getLoggedInLoginView(String currentUser){
		
		try {
			StringWriter writer = new StringWriter();
			StringWriter writer2 = new StringWriter();
			
			Template logginPage = cfg.getTemplate("login.html");
			Template loggedInBody = cfg.getTemplate("loggedin.html");
			
			 Map<String, String> templateData = new HashMap<String, String>();
			 templateData.put("name", currentUser);
			 
			 loggedInBody.process(templateData, writer);
			 
			 Map<String, String> pageParts = new HashMap<String, String>();
			 pageParts.put("loginBody", writer.toString());
			 
			
			 logginPage.process(pageParts, writer2);
			
			
			return writer2.toString();
			
			
		} catch (IOException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return error();

	}
	
	
	  public static TemplateGen getInstance(){
		  
		   if(instance == null){
			   
			   
			   return new TemplateGen();
		   }else{
			   
			   return instance;
		   }
		  
		  
		  
	  }
	
      public String getLoggedOutLoginView(){
    	  try {
  			StringWriter writer = new StringWriter();
  			Template logginPage = cfg.getTemplate("login.html");
  			Template loggedOutBody = cfg.getTemplate("loggedout.html");
  			
          
  			 
  			 Map<String, String> pageParts = new HashMap<String, String>();
  			 pageParts.put("loginBody", loggedOutBody.toString());
  			 
  			 logginPage.process(pageParts, writer);
  			
  			return writer.toString();
  			
  			
  		} catch (IOException | TemplateException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  		
  		return error();
	}
      
      
      public String getLoginViewWithError(String errorMessage){
    	  try {
    		StringWriter writer1 = new StringWriter();
  			StringWriter writer2 = new StringWriter();
  			
  			Template logginPage = cfg.getTemplate("login.html");
  			Template loggedOutBody = cfg.getTemplate("loggedoutwitherrors.html");
  			
  			Map<String, String> errors = new HashMap<String, String>();
  			errors.put("errors", errorMessage);
  			
  			log.log(Level.INFO, "called login view with errors");
  			
  			loggedOutBody.process(errors, writer1);
             
  			 
  			 Map<String, String> pageParts = new HashMap<String, String>();
  			 pageParts.put("loginBody", writer1.toString());
  			 
  			 logginPage.process(pageParts, writer2);
  			
  			return writer2.toString();	
  		} catch (IOException | TemplateException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  		
  		return error();
	}
	
	
   private static String error(){
	   return "<h1>Error</h1><div>Error redenring the page, please try later</div>";
   }
   

}