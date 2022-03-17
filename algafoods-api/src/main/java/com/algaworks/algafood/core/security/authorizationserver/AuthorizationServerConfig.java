package com.algaworks.algafood.core.security.authorizationserver;


import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
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

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;


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
	public JWKSet jwkSet() {
		//builder de chave , pegamos nossa chave publica dentro do nosso par de cahves keyPair
		RSAKey.Builder builder =  new RSAKey.Builder((RSAPublicKey) keyPair().getPublic())
				.keyUse(KeyUse.SIGNATURE) //chave de assinatura pq estamos usando token
				.algorithm(JWSAlgorithm.RS256) //algoritimo que estamos usando pra gerar esse token
				.keyID("algafood-key-id"); //identificação do jwk 1 chave pode ter varias
		
		return new JWKSet(builder.build()); //instancia o método jwks
	}
	
	private KeyPair keyPair() {
		//chave assimetrica
	    var keyStorePass = jwtKeyStoreProperties.getPassword();
	    var keyPairAlias = jwtKeyStoreProperties.getKeypairAlias();
	    
	    var keyStoreKeyFactory = new KeyStoreKeyFactory(jwtKeyStoreProperties.getJksLocation(),
	    		keyStorePass.toCharArray());
	    
	   return keyStoreKeyFactory.getKeyPair(keyPairAlias);
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		
		//chave simetrica
		//jwtAccessTokenConverter.setSigningKey("DB4AEF4719809709E560ED8DE2F9C77B886B963B28BA20E9A8A621BBD4ABA400");
		    
		    jwtAccessTokenConverter.setKeyPair(keyPair());
		    
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
 