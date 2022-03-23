package com.algaworks.algafood.core.security.authorizationserver;



import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;

public class JwtCustomClaimsTokenEnhancer implements TokenEnhancer {

   /*Podemis implementar as informações que vem no payload do jwt com claims customizadas*/
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
       if(authentication.getPrincipal() instanceof  AuthUser) { //verifica se é a instacai do authuser

           var info = new HashMap<String, Object>();
           var authUser = (AuthUser) authentication.getPrincipal();

           info.put("nome_completo", authUser.getFullName());
           info.put("usuario_id", authUser.getUserId());
           var oAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
           oAuth2AccessToken.setAdditionalInformation(info);
       }
        return accessToken;
    }
}
