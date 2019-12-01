package com.future.clockio.entity.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.future.clockio.entity.constant.DocumentName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = DocumentName.USER)
public class User implements UserDetails {
  @Id
  @GeneratedValue
  private UUID id;

  @NotBlank(message = "username must not be blank")
  private String username;
  @JsonIgnore
  private String password;
  private UUID employeeId;

  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

//  @Transient
//  @JsonIgnore
//  private Collection<? extends GrantedAuthority> authorities;

  /*
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

  public User(String username, String password, @NotEmpty List<String> roles) {
    this.username = username;
    this.password = password;
    this.roles = roles;
  }
   */

  @Transient
  @JsonIgnore
  private boolean enabled = true;
  @Transient
  @JsonIgnore
  private boolean accountNonExpired = true;
  @Transient
  @JsonIgnore
  private boolean accountNonLocked = true;
  @Transient
  @JsonIgnore
  private boolean credentialsNonExpired = true;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> result =
            new ArrayList<GrantedAuthority>();
    result.add(new SimpleGrantedAuthority(this.role.getRole()));
    return result;
  }

  public User(String username, String password, Role role) {
    this.username = username;
    this.password = password;
    this.role = role;
  }
}
