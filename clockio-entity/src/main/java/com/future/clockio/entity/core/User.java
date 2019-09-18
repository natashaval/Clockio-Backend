package com.future.clockio.entity.core;

import com.future.clockio.entity.constant.DocumentName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = DocumentName.USER)
public class User implements UserDetails {
  @Id
  private String id;

  @NotBlank(message = "username must not be blank")
  private String username;
  private String password;

  @NotEmpty
  private List<String> roles = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

  public User(String username, String password, @NotEmpty List<String> roles) {
    this.username = username;
    this.password = password;
    this.roles = roles;
  }

  @Transient
  private boolean enabled = true;
  @Transient
  private boolean accountNonExpired = true;
  @Transient
  private boolean accountNonLocked = true;
  @Transient
  private boolean credentialsNonExpired = true;
  @Transient
  private Collection<? extends GrantedAuthority> authorities;
}
