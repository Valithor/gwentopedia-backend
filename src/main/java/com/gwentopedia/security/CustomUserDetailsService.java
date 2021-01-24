package com.gwentopedia.security;

import com.gwentopedia.model.User;
import com.gwentopedia.repository.UserRepository;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> 
                        new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
        );

        return UserPrincipal.create(user);
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new UsernameNotFoundException("User not found with id : " + id)
        );
        String url= "https://www.playgwent.com/en/profile/"+user.getGogName();
        try {
			Document doc = Jsoup.connect(url).get();
			Element avatar= doc.select("body > div > div > div.l-player-details > div.l-player-details__left > div.l-player-details__avatar > img").first();;
			user.setAvatar(avatar.absUrl("src"));
			if(user.getAvatar().length()==0)
			user.setAvatar("https://cdn-l-playgwent.cdprojektred.com/avatars/0-default.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        return UserPrincipal.create(user);
    }
}