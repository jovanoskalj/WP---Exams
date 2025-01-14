package mk.ukim.finki.wp.exam.example.service;

import mk.ukim.finki.wp.exam.example.model.Role;
import mk.ukim.finki.wp.exam.example.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    /**
     * (5 points) This method should create a new user. The password should be encrypted before saving.
     *
     * @param username
     * @param password
     * @param role
     * @return
     */
    User create(String username, String password, Role role);

}
