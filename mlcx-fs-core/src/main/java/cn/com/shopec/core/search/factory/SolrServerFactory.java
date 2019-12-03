package cn.com.shopec.core.search.factory;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.LBHttpSolrServer; 
import org.springframework.stereotype.Service;

/** 
 * SolrServer工厂类
 *  
 */
//@Service
public class SolrServerFactory {
	
	private static Log log = LogFactory.getLog(SolrServerFactory.class);
	
//	@Value("${search.solr_server_url}") 	
	private String solrServerUrl;
	
//	@Value("${search.solr_server_user}")	
	private String user;
	
//	@Value("${search.solr_server_password}")	
	private String password;

//	@Value("${search.solr_server_conn_timeout}")
	private int connectionTimeout;
	
//	@Value("${search.solr_server_so_timeout}")
	private int soTimeout;
	
	private SolrServer searchServer;
	
	public SolrServer getServer() {
		return this.searchServer;
	}

	@PostConstruct
	void init() {
		System.out.println("Solr server url = " + solrServerUrl);
		try {
			String[] solrServerUrlArr = null;
			if(this.solrServerUrl.indexOf(",") > 0) {
				solrServerUrlArr = this.solrServerUrl.split(",");
			} else {
				solrServerUrlArr = new String[]{this.solrServerUrl};
			}
			
			LBHttpSolrServer httpSolrServer = new LBHttpSolrServer(solrServerUrlArr);
			httpSolrServer.setConnectionTimeout(this.connectionTimeout); //the connection timeout of establishing TCP
			httpSolrServer.setSoTimeout(this.soTimeout);  // socket read timeout
		    
			//Setting the authentification for solr security
//			String userStr = user == null ? "" : user.trim();
//			String passwordStr = password == null ? "" : password.trim();
//			
//			DefaultHttpClient httpClient =(DefaultHttpClient)httpSolrServer.getHttpClient(); 
//			httpClient.addRequestInterceptor(new PreemptiveAuthInterceptor(),0);
//		    httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userStr, passwordStr));
			
			this.searchServer = httpSolrServer;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void setSolrServerUrl(String solrServerUrl) {
		this.solrServerUrl = solrServerUrl;
	}

	public String getSolrServerUrl() {
		return this.solrServerUrl;
	}
	
	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}   
	
//	private class PreemptiveAuthInterceptor implements HttpRequestInterceptor {
//		
//		@SuppressWarnings("deprecation")
//		public void process(final HttpRequest request, final HttpContext context) throws HttpException, IOException {
//			AuthState authState = (AuthState) context.getAttribute(ClientContext.TARGET_AUTH_STATE);
//			
//			// If no auth scheme avaialble yet, try to initialize it
//			// preemptively
//			if (authState.getAuthScheme() == null) {
//				CredentialsProvider credsProvider = (CredentialsProvider) context.getAttribute(ClientContext.CREDS_PROVIDER);
//				HttpHost targetHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
//				Credentials creds = credsProvider.getCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()));
//				if (creds == null) {
//					throw new HttpException("No credentials for preemptive authentication");
//				}
//				authState.setAuthScheme(new BasicScheme());
//				authState.setCredentials(creds);
//			}
//			
//		}
//	}   
}

