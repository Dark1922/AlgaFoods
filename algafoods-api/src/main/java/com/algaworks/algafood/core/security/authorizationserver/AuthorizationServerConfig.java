package com.algaworks.algafood.core.security.authorizationserver;


import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtKeyStoreProperties jwtKeyStoreProperties;

	@Autowired
	private DataSource dataSource;
	
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);

	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		var enchancerChain =  new TokenEnhancerChain(); // cadeia de enchancer q incrementa o token
		enchancerChain.setTokenEnhancers(Arrays.asList(
				new JwtCustomClaimsTokenEnhancer(),jwtAccessTokenConverter()));
		endpoints
		.authenticationManager(authenticationManager)//endpoint de gerar o token
		.userDetailsService(userDetailsService)//tava nulo ent colcoamos o userDetailsService
		.reuseRefreshTokens(false)
				.tokenEnhancer(enchancerChain)
		.accessTokenConverter(jwtAccessTokenConverter()) //conversos de access token jwt
		.approvalStore(approvalStore(endpoints.getTokenStore())) //armazenamento de aprovações
		.tokenGranter(tokenGranter(endpoints));
		 
	}
	
	private ApprovalStore approvalStore(TokenStore tokenStore) {
		//permite que o token store com oq o authorization server configue um handler que permite a aprovação
		//granular dos escopos
		var approvalStore = new TokenApprovalStore();
		approvalStore.setTokenStore(tokenStore);
		return approvalStore;
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		
		//chave simetrica
		//jwtAccessTokenConverter.setSigningKey("DB4AEF4719809709E560ED8DE2F9C77B886B963B28BA20E9A8A621BBD4ABA400");
		
		//chave assimetrica
		    var keyStorePass = jwtKeyStoreProperties.getPassword();
		    var keyPairAlias = jwtKeyStoreProperties.getKeypairAlias();
		    
		    var keyStoreKeyFactory = new KeyStoreKeyFactory(jwtKeyStoreProperties.getJksLocation(),
		    		keyStorePass.toCharArray());
		    
		    var keyPair = keyStoreKeyFactory.getKeyPair(keyPairAlias);
		    
		    jwtAccessTokenConverter.setKeyPair(keyPair);
		    
		    return jwtAccessTokenConverter;
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
	//	security.checkTokenAccess("isAuthenticated()");//para acessar o endpoint de check token tem que está autenticado
		security.checkTokenAccess("permitAll()")//permite acesso sem o acesso do base auth
		.tokenKeyAccess("permitAll()"); 
	}

	private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
		var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
				endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
				endpoints.getOAuth2RequestFactory());
		
		var granters = Arrays.asList(
				pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());
		
		return new CompositeTokenGranter(granters);
	}
	
}
 