package com.dans.pro.mrbro.user.payload.response;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

public class JwtV {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class JwtResponse implements Serializable {
        @Expose
        private String token;

        @Expose
        private String type = "Bearer";

        @Expose
        private Long id;

        @Expose
        private String username;

        @Expose
        private String email;

        @Expose
        private List<String> roles;

        public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
            this.token = accessToken;
            this.id = id;
            this.username = username;
            this.email = email;
            this.roles = roles;
        }
    }

}
